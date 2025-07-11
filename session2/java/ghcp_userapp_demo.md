
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
> Generate a standard Spring Boot architecture for a REST API named `UserApp` that includes:  
> - **Model:** `User` entity with fields: `id (Long)`, `email (String)`, and `name (String)`  
> - `id` should be the primary key (`@Id`)  
> - **Repository:** `UserRepository` that extends `JpaRepository<User, Long>`  
> - **Service:** `UserService` with method `getUserById(Long id)`  
> - **Controller:** `UserController` mapped to `/api/user/{id}`, returning user info by ID  
> - Use `touch` to create the Java class files (in a single `bash` display block)  
> - Provide the full Java class code for each component in separate `java` blocks  



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
> “Create an insecure version of `UserController`: 
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
      - name: Initialize CodeQL
        uses: github/codeql-action/init@v3
        with:
          languages: java
      - name: Build with Maven
        run: mvn clean install
        working-directory: session2/java/UserApp
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

## Step ✅ 13: Create a Custom CodeQL Query to Detect Hardcoded Secrets
Build and use a custom CodeQL query in a Java project to detect hardcoded secrets using GitHub Copilot and Actions.

### 📁 Create the Folder Structure

> **Prompt:** \
> Create the following directory structure for a custom CodeQL Java query pack:  
> `codeql-packs/java/userapp-secrets/`  
> Inside it, create:
> - `qlpack.yml`  
> - A `queries/` folder containing `FindHardcodedSecrets.ql`  


> ✅ Expected Output:


✅ **Expected Output (File Tree):**

```bash
mkdir -p codeql-packs/java/userapp-secrets/queries
touch codeql-packs/java/userapp-secrets/qlpack.yml
touch codeql-packs/java/userapp-secrets/queries/FindHardcodedSecrets.ql
```

> ✅ **Expected Output:**

```
codeql-packs/
└── java/
    └── userapp-secrets/
        ├── qlpack.yml
        └── queries/
            └── FindHardcodedSecrets.ql
```

### ✨ Create `FindHardcodedSecrets.ql`
> **Prompt:** \
> Write a CodeQL query to detect hardcoded API keys or secrets in Java. 
> Match string literals that include the words `apiKey`, `token`, `secret`, or `password`."

> ✅ **Expected Output:**

```ql
/**
 * @name Hardcoded secret string
 * @description Flags string literals that look like API keys, tokens, or secrets.
 * @kind problem
 * @problem.severity warning
 * @tags security
 */

import java
import semmle.code.java.dataflow.FlowSources
import semmle.code.java.dataflow.TaintTracking

class SuspiciousStringLiteral extends StringLiteral {
  SuspiciousStringLiteral() {
    this.getValue().regexpMatch("(?i)(api[_-]?key|token|secret|password).*")
  }
}

from SuspiciousStringLiteral literal
select literal, "Potential hardcoded secret found: " + literal.getValue()
```

### ✨ Create qlpack.yml File
Register the custom query pack and declare its dependency on the Java CodeQL libraries.

> **Prompt:** \
> Create a `qlpack.yml` for a CodeQL query pack named `userapp/find-hardcoded-secrets` (version 0.0.1) that depends on `codeql/java-all` and sets the default suite to run all

> ✅ **Expected Output:**

```yaml
name: userapp/secrets
version: 0.0.1
dependencies:
  codeql/java-all: "*"
defaultSuite:
  - query: queries/
```

### 🔗 Integrate Custom Query in CodeQL Workflow
> **Prompt:** \
> Update the codeql.yaml workflow to include a custom query folder located at .session2/java/security for Java scanning. 
> The workflow to reference qlpack.yaml `packs: ./codeql-packs/java/userapp-secrets`

> ✅ **Expected Output:**

```yaml
- name: Initialize CodeQL
  uses: github/codeql-action/init@v3
  with:
    languages: java
    packs: ./codeql-packs/java/userapp-secrets

### 📝 Add Test Case for Secret Detection

```java
package com.example.UserApp.model;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String email;
  private String name;
  private static final String API_KEY = "sk_test_abc123"; // Example API key

  // getters/setters...
}

```

### 🚦 Push the change and trigger the workflow

Push this test file and monitor GitHub Actions CodeQL workflow logs for detected secrets

> ✅ **Expected Output:**

```yaml
Potential hardcoded secret found: sk_test_abc123
```

> 🧠 NOTE: \
> To see results in the Security tab, GitHub Enterprise (GHAS) is required. \
> On GitHub Pro, review results from the CI logs only.
---

## ✅ Step 14: Enforce Org-Wide CodeQL Policy *(Enterprise Only)*

> **Prompt:**  
> Inside your organization’s `.github` repository (e.g. `github.com/your-org/.github`), create a file named `.github/codeql/org-policy.yml`.  
> Define a policy to enforce a custom CodeQL query (`FindHardcodedSecrets.ql`) across all Java repositories.  
> The policy should:
> - Include the query pack from a central location
> - Target Java projects
> - Block PRs that violate the rule

> ✅ **Expected Output (`.github/codeql/org-policy.yml`):**
```yaml
queries:
  - uses: your-org/codeql-query-pack/FindHardcodedSecrets.ql
languages:
  - java
enforce:
  block_on_violation: true
```

> ✅ **Expected Output (`FindHardcodedSecrets.ql`):**
```ql
/**
 * @name Hardcoded secret string
 * @description Detects suspicious string literals that may contain API keys, secrets, or tokens.
 * @kind problem
 * @problem.severity warning
 * @tags security
 */

import java

class SuspiciousSecret extends StringLiteral {
  SuspiciousSecret() {
    this.getValue().regexpMatch("(?i)(api[_-]?key|token|secret|password).*")
  }
}

from SuspiciousSecret s
select s, "Potential hardcoded secret found: " + s.getValue()
```

> ✅ **Expected Output (`qlpack.yml`):**
```yaml
name: your-org/codeql-query-pack
version: 0.0.1
dependencies:
  codeql/java-all: "*"
defaultSuite:
  - query: queries/
```

---

## ✅ Step 15: Use GitHub Security Graph API *(Enterprise Only)*

> **Prompt:**  
> Use GitHub’s Security GraphQL API to retrieve open vulnerability alerts from the `UserApp` repository.  
> Include:  
> - `vulnerableManifestFilename`  
> - Package name  
> - Severity  
> - Advisory description

> ✅ **Expected Output (GraphQL Query):**
```graphql
query {
  repository(owner: "your-org", name: "UserApp") {
    vulnerabilityAlerts(first: 10) {
      nodes {
        vulnerableManifestFilename
        securityVulnerability {
          package { name }
          severity
          advisory {
            description
            identifiers { type value }
          }
        }
      }
    }
  }
}
```

---

## 🔍 Query GitHub Security Alerts via GraphQL (query Step 15) *(Enterprise Only)*

You can retrieve vulnerability alerts (e.g. from CodeQL, Dependabot) using GitHub’s GraphQL API in two main ways:

### ✅ OptionA: Use GitHub GraphQL Explorer (Manual)

1. Open: [GitHub GraphQL Explorer](https://docs.github.com/en/graphql/overview/explorer)
2. Sign in with an account that has access to the repository
3. Paste and run the following query:

```graphql
query {
  repository(owner: "your-org", name: "UserApp") {
    vulnerabilityAlerts(first: 10) {
      nodes {
        vulnerableManifestFilename
        securityVulnerability {
          package { name }
          severity
          advisory {
            description
            identifiers { type value }
          }
        }
      }
    }
  }
}
```

> ⚠️ Replace `"your-org"` and `"UserApp"` with your actual organization and repository names.  
> 🔐 Ensure your GitHub account has `read:vulnerability_alerts` access to the repo.

### 🛠️ Option B: Use Curl in Terminal or Script

Use this if you want to automate the query or run it from CI tools:

```bash
curl -H "Authorization: bearer YOUR_GITHUB_TOKEN" \
     -H "Content-Type: application/json" \
     -X POST https://api.github.com/graphql \
     -d '{"query": "query { repository(owner: \"your-org\", name: \"UserApp\") { vulnerabilityAlerts(first: 10) { nodes { vulnerableManifestFilename securityVulnerability { package { name } severity advis
```

---

### 🧩 Summary Table

| Feature                              | Status        | Access            |
|--------------------------------------|---------------|--------------------|
| Custom CodeQL Query Pack             | ✅ Created     | Enterprise Only    |
| Org-wide Policy Enforcement          | ✅ Configured  | Enterprise Only    |
| PR Block on Violations               | ✅ Enabled     | Enterprise Only    |
| Security Graph API Query             | ✅ Available   | Enterprise Only    |
| Custom Alerts via GraphQL            | ✅ Supported   | Enterprise Only    |



---

## 🔍 CodeQL Workflow Capability Comparison: GitHub Pro vs GitHub Enterprise

This table compares the capabilities and effects of the provided CodeQL GitHub Actions workflow when executed under **GitHub Pro** and **GitHub Enterprise (GHAS)** subscriptions.
| Capability                                            | **GitHub Pro (Public Repo)** | **GitHub Pro (Private Repo)** | **GitHub Enterprise (GHAS)** |
| ----------------------------------------------------- | ---------------------------- | ----------------------------- | ---------------------------- |
| **Run CodeQL via GitHub Actions**                     | ✅ Yes                        | ✅ Yes                         | ✅ Yes                        |
| **Use Custom CodeQL Queries** (local repo queries)    | ✅ Yes (CI log only)          | ✅ Yes (CI log only)           | ✅ Yes (CI + Security tab)    |
| **Upload results to Security tab (Code Scanning UI)** | ✅ Yes (built-in only)        | ❌ No                          | ✅ Yes (custom + built-in)    |
| **Secret Scanning (automatic)**                       | ✅ Yes                        | ❌ No                          | ✅ Yes                        |
| **Push Protection for secrets**                       | ✅ Yes                        | ❌ No                          | ✅ Yes                        |
| **Dependabot Alerts**                                 | ✅ Yes                        | ✅ Yes                         | ✅ Yes                        |
| **Dependabot Security Updates**                       | ✅ Yes                        | ✅ Yes                         | ✅ Yes                        |
| **Policy enforcement / org-wide query management**    | ❌ No                         | ❌ No                          | ✅ Yes                        |
| **Security Graph API Access (full)**                  | ❌ Limited                    | ❌ Limited                     | ✅ Yes                        |


