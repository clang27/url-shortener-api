# url-shortener-api

A RESTful API that generates and redirects short URLs.

## How to Run

### External Dependencies

You will need the following installed to build and run this app locally.

- Java 17
- Maven
- [Docker](https://hub.docker.com/welcome)

### Setup

- Run `mvn clean install` to run the test suite and build the .jar
- Run `docker compose up` to instantiate Postgres DB in a Docker container
  - If you do not want mock data inserted into the `slugs` table, set `flyway.placeholders.runMockData` to false 
  in `./database/flyway.conf`

### Web Server and API

- Run `java -jar ./target/url-shortener-api-0.1.0-SNAPSHOT.jar` via command line or IDE runtime
