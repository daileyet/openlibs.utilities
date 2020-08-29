package com.openthinks.libs.utilities.websocket.support;


/**
 * ClassName: IHander <br>
 * Function: TODO FUNCTION description of this class. <br>
 * Reason: TODO why you add this class?(Optional). <br>
 * date: Jul 26, 2017 4:22:04 PM <br>
 * 
 * @author dailey.yet@outlook.com
 * @since JDK 1.8
 */
public interface IHander<T> {
  void process(T t);
}
