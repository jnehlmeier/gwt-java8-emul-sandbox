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
package com.google.gwt.dev.js.ast;

/**
 * Represents a JavaScript binary operation.
 */
public final class JsBinaryOperation extends JsExpression {

  public JsBinaryOperation(JsBinaryOperator op) {
    this(op, null, null);
  }

  public JsBinaryOperation(JsBinaryOperator op, JsExpression arg1, JsExpression arg2) {
    this.op = op;
    this.arg1 = arg1;
    this.arg2 = arg2;
  }
  
  public void traverse(JsVisitor v) {
    if (v.visit(this)) {
      arg1.traverse(v);
      arg2.traverse(v);
    }
    v.endVisit(this);
  }

  public JsExpression getArg1() {
    return arg1;
  }

  public JsExpression getArg2() {
    return arg2;
  }

  public JsBinaryOperator getOperator() {
    return op;
  }

  public void setArg1(JsExpression arg1) {
    this.arg1 = arg1;
  }

  public void setArg2(JsExpression arg2) {
    this.arg2 = arg2;
  }

  private JsExpression arg1;
  private JsExpression arg2;
  private final JsBinaryOperator op;
}
