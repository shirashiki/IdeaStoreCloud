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
package com.github.shirashiki.ideastore.cloud.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.github.shirashiki.ideastore.cloud.Greeting;
import com.github.shirashiki.ideastore.cloud.GreetingController;

/**
 * Tests features in the example application. This will be used for test, and also document what
 * the application does. Put the application to run and then run this as a jUnit test.
 * The name of the class ends in 'Test' so Maven can locate and run it automatically
 * @author silvio hirashiki
 *
 */
public class ExampleUnitTest {
	
	
	/**
	 * GreetingController implements the path greeting, which should return an echo message.
	 * This is using Jackson (https://github.com/FasterXML/jackson) to convert JSON
	 * to object
	 * @throws Exception
	 */
	@Test
	public void testGreetingEcho() throws Exception {
		GreetingController gc = new GreetingController();
		
		// tests greeting when sending name
		String myString = "Brat le bricoleur";
		
		Greeting baseGreeting = new Greeting(1, "Hello, " + myString + "!");
		Greeting receivedGreeting = gc.greeting(myString);
		assertEquals(baseGreeting.getId(), receivedGreeting.getId());
		assertEquals(baseGreeting.getContent(), receivedGreeting.getContent());
		
	}
}
