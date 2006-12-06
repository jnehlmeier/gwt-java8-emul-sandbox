// Copyright 2006 Google Inc. All Rights Reserved.
package com.google.gwt.dev.shell.tomcat;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.util.FileOracle;
import com.google.gwt.dev.util.FileOracleFactory;
import com.google.gwt.util.tools.Utility;

import org.apache.catalina.Connector;
import org.apache.catalina.ContainerEvent;
import org.apache.catalina.ContainerListener;
import org.apache.catalina.Engine;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Logger;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Embedded;
import org.apache.catalina.startup.HostConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URL;

public class EmbeddedTomcatServer {

  static EmbeddedTomcatServer sTomcat;

  public synchronized static String start(TreeLogger topLogger, int port,
      File outDir) {
    if (sTomcat != null) {
      throw new IllegalStateException("Embedded Tomcat is already running");
    }

    try {
      new EmbeddedTomcatServer(topLogger, port, outDir);
      sTomcat.fCatEmbedded.start();
      return null;
    } catch (LifecycleException e) {
      String msg = e.getMessage();
      if (msg != null && msg.indexOf("already in use") != -1) {
        msg = "Port "
          + port
          + " is already is use; you probably still have another session active";
      } else {
        msg = "Unable to start the embedded Tomcat server; double-check that your configuration is valid";
      }
      return msg;
    }
  }

  // Stop the embedded Tomcat server.
  //
  public synchronized static void stop() {
    if (sTomcat != null) {
      try {
        sTomcat.fCatEmbedded.stop();
      } catch (LifecycleException e) {
        // There's nothing we can really do about this and the logger is
        // gone in many scenarios, so we just ignore it.
        //
      } finally {
        sTomcat = null;
      }
    }
  }

  private EmbeddedTomcatServer(final TreeLogger topLogger, int listeningPort,
      final File outDir) {
    if (topLogger == null) {
      throw new NullPointerException("No logger specified");
    }

    final TreeLogger logger = topLogger.branch(TreeLogger.INFO,
      "Starting HTTP on port " + listeningPort, null);

    fStartupBranchLogger = logger;

    // Make myself the one static instance.
    // NOTE: there is only one small implementation reason that this has
    // to be a singleton, which is that the commons logger LogFactory insists
    // on creating your logger class which must have a constructor with
    // exactly one String argument, and since we want LoggerAdapter to delegate
    // to the logger instance available through instance host, there is no
    // way I can think of to delegate without accessing a static field.
    // An inner class is almost right, except there's no outer instance.
    //
    sTomcat = this;

    // Set the port, which is used in the creation of catalina.home.
    //
    fPort = listeningPort;

    // Assume the working directory is simply the user's current directory.
    //
    File topWorkDir = new File(System.getProperty("user.dir"));

    // Tell Tomcat its base directory so that it won't complain.
    //
    String catBase = System.getProperty("catalina.base");
    if (catBase == null) {
      catBase = generateDefaultCatalinaBase(logger, topWorkDir);
      System.setProperty("catalina.base", catBase);
    }

    // Some debug messages for ourselves.
    //
    logger.log(TreeLogger.DEBUG, "catalina.base = " + catBase, null);

    // Set up the logger that will be returned by the Commons logging factory.
    //
    String adapterClassName = CommonsLoggerAdapter.class.getName();
    System.setProperty("org.apache.commons.logging.Log", adapterClassName);

    // And set up an adapter that will work with the Catalina logger family.
    //
    Logger catalinaLogger = new CatalinaLoggerAdapter(topLogger);

    // Create an embedded server.
    //
    fCatEmbedded = new Embedded();
    fCatEmbedded.setDebug(0);
    fCatEmbedded.setLogger(catalinaLogger);

    // The embedded engine is called "gwt".
    //
    fCatEngine = fCatEmbedded.createEngine();
    fCatEngine.setName("gwt");
    fCatEngine.setDefaultHost("localhost");

    // It answers localhost requests.
    //
    // String appBase = fCatalinaBaseDir.getAbsolutePath();
    String appBase = catBase + "/webapps";
    fCatHost = (StandardHost) fCatEmbedded.createHost("localhost", appBase);

    // Hook up a host config to search for and pull in webapps.
    //
    HostConfig hostConfig = new HostConfig();
    fCatHost.addLifecycleListener(hostConfig);

    // Hook pre-install events so that we can add attributes to allow loaded
    // instances to find their development instance host.
    //
    fCatHost.addContainerListener(new ContainerListener() {
      public void containerEvent(ContainerEvent event) {
        if (StandardHost.PRE_INSTALL_EVENT.equals(event.getType())) {
          StandardContext webapp = (StandardContext) event.getData();
          publishShellLoggerAttribute(logger, topLogger, webapp);
          publishShellOutDirAttribute(logger, outDir, webapp);
        }
      }
    });

    // Tell the engine about the host.
    //
    fCatEngine.addChild(fCatHost);
    fCatEngine.setDefaultHost(fCatHost.getName());

    // Tell the embedded manager about the engine.
    //
    fCatEmbedded.addEngine(fCatEngine);
    InetAddress nullAddr = null;
    Connector connector = fCatEmbedded.createConnector(nullAddr, fPort, false);
    fCatEmbedded.addConnector(connector);
  }

