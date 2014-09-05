package com.github.shirashiki.ideastore.cloud.entity;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Value;

import com.google.common.base.Objects;


/**
 *  Represents a business idea
 * @author silvio hirashiki
 *
 */


public class Idea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	private String description;
	private Date creationDate;

	private String timeZoneId = "America/Montreal";
	
	
	public Idea(){
	}
	
	public Idea(String name, String description) {
		super();
		
		this.name = name;
		this.description = description;
		
		// sets creation date as current date
		TimeZone tz = TimeZone.getTimeZone(timeZoneId);
		Calendar myCalendar = Calendar.getInstance(tz);
		creationDate = new Date(myCalendar.getTimeInMillis());
		
	}
	
	/**
	 * Generates hashcode. In this implementation, a unique idea is defined
	 * by name and description, so ideas with SAME name and description will return the SAME
	 * hashcode. This uses google guava.
	 * @see http://docs.oracle.com/javase/7/docs/api/java/lang/Object.html#hashCode%28%29
	 * 
	 */
	@Override
	public int hashCode() {
		// Google Guava provides great utilities for hashing
		return Objects.hashCode(name, description);
	}
	
	/**
	 * Two Ideas are considered equal if they have exactly the same values for
	 * their name and description.
	 * 
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Idea) {
			Idea other = (Idea) obj;
			// Google Guava provides great utilities for equals too!
			return Objects.equal(name, other.name)
					&& Objects.equal(description, other.description);
		} else {
			return false;
		}
	}
	
	

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
}

