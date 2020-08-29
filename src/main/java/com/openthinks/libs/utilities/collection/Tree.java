/**
 * 
 */
package com.openthinks.libs.utilities.collection;

import java.io.Serializable;

/**
 * @author dailey.yet@outlook.com
 *
 */
public interface Tree<T> extends Serializable {

	  /**
	   * 
	   * depth: tree depth. <br> 
	   * 
	   * @return number of tree depth
	   */
	  public int depth();

	  /**
	   * 
	   * size:tree node size. <br> 
	   * 
	   * @return number of tree node size
	   */
	  public int size();

	  /**
	   * 
	   * traverse: traverse tree node by {@link TreeFilter}. <br> 
	   * 
	   * @param filter {@link TreeFilter}
	   */
	  public void traverse(TreeFilter<T> filter);

	}