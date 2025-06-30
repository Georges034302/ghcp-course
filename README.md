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

### 🧪 Hands-On Lab: Secure and Document an Application Using Copilot and GitHub Security Tools

#### 🏷️ Lab Title
Secure a Spring Boot or .NET App with Copilot + CodeQL and Auto-Generated Documentation

#### 🎯 Lab Objective
Auto-detect insecure code patterns using GitHub Advanced Security and Copilot suggestions. Refactor risky code, enforce validation logic, and generate Javadoc/XML comments and Markdown documentation using GitHub Copilot Chat and GitHub Actions workflows.

#### ✅ Lab Scenario
A developer inherits a legacy app with outdated code. They want to secure input validation, remove hardcoded secrets, and create clean, maintainable API documentation and workflows using GitHub tools.

### 🔧 Lab Steps Overview

| Setup Step | Description                                      |
|------------|--------------------------------------------------|
| 1          | Enable GitHub Advanced Security (CodeQL, Dependabot) |
| 2          | Scan app using CodeQL and secret scanning        |
| 3          | Prompt Copilot to refactor risky input handling  |
| 4          | Add missing validation and output encoding       |
| 5          | Use Copilot Chat to generate XML/Javadoc         |
| 6          | Generate README.md and CONTRIBUTING.md           |
| 7          | Validate documentation sync with GitHub Actions  |

| Expected Outcome | Description                                  |
|------------------|----------------------------------------------|
| 1                | Unsafe code is identified and refactored     |
| 2                | Secure defaults and validation added         |
| 3                | XML/Javadoc and Markdown docs are generated  |
| 4                | CI ensures docs and code stay in sync        |
| 5                | GitHub Copilot Chat demonstrates real-time aid |

</details>

<details>
<summary><strong>Session 2 – Demo</strong> (click to expand/hide)</summary>

### 🚀 Instructor Demo: Refactor and Harden a Legacy API Using Copilot + CodeQL

#### 🏷️ Demo Title
Fix Unsafe Code and Auto-Document a Java/.NET App Using GitHub Copilot and Advanced Security

#### 🎯 Demo Objective
Live refactor of an insecure endpoint with Copilot suggestions and CodeQL scanning. Enhance it with validation, documentation, and GitHub Actions enforcement.

#### ✅ Demo Scenario
An existing endpoint has hardcoded secrets, missing validation, and lacks documentation. The instructor walks through correcting it interactively using GitHub tools.

### 🔧 Demo Steps Overview

| Phase         | Description                                                     |
|---------------|-----------------------------------------------------------------|
| Code Review   | Use Copilot to analyze and rewrite vulnerable method            |
| Security Scan | Run CodeQL via GitHub Actions                                  |
| Validation    | Add input checks, null checks, and logging                     |
| Documentation | Use Copilot Chat to generate XML or Javadoc                    |
| CI Enforcement| Require test + doc checks using GitHub Actions                 |

| Expected Outcome | Description                                          |
|------------------|------------------------------------------------------|
| 1                | Legacy code is refactored securely with Copilot      |
| 2                | CodeQL confirms no high/medium-severity vulnerabilities |
| 3                | Copilot Chat generates accurate, readable documentation |
| 4                | PRs blocked without passing tests + docs             |
| 5                | Secure coding and documentation workflows are reinforced |

</details>


---
#### 🧑‍🏫 Author: Georges Bou Ghantous
<sub><i>This repository delivers GitHub Copilot training for Java & .NET developers, showcasing real-world workflows for test automation, secure coding, documentation, and CI/CD using VS Code and GitHub Actions.</i></sub>
