/**
 * 
 */
package com.openthinks.libs.utilities.collection;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class TreeNode<T> implements Serializable {
	  private static final long serialVersionUID = 836966324091263005L;
	  private T data;
	  private final TreeNode<T> parent;
	  private final List<TreeNode<T>> children = new ArrayList<>();

	  public TreeNode(T data) {
	    this.data = data;
	    this.parent = null;
	  }

	  public TreeNode(TreeNode<T> parent, T data) {
	    this.data = data;
	    this.parent = parent;
	    addToParent();
	  }

	  public T getData() {
	    return data;
	  }

	  public void setData(T data) {
	    this.data = data;
	  }

	  public TreeNode<T> getParent() {
	    return parent;
	  }

	  public List<TreeNode<T>> getChildren() {
	    return children;
	  }

	  public boolean isEmpty() {
	    return children.isEmpty();
	  }

	  void addToParent() {
	    if (this.parent == null)
	      throw new IllegalArgumentException("Parent node not exist");
	    this.parent.children.add(this);
	  }

	}