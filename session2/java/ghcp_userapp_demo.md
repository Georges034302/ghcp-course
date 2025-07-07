# 🚀 GHCP Session 2 Instructor Demo – UserApp with Copilot, CodeQL & GitHub Security

## 🧱 Overview

| Component     | Description                                                     |
| ------------- | --------------------------------------------------------------- |
| App           | Spring Boot REST API (`UserApp`)                                |
| Insecure Code | Vulnerable endpoint with SQL injection and hardcoded secret     |
| Security Fix  | Copilot-aided refactor with validation and config-based secrets |
| Docs          | Copilot-generated Javadoc + `README.md` + `CONTRIBUTING.md`     |
| CI            | GitHub Actions:                                                 |

| **build + test + CodeQL + doc check** |                                                             |
| ------------------------------------- | ----------------------------------------------------------- |
| Security                              | Secret Scanning + Push Protection + Dependabot              |
| Tools                                 | IntelliJ or VS Code, GitHub Copilot, GitHub Actions, CodeQL |

---

## ✅ Step 1: Scaffold Spring Boot Project

### ⚒️ Option A: Use Spring Initializr

```bash
cd /workspaces/ghcp-course/session2/java
mkdir UserApp
cd UserApp && curl https://start.spring.io/starter.zip \
  -d dependencies=web \
  -d name=UserApp \
  -d artifactId=UserApp \
  -d type=maven-project \
  -d language=java \
  -d javaVersion=21 \
  -o UserApp.zip
unzip UserApp.zip && rm UserApp.zip
```

Run the app:

```bash
./mvnw spring-boot:run
```

---

## 📁 Step 2: Create Vulnerable Endpoint

### 📄 UserController.java (injection + secret)

> *Prompt:* Create a controller with `/api/user` endpoint that uses a SQL query with string concatenation and a hardcoded API key in the class.

#### ✅ Expected Outcome:

```java
@RestController
@RequestMapping("/api")
public class UserController {

    private static final String API_KEY = "sk_test_hardcoded123";

    @GetMapping("/user")
    public ResponseEntity<String> getUser(@RequestParam String email) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE email = '" + email + "'");
        return rs.next() ? ResponseEntity.ok("User Found") : ResponseEntity.notFound().build();
    }
}
```

---

## 🤖 Step 3: Refactor Insecure Code with Copilot

### 🧑‍💻 Prompt to Copilot Chat:

- “Is this code vulnerable to SQL injection?”
- “Refactor to use prepared statement”
- “Move API key to application.properties and inject securely”

#### ✅ Copilot Refactored Version:

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

### 📓 application.properties

```
app.api.key=sk_configured_123
```

---

## 🕊️ Step 4: Add Javadoc with Copilot

> *Prompt:* Place cursor above method and type `/**`, then ask: “Explain the return value and edge cases”

#### ✅ Expected Outcome:

```java
/**
 * Retrieves a user by email.
 * @param email The user email to query.
 * @return 200 OK if user found, 404 if not found.
 * @throws SQLException if database access fails.
 */
```

---

## 📜 Step 5: Generate Markdown Docs with Copilot

### 📄 README.md

> *Prompt:* “Generate a README with usage and setup steps”

### 📄 CONTRIBUTING.md

> *Prompt:* “Write contribution setup instructions for this Spring Boot project”

### 📄 CHANGELOG.md

> *Prompt:* “Create a changelog for version 1.0.0”

---

## ✅ Step 6: Add GitHub Security Tools

### ⚠️ Enable Secret Scanning & Push Protection

- Go to **Settings > Code security and analysis**
- Enable:
  -

### 🚀 Enable CodeQL via GitHub Actions

> *Prompt:* "Create GitHub Action to run CodeQL on pull request"

### .github/workflows/codeql.yml

```yaml
name: CodeQL
on:
  push:
    branches: [main]
  pull_request:
    branches: [main]
jobs:
  analyze:
    name: Analyze Code
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Initialize CodeQL
        uses: github/codeql-action/init@v2
        with:
          languages: java
      - name: Perform CodeQL Analysis
        uses: github/codeql-action/analyze@v2
```

### 🚀 Add Markdown + Docs Check

> *Prompt:* "Create GitHub Action to lint markdown and check doc presence"

### .github/workflows/docs-check.yml

```yaml
name: Docs Check
on:
  pull_request:
    branches: [main]
jobs:
  markdownlint:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: DavidAnson/markdownlint-cli2-action@v10
```

### 🚀 Add Dependabot

```yaml
# .github/dependabot.yml
version: 2
updates:
  - package-ecosystem: "maven"
    directory: "/"
    schedule:
      interval: "daily"
```

---

## 🔧 Step 7: CI Workflow with Build + Test

### .github/workflows/ci.yml

```yaml
name: Java CI
on:
  push:
    branches: [main]
  pull_request:
    branches: [main]
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
      - name: Build with Maven
        run: mvn clean install
```

---

## 👌 Summary

| Task                | Outcome / Tool                                       |
| ------------------- | ---------------------------------------------------- |
| Vulnerability Fixed | Copilot Chat: SQL injection + secret injection fix   |
| Docs Added          | Javadoc, README.md, CONTRIBUTING.md                  |
| Security Tools      | CodeQL, Secret Scanning, Push Protection, Dependabot |
| CI/CD               | GitHub Actions: build + test + docs + CodeQL         |
| Style Check         | markdownlint in GitHub Actions                       |

