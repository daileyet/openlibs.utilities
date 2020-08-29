package com.openthinks.libs.utilities.websocket.helper.json;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

/**
 * 
 * ClassName: AnnotationExclusionStrategy <br>
 * Function: TODO FUNCTION description of this class. <br>
 * Reason: TODO why you add this class?(Optional). <br>
 * date: Jul 26, 2017 4:21:42 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @since JDK 1.8
 */
public class AnnotationExclusionStrategy implements ExclusionStrategy {

  @Override
  public boolean shouldSkipField(FieldAttributes f) {
    return f.getAnnotation(Exclude.class) != null;
  }

  @Override
  public boolean shouldSkipClass(Class<?> clazz) {
    return clazz.getAnnotation(Exclude.class) != null;
  }
}
