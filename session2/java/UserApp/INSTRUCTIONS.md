# UserApp – Setup & Usage Guide

## 🚀 Technologies Used
- Java 21
- Spring Boot (Spring Web, Spring Data JPA)
- H2 In-Memory Database
- Maven

## 🛠️ Setup

1. **Install Java 21 and Maven**  
   Ensure Java 21 and Maven are installed on your system.

2. **Configure API Key**  
   Edit `src/main/resources/application.properties` and set your API key:
   ```
   app.api.key=your_api_key_here
   ```

3. **Build the Project**
   ```bash
   ./mvnw clean package
   ```

## ▶️ How to Run

Start the application using Maven:
```bash
./mvnw spring-boot:run
```

The app will start on [http://localhost:8080](http://localhost:8080).

## 🌐 API Endpoints

- **Get User by ID**
  ```
  GET /api/user/{id}
  ```
  Returns user details for the given ID.

- **Get User by Email**
  ```
  GET /api/user/by-email?email={email}
  ```
  Returns user details for the given email address.

## 🗄️ Database

- Uses H2 in-memory database (no setup required).
- H2 Console available at `/h2-console` (if enabled).

## 📦 Notes

- All dependencies are managed via Maven.
- API key is injected from configuration for demonstration.
- For production, use a persistent database and secure