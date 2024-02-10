# Introduction to Spring Initializr

## Web Interface
Navigate to [https://start.spring.io](https://start.spring.io).

## Project Metadata
Specify **Group** (`com.example`), **Artifact** (`todo-service`), **Name**, and **Description** of your project.

## Choose the Project
Maven or Gradle (Maven is commonly used for its simplicity and widespread support).

## Select Java Version
Recommend Java 11 or newer for long-term support.

## Selecting Key Dependencies

- **Spring Web**: Essential for building web applications and RESTful services, including controllers and REST support.
  - Enables creation of web endpoints using annotations.
- **Spring Data JPA**: Facilitates database operations, abstracting boilerplate CRUD operations, and integrates with JPA providers like Hibernate.
  - Simplifies data persistence in SQL databases.
- **Spring Security**: Provides authentication and authorization capabilities to secure your application.
  - Protects API endpoints and manages user sessions.

## Project Generation

- **Generate and Download**: Click "Generate" to download the project template.
- **Import Project**: Open your IDE (e.g., IntelliJ IDEA, Eclipse) and import the project as a Maven or Gradle project.

## Initial Configuration

- `application.properties`: Configure application-level settings, like server port, database connection details (if necessary for this step).
- **Main Application Class**: Review the generated main class (`TodoServiceApplication.java`), which is annotated with `@SpringBootApplication` to enable auto-configuration and component scanning.

## First Run

- **Running the Application**: 
  - Use the IDE or command line (`mvn spring-boot:run` for Maven, `gradlew bootRun` for Gradle) to start the application.
- **Verification**: Ensure the application starts without errors. Default port 8080 should be accessible.
