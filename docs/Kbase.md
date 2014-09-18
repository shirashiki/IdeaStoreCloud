## Knowledge Base


### Objective

This documents solutions Java and Android problems, in order to speed development. Consider we are using the following platform:

Component | What for | Where is used 
------ |------ | ----------------------------------
Jackson | Serialize objects in JSON | Server (Java Spring Boot)
GSON | Serialize objects in JSON | Client ( Android, using retrofit)
Retrofit | Serialize/Deserialize objects in JSON | Client ( Android, using retrofit)


### Date formatting in JSON

Problem: you need to have a portable date format in JSON, which works in Android and in the Java Spring Server. It is important to note that in the client we use Retrofit to have object-JSON conversion, and in the server we use Jackson which is integrated in Spring. For this reason, the solution has a client and a server part.

 In this solution, we will use the following date format: yyyy-MM-dd'T'HH:mm:ss.SSSZ. Solution Steps:

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
{"id":0,"name":"1622721a-28c2-428d-a536-1e85979d6ea6","description":"Idea-1622721a-28c2-428d-a536-1e85979d6ea6","creationDate":"2014-09-18T11:48:27.104-0400"}
```

- JSON received from server. The server persisted this in a database and returned, using UTC timezone: 
```
{"id":1,"name":"1622721a-28c2-428d-a536-1e85979d6ea6","description":"Idea-1622721a-28c2-428d-a536-1e85979d6ea6","creationDate":"2014-09-18T15:48:27.104+0000"}
```








#### Sources

- [Jackson Date handling](http://wiki.fasterxml.com/JacksonFAQDateHandling)
- [String to Date Parsing with GSON and UTC Time Zone Handling](http://kylewbanks.com/blog/String-Date-Parsing-with-GSON-UTC-Time-Zone)
- [How to Serialize Java.util.Date with Jackson JSON Processor / Spring 3.0](http://java.dzone.com/articles/how-serialize-javautildate)