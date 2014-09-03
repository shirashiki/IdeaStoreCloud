package com.github.shirashiki.ideastore.cloud.entity;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Value;


/**
 *  Represents a business idea
 * @author silvio hirashiki
 *
 */


public class Idea {
	

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

