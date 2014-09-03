package com.github.shirashiki.ideastore.cloud;

import java.sql.Date;
import java.util.Calendar;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import com.github.shirashiki.ideastore.cloud.entity.Idea;

public class JustTest {

	
	public static void main(String[] args) {
		
		Idea myIdea = new Idea("first idea", "become billionaire");
		
		System.out.println(myIdea.getCreationDate());
		
	}
}
