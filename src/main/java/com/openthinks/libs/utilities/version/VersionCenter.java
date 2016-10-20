/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
* @Title: VersionCenter.java 
* @Package openthinks.libs.utilities.version 
* @Description: TODO
* @author dailey.yet@outlook.com  
* @date Aug 20, 2015
* @version V1.0   
*/
package com.openthinks.libs.utilities.version;

/**
 * This is make the carrier for {@link AppVersion}
 * @author dailey.yet@outlook.com
 * @version 2015-08-20
 * @see VersionGetter
 */
public abstract class VersionCenter {

	/**
	 * get current version number
	 * @return String
	 */
	public String get() {
		return VersionGetter.valueOf(this.getClass()).get();
	}

	/**
	 * get current version number with prefix
	 * @param prefix String
	 * @return String
	 */
	public String get(String prefix) {
		return VersionGetter.valueOf(this.getClass()).get(prefix);
	}
}
