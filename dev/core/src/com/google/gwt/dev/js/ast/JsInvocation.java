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
 * Reprents a JavaScript invocation.
 */
public final class JsInvocation extends JsExpression implements HasArguments {

  public JsInvocation() {
  }

  public JsExpressions getArguments() {
    return args;
  }

  public JsExpression getQualifier() {
    return qualifier;
  }

  public void setQualifier(JsExpression qualifier) {
    this.qualifier = qualifier;
  }

  public void traverse(JsVisitor v) {
    if (v.visit(this)) {
      qualifier.traverse(v);
      args.traverse(v);
    }
    v.endVisit(this);
  }

  private final JsExpressions args = new JsExpressions();
  private JsExpression qualifier;
}
