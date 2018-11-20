/**
 * 
 */
package com.openthinks.libs.utilities;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author dailey.yet@outlook.com
 *
 */
public class StringUtilsTest {

//	@Test
	public void testTrimBlank() {
		String test = "\t  aa\tbb \t";
		String t=StringUtils.trimBlank(test);
		System.out.println(t);
		Assert.assertTrue(t.length()==5);
		
		test="abc";
		t=StringUtils.trimBlank(test);
		System.out.println(t);
		Assert.assertTrue(t.length()==3);
	}
	
	@Test
	public void test() {
		String test="人民币/克 ";
		System.out.println(test.length());
		Assert.assertTrue(test.length()==6);
		int c =test.charAt(5);
		System.out.println(c);
		System.out.println((int)'A');
		test=StringUtils.trimBlank(test);
		System.out.println(test.length());
		Assert.assertTrue(test.length()==5);
	}
	
	@Test
	public void testNewLine() {
		String test="\r\n" + 
				"          	2018-11-20 10:07:47\r\n" + 
				"           北京时间";
		System.out.println(test.length());
		test=StringUtils.trimBlank(test);
		System.out.println(test);
		System.out.println(test.length());
	}
}
