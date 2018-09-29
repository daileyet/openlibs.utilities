package com.openthinks.libs.utilities.handler;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * ClassName: GroupHandler <br>
 * Function: 父层的{@link Handler}，没有具体的业务，只负责路由到具体的业务{@link Handler}和调用. <br>
 * date: May 29, 2018 2:30:36 PM <br>
 * 
 * <p>
 * <B>This(HandlerDispatcher) way by GroupHandler to condition judge</B>:
 * 
 * <pre>
 * <code>
 * class DemoService extends AdvanceHandlerDispatcher<CommData>{
 * 
 *  public void onDataReceived(CommData data){
 *     byte commandId = CommDataUtil.getCommandId(data); 
 *     getHandlerOrDefault(commandId,Handler.empty()).process(data);
 *  }
 *  &#64;MappedByte(1)
 *  final Handler<CommData> topHandler1 = (data)->{};
 *  &#64;MappedByte(2)
 *  final Handler<CommData> topHandler2 = (data)->{};
 *  &#64;MappedByte(3)
 *  final GroupHandler<CommData> topHandler3 = GroupHandler.buildByte(this,(data) -> (CommDataUtil.getContentAt(data, 0) ));
 *  &#64;GroupRef("topHandler3")
 *  &#64;MappedByte(1)
 *  final Handler<CommData> topHandler1 = (data)->{};    
 *  &#64;GroupRef("topHandler3")
 *  &#64;MappedByte(2)
 *  final Handler<CommData> topHandler1 = (data)->{};
 *  
 * }
 * </code>
 * </pre>
 * </p>
 * 
 * @author dailey.dai@cn.bosch.com DAD2SZH
 * @since JDK 1.8
 */
public interface GroupHandler<T> extends Handler<T> {

  /**
   * 
   * resolve: 从处理的对象中获取条件值，以便映射到具体的业务{@link Handler}. <br>
   * 
   * @author dailey.dai@cn.bosch.com DAD2SZH
   * @param data 带有条件值的待处理数据
   * @return String
   */
  public String resolve(T data);

  /**
   * 
   * getExecutor:获取实例{@link HandlerDispatcher}. <br>
   * 
   * @return {@link HandlerDispatcher}
   */
  public HandlerDispatcher<T> getDispatcher();

  @Override
  default void process(T data) {
    getDispatcher().getSubHandlerOrDefault(this, resolve(data), Handler.empty()).process(data);
  }

  /**
   * 
   * ClassName: MappedKeyResolver <br>
   * Function: 映射key函数. <br>
   * date: May 29, 2018 3:15:10 PM <br>
   * 
   * @author dailey.dai@cn.bosch.com DAD2SZH
   */
  public interface MappedResolver<T> extends Function<T, String> {
  }

  public interface MappedByteResolver<T> extends Function<T, Byte> {
  }

  public interface MappedIntResolver<T> extends Function<T, Integer> {
  }

  /**
   * 
   * build:创建{@link GroupHandler}实例. <br>
   * 注意：在传递executor时注意是否为空及初始化顺序.<br>
   * 
   * @author dailey.dai@cn.bosch.com DAD2SZH
   * @param executor {@link HandlerDispatcher}
   * @param resolver {@link MappedResolver}
   * @return {@link GroupHandler}
   */
  public static <T> GroupHandler<T> build(final HandlerDispatcher<T> executor,
      final MappedResolver<T> resolver) {
    final GroupHandler<T> handler = new GroupHandler<T>() {

      @Override
      public String resolve(T data) {
        return resolver.apply(data);
      }

      @Override
      public HandlerDispatcher<T> getDispatcher() {
        return executor;
      }
    };
    return handler;
  }

  public static <T> GroupHandler<T> buildByte(final HandlerDispatcher<T> executor,
      final MappedByteResolver<T> resolver) {
    final GroupHandler<T> handler = new GroupHandler<T>() {

      @Override
      public String resolve(T data) {
        return String.valueOf(resolver.apply(data));
      }

      @Override
      public HandlerDispatcher<T> getDispatcher() {
        return executor;
      }
    };
    return handler;
  }

  public static <T> GroupHandler<T> buildInt(final HandlerDispatcher<T> executor,
      final MappedIntResolver<T> resolver) {
    final GroupHandler<T> handler = new GroupHandler<T>() {

      @Override
      public String resolve(T data) {
        return String.valueOf(resolver.apply(data));
      }

      @Override
      public HandlerDispatcher<T> getDispatcher() {
        return executor;
      }
    };
    return handler;
  }

  public static <T> GroupHandler<T> build(final Supplier<HandlerDispatcher<T>> dispatcherSupplier,
      final MappedResolver<T> resolver) {
    final GroupHandler<T> handler = new GroupHandler<T>() {

      @Override
      public String resolve(T data) {
        return resolver.apply(data);
      }

      @Override
      public HandlerDispatcher<T> getDispatcher() {
        return dispatcherSupplier.get();
      }
    };
    return handler;
  }

  public static <T> GroupHandler<T> buildByte(
      final Supplier<HandlerDispatcher<T>> dispatcherSupplier,
      final MappedByteResolver<T> resolver) {
    final GroupHandler<T> handler = new GroupHandler<T>() {

      @Override
      public String resolve(T data) {
        return String.valueOf(resolver.apply(data));
      }

      @Override
      public HandlerDispatcher<T> getDispatcher() {
        return dispatcherSupplier.get();
      }
    };
    return handler;
  }


  public static <T> GroupHandler<T> buildInt(
      final Supplier<HandlerDispatcher<T>> dispatcherSupplier,
      final MappedIntResolver<T> resolver) {
    final GroupHandler<T> handler = new GroupHandler<T>() {

      @Override
      public String resolve(T data) {
        return String.valueOf(resolver.apply(data));
      }

      @Override
      public HandlerDispatcher<T> getDispatcher() {
        return dispatcherSupplier.get();
      }
    };
    return handler;
  }


}
