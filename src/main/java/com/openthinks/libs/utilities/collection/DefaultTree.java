/**
 * 
 */
package com.openthinks.libs.utilities.collection;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class DefaultTree<T> implements Tree<T> {
	  private static final long serialVersionUID = -3623645850736542520L;
	  private TreeNode<T> root;

	  public DefaultTree(TreeNode<T> root) {
	    super();
	    this.root = root;
	  }

	  @Override
	  public int depth() {
	    // TODO
	    return 0;
	  }

	  @Override
	  public int size() {
	    final AtomicInteger rst = new AtomicInteger(0);
	    traverse((node) -> {
	      rst.incrementAndGet();
	      return false;
	    });
	    return rst.get();
	  }

	  @Override
	  public void traverse(TreeFilter<T> filter) {
	    if (root == null)
	      return;
	    search(root, filter);
	  }

	  /**
	   * 
	   * search:递归遍历树节点,根据给定的{@link TreeFilter}来决定是否遍历结束. <br>
	   * 
	   * @param node {@link TreeNode} 开始遍历的节点
	   * @param filter {@link TreeFilter}
	   * @return true or false 是否遍历到目标节点
	   */
	  private boolean search(TreeNode<T> node, TreeFilter<T> filter) {
	    if (filter.test(node)) {
	      return true;
	    } else {
	      for (TreeNode<T> child : node.getChildren()) {
	        if (search(child, filter) == true) {
	          return true;
	        }
	      }
	      return false;
	    }

	  }

	}
