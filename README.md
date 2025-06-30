# ghcp-course
GitHub Copilot for Java & .NET Web Developers – GHCP Training Series. This course delivers 2 comprehensive sessions on integrating GitHub Copilot into enterprise workflows for test automation, secure coding, documentation, and performance optimization using Visual Studio Code and GitHub Actions.

---

<details>
<summary><strong>Session 1 – Demo</strong> (click to expand/hide)</summary>

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
<summary><strong>Session 2 – Demo</strong> (click to expand/hide)</summary>

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

---
#### 🧑‍🏫 Author: Georges Bou Ghantous
<sub><i>This repository delivers GitHub Copilot training for Java & .NET developers, showcasing real-world workflows for test automation, secure coding, documentation, and CI/CD using VS Code and GitHub Actions.</i></sub>
