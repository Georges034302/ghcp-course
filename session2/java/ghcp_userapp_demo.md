
# 🚀 GHCP Instructor Demo – `UserApp` with GitHub Copilot, CodeQL & GitHub Security

This demo guides instructors through building a secure, layered Spring Boot application (`UserApp`) using GitHub Copilot and GitHub Security tools. It includes model, repository, service, and controller components with security scans and CI/CD integration.

---

## ✅ Step 1: Scaffold the Spring Boot Project

> **Prompt:**  
> “Use Spring Initializr to generate a Maven-based Spring Boot project named `UserApp` with:
> - Java 21
> - Dependencies: Spring Web, Spring Data JPA, H2 Database
> - Output as a ZIP
> - Unzip it into `/workspaces/ghcp-course/session2/java/UserApp`, then delete the ZIP.”

```bash
cd /workspaces/ghcp-course/session2/java/UserApp
curl https://start.spring.io/starter.zip \
  -d dependencies=web,data-jpa,h2 \
  -d name=UserApp \
  -d artifactId=UserApp \
  -d type=maven-project \
  -d language=java \
  -d javaVersion=21 \
  -o UserApp.zip
unzip UserApp.zip
rm UserApp.zip
```

> ✅ **Expected Output:**
> ```
> UserApp/
> ├── src/
> │   ├── main/
> │   │   ├── java/com/example/UserApp/
> │   │   └── resources/
> │   └── test/
> ├── pom.xml
> └── mvnw, mvnw.cmd
> ```

---

## ✅ Step 2: Define the Clean Architecture

> **Prompt:**  
> “Generate a standard Spring Boot architecture for a REST API named `UserApp` that includes:
> - Model: `User` entity with `email` and `name`
> - Repository: `UserRepository` extends `JpaRepository`
> - Service: `UserService` with method `getUserByEmail()`
> - Controller: `UserController` that maps to `/api/user` and returns user info by email”
> - Use touch to create the java classes (own display block)
> - Provide the java classes code


> ✅ **Expected Output:**
> ```
> com.example.UserApp
> ├── controller/UserController.java
> ├── service/UserService.java
> ├── model/User.java
> └── repository/UserRepository.java
> ```

---

## ✅ Step 3: Add Configuration

> **Prompt:**  
> “Create `application.properties` with:
> - `app.api.key`
> - H2 DB connection settings
> - Hibernate config for auto schema generation”

> ✅ **Expected Output (`src/main/resources/application.properties`):**
```properties
app.api.key=sk_configured_123
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create
spring.h2.console.enabled=true
```

---

## ✅ Step 4: Demonstrate Insecure Version (Before Copilot Fix)

> **Prompt:**  
> “Create an insecure version of `UserController`: (Use getEmail() for entity ID)
> - Use `Statement` and string concatenation for SQL query
> - Hardcode secret in the class”

> ✅ **Expected Output (simplified vulnerable controller):**
```java
private static final String API_KEY = "sk_test_123";

@GetMapping("/user")
public ResponseEntity<String> getUser(@RequestParam String email) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    Statement stmt = conn.createStatement();
    ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE email = '" + email + "'");
    return rs.next() ? ResponseEntity.ok("User Found") : ResponseEntity.notFound().build();
}
```

---

## ✅ Step 5: Refactor Using GitHub Copilot

> **Prompt:**  
> “Is this vulnerable to SQL injection?”  
> “Refactor to use `PreparedStatement`.”  
> “Inject API key using `@Value` from config.”

> ✅ **Expected Output:**
```java
@Value("${app.api.key}")
private String apiKey;

@GetMapping("/user")
public ResponseEntity<String> getUser(@RequestParam String email) throws SQLException {
    Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE email = ?");
    stmt.setString(1, email);
    ResultSet rs = stmt.executeQuery();
    return rs.next() ? ResponseEntity.ok("User Found") : ResponseEntity.notFound().build();
}
```

---
## ✅ Step 6: Add dependencies to POM.XML

> **Prompt:** \
> Open the `pom.xml` file  
> Add the required dependencies to support:  
> - Spring Web (`spring-boot-starter-web`)  
> - Spring Data JPA (`spring-boot-starter-data-jpa`)  
> - H2 in-memory database (`h2`)  
>  
> These are required to fix compilation errors related to:  
> - `jakarta.persistence` (for `@Entity`, `@Id`)  
> - `JpaRepository`  
>  
> Format the output as a single XML block (to copy into `<dependencies>`).


