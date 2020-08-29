package com.openthinks.libs.utilities.handler;

/**
 * ClassName: DefaultGroupHandler <br>
 * Function: 默认的{@link GroupHandler}实现. <br>
 * date: May 29, 2018 3:06:55 PM <br>
 * 
 * @since JDK 1.8
 */
public class DefaultGroupHandler<T> implements GroupHandler<T> {
  private final HandlerDispatcher<T> dispatcher;
  private final MappedResolver<T> resolver;
  
  public DefaultGroupHandler(HandlerDispatcher<T> dispatcher, MappedResolver<T> resolver) {
    super();
    this.dispatcher = dispatcher;
    this.resolver = resolver;
  }

  @Override
  public String resolve(T data) {
    return resolver.apply(data);
  }

  @Override
  public HandlerDispatcher<T> getDispatcher() {
    return dispatcher;
  }

}
