/*
 * 
 * Copyright 2014 Silvio Hirashiki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.github.shirashiki.ideastore.cloud.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.springframework.web.client.RestTemplate;
import org.junit.Test;

import com.github.shirashiki.ideastore.cloud.Greeting;

/**
 * Tests features in the example application. This will be used for test, and also document what
 * the application does. Put the application to run and then run this as a jUnit test.
 * The name of the class starts with 'Abstract', this will be ignored in build.
 * @author silvio hirashiki
 *
 */
public class AbstractIntegrationTest {
	
	private static final String SERVER = "http://localhost:8080";
	
	/**
	 * GreetingController implements the path greeting, which should return an echo message.
	 * This is using Jackson (https://github.com/FasterXML/jackson) to convert JSON
	 * to object
	 * @throws Exception
	 */
	@Test
	public void testGreetingEcho() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		
		// Checks if returns the default greeting
		Greeting emptyGreeting = restTemplate.getForObject(SERVER + "/greeting", Greeting.class);
		assertEquals(1, emptyGreeting.getId());
		assertEquals("Hello, World!", emptyGreeting.getContent());
		
		// Checks if generates the greeting with parameters 
		String echoStr = "Montreal Canadiens";
		Greeting myGreeting = restTemplate.getForObject(SERVER + "/greeting?name=" + echoStr, Greeting.class);
		assertEquals(1, myGreeting.getId());
		assertEquals("Hello, " + echoStr + "!", myGreeting.getContent());		
	}
}
