/**   
 *  Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
* @Title: QuickSort.java 
* @Package utilities.sort 
* @Description: TODO
* @author dailey  
* @date 2012-10-30
* @version V1.0   
*/
package com.openthinks.libs.utilities.sort;

/**
 * @author dailey
 *
 */
public class QuickSort<T extends Comparable<T>> implements Sortable<T> {

	private T[] array;
	private Direction direction = Direction.ASC;

	public QuickSort(T[] array) {
		this.array = array;
	}

	/**
	 * @param direction the direction to set
	 */
	@Override
	public void setDirection(Direction direction) {
		if (direction != null)
			this.direction = direction;
	}

	/* (non-Javadoc)
	 * @see utilities.sort.Sortable#sort()
	 */
	@Override
	public void sort() {
		quickSort(array, 0, array.length - 1);
	}

	/**
	 * Quick sort method
	 * @param array int[] sort array
	 * @param begin int array begin index
	 * @param end	int array end index
	 */
	void quickSort(T[] array, int begin, int end) {
		if (begin >= end)
			return;
		int p = partition(array, begin, end);
		quickSort(array, begin, p - 1);
		quickSort(array, p + 1, end);
	}

	/**
	 * @param array
	 * @param begin
	 * @param end
	 * @return partition index
	 */
	private int partition(T[] array, int begin, int end) {

		T pVal = array[begin];
		int left = begin, right = end + 1;
		switch (direction) {
		case DESC:
			do {

				do {
					if (left < end)
						++left;
					else
						break;
				} while (array[left].compareTo(pVal) > 0);

				do {
					if (right > 0)
						--right;
					else
						break;
				} while (array[right].compareTo(pVal) < 0);

				swap(array, left, right);
			} while (left < right);
			break;

		default:
			do {

				do {
					if (left < end)
						++left;
					else
						break;
				} while (array[left].compareTo(pVal) < 0);

				do {
					if (right > 0)
						--right;
					else
						break;
				} while (array[right].compareTo(pVal) > 0);

				swap(array, left, right);
			} while (left < right);
			break;
		}
		swap(array, left, right);
		swap(array, begin, right);

		return right;
	}

	/**
	 * @param array
	 * @param left
	 * @param right
	 */
	private void swap(T[] array, int left, int right) {
		T temp = array[left];
		array[left] = array[right];
		array[right] = temp;
	}

}
