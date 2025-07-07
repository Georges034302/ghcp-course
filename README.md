# ghcp-course
GitHub Copilot for Java & .NET Web Developers – GHCP Training Series. This course delivers 2 comprehensive sessions on integrating GitHub Copilot into enterprise workflows for test automation, secure coding, documentation, and performance optimization using Visual Studio Code and GitHub Actions.

---

<details>
<summary><strong>Session 1 – Lab</strong> (click to expand/hide)</summary>

### 🧪 Hands-On Lab: Build & Test a RESTful API in .NET with Copilot-Aided Unit Testing

#### 🏷️ Lab Title
Build, Test, and Serve a ProductService API with Static UI, GitHub Copilot, and Bogus Test Data

#### 🎯 Lab Objective
Create a .NET 6 Web API project that exposes endpoints for managing products. Use GitHub Copilot to generate unit tests with edge-case data using Bogus. Add a simple `index.html` UI and serve it alongside the API. Run and validate all components locally.

#### ✅ Lab Scenario
A developer is building a backend ProductService for an e-commerce platform. The app exposes REST endpoints for CRUD operations. The team wants to enforce automated testing from day one and leverage Copilot for increased productivity and quality.

### 🔧 Lab Steps Overview

| Setup Step | Description                            |
|------------|----------------------------------------|
| 1          | Scaffold project using `dotnet new webapi` |
| 2          | Add `Product` model and `ProductService` logic |
| 3          | Add API controller with 3 routes: get all, get by ID, add product |
| 4          | Create a static `index.html` file for local testing |
| 5          | Enable serving static files in `Program.cs` |

| Test Step | Description                              |
|-----------|------------------------------------------|
| 1         | Create `ProductService.Tests` with xUnit |
| 2         | Install and use `Bogus` for fake data    |
| 3         | Use Copilot to generate tests for all methods |
| 4         | Add prompts for edge cases (invalid price, empty name) |
| 5         | Run tests locally and verify via Swagger/UI |

| Expected Outcome | Description                                      |
|------------------|--------------------------------------------------|
| 1                | REST API exposes 3 working endpoints             |
| 2                | index.html allows live testing via fetch() calls |
| 3                | Unit tests exist and cover normal + edge cases   |
| 4                | Copilot correctly scaffolds tests based on comments |
| 5                | Developer understands when to override Copilot  |

</details>

<details>
<summary><strong>Session 1 – Demo</strong> (click to expand/hide)</summary>

### 🚀 Instructor Demo: PlayerApp – Spring Boot API with Copilot + GitHub Actions

#### 🏷️ Demo Title
Build, Test, and Deploy a Java Spring Boot API Using GitHub Copilot and Azure App Service

#### 🎯 Demo Objective
Use GitHub Copilot to build a Java-based Player API (Player ID, name, random score), generate unit tests with Java Faker, create CI/CD pipelines via Copilot prompts, and deploy to Azure App Service.

#### ✅ Demo Scenario
The instructor demonstrates how Copilot can serve as a real-time assistant to write Java code, generate test coverage, and scaffold deployment workflows.

### 🔧 Demo Steps Overview

| Phase        | Description                                                           |
|--------------|-----------------------------------------------------------------------|
| Project Init | Use Spring Initializr to scaffold a Maven-based Spring Boot project  |
| API Logic    | Create Player model, service, and controller using Copilot prompts   |
| Unit Tests   | Use Copilot to generate JUnit tests + Java Faker for test data       |
| CI Pipeline  | Copilot-generated GitHub Actions YAML for build/test with Maven      |
| CD Pipeline  | Copilot-generated GitHub Actions YAML for deploying to Azure         |
| Deployment   | Deploy to Azure App Service using GitHub Secrets + publish profile   |
| Validation   | Call endpoints via browser/Postman to validate full flow             |

| Expected Outcome | Description                                                    |
|------------------|------------------------------------------------------------------|
| 1                | Fully working Player API deployed to Azure                      |
| 2                | API returns mock data with scores between 0–10                 |
| 3                | Tests are Copilot-generated and pass locally + in GitHub CI     |
| 4                | CI/CD workflows are Copilot-assisted and reusable               |
| 5                | Demonstrates real-time Copilot productivity in Java development |

