## Knowledge Base


### Objective

This documents solutions Java and Android problems, in order to speed development. Consider we are using the following platform:

Component | What for | Where is used 
------ |------ | ----------------------------------
Jackson | Serialize objects in JSON | Server (Java Spring Boot)
GSON | Serialize objects in JSON | Client ( Android, using retrofit)
Retrofit | Serialize/Deserialize objects in JSON | Client ( Android, using retrofit)


#### Index

- [Database setup - basics](#database-setup---basics)
- [Database setup with multiple environments, like DEV-QA-PROD](#database-setup-with-multiple-environments-like-dev-qa-prod)
- [Date formatting in JSON](#date-formatting-in-json)
- [Maven jdbc dependency cannot be resolved](#maven-jdbc-dependency-cannot-be-resolved-problems-with-sun-jars)


### Database setup - basics

1 - Create database using a script

To research: import.sql in Hibernate

2 - Prevent database being dropped and recreated every time you start the application. For embedded databases like H2, Spring assumes you want to drop and create database objects when starting the application, to prevent this, add the following to your `application.properties`:
```
spring.jpa.hibernate.ddl-auto=none 
```

Possible values:
- none: does nothing
- validate: validate the schema, makes no changes to the database.
- update: update the schema.
- create: creates the schema, destroying previous data.
- create-drop: drop the schema at the end of the session.


#### Sources
- [Spring database initialization](http://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html)


### Database setup with multiple environments, like DEV-QA-PROD

Problem: you need to have different database setups for development, qa and production environments. You may want to use even different database systems, like H2 for DEV/QA and Postgres for PROD. 

#### Notes

- DataSource configuration is controlled by external configuration properties in spring.datasource.*.


#### Workaround

This uses two H2 databases, primary and secondary. This is considered a workaround because we need to change the Configuration code assigning @Primary, and the database password is all visible. The secondary datasource is not loaded, allowing the configuration of another database system (MySQl, DB2) even if not accessible.

1 - Add to your Configuration class (in our case, Application.java) two datasources, and annotate with @Primary the one you want active:

```java
	@Bean
	@Primary
	@ConfigurationProperties(prefix="datasource.devqa")
	public DataSource primaryDataSource() {
    	return DataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix="datasource.production")
	public DataSource secondaryDataSource() {
	    return DataSourceBuilder.create().build();
	}
```


2 - In `application.properties`, add the corresponding properties:
```
## database for dev and qa
datasource.devqa.url=jdbc:h2:~/data/ideastore_devqa
datasource.devqa.username=sa
datasource.devqa.password=mypsw1
datasource.devqa.driverClassName=org.h2.Driver

## production database
datasource.production.url=jdbc:h2:~/data/ideastore_prod
datasource.production.username=sa
datasource.production.password=mypsw2
datasource.production.driverClassName=org.h2.Driver
```


#### Solution 1

This covers a solution for a H2 database for DEV-QA and a Postgres database for PROD.



#### Sources
- [Spring Boot Data Access](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-data-access)
- [Spring Datasource configuration](http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-configure-datasource)


### Database initialization

Problem: you want to have control when initializing a database, running a script to create database objects.

#### Sources
http://simplespringtutorial.com/springjdbc.html
http://www.tutorialspoint.com/spring/spring_jdbc_example.htm
http://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-integration-testing/
http://www.infoq.com/articles/microframeworks1-spring-boot




### Date formatting in JSON

Problem: you need to have a portable date format in JSON, which works in Android and in the Java Spring Server. It is important to note that in the client we use Retrofit to have object-JSON conversion, and in the server we use Jackson which is integrated in Spring. For this reason, the solution has a client and a server part.

 In this solution, we will use the following date format: yyyy-MM-dd'T'HH:mm:ss.SSSZ.
  
#### Solution Steps:

1 - Server: in the Entities, annotate Date getter methods with the desired jackson JsonFormat. This will tell Spring and jackson how to serialize a Date to JSON.
```java
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="UTC")
public Date getCreationDate() {
	return creationDate;
}
```

2 - Client: In order to proper serialize a date using retrofit, customize the GsonConverter:

```java
Gson gson = new GsonBuilder()
	.setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	.create();
		
IdeaSvcApi ideaService = new RestAdapter.Builder()
	.setConverter(new GsonConverter(gson))
	.setEndpoint(TEST_URL).setLogLevel(LogLevel.FULL).build()
	.create(IdeaSvcApi.class);
```

3 - Results:

- JSON created in client and sent to server. Note it uses the system timezone, in this case, EDT (Eastern Daylight)
```
{
	"id": 0,
	"name": "1622721a-28c2-428d-a536-1e85979d6ea6",
	"description": "Idea-1622721a-28c2-428d-a536-1e85979d6ea6",
	"creationDate": "2014-09-18T11:48:27.104-0400"
}
```

- JSON received from server. The server persisted this in a database and returned, using UTC timezone: 
```
{
	"id": 1,
	"name": "1622721a-28c2-428d-a536-1e85979d6ea6",
	"description": "Idea-1622721a-28c2-428d-a536-1e85979d6ea6",
	"creationDate": "2014-09-18T15:48:27.104+0000"
}
```

#### Sources

- [Jackson Date handling](http://wiki.fasterxml.com/JacksonFAQDateHandling)
- [String to Date Parsing with GSON and UTC Time Zone Handling](http://kylewbanks.com/blog/String-Date-Parsing-with-GSON-UTC-Time-Zone)
- [How to Serialize Java.util.Date with Jackson JSON Processor / Spring 3.0](http://java.dzone.com/articles/how-serialize-javautildate)



### Maven jdbc dependency cannot be resolved, problems with Sun jars

I had a problem with the jdbc reference, it was working on Eclipse in Mac, but not in Windows. After a lot of research, I found a not in the Maven site:

"Often users are confronted with the need to build against JARs provide by Sun like the JavaMail JAR, or the Activation JAR and users have found these JARs not present in central repository resulting in a broken build. Unfortunately most of these artifacts fall under Sun's Binary License which disallows us from distributing them from Ibiblio.
. . . . .
Java.net provides a Maven 2 repository. You could specify it directly in your POM or in your settings.xml between the tags <repositories>:".

This was resolved adding this element to the Maven pom:

```
<repositories>
	<repository>
    	<id>maven2-repository.dev.java.net</id>
       	<name>Java.net Repository for Maven</name>
       	<url>http://download.java.net/maven/2/</url>
     	<layout>default</layout>
  	</repository>
</repositories>
```

Also, the jdbc dependency was changed to:
```
<dependency>
	<groupId>javax.sql</groupId>
	<artifactId>jdbc-stdext</artifactId>
	<version>2.0</version>
</dependency>
```


#### Sources

- [Coping with Sun Jars](http://maven.apache.org/guides/mini/guide-coping-with-sun-jars.html)

