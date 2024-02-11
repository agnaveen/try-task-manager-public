# Installing the required software
## Install Java 17
Follow this link - https://java.tutorials24x7.com/blog/how-to-install-openjdk-17-on-windows

## Install MySQL 8
Follow this link - https://www.sqlshack.com/how-to-install-mysql-database-server-8-0-19-on-windows-10/





# Introduction to Spring Initializr
To create a new spring project follow the below steps, to import this project, just skip the below steps.

## Web Interface
Navigate to [https://start.spring.io](https://start.spring.io).

## Project Metadata
Specify **Group** (`com.tecgriseyou`), **Artifact** (`try-task-manager`), **Name**, and **Description** of your project.

## Choose the Project
Maven

## Select Java Version
Recommend Java 11 or newer for long-term support.(We used Java 17 for this project)

## Selecting Key Dependencies

- **Spring Web**: Essential for building web applications and RESTful services, including controllers and REST support.
  - Enables creation of web endpoints using annotations.
- **Spring Data JPA**: Facilitates database operations, abstracting boilerplate CRUD operations, and integrates with JPA providers like Hibernate.
  - Simplifies data persistence in SQL databases.
- **Spring Security**: Provides authentication and authorization capabilities to secure your application.
  - Protects API endpoints and manages user sessions.

## Project Generation

- **Generate and Download**: Click "Generate" to download the project template.
- **Import Project**: Open your IDE (e.g., IntelliJ IDEA, Eclipse) and import the project as a Maven project. We prefer Intellij Idea community version

## Initial Configuration

- `application.properties`: Configure application-level settings, like server port, database connection details (if necessary for this step).
- **Main Application Class**: Review the generated main class (`TaskManagerApplication.java`), which is annotated with `@SpringBootApplication` to enable auto-configuration and component scanning.

## First Run

- **Running the Application**: 
  - Use the IDE or command line (`mvn spring-boot:run`) to start the application.
- **Verification**: Ensure the application starts without errors. Default port 8080 should be accessible. (we used 8001 port for this project)
