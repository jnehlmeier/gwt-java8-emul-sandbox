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
package com.google.gwt.dev;

/**
 * About information for GWT. 
 */
public class About {

  public static final String GWT_VERSION_NUM = "${project.version}"; 

  public static final String GWT_NAME = "Google Web Toolkit"; 

  public static final String GWT_VERSION = GWT_NAME + " " + GWT_VERSION_NUM; 

  private About() {
  }
  
}
