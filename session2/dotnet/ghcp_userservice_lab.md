# 🚀 GHCP Session 2 Lab – Secure, Refactor, and Harden .NET 8 API

## 🧱 Overview

| Component | Description                                                           |
| --------- | --------------------------------------------------------------------- |
| App       | .NET 8 Web API (`UserApp`)                                            |
| Feature   | Refactor insecure logic + document with Copilot                       |
| Tools     | GitHub Copilot, CodeQL, Secret Scanning, markdownlint, GitHub Actions |
| Security  | CodeQL + Push Protection + Dependabot                                 |
| Docs      | XML Comments + README + CONTRIBUTING.md                               |

---

## 🎯 Objectives

- Identify and fix insecure logic (SQL injection, hardcoded secrets)
- Use GitHub Copilot Chat to refactor logic and generate documentation
- Add XML summary comments and markdown files
- Enforce doc presence and code quality in GitHub Actions CI

---

## ✅ Step 1: Scaffold the .NET 8 Project

```bash
dotnet new webapi -n UserApp
cd UserApp
code .
```

---

## ✅ Step 2: Define the Clean Architecture

> *Prompt:* Create `User` model, `UserService` class, and `UserController` with `/api/user?email=` endpoint.

### 📄 Models/User.cs

```csharp
public class User
{
    public int Id { get; set; }
    public string Email { get; set; }
    public string Name { get; set; }
}
```

### 📄 Services/UserService.cs

```csharp
public class UserService
{
    public User GetUserByEmail(string email)
    {
        // Simulate fetching from DB
        return new User { Id = 1, Email = email, Name = "Alice" };
    }
}
```

### 📄 Controllers/UserController.cs

```csharp
[ApiController]
[Route("api/[controller]")]
public class UserController : ControllerBase
{
    private readonly UserService _userService = new();

    [HttpGet]
    public IActionResult GetUser(string email)
    {
        var user = _userService.GetUserByEmail(email);
        return Ok(user);
    }
}
```

---

## ✅ Step 3: Add Configuration and Secrets

### 📄 appsettings.json

```json
{
  "ApiKey": "sk_secure_configured_123",
  "ConnectionStrings": {
    "Default": "Server=.;Database=TestDb;Trusted_Connection=True;"
  }
}
```

---

## ✅ Step 4: Demonstrate Insecure Version (Before Copilot Fix)

```csharp
[ApiController]
[Route("api/[controller]")]
public class UserController : ControllerBase
{
    private static string API_KEY = "sk_test_123";

    [HttpGet]
    public IActionResult GetUser(string email)
    {
        var query = $"SELECT * FROM Users WHERE Email = '{email}'";
        return Ok();
    }
}
```

---

## ✅ Step 5: Refactor Using GitHub Copilot

> Prompts:
>
> - "Is this vulnerable to SQL injection?"
> - "Refactor using SqlCommand with parameters"
> - "Inject API key via configuration"

```csharp
private readonly IConfiguration _config;
public UserController(IConfiguration config) => _config = config;

[HttpGet]
public IActionResult GetUser(string email)
{
    var conn = new SqlConnection(_config["ConnectionStrings:Default"]);
    var cmd = new SqlCommand("SELECT * FROM Users WHERE Email = @email", conn);
    cmd.Parameters.AddWithValue("@email", email);
    return Ok();
}
```

---

## ✅ Step 6: Add NuGet Package References

Use `dotnet add package` to install:

```bash
dotnet add package Microsoft.Data.SqlClient
```

---

## ✅ Step 7: Generate Documentation with Copilot

> *Prompt:* Type `///` above method and ask Copilot Chat: "Document return type and edge cases"

```csharp
/// <summary>
/// Returns user data by email address.
/// </summary>
/// <param name="email">Email to search</param>
/// <returns>200 OK if found, 404 if not</returns>
```

---

## ✅ Step 8: Create INSTRUCTIONS.md and CONTRIBUTING.md

> Prompts:
>
> - "Create README.md with project setup and usage"
> - "Generate CONTRIBUTING.md for new developers"

✅ Files created:

- `README.md`
- `CONTRIBUTING.md`

---

## ✅ Step 9: Add GitHub Actions CI/CD

```yaml
# .github/workflows/ci.yml
name: .NET CI
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
      - name: Setup .NET
        uses: actions/setup-dotnet@v3
        with:
          dotnet-version: 8.0.x
      - name: Restore
        run: dotnet restore
      - name: Build
        run: dotnet build --no-restore
      - name: Test
        run: dotnet test --no-build --verbosity normal
```

---

## ✅ Step 10: Enable Secret Scanning and Push Protection

1. Go to **Settings > Code security and analysis** in GitHub.
2. Enable:
   - ✅ **Secret scanning**
   - 🚦 **Push protection**
   - 📊 **Dependency graph**

---

## ✅ Step 11: Add CodeQL Scan

```yaml
# .github/workflows/codeql.yml
name: CodeQL Scan
on:
  push:
    paths:
      - '**/*.cs'
    branches: [main]
  pull_request:
    paths:
      - '**/*.cs'
    branches: [main]
permissions:
  security-events: write
  contents: read
jobs:
  analyze:
    name: CodeQL Analyze C#
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-dotnet@v3
        with:
          dotnet-version: '8.0.x'
      - uses: github/codeql-action/init@v3
        with:
          languages: csharp
      - run: dotnet build --configuration Release
      - uses: github/codeql-action/analyze@v3
```

---

## ✅ Step 12: Create a Custom CodeQL Query to Detect Hardcoded Secrets

📁 Structure:

```
.github/
└── codeql/
    ├── config.yml
    ├── qlpack.yml
    └── queries/
        └── FindHardcodedSecrets.ql
```

📄 `FindHardcodedSecrets.ql`

```ql
/**
 * @name Find hardcoded secrets in C#
 * @description Detects hardcoded strings that look like secrets
 * @kind problem
 * @problem.severity warning
 * @security-severity 8.0
 * @id cs/hardcoded-secrets
 * @tags security
 */

import csharp

from string_literal s
where
  s.getValue().regexpMatch("(?i).*(sk_.*|token_.*|apikey_.*|[a-zA-Z0-9+/=]{32,})")
select s, "Hardcoded secret detected: '" + s.getValue() + "'"
```

📄 `config.yml`

```yaml
name: "CodeQL Config"
disable-default-queries: false
queries:
  - uses: security-extended
  - uses: .
paths:
  - '**/*.cs'
paths-ignore:
  - '**/test/**'
```

📄 `qlpack.yml`

```yaml
name: userapp/secrets
version: 0.0.1
dependencies:
  codeql/csharp-all: "*"
```

---

## ✅ Step 13: Add Dependabot

```yaml
# .github/dependabot.yml
version: 2
updates:
  - package-ecosystem: "nuget"
    directory: "/"
    schedule:
      interval: "daily"
    labels:
      - "dependencies"
      - "automerge"
    open-pull-requests-limit: 10
    commit-message:
      prefix: "📦 deps:"
```

---

## ✅ Step 14: Enforce Org-Wide CodeQL Policy *(Enterprise Only)*

```yaml
# .github/codeql/org-policy.yml
name: "Org-Wide CodeQL Policy"
disable-default-queries: false
queries:
  - uses: org/codeql-queries@v1.0.0/csharp/security/FindHardcodedSecrets.ql
  - uses: security-and-quality
languages:
  - csharp
paths:
  - '**/*.cs'
rules:
  - id: cs/hardcoded-secrets
    severity: error
    paths:
      - '**/*.cs'
    mode: block
    message: |
      ❌ Hardcoded secrets detected. Please:
      1. Remove embedded credentials
      2. Use environment variables or secrets config
      3. Follow secure development best practices
```

---

## ✅ Step 15: Use GitHub Security Graph API *(Enterprise Only)*

```graphql
query VulnerabilityAlerts {
  repository(owner: "YOUR_ORG", name: "UserApp") {
    vulnerabilityAlerts(first: 100, states: OPEN) {
      nodes {
        vulnerableManifestPath
        securityVulnerability {
          package {
            name
          }
          severity
          advisory {
            description
          }
        }
      }
    }
  }
}
```

---

## 📊 Summary Table

| Step | Feature                  | Tool / Prompt       | ✅ Outcome                        |
| ---- | ------------------------ | ------------------- | -------------------------------- |
| 1    | Scaffold Project         | `dotnet new webapi` | Ready-to-code API                |
| 2    | Clean Architecture       | Copilot             | Model, Service, Controller       |
| 3    | Config + Secrets         | Copilot Chat        | appsettings + IConfiguration     |
| 4    | Vulnerable Logic         | Manual              | Insecure logic w/ hardcoded key  |
| 5    | Copilot Refactor         | Copilot Chat        | Safer SQL + secrets              |
| 6    | NuGet Packages           | dotnet add package  | Installed SqlClient              |
| 7    | XML Docs                 | `///`               | Method doc added                 |
| 8    | Markdown Docs            | Copilot Chat        | README + CONTRIB                 |
| 9    | CI Workflow              | GitHub Actions      | Build + Test                     |
| 10   | GitHub Security Settings | GitHub UI           | Secret scanning, push protection |
| 11   | CodeQL Scan              | GitHub Actions      | Static security scan             |
| 12   | Custom CodeQL Query      | Copilot + CodeQL    | Detect secrets in C#             |
| 13   | Dependabot               | GitHub Config       | Auto package updates             |
| 14   | Org Policy (GHES)        | CodeQL              | Enforced org-wide rule           |
| 15   | Security API (GHES)      | GraphQL             | Query vulnerabilities            |

---

## 📄 End of Lab

You have now secured, documented, and enforced a modern .NET 8 Web API with GitHub Copilot and GitHub Advanced Security!

