/**
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 *
 * @Title: Versions.java
 * @author dailey.yet@outlook.com
 * @date Aug 20, 2015
 * @version V1.0
 */
package com.openthinks.libs.utilities;

import com.openthinks.libs.utilities.logger.ProcessLogger;
import com.openthinks.libs.utilities.logger.RootLoggerManager;
import com.openthinks.libs.utilities.version.AppVersion;
import com.openthinks.libs.utilities.version.VersionCenter;

/**
 * openthinks.libs.utilities version class
 * 
 * @author dailey.yet@outlook.com
 *
 */
@AppVersion("1.2.2.4")
public class Versions extends VersionCenter {
  /**
   * 
   * @change
   *         <ul>
   *         <li>add demo package</li>
   *         <li>enhance logger log level setting from system property</li>
   *         <li>enhance logger with entry name and support customized style formatter</li>
   *         </ul>
   */
  String v_1_2_2_4;
  /**
   * 
   * @change
   *         <ul>
   *         <li>modify logger, use {@link ConsoleImpl} when {@link RootLoggerManager#implMap} is
   *         empty</li>
   *         <li>add test</li>
   *         <li>add @released document annotation</li>
   *         </ul>
   */
  String v_1_2_2_3;
  /**
   * @released
   * @change
   *         <ul>
   *         <li>enhance logger</li>
   *         </ul>
   */
  String v_1_2_2_2;
  /**
   * @released
   * @change
   *         <ul>
   *         <li>fix issue on com.openthinks.libs.utilities.json.support.JSONFinder for special
   *         token "\\u" and "\/"</li>
   *         <li>enhance StringUtils</li>
   *         <li>enhance com.openthinks.libs.utilities.json.JSONConverters, support condition token
   *         ?</li>
   *         <li>enhance com.openthinks.libs.utilities.json.support.JSONElement, support null
   *         value</li>
   *         </ul>
   */
  String v_1_2_2_1;
  /**
   * @released
   * @change
   *         <ul>
   *         <li>add package com.openthinks.libs.utilities.handler</li>
   *         <li>add package com.openthinks.libs.utilities.net</li>
   *         <li>add {@link DateUtils}</li>
   *         <li>add {@link NIOByteUtils}</li>
   *         <li>Modify package com.openthinks.libs.utilities.collection</li>
   *         <li>add trace level in {@link ProcessLogger}</li>
   *         </ul>
   */
  String v_1_2_2;
  /**
   * @released
   * @change
   *         <ul>
   *         <li>add package com.openthinks.libs.utilities.json</li>
   *         <li>remove package com.openthinks.libs.utilities.websocket</li>
   *         <li>enhance com.openthinks.libs.utilities.lookup.LookupPool</li>
   *         <li>rename LookupPools to Lookups</li>
   *         </ul>
   */
  String v_1_2_1;
  /**
   * @released
   * @change
   *         <ul>
   *         <li>Add package com.openthinks.libs.utilities.websocket</li>
   *         <li>Add package com.openthinks.libs.utilities.lookup</li>
   *         <li>Modify {@link ObjectPool}</li>
   *         <li>Modify {@link ProcessLogger}</li>
   *         </ul>
   */
  String v_1_2;
  /**
   * @released
   * @change
   *         <ul>
   *         <li>Add {@link ObjectPool} and {@link SharedContext}</li>
   *         </ul>
   */
  String v_1_1;
  /**
   * @released
   * @base
   */
  String v_1_0;
}