</details>

<details>
<summary><strong>Session 2 – Lab</strong> (click to expand/hide)</summary>

### 🧪 Hands-On Lab: Secure and Document a .NET API Using Copilot + GitHub Security Tools

#### 🏷️ Lab Title
Secure and Document a .NET 8 Web API (`UserApp`) with GitHub Copilot, CodeQL, and Docs Enforcement

#### 🎯 Lab Objective
Refactor a vulnerable .NET Web API using GitHub Copilot Chat, remove SQL injection and hardcoded secrets, add documentation, and use GitHub Actions and Advanced Security to enforce best practices.

#### ✅ Lab Scenario
A .NET developer inherits an insecure codebase. Using GitHub Copilot, they clean the logic, apply validation, inject secrets securely, and auto-generate XML + markdown docs. GitHub Actions and CodeQL enforce CI and doc checks.

### 🔧 Lab Steps Overview

| Step | Description                                           |
|------|-------------------------------------------------------|
| 1    | Scaffold .NET Web API using `dotnet new webapi`       |
| 2    | Add an insecure controller method                     |
| 3    | Refactor using Copilot prompts and config secrets     |
| 4    | Generate XML comments with `///` and Copilot Chat     |
| 5    | Generate markdown files with Copilot                  |
| 6    | Enable Secret Scanning + Push Protection              |
| 7    | Add CodeQL analysis via GitHub Actions                |
| 8    | Add markdownlint GitHub Action                        |
| 9    | Add CI GitHub Action (build + test)                   |
| 10   | Add Dependabot to manage NuGet package security       |

| Expected Outcome | Description                                                   |
|------------------|---------------------------------------------------------------|
| 1                | Insecure logic is cleaned and secured                         |
| 2                | XML comments are auto-generated                               |
| 3                | README.md, CONTRIBUTING.md, CHANGELOG.md created              |
| 4                | GitHub Actions enforce CI, docs, and secure coding practices  |
| 5                | Copilot Chat accelerates developer workflow                   |

</details>

<details>
<summary><strong>Session 2 – Demo</strong> (click to expand/hide)</summary>

### 🚀 Instructor Demo: Java Spring Boot UserApp – Secure API with Copilot + CodeQL

#### 🏷️ Demo Title
Fix SQL Injection + Add Documentation in Spring Boot with GitHub Copilot and GitHub Advanced Security

#### 🎯 Demo Objective
Refactor a vulnerable Java Spring Boot endpoint with Copilot, validate with CodeQL, add documentation using Javadoc and markdown files, and enforce via GitHub Actions.

#### ✅ Demo Scenario
A legacy Java controller contains SQL injection and hardcoded secrets. The instructor walks through identifying and remediating these with Copilot prompts, then integrates documentation and CI security workflows.

### 🔧 Demo Steps Overview

| Phase           | Description                                                      |
|------------------|------------------------------------------------------------------|
| Create Vulnerable| Endpoint with string concatenation and hardcoded token          |
| Refactor         | Use Copilot Chat to replace with PreparedStatement and @Value   |
| Validate         | Enable CodeQL and secret scanning                               |
| Document         | Generate Javadoc and markdown docs using Copilot prompts        |
| CI Enforcement   | Add GitHub Actions for code + doc checks                        |

| Expected Outcome | Description                                                    |
|------------------|-----------------------------------------------------------------|
| 1                | SQL injection is mitigated and secret moved to config          |
| 2                | CodeQL passes with no security alerts                          |
| 3                | Javadoc + markdown docs auto-generated                         |
| 4                | CI workflows enforce secure + documented codebase              |
| 5                | Instructor demonstrates secure DevOps pipeline with Copilot    |

</details>



---
#### 🧑‍🏫 Author: Georges Bou Ghantous
<sub><i>This repository delivers GitHub Copilot training for Java & .NET developers, showcasing real-world workflows for test automation, secure coding, documentation, and CI/CD using VS Code and GitHub Actions.</i></sub>
