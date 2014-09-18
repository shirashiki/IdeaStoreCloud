## Knowledge Base


### Objective

This documents solutions Java and Android problems, in order to speed development. Consider we are using the following platform:

Component | What for | Where is used 
------ |------ | ----------------------------------
Jackson | Serialize objects in JSON | Server (Java Spring Boot)
GSON | Serialize objects in JSON | Client ( Android, using retrofit)
Retrofit | Serialize/Deserialize objects in JSON | Client ( Android, using retrofit)


### Date formatting in JSON

Problem: you need to have a portable date format in JSON, which works in Android and in the Java Spring Server. In this solution, we will use the following date format: yyyy-MM-dd'T'HH:mm:ss.SSSZ. Solution Steps:

1 - In the Entities, annotate Date getter methods with the desired jackson JsonFormat:
```java
@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone="UTC")
public Date getCreationDate() {
	return creationDate;
}
```

2 - 

 




#### Sources

- [Jackson Date handling](http://wiki.fasterxml.com/JacksonFAQDateHandling)
- [String to Date Parsing with GSON and UTC Time Zone Handling](http://kylewbanks.com/blog/String-Date-Parsing-with-GSON-UTC-Time-Zone)
- [How to Serialize Java.util.Date with Jackson JSON Processor / Spring 3.0](http://java.dzone.com/articles/how-serialize-javautildate)