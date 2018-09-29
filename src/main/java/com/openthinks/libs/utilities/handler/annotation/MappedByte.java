package com.openthinks.libs.utilities.handler.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target(FIELD)
/**
 * ClassName: MappedByte <br>
 * Function: 标注最小业务处理单元,key为byte. <br>
 * Reason: 通过该注解进行扫描和识别 Handler. <br>
 * date: May 29, 2018 4:21:49 PM <br>
 * 
 * @author dailey.dai@cn.bosch.com DAD2SZH
 * @since JDK 1.8
 */
public @interface MappedByte {
  public static final byte NULL = Byte.MIN_VALUE;

  /**
   * 绑定key值
   */
  byte value();

}
