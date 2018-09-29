package com.openthinks.libs.utilities.handler.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * ClassName: Mapped <br>
 * Function: 标注最小业务处理单元. <br>
 * Reason: 通过该注解进行扫描和识别 Handler. <br>
 * date: May 9, 2018 4:19:58 PM <br>
 * 
 * @author dailey.dai@cn.bosch.com DAD2SZH
 * @since JDK 1.8
 */
public @interface Mapped {
  public static final String NULL = "";

  /**
   * 绑定key值
   */
  String value();

}
