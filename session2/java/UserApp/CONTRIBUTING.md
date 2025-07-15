# Contributing to UserApp

Thank you for your interest in contributing to UserApp!  
This guide will help you get started with development and submitting changes.

## 🛠 Prerequisites

- Java 21
- Maven (recommended: use the included `mvnw` wrapper)
- Git

## 🚀 Getting Started

1. **Fork the repository** and clone your fork:
   ```bash
   git clone <your-fork-url>
   cd UserApp
   ```

2. **Install dependencies**:
   ```bash
   ./mvnw clean install
   ```

3. **Run the application locally**:
   ```bash
   ./mvnw spring-boot:run
   ```

## 📝 Making Changes

- Follow standard Java and Spring Boot coding conventions.
- Place new models in `src/main/java/com/example/userapp/model/`
- Place new controllers in `src/main/java/com/example/userapp/controller/`
- Place new services in `src/main/java/com/example/userapp/service/`
- Place new repositories in `src/main/java/com/example/userapp/repository/`

## ✅ Testing

- Add unit and integration tests in `src/test/java/`
- Run tests with:
  ```bash
  ./mvnw test
  ```

## 📦 Submitting a Pull Request

1. Create a new branch for your feature or fix:
   ```bash
   git checkout -b feature/your-feature-name
   ```
2. Commit your changes with clear messages.
3. Push your branch and open a pull request.

## 💡 Tips

- Keep pull requests focused and concise.
- Document any new endpoints or configuration changes.
- Ensure all tests pass before submitting.

Thank