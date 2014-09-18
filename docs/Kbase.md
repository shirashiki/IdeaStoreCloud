## Knowledge Base


### Objective

This documents solutions Java and Android problems, in order to speed development. Consider we are using the following platform:

Component | What for | Where is used 
------ |------ | ----------------------------------
Jackson | Serialize objects in JSON | Server (Java Spring Boot)
GSON | Serialize objects in JSON | Client ( Android, using retrofit)
Retrofit | Serialize/Deserialize objects in JSON | Client ( Android, using retrofit)


#### Index

[Database setup - basics](#Database setup - basics)
[Database setup with multiple environments, like DEV-QA-PROD](#Database setup with multiple environments, like DEV-QA-PROD)
[Date formatting in JSON](#Date formatting in JSON)


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
- (Spring Boot Data Access)[http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-data-access]
- (Spring Datasource configuration)[http://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-configure-datasource]



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