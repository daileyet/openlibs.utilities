/**
 * 
 */
package com.openthinks.libs.utilities.json;

import org.junit.Test;

/**
 * @author dailey.yet@outlook.com
 *
 */
public final class JSONConvertersTest {

	
	@Test
	public void testPerparedAndGet() {
		POJO_JSON json = new POJO_JSON();
		json.setAge(11);
		json.setUserName("dailey");
		json.setUserPass("1111");
		JSONObject jsonObj = JSONConverters.perparedAndGet(json);
		jsonObj.forEach((key,val)->{
			System.out.println(key+" = "+val);
		});
	}
	
	public static void main(String[] args) {
		POJO_JSON json = new POJO_JSON();
		json.setAge(11);
		json.setUserPass("1111");
		JSONObject jsonObj = JSONConverters.perparedAndGet(json);
		System.out.println(jsonObj);
		
		json.setUserName("Dailey");
		jsonObj = JSONConverters.perparedAndGet(json);
		System.out.println(jsonObj);
	}
}
