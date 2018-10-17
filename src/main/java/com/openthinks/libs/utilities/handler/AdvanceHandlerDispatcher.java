package com.openthinks.libs.utilities.handler;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

import com.openthinks.libs.utilities.Checker;
import com.openthinks.libs.utilities.Result;
import com.openthinks.libs.utilities.collection.DefaultTree;
import com.openthinks.libs.utilities.collection.Tree;
import com.openthinks.libs.utilities.collection.TreeFilter;
import com.openthinks.libs.utilities.collection.TreeNode;
import com.openthinks.libs.utilities.handler.annotation.GroupRef;
import com.openthinks.libs.utilities.handler.annotation.Mapped;
import com.openthinks.libs.utilities.handler.annotation.MappedByte;
import com.openthinks.libs.utilities.handler.annotation.MappedInt;
import com.openthinks.libs.utilities.handler.annotation.MappedShort;
import com.openthinks.libs.utilities.logger.ProcessLogger;

/**
 * ClassName: AdvanceHandlerDispatcher <br>
 * Function: without hierarchy limitation, could dispatch more than 3 level. <br>
 * date: May 31, 2018 3:40:11 PM <br>
 * 
 * @since JDK 1.8
 */
public class AdvanceHandlerDispatcher<V> implements HandlerDispatcher<V> {
  private final Tree<NodeData> tree;
  private final TreeNode<NodeData> root;
  // 顶层映射 键值 <-> Handler
  private final Map<String, Handler<V>> topMap;
  private final Map<Handler<V>, TreeNode<NodeData>> nodeMap;

  public AdvanceHandlerDispatcher() {
    root = new TreeNode<>(new NodeData(Handler.empty(), Mapped.NULL));
    tree = new DefaultTree<>(root);
    topMap = new ConcurrentHashMap<>();
    nodeMap = new ConcurrentHashMap<>();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void load(final Object instance) {
    Checker.require(instance).notNull();
    // MappedWith
    // GroupRef
    // process fields in given object
    Class<?> instanceClazz = instance.getClass();
    Field[] fields = instanceClazz.getDeclaredFields();
    List<Field> topHandlerFields = new LinkedList<>();
    List<Field> subHandlerFields = new LinkedList<>();

    for (Field field : fields) {
      String key = extactMappedKey(field);
      if (key == null)
        continue;
      // is annotation Mapped/MappedByte/MappedInt
      Class<?> fieldType = field.getType();
      // check field type is extends from express.util.handler.Handler
      if (!Handler.class.isAssignableFrom(fieldType))
        continue;
      GroupRef groupRef = field.getAnnotation(GroupRef.class);
      if (groupRef == null)// top level handler
        topHandlerFields.add(field);
      else
        subHandlerFields.add(field);
    }

    topHandlerFields.forEach(field -> {
      String key = extactMappedKey(field);
      try {
        field.setAccessible(true);
        Handler<V> handler = (Handler<V>) field.get(instance);
        TreeNode<NodeData> treeNode = new TreeNode<>(root, new NodeData(handler, key));
        topMap.put(key, handler);
        nodeMap.put(handler, treeNode);
      } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
        e.printStackTrace();
        ProcessLogger.warn("skip this handler field:{0} for reason:{1}", field, e);
      }
    });


    // process sub fields
    List<Field> processed = new LinkedList<>();
    while (processed.size() < subHandlerFields.size()) {
      subHandlerFields.stream().filter(field -> !processed.contains(field)).forEach(field -> {
        final GroupRef groupRef = field.getAnnotation(GroupRef.class);
        Field groupField = null;
        try {
          groupField = instanceClazz.getDeclaredField(groupRef.name());
        } catch (NoSuchFieldException e) {
          ProcessLogger.warn("Failed to get field:{0} for reason:{1}", field, e);
        }
        if (groupField != null) {
          findGroupWithName(instance, processed, field, groupField);
        } else {
          findGroupWithKey(instance, processed, field, groupRef);
        }

      });
    }
  }

  /**
   * findGroupWithKey:通过{@link GroupRef#key()}中的值来查找Handler.该方法只有在通过{@link GroupRef#name()}的字段名称来查找Handler.
   * <br>
   * 
   * @param instance target object
   * @param processed list of processed {@link Field}
   * @param childField {@link Field}
   * @param groupRef {@link GroupRef}
   */
  @SuppressWarnings("unchecked")
  private void findGroupWithKey(final Object instance, List<Field> processed, Field childField,
      final GroupRef groupRef) {
    final String key = extactMappedKey(childField);
    String groupKey = groupRef.key();
    if (Mapped.NULL == groupKey || Mapped.NULL.equals(groupKey)) {
      processed.add(childField);
      return;
    }
    ProcessLogger.trace("Try to find group handler use key:{}", groupKey);
    final Handler<V> groupHandler = topMap.get(groupKey);
    if (groupHandler == null) {
      processed.add(childField);
      return;
    }
    final TreeNode<NodeData> groupTreeNode = nodeMap.get(groupHandler);
    if (groupTreeNode == null) {
      ProcessLogger.trace(
          "Not support this field:{} this time for could not get parent handler from field key:{}",
          childField, groupKey);
      processed.add(childField);
      return;
    }
    // 父级是top level的节点或者已经创建到的中间节点
    Handler<V> handler = null;
    try {
      childField.setAccessible(true);
      handler = (Handler<V>) childField.get(instance);
      TreeNode<NodeData> childNode = new TreeNode<>(groupTreeNode, new NodeData(handler, key));
      nodeMap.put(handler, childNode);
      processed.add(childField);
    } catch (IllegalArgumentException | IllegalAccessException e) {
    	ProcessLogger.warn("Mark this sub handler field:{0} as processed for reason:{1}", childField, e);
      processed.add(childField);
    }

  }

