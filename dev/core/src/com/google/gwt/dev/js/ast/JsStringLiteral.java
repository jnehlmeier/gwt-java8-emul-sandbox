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
 * A JavaScript string literal expression.
 */
public final class JsStringLiteral extends JsExpression {

  // These only get created by JsProgram so that they can be interned.
  JsStringLiteral(String value) {
    this.value = value;
  }
  
  public boolean isLeaf() {
    return true;
  }

  public String getValue() {
    return value;
  }

  public void traverse(JsVisitor v) {
    v.visit(this);
    v.endVisit(this);
  }

  private final String value;
}
