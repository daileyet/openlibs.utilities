package com.openthinks.libs.utilities.net;

/**
 * ClassName: Lifecycle is the top level interface for all clients and servers<br>
 * date: 2017-6-1 下午3:31:48 <br>
 * 
 * @since JDK 1.8
 */
public interface Lifecycle {
  /**
   * 
   * start: start the client<br>
   */
  void start();

  /**
   * stop the client
   */
  void stop();

  /**
   * shrink the memory usage for low power model
   */
  void shrink();

  /**
   * expand the client memory usage for high power suppliment enabled
   */
  void expand();
}
