# url-shortener-api

> A Spring Boot application of REST endpoints that:
> - Generate and store unique URLs
> - HTTP redirects users that visit any generated URL
> - Stores data event for every HTTP redirect ("click")
> - Provide analytical queries on total click and clicks per day

## How to Run

### External Dependencies

You will need the following installed to build and run this app locally.

- Java 17
- Maven
- [Docker](https://hub.docker.com/welcome)

### Local Setup

- Run `mvn clean install` to run the test suite and build the .jar
- Run `docker compose up` to instantiate Postgres DB in a Docker container
  - If you do not want mock data inserted into the `slugs` table, set `flyway.placeholders.addMockData` to false 
  in `./database/flyway.conf`

### How to Use

- Run `java -jar ./target/url-shortener-api-0.1.0-SNAPSHOT.jar` via command line or IDE
- Use [url-shortener-ui](https://github.com/clang27/url-shortener-ui) to interact with API

## Notes

### Trade Offs

> REST APIs are more rigid when it comes to dynamic data fetching. If analytics were a key feature of this application,
> I would create a GraphQL service. For now, two endpoints to manage total clicks and total clicks per day is manageable,
> and it allows the frontend to manipulate the data to extrapolate results like "days that have 0 clicks between x and y"
>  - By "more rigid", I mean if the frontend wants a different response model, the backend needs to make a change

> I used a Postgres DB because of the requirements, but if the app's usage grew exponentially I would want to use 
> MongoDB for data storage. This will make real-time data and real-time analytics highly scalable.

> For time's sake, I took the first 16 characters of a random UUID to generate the slug. This is highly random, but
> makes for a weaker UX because they are easily forgettable and mixable.

### Assumptions

- The relationship between slugs and target URLs are one-to-one
- If two different users request a URL for the same target URL, they receive the same URL
- "user agent" implies the request header `"User-Agent"`
- A "click" is the same as the server making a "redirect"
  - This means someone could turn a generated URL into another generated URL and when someone
  clicks the former, it counts as a click for the latter 

### "Next Steps" If Given More time

- More robust error handling and responses
- WebMvc and/or TestContainer tests for Controller classes
- Better URL data validation and sanitation
- JWT/OAuth authentication and authorization
- Pagination

### AI Usage

The following GPT-5 LLM prompts were used to assist in development:

- *"How to make a URL redirect in Spring?"*
```java
return new RedirectView(target);
```
- *"Show me how to setup a TestContainer, JUnit 5 test case for a service using Postgres."*
```java
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class DatabaseTest {
  private static final PostgreSQLContainer postgresContainer =
          new PostgreSQLContainer("postgres:17.6")
                  .withReuse(true);

  @BeforeAll
  static void beforeAll() {
    postgresContainer.start();
  }

  @AfterAll
  static void afterAll() {
    postgresContainer.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
    registry.add("spring.datasource.username", postgresContainer::getUsername);
    registry.add("spring.datasource.password", postgresContainer::getPassword);
  }
}
```
- *"Show me a Java Regex matcher example"*
```java
var regex = "^https?://";
var pattern = Pattern.compile(regex);
var matcher = pattern.matcher(url);

return matcher.find();
```
