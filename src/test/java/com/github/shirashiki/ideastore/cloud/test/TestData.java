package com.github.shirashiki.ideastore.cloud.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.shirashiki.ideastore.cloud.repository.Idea;

/**
 * This is a utility class to aid in the construction of
 * Idea objects with random names, descriptions.
 * The class also provides a facility to convert objects
 * into JSON using Jackson, which is the format that the
 * IdeaSvc controller is going to expect data in for
 * integration testing.
 * 
 *
 */
public class TestData {

	private static final ObjectMapper objectMapper = new ObjectMapper();
	
	/**
	 * Construct and return a Video object with a
	 * random name, description.
	 * 
	 * @return
	 */
	public static Idea randomIdea() {
		// Information about the video
		// Construct a random identifier using Java's UUID class
		String name = UUID.randomUUID().toString();
		String description = "Idea-" + name;
		
		// sets creation date as current date
		TimeZone tz = TimeZone.getTimeZone("America/Montreal");
		Calendar myCalendar = Calendar.getInstance(tz);
		Date creationDate = new Date(myCalendar.getTimeInMillis());
		
		return new Idea(name, description, creationDate);
	}
	
	/**
	 *  Convert an object to JSON using Jackson's ObjectMapper
	 *  
	 * @param o
	 * @return
	 * @throws Exception
	 */
	public static String toJson(Object o) throws Exception{
		


		return objectMapper.writeValueAsString(o);
	}
}
