package com.github.shirashiki.ideastore.cloud.repository;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Objects;


/**
 *  Represents a business idea. 
 * @author silvio hirashiki
 *
 */

@Entity
public class Idea {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	private String name;
	private String description;
	
	// monetary target value
	private BigDecimal targetValue;
	
	private Date creationDate;
	
	public Idea(){
	}
	
	public Idea(String name, String description, BigDecimal targetValue, Date creationDate) {
		super();
		
		this.name = name;
		this.description = description;
		this.targetValue = targetValue;
		this.creationDate = creationDate;
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
	
	/**
	 * Returns target value. Note that BogDecimal is immutable, so we don't need to work with
	 * defensive copies
	 * @return Idea target Value
	 */
	public BigDecimal getTargetValue() {
		return targetValue;
	}
	
	public void setTargetValue(BigDecimal newTargetValue) {
		this.targetValue = newTargetValue;
	}

	/**
	 * Returns creation date. When serializing the date in JSON, the pattern used is
	 * yyyy-MM-dd'T'HH:mm:ss.SSSZ, timezone UTC 
	 * @return idea creation date
	 */
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="UTC")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}
		
}

