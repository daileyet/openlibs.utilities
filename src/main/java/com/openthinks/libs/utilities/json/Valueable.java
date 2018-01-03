/**
 * 
 */
package com.openthinks.libs.utilities.json;

/**
 * used for {@link JSONObject#addProperty(String, Object)}, if the property value is implementation
 * of this interface, then {@link #value()} will be replaced as real value
 * 
 * @author dailey.yet@outlook.com
 *
 */
public interface Valueable<T> {

  T value();
}


