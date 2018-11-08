/**
 * 
 */
package com.openthinks.libs.utilities.json;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.openthinks.libs.utilities.json.support.JSONElement;


/**
 * @author dailey.yet@outlook.com
 *
 */
public class ParseTest {

	void testParseFromFile() throws IOException, URISyntaxException {
		String jsonStr = "";
		URL url=ParseTest.class.getResource("/da.js");
		byte[] readBuff = Files.readAllBytes(Paths.get(url.toURI()));
		jsonStr = new String(readBuff);
		System.out.println(jsonStr);
		JSONElement element = JSON.parse(jsonStr);
		System.out.println(element);
	}
	
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		ParseTest tester = new ParseTest();
		tester.testParseFromFile();
	}
}
