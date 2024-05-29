
# Marketplace Backend Service

This is a Spring Boot backend service for a marketplace application. It provides various functionalities such as data management, email services, security, and more.

## Table of Contents

- [Requirements](#requirements)
- [Setup](#setup)
- [Configuration](#configuration)
- [Running the Application](#running-the-application)

## Requirements

- Java 17
- Maven 3.6+

## Setup

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd <repository-directory>
   ```

2. **Install dependencies:**
   ```bash
   mvn clean install
   ```

## Configuration

Configure the application by modifying the `src/main/resources/application.properties` file. Key configurations include:

```properties
# WebSocket Configuration
window.size=5
```

## Running the Application

You can run the application using Maven or your preferred IDE.

### Using Maven:

```bash
mvn spring-boot:run
```

### Using IntelliJ IDEA:

1. Open the project in IntelliJ IDEA.
2. Navigate to `Application` class.
3. Right-click and select `Run 'Application.main()'`.
