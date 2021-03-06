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

import java.util.Collection;
import java.util.Date;

import org.springframework.web.client.RestTemplate;
import org.junit.Test;

import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.converter.GsonConverter;

import com.github.shirashiki.ideastore.client.IdeaSvcApi;
import com.github.shirashiki.ideastore.cloud.Greeting;
import com.github.shirashiki.ideastore.cloud.repository.Idea;
import com.github.shirashiki.ideastore.cloud.test.TestData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.bind.DateTypeAdapter;

/**
 * Tests features in the example application. This will be used for test, and also document what
 * the application does. Put the application to run and then run this as a jUnit test.
 * The name of the class starts with 'Abstract', this will be ignored in build.
 * @author silvio hirashiki
 *
 */
public class AbstractApiTest {
	
	private static final String TEST_URL = "http://localhost:8080";
	



	@Test
	public void testAddIdea() throws Exception{
		// this uses Gson to convert dates to a specific format.
		// A similar task to be done in the server side in spring;
		// retrofit is used in the client, jackson is used in the server
		Gson gson = new GsonBuilder()
			.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ") // this is one of the jackson accepted formats
	    	.create();
		
		IdeaSvcApi ideaService = new RestAdapter.Builder()
			.setConverter(new GsonConverter(gson))
			.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
			.create(IdeaSvcApi.class);
		
		Idea newIdea = TestData.randomIdea();
		
		// adds idea
		boolean ok = ideaService.addIdea(newIdea);
		
		// checks if it was added
		Collection<Idea> ideas = ideaService.getIdeaList();
		assertTrue(ideas.contains(newIdea));
		
		
	}
	
	
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
		Greeting emptyGreeting = restTemplate.getForObject(TEST_URL + "/greeting", Greeting.class);
		assertEquals(1, emptyGreeting.getId());
		assertEquals("Hello, World!", emptyGreeting.getContent());
		
		// Checks if generates the greeting with parameters 
		String echoStr = "Montreal Canadiens";
		Greeting myGreeting = restTemplate.getForObject(TEST_URL + "/greeting?name=" + echoStr, Greeting.class);
		assertEquals(1, myGreeting.getId());
		assertEquals("Hello, " + echoStr + "!", myGreeting.getContent());		
	}
	
	
	
}
