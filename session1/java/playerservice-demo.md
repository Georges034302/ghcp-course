# 🚀 GHCP Session 1 Instructor Demo – PlayerApp with GitHub Copilot

## 🧱 Overview

| Component | Description |
|----------|-------------|
| App      | Spring Boot REST API (`PlayerApp`) |
| Data     | `Player` class with mock data (ID, name, score 0–10) |
| API      | `GET /api/player/{id}`, `GET /api/players` |
| Test     | JUnit 5 tests with **Faker** |
| CI       | GitHub Actions for **build + test** |
| CD       | GitHub Actions to **deploy to Azure App Service** |
| Tools    | VS Code or IntelliJ with GitHub Copilot |

---

## ✅ Step 1: Create Spring Boot Project

### ⛏️ Option A: Use Spring Initializr
```bash
cd /workspaces/ghcp-course/session1/java
mkdir PlayerApp
cd PlayerApp && curl https://start.spring.io/starter.zip \
  -d dependencies=web \
  -d name=PlayerApp \
  -d artifactId=PlayerApp \
  -d type=maven-project \
  -d language=java \
  -d javaVersion=21 \
  -o PlayerApp.zip
unzip PlayerApp.zip
rm PlayerApp.zip 
```

Run the app:
```bash
./mvnw spring-boot:run
```
---
## Step 2: Project Structure with Copilot

> *Prompt: Create this Spring Boot structure under `com.example.PlayerApp`:*
```
src/main/java/com/example/PlayerApp/
│
├── model/
│   └── Player.java
│
├── repository/
│   └── PlayerRepository.java
│
├── service/
│   └── PlayerService.java
│
├── controller/
│   └── PlayerController.java
│
└── PlayerAppApplication.java
```

---

## ✅ Step 3: Develop Model, Repository, and Service (with Copilot)

### 📄 Player.java (Model)
> * Create a Player class in PlayerApp/model with:  
>  - id: String (3-digit, "000"–"999")  
>  - name: String ("Player-<random 0-100>")  
>  - score: int (random 0–100)  
>  - Include constructor, getters, and setters.*

```java
public class Player {
    private String id; 
    private String name; 
    private int score; 
    // constructor, getters, setters
}
```

---

### 📄 PlayerRepository.java
```java
// create a repository interface for Player with getById and getAll methods
public interface PlayerRepository {
    Player getById(int id);
    List<Player> getAll();
}
```

Then implement it with mock data.

---

### 📄 PlayerService.java
```java
// create a service that uses PlayerRepository
public class PlayerService {
    ...
}
```

---

### 📄 PlayerController.java
```java
@RestController
@RequestMapping("/api")
public class PlayerController {

    private final PlayerService service;

    @GetMapping("/player/{id}")
    public ResponseEntity<Player> getPlayer(@PathVariable int id) { ... }

    @GetMapping("/players")
    public List<Player> getAllPlayers() { ... }
}
```

---

## ✅ Step 4: Test API Locally
```bash
./mvnw spring-boot:run
```
Visit:
- http://localhost:8080/api/players
- http://localhost:8080/api/player/1

---

## ✅ Step 5: Create Unit Tests (with Copilot + Faker)

### Add Faker to pom.xml
```xml
<dependency>
  <groupId>com.github.javafaker</groupId>
  <artifactId>javafaker</artifactId>
  <version>1.0.2</version>
</dependency>
```

### Sample Prompts for Copilot:
```java
// test getAll returns non-empty list
// test getPlayer returns correct player
// test getPlayer returns null for unknown ID
// test add player with Faker name and score
```

Use Faker like:
```java
Faker faker = new Faker();
String name = faker.name().fullName();
int score = faker.number().numberBetween(0, 10);
```

---

## ✅ Step 6: Run Unit Tests Locally
```bash
./mvnw test
```

---

## ✅ Step 7: Generate ci.yml (with Copilot)

### .github/workflows/ci.yml
```yaml
# GitHub Actions to build and test Spring Boot app with Maven
name: Java CI

on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 21
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Build and test with Maven
        run: mvn clean install
```

---

## ✅ Step 8: Generate cd.yml for Azure Deployment

### .github/workflows/cd.yml
```yaml
name: Deploy to Azure

on:
  push:
    branches: [ "main" ]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up Java
        uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Build with Maven
        run: mvn clean package
      - name: Deploy to Azure Web App
        uses: azure/webapps-deploy@v2
        with:
          app-name: YOUR_APP_NAME
          publish-profile: ${{ secrets.AZURE_WEBAPP_PUBLISH_PROFILE }}
          package: target/*.jar
```

---

## ✅ Step 9: Test Deployed API

Visit:
- https://<YOUR-APP>.azurewebsites.net/api/players
- https://<YOUR-APP>.azurewebsites.net/api/player/1

---

## ✅ Summary

| Task                       | Tool / Prompt                      |
|----------------------------|------------------------------------|
| Build Spring App           | `start.spring.io` + Copilot        |
| Create Model + Controller  | Copilot inline prompts             |
| Write Tests                | Copilot + Faker                    |
| CI                         | GitHub Actions YAML via Copilot    |
| CD to Azure                | YAML + Azure publish profile       |
| Test via Browser/Postman   | API + Copilot-generated data       |