  /**
   * findGroupWithName:通过{@link GroupRef#name()}的字段名称来查找Handler <br>
   */
  @SuppressWarnings("unchecked")
  private void findGroupWithName(final Object instance, List<Field> processed, Field childField,
      Field groupField) {
    final String key = extactMappedKey(childField);
    try {
      groupField.setAccessible(true);
      final Handler<V> groupHandler = (Handler<V>) groupField.get(instance);
      final TreeNode<NodeData> groupTreeNode = nodeMap.get(groupHandler);
      if (groupTreeNode != null) {// 父级是top level的节点或者已经创建到的中间节点
        childField.setAccessible(true);
        Handler<V> handler = (Handler<V>) childField.get(instance);
        TreeNode<NodeData> childNode = new TreeNode<>(groupTreeNode, new NodeData(handler, key));
        nodeMap.put(handler, childNode);
        processed.add(childField);
      } else {// 父级是不是top level的节点或者没有创建到的中间节点
        // 不处理，不标志
        ProcessLogger.trace(
            "Not process this field:{} this time for could not get parent handler from field name:{}",
            childField, groupField);
      }
    } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) { // 异常失败，也标志为处理
    	ProcessLogger.warn("Mark this sub handler field:{} as processed for reason:{}", childField, e);
      processed.add(childField);
    }
  }

  /**
   * 
   * extactMappedKey:从注解中抽取映射的key值. <br>
   * 
   * @param accessible {@link AccessibleObject}
   * @return String key值
   */
  private String extactMappedKey(AccessibleObject accessible) {
    Mapped mapped = accessible.getAnnotation(Mapped.class);
    String key = null;
    if (mapped != null) {
      key = mapped.value();
    } else {
      MappedByte mappedByte = accessible.getAnnotation(MappedByte.class);
      if (mappedByte != null) {
        key = String.valueOf(mappedByte.value());
      } else {
        MappedShort mappedShort = accessible.getAnnotation(MappedShort.class);
        if (mappedShort != null) {
          key = String.valueOf(mappedShort.value());
        } else {
          MappedInt mappedInt = accessible.getAnnotation(MappedInt.class);
          if (mappedInt != null) {
            key = String.valueOf(mappedInt.value());
          }
        }
      }
    }
    return key;
  }


  @Override
  public Handler<V> getHandler(String key) {
    Checker.require(key).notNull();
    Handler<V> handler = topMap.get(key);
    // if (handler != null)
    return handler;
    /*
     * final Result<Handler<V>> rst = new Result<>(); tree.traverse((node) -> { final NodeData data
     * = node.getData(); if (data != null && data.accept(key)) { rst.set(data.handler);
     * topMap.put(key, data.handler); return true; } return false; }); return rst.get();
     */
  }

  @Override
  public Handler<V> getSubHandler(Handler<V> parentHandler, String subKey) {
    Checker.require(parentHandler).notNull();
    TreeNode<NodeData> treeNode = nodeMap.get(parentHandler);
    final Result<TreeNode<NodeData>> rst = new Result<>();
    final Predicate<TreeNode<NodeData>> SUBHANDLER_PREDICATE = (childNode) -> {
      final NodeData childData = childNode.getData();
      if (childData != null && childData.accept(subKey)) {
        return true;
      }
      return false;
    };
    if (treeNode != null) {
      treeNode.getChildren().stream().filter(SUBHANDLER_PREDICATE).findFirst().ifPresent(rst::set);
    } else {
      final TreeFilter<NodeData> TREENODE_FILTER = (node) -> {
        final NodeData data = node.getData();
        if (data != null && data.accept(parentHandler)) {
          node.getChildren().stream().filter(SUBHANDLER_PREDICATE).findFirst().ifPresent(rst::set);
          nodeMap.put(parentHandler, node);
          return true;
        }
        return false;
      };
      tree.traverse(TREENODE_FILTER);
    }
    return rst.isNull() ? null : rst.get().getData().handler;
  }

  private class NodeData {
    final Handler<V> handler;
    final String key;

    NodeData(Handler<V> handler, String key) {
      this.handler = handler;
      this.key = key;
    }

    boolean accept(String kez) {
      return this.key != null && this.key.equals(kez);
    }

    boolean accept(Handler<V> handler) {
      return this.handler != null && (this.handler == handler || this.handler.equals(handler));
    }
  }

}
