## Idea Store

### Project Components

Component | Detail | Comments
------ |------ | ----------------------------------
Java | Java 1.7 | In the server will use openJDK 1.7
Eclipse | Eclipse Luna 4.4.0 |
Eclipse | m2e plugin | Maven to Eclipse, adds Maven support
Hosting | Heroku | Because we will provide Heroku the functionality to monitor
Build tool | Maven | Needed for Heroku
Servlet container | Jetty | Needed for Heroku

### IdeaStore Service HTTP API


#### GET /idea

- Returns the list of idea that have been added to the server as JSON. The list of idea should be persisted using Spring Data. The list of idea objects should be able to be unmarshalled by the client into a Collection.
- The return content-type should be application/json.

#### POST /idea

- The idea metadata is provided as an application/json request body. The JSON should generate a valid instance of the Idea class when deserialized by Spring's default Jackson library.
- Returns a boolean indicating if the insertion was ok.

#### GET /idea/{id}

- Returns the idea with the specified id or a 404.
- The return content-type should be application/json.

#### GET /idea/search/findByName?name={name}

- Returns a list of ideas whose names match the given parameter or an empty list if none are found.
