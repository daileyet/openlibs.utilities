/**
 * 
 */
package com.openthinks.libs.utilities.collection;

import java.io.Serializable;

/**
 * @author dailey.yet@outlook.com
 *
 */
public interface Tree extends Serializable{
	
	public int depth();
	
	public int size();
	
	public void traverse(TreeFilter filter);
	
}
