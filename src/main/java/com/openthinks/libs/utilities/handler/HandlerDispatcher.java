package com.openthinks.libs.utilities.handler;


/**
 * ClassName: HandlerDispatcher <br>
 * Function: {@link Handler}的分发器. <br>
 * Reason: 避免重复进行条件判断，分离逻辑控制和业务处理.<br>
 * date: May 9, 2018 4:27:33 PM <br>
 * <p>
 * <B>Old way to condition judge</B>:
 * 
 * <pre>
 * <code>
 * class DemoService{
 *  public void onDataReceived(CommData data){
 *     byte commandId = CommDataUtil.getCommandId(data); 
 *     if(commandId==1){
 *       // process business logic for condition 1
 *     }else if(commandId==2){
 *      // process business logic for condition 2
 *     }else if(commandId==3){
 *       byte subCommand = CommDataUtil.getContentAt(data, 0); 
 *       // process sub conditions
 *       if(subCommand==0x01){
 *           // process sub conditions business logic 1
 *       }else if(subCommand==0x02){
 *          // process sub conditions business logic 2
 *       }
 *     }else{
 *      // other condition
 *     }
 *  }
 * }
 * </code>
 * </pre>
 * </p>
 * *
 * <p>
 * <B>This(HandlerDispatcher) way to condition judge</B>:
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
 *  final Handler<CommData> topHandler3 = new Handler<>(){
 *      void process(CommData data){
 *          byte subCommand = CommDataUtil.getContentAt(data, 0); 
 *          getHandlerOrDefault(subCommand,Handler.empty()).process(data);
 *      }  
 *  }
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
 * @param <T> the data need process
 * @author dailey.dai@cn.bosch.com DAD2SZH
 * @see GroupHandler
 * @since JDK 1.8
 */
public interface HandlerDispatcher<V> {

  /**
   * 
   * load:从给定的目标对象中加载 {@link Handler}s. <br>
   * 
   * @param instance target
   */
  void load(Object instance);

  /**
   * 
   * load:从当前对象中加载 {@link Handler}s. <br>
   * 
   */
  default void load() {
    load(this);
  }

  /**
   * 
   * getHandler:获取top level的 {@link Handler}. <br>
   * 
   * @param key String
   * @return {@link Handler}
   */
  Handler<V> getHandler(String key);

  /**
   * 
   * getSubHandler:获取top level的 {@link Handler}. <br>
   * 
   * @param parentHandler {@link Handler} parent handler实例
   * @param subKey String
   * @return {@link Handler}
   */
  Handler<V> getSubHandler(Handler<V> parentHandler, String subKey);


  default Handler<V> getHandler(byte key) {
    return getHandler(String.valueOf(key));
  }

  default Handler<V> getHandler(short key) {
    return getHandler(String.valueOf(key));
  }

  default Handler<V> getHandler(int key) {
    return getHandler(String.valueOf(key));
  }

  default Handler<V> getHandlerOrDefault(String key, Handler<V> defaultHandler) {
    Handler<V> handler = getHandler(key);
    if (handler == null) {
      handler = defaultHandler;
    }
    return handler;
  }

  default Handler<V> getHandlerOrDefault(byte key, Handler<V> defaultHandler) {
    return getHandlerOrDefault(String.valueOf(key), defaultHandler);
  }

  default Handler<V> getHandlerOrDefault(short key, Handler<V> defaultHandler) {
    return getHandlerOrDefault(String.valueOf(key), defaultHandler);
  }

  default Handler<V> getHandlerOrDefault(int key, Handler<V> defaultHandler) {
    return getHandlerOrDefault(String.valueOf(key), defaultHandler);
  }

  default Handler<V> getSubHandler(Handler<V> parentHandler, byte subKey) {
    return getSubHandler(parentHandler, String.valueOf(subKey));
  }

  default Handler<V> getSubHandler(Handler<V> parentHandler, int subKey) {
    return getSubHandler(parentHandler, String.valueOf(subKey));
  }

  default Handler<V> getSubHandlerOrDefault(Handler<V> parentHandler, String subKey,
      Handler<V> defaultHandler) {
    Handler<V> handler = getSubHandler(parentHandler, subKey);
    if (handler == null) {
      handler = defaultHandler;
    }
    return handler;
  }

  default Handler<V> getSubHandlerOrDefault(Handler<V> parentHandler, byte subKey,
      Handler<V> defaultHandler) {
    return getSubHandlerOrDefault(parentHandler, String.valueOf(subKey), defaultHandler);
  }

  default Handler<V> getSubHandlerOrDefault(Handler<V> parentHandler, int subKey,
      Handler<V> defaultHandler) {
    return getSubHandlerOrDefault(parentHandler, String.valueOf(subKey), defaultHandler);
  }
}
