package org.yubi.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class TestScanner {

	@Test
	public void test() {

		final String response = "TOKEN=EC%2d0L071499AX847590E&TIMESTAMP=2012%2d11%2d24T02%3a02%3a52Z&CORRELATIONID=afc0b4788bf89&ACK=Success&VERSION=72%2e0&BUILD=4181146";

		Map<String, String> resultMap = new HashMap<String, String>();

		Scanner scanner = new Scanner(response);
		scanner.useDelimiter("&");

		while (scanner.hasNext()) {
			String item = scanner.next();
			resultMap.put(StringUtils.split(item, "=")[0],
					StringUtils.split(item, "=")[1]);
		}
		
		System.out.println(resultMap);
	}

}