```xml
<!-- ...existing code... -->
<dependencies>
    <!-- Spring Boot Starter Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <!-- H2 Database (for local testing) -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <!-- Jakarta Persistence API -->
    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
        <version>3.1.0</version>
    </dependency>
    <!-- ...other dependencies... -->
</dependencies>
<!-- ...existing code... -->
```

---

## ✅ Step 7: Generate Documentation with Copilot

> **Prompt:**  
> Generate Javadoc for `UserController` methods:
> - Describe the parameters, return types, and exceptions.

> ✅ **Expected Output:**
```java
/**
 * Retrieves a user by email.
 * @param email The email address to search for.
 * @return 200 OK with user info if found, otherwise 404.
 * @throws SQLException if the DB connection fails.
 */
```

---

## ✅ Step 8: Create INSTRUCTIONS.md and CONTRIBUTING.md

> **Prompt 1 (INSTRUCTIONS.md):**  
> Create new `INSTRUCTIONS.md` in the UserApp directory \
> Provide instructions on setup, how to run, API endpoints, and technologies


> **Prompt 2 (CONTRIBUTING.md):** \
> Create new `CONTRIBUTING.md` in the UserApp directory \
> Provide a guide for this Spring Boot app using Maven.

---

## ✅ Step 9: Add GitHub Actions CI/CD

> **Prompt:**  
> Create the file user-ci.yaml in github root direction .github/workflows
> Generate GitHub Actions workflow  for:
> - Java 21
> - `mvn clean install` on push and pull request”
> - Workflow triggers on changes in session2/java

> ✅ **Expected Output (`.github/workflows/user-ci.yml`):**
```yaml
name: Java CI

on:
  push:
    paths:
      - 'session2/java/**'
    branches: [main]

jobs:
  build:
    name: Build and Test User App
    runs-on: ubuntu-latest
    steps:
      - name: Checkout Repository
        uses: actions/checkout@v3
      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean package
        working-directory: session2/java/UserApp
```

---

## ✅ Step 10: Add CodeQL Scan

> **Prompt:** 
> Create the file `codeql.yaml` in the `.github/workflows` directory at the root of the repository.  
> Generate a GitHub Actions workflow that:
> - Runs CodeQL analysis for Java  
> - Triggers on both push and pull request events  
> - Only runs when changes are made under the `session2/java/` directory  
> - Uses the latest CodeQL action (`@v3`)  
> - Includes proper permissions (`security-events: write`)  
> - Includes a Maven build step before analysis  


> ✅ **Expected Output (`.github/workflows/codeql.yml`):**
```yaml
name: CodeQL

on:
  push:
    paths:
      - 'session2/java/**'
    branches: [main]
  pull_request:
    paths:
      - 'session2/java/**'
    branches: [main]

permissions:
  security-events: write

jobs:
  analyze:
    name: CodeQL Analyze Java
    runs-on: ubuntu-latest
    steps:
      - name: Checkout repository
        uses: actions/checkout@v3
      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean install
        working-directory: session2/java/UserApp
      - name: Initialize CodeQL
        uses: github/codeql-action/init@v3
        with:
          languages: java
      - name: Perform CodeQL Analysis
        uses: github/codeql-action/analyze@v3
```

---

## ✅ Step 11: Enable Secret Scanning and Push Protection


### ⚙️ Enable security features in GitHub:

1. Go to your repository on GitHub.
2. Click on **Settings** (top right of the repo page).
3. In the left sidebar, click **Code security**.
4. Enable the following options:
   - **Secret scanning** 🕵️‍♂️
   - **Push protection** 🚦
   - **Dependency graph** 📊
5. Click **Save** 💾 if

These steps will enable secret scanning, push protection, and the dependency graph for

---

## ✅ Step 12: Add Dependabot
> **Prompt:**
> Create the file `.github/dependabot.yml`  
> Add a configuration to enable daily updates for Maven dependencies in the root directory  


> ✅ **Expected Output (`.github/dependabot.yml`):**
```yaml
version: 2
updates:
  - package-ecosystem: "maven"
    directory: "/"
    schedule:
      interval: "daily"
```

---

## 🧩 Summary Table

| Layer         | Status     |
|---------------|------------|
| Model         | ✅ Created |
| Repository    | ✅ Created |
| Service       | ✅ Created |
| Controller    | ✅ Created |
| Properties    | ✅ Configured |
| CI Workflow   | ✅ Added |
| CodeQL        | ✅ Enabled |
| Secret Scanning | ✅ Enabled |
| Documentation | ✅ Generated |
| Architecture  | ✅ Verified |
