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
public class TreeNode implements Serializable{
	private static final long serialVersionUID = 836966324091263005L;
	private Object data;
	private final TreeNode parent;
	private final List<TreeNode> children = new ArrayList<>();

	public TreeNode(Object data) {
		this.data = data;
		this.parent = null;
	}

	public TreeNode(TreeNode parent, Object data) {
		this.data = data;
		this.parent = parent;
		addToParent();
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public TreeNode getParent() {
		return parent;
	}

	public List<TreeNode> getChildren() {
		return children;
	}
	
	void addToParent() {
		if (this.parent == null)
			throw new IllegalArgumentException("Parent node not exist");
		this.parent.children.add(this);
	}

}
