package com.openthinks.libs.utilities.handler;

/**
 * ClassName: Handler <br>
 * Function: 逻辑分发处理的最小业务段. <br>
 * Reason: 避免重复进行条件判断，分离逻辑控制和业务处理. <br>
 * date: May 9, 2018 4:18:27 PM <br>
 * 
 * @param <T> the data need process
 * @see GroupHandler
 * @since JDK 1.8
 */
public interface Handler<T> {

  void process(T data);

  public static <E> Handler<E> empty() {
    return (data) -> {
    };
  }

}
