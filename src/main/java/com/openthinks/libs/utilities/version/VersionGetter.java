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
* @Title: VersionGetter.java 
* @Package openthinks.libs.utilities.version
* @author dailey.yet@outlook.com  
* @date 2015/7/22
* @version V1.0   
*/
package com.openthinks.libs.utilities.version;

/**
 * Version {@link AppVersion} getter helper 
 * @author dailey.yet@outlook.com
 * @since v1.0
 */
public final class VersionGetter {
	public final static VersionGetter valueOf(Class<?> appClz) {
		return new VersionGetter(appClz);
	}

	private Class<?> appClz;

	private VersionGetter(Class<?> appClz) {
		this.appClz = appClz;
	}

	public final String get() {
		AppVersion appVersion = appClz.getAnnotation(AppVersion.class);
		if (appVersion != null) {
			return appVersion.value();
		}
		return "1.0";
	}

	public final String get(String prefix) {
		return prefix + "" + get();
	}
}