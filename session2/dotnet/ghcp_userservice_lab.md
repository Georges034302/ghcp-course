# 🤮 GHCP Session 2 Lab – Secure & Document a .NET API with GitHub Copilot + CodeQL

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

## ✅ Step 1: Scaffold a .NET 8 Web API

```bash
dotnet new webapi -n UserApp
cd UserApp
code .
```

---

## 📁 Step 2: Add Insecure Endpoint

### 📄 Controllers/UserController.cs

> *Prompt:* Create a GET endpoint `/api/user?email=` that uses string-concatenated SQL and a hardcoded API key.

```csharp
[ApiController]
[Route("api/[controller]")]
public class UserController : ControllerBase
{
    private static string API_KEY = "sk_test_insecure123";

    [HttpGet]
    public IActionResult GetUser(string email)
    {
        var query = $"SELECT * FROM Users WHERE Email = '{email}'";
        // simulate DB logic...
        return Ok();
    }
}
```

---

## 🤖 Step 3: Refactor with Copilot Chat

> *Prompts:*
>
> - "Is this code vulnerable to SQL injection?"
> - "Refactor using parameterized SQL"
> - "Move secret to appsettings.json and read with IConfiguration"

### ✅ Expected Outcome:

```csharp
private readonly IConfiguration _config;

public UserController(IConfiguration config) => _config = config;

[HttpGet]
public IActionResult GetUser(string email)
{
    var conn = new SqlConnection(_config["ConnectionStrings:Default"]);
    var cmd = new SqlCommand("SELECT * FROM Users WHERE Email = @email", conn);
    cmd.Parameters.AddWithValue("@email", email);
    // execute safely...
    return Ok();
}
```

**appsettings.json**

```json
"ConnectionStrings": {
  "Default": "Server=.;Database=Test;Trusted_Connection=True;"
},
"ApiKey": "sk_secure_from_config"
```

---

## 🕊️ Step 4: Add XML Documentation

> *Prompt:* Type `///` above method and ask Copilot Chat: "Document return type and edge cases"

```csharp
/// <summary>
/// Returns user data by email address.
/// </summary>
/// <param name="email">Email to search</param>
/// <returns>200 OK if found, 404 if not</returns>
```

---

## 📜 Step 5: Generate Markdown Docs

> *Copilot Prompts:*
>
> - "Generate README.md with usage and setup"
> - "Write CONTRIBUTING.md for team onboarding"
> - "Create CHANGELOG.md with v1.0.0"

### ✅ Expected Outcome:

- `README.md` with API usage and local run steps
- `CONTRIBUTING.md` with setup and testing
- `CHANGELOG.md` with new refactor entry

---

## 🚀 Step 6: Add GitHub Security Checks

### ⚠ Enable Secret Scanning & Push Protection

- Go to **Settings > Code Security & Analysis**
- Enable:
  -

### ⚡ Add CodeQL Scan

> *Prompt:* Create a GitHub Action workflow to run CodeQL scan on every PR.

```yaml
# .github/workflows/codeql.yml
name: CodeQL Scan
on:
  pull_request:
    branches: [ main ]
jobs:
  analyze:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Setup .NET
        uses: actions/setup-dotnet@v3
        with:
          dotnet-version: 8.0.x
      - name: Init CodeQL
        uses: github/codeql-action/init@v2
        with:
          languages: csharp
      - name: Analyze
        uses: github/codeql-action/analyze@v2
```

---

## 🔧 Step 7: Enforce Docs with GitHub Actions

> *Prompt:* "Create CI workflow that blocks missing markdown or XML docs"

### ✅ Expected Workflow:

```yaml
# .github/workflows/docs-check.yml
name: Docs Check
on:
  pull_request:
    branches: [main]
jobs:
  validate:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: DavidAnson/markdownlint-cli2-action@v10
```

---

## 🌐 Step 8: Add Dependabot

```yaml
# .github/dependabot.yml
version: 2
updates:
  - package-ecosystem: "nuget"
    directory: "/"
    schedule:
      interval: "daily"
```

---

## 🚀 Step 9: CI Workflow for Build + Test

> *Prompt:* "Generate .NET CI GitHub Action using dotnet test"

### ✅ Outcome:

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

## 📊 Summary Table

| Step | Feature                  | Tool / Prompt       | ✅ Outcome                      |
| ---- | ------------------------ | ------------------- | ------------------------------ |
| 1    | Scaffold Project         | `dotnet new webapi` | Ready-to-code API              |
| 2    | Insecure Logic           | Copilot prompt      | SQL + secret exposed           |
| 3    | Refactor + Secure        | Copilot Chat        | Safer queries + config secrets |
| 4    | XML Comments             | `///` + Chat        | Inline docs on method          |
| 5    | Markdown Docs            | Copilot prompt      | README + CONTRIB + CHANGELOG   |
| 6    | Secret + Push Protection | GitHub UI           | Enabled warnings + blocking    |
| 7    | CodeQL                   | Copilot prompt      | Detects taint flows            |
| 8    | Docs Check               | markdownlint action | PRs blocked if docs missing    |
| 9    | CI (Build + Test)        | Copilot YAML        | Test and build pipeline        |
| 10   | Dependabot               | GitHub file         | Secures NuGet packages         |

---

## 📄 End of Lab

You have now secured, documented, and enforced a modern .NET API with GitHub Copilot and GitHub Advanced Security!

