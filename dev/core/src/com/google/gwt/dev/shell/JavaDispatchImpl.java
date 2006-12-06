// Copyright 2006 Google Inc. All Rights Reserved.
package com.google.gwt.dev.shell;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

/**
 * Class for wrapping Java things for JavaScript.
 */
public class JavaDispatchImpl implements JavaDispatch {

  /**
   * This constructor initializes a dispatcher around a particular instance.
   * 
   * @param ccl class loader to use for dispatching member access
   * @param target the instance object to use for dispatching member accesses
   * 
   * @throws NullPointerException if target is null
   */
  public JavaDispatchImpl(CompilingClassLoader ccl, Object target) {
    if (target == null) {
      throw new NullPointerException("target cannot be null");
    }

    classLoader = ccl;
    this.target = target;
  }

  /**
   * This constructor initializes a dispatcher for handling static members.
   * 
   * @param ccl class loader to use for dispatching member access
   */
  public JavaDispatchImpl(CompilingClassLoader ccl) {
    classLoader = ccl;
    target = null;
  }

  /**
   * @param dispId the unique number of a field
   * @return the field
   */
  public Field getField(int dispId) {
    return (Field) getMember(dispId);
  }

  /**
   * @param dispId the unique number of a field
   * @return true the value of the field
   * @throws IllegalArgumentException
   */
  public Object getFieldValue(int dispId) {
    Field field = (Field) getMember(dispId);
    try {
      return field.get(target);
    } catch (IllegalAccessException e) {
      // should never, ever happen
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * @param dispId the unique number of a method
   * @return the method
   */
  public Method getMethod(int dispId) {
    return (Method) getMember(dispId);
  }

  public Object getTarget() {
    return target;
  }

  /**
   * @param dispId the unique number of a method or field
   * @return true if the dispId represents a field
   */
  public boolean isField(int dispId) {
    if (dispId < 0) return false;
    return getMember(dispId) instanceof Field;
  }

  /**
   * @param dispId the unique number of a method or field
   * @return true if the dispId represents a method
   */
  public boolean isMethod(int dispId) {
    if (dispId < 0) return false;
    return getMember(dispId) instanceof Method;
  }

  /**
   * @param dispId the unique number of a field
   * @param value the value to assign to the field
   * @throws IllegalArgumentException
   */
  public void setFieldValue(int dispId, Object value) {
    Field field = (Field) getMember(dispId);
    try {
      field.set(target, value);
    } catch (IllegalAccessException e) {
      // should never, ever happen
      e.printStackTrace();
      throw new RuntimeException(e);
    }
  }

  /**
   * @param dispId the unique number of a method or field
   * @return the member
   */
  protected Member getMember(int dispId) {
    if (dispId == 0) {
      try {
        return Object.class.getDeclaredMethod("toString", null);
      } catch (SecurityException e) {
        // Should never get here
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        // Should never get here
        e.printStackTrace();
      }
    }
    DispatchClassInfo clsInfo = classLoader.getClassInfoByDispId(dispId);
    return clsInfo.getMember(dispId);
  }

  private final CompilingClassLoader classLoader;
  private final Object target;
}
