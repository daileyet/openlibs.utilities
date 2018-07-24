/**
 * 
 */
package com.openthinks.libs.utilities.collection;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class NormalTree implements Tree {
	private static final long serialVersionUID = -3623645850736542520L;
	private TreeNode root;

	public NormalTree(TreeNode root) {
		super();
		this.root = root;
	}

	@Override
	public int depth() {
		return 0;
	}

	@Override
	public int size() {
		final AtomicInteger rst = new AtomicInteger(0);
		traverse((node)->{
			rst.incrementAndGet();
			return false;
		});
		return rst.get();
	}

	@Override
	public void traverse(TreeFilter filter) {
		if (root == null)
			return;
		search(root, filter);
	}

	private boolean search(TreeNode node, TreeFilter filter) {
		if (filter.test(node)) {
			return true;
		} else {
			for (TreeNode child : node.getChildren()) {
				if (search(child, filter) == true) {
					return true;
				}
			}
			return false;
		}

	}

}
