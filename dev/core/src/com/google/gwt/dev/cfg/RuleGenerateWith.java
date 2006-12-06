/*
 * Copyright 2006 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.google.gwt.dev.cfg;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;

public class RuleGenerateWith extends Rule {

  public RuleGenerateWith(Generator generator) {
    this.generator = generator;
  }

  public String realize(TreeLogger logger, GeneratorContext context,
      String typeName) throws UnableToCompleteException {

    String msg = "Invoking " + toString();
    logger = logger.branch(TreeLogger.DEBUG, msg, null);

    long before = System.currentTimeMillis();
    String className = generator.generate(logger, context, typeName);
    long after = System.currentTimeMillis();
    if (className == null) {
      msg = "Generator returned null, so the requested type will be used as is";
    } else {
      msg = "Generator returned class '" + className + "'";
    }
    logger.log(TreeLogger.DEBUG, msg, null);

    msg = "Finished in " + (after - before) + " ms";
    logger.log(TreeLogger.DEBUG, msg, null);

    return className;
  }

  public String toString() {
    return "<generate-with class='" + generator.getClass().getName() + "'/>";
  }

  private final Generator generator;
}