  /**
   * Extracts a valid catalina base instance from the classpath. Does not
   * overwrite any existing files.
   */
  private String generateDefaultCatalinaBase(TreeLogger logger, File workDir) {
    logger = logger
      .branch(
        TreeLogger.TRACE,
        "Property 'catalina.base' not specified; checking for a standard catalina base image instead",
        null);

    // Recursively copies out files and directories under
    // com.google.gwt.dev.etc.tomcat.
    //
    FileOracleFactory fof = new FileOracleFactory();
    final String TOMCAT_ETC_DIR = "com/google/gwt/dev/etc/tomcat/";
    fof.addRootPackage(TOMCAT_ETC_DIR, null);
    FileOracle fo = fof.create(logger);
    if (fo.isEmpty()) {
      logger.log(TreeLogger.WARN, "Could not find " + TOMCAT_ETC_DIR, null);
      return null;
    }

    File catBase = new File(workDir, "tomcat");
    String[] allChildren = fo.getAllFiles();
    for (int i = 0; i < allChildren.length; i++) {
      String src = allChildren[i];
      copyFileNoOverwrite(logger, fo, src, catBase);
    }

    return catBase.getAbsolutePath();
  }

  /*
   * Assumes that the leaf is a file (not a directory).
   */
  private void copyFileNoOverwrite(TreeLogger logger, FileOracle fileOracle,
      String srcResName, File catBase) {

    File dest = new File(catBase, srcResName);
    InputStream is = null;
    FileOutputStream os = null;
    try {
      URL srcRes = fileOracle.find(srcResName);
      if (srcRes == null) {
        logger.log(TreeLogger.TRACE, "Cannot find: " + srcResName, null);
        return;
      }

      // Only copy if src is newer than desc.
      //
      long srcLastModified = srcRes.openConnection().getLastModified();
      long dstLastModified = dest.lastModified();

      if (srcLastModified < dstLastModified) {
        // Don't copy over it.
        //
        logger.log(TreeLogger.TRACE, "Source is older than existing: "
          + dest.getAbsolutePath(), null);
        return;
      } else if (srcLastModified == dstLastModified) {
        // Exact same time; quietly don't overwrite.
        //
        return;
      }

      // Make dest directories as required.
      //
      File destParent = dest.getParentFile();
      if (destParent != null) {
        // No need to check mkdirs result because IOException later anyway.
        destParent.mkdirs();
      }

      // Open in and out streams.
      //
      is = srcRes.openStream();
      os = new FileOutputStream(dest);

      // Copy the bytes over.
      //
      Utility.streamOut(is, os, 1024);

      // Try to close and change the last modified time.
      //
      Utility.close(os);
      dest.setLastModified(srcLastModified);

      logger.log(TreeLogger.TRACE, "Wrote: " + dest.getAbsolutePath(), null);
    } catch (IOException e) {
      logger.log(TreeLogger.WARN, "Failed to write: " + dest.getAbsolutePath(),
        e);
    } finally {
      Utility.close(is);
      Utility.close(os);
    }
  }

  private void publishAttributeToWebApp(TreeLogger logger,
      StandardContext webapp, String attrName, Object attrValue) {
    logger.log(TreeLogger.TRACE, "Adding attribute  '" + attrName
      + "' to web app '" + webapp.getName() + "'", null);
    webapp.getServletContext().setAttribute(attrName, attrValue);
  }

  /**
   * Publish the shell's tree logger as an attribute. This attribute is used to
   * find the logger out of the thin air within the shell servlet.
   */
  private void publishShellLoggerAttribute(TreeLogger logger,
      TreeLogger loggerToPublish, StandardContext webapp) {
    final String ATTR = "com.google.gwt.dev.shell.logger";
    publishAttributeToWebApp(logger, webapp, ATTR, loggerToPublish);
  }

  /**
   * Publish the shell's output dir as an attribute. This attribute is used to
   * find it out of the thin air within the shell servlet.
   */
  private void publishShellOutDirAttribute(TreeLogger logger,
      File outDirToPublish, StandardContext webapp) {
    final String ATTR = "com.google.gwt.dev.shell.outdir";
    publishAttributeToWebApp(logger, webapp, ATTR, outDirToPublish);
  }

  public TreeLogger getLogger() {
    return fStartupBranchLogger;
  }

  private Embedded fCatEmbedded;
  private Engine fCatEngine;
  private StandardHost fCatHost = null;
  private final int fPort;
  private final TreeLogger fStartupBranchLogger;
}
