package com.openthinks.libs.utilities.handler.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Documented
@Retention(RUNTIME)
@Target({FIELD})
/**
 * ClassName: GroupRef <br>
 * Function: 指定所属的上级 {@link Handler}. <br>
 * date: May 31, 2018 2:57:37 PM <br>
 * 
 * @author dailey.dai@cn.bosch.com DAD2SZH
 * @since JDK 1.8
 */
public @interface GroupRef {
  /**
   * 
   * name:target parent name, field name. <br>
   * 
   * @return target parent name
   */
  String name();

  /**
   * 
   * key:target parent mapped key. <br>
   * <b>Notice:</b>只有在 name不起作用时才会使用
   * 
   * @return
   */
  String key() default Mapped.NULL;
}
