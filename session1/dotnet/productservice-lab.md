# 🚀 GHCP Instructor Demo – ProductService (.NET 6) with Copilot, Bogus, Local UI

## 🧱 Overview

| Component | Description |
|-----------|-------------|
| App       | .NET 8 Web API (`ProductService`) |
| Data      | `Product` model (Id, Name, Price), in-memory list |
| API       | `GET /api/product`, `GET /api/product/{id}`, `POST /api/product` |
| UI        | HTML/JS in `wwwroot/index.html` served with API |
| Test      | xUnit tests with **Bogus** for test data |
| Copilot   | Automated code/test generation |
| CI        | GitHub Actions for **build + test** |
| Tools     | VS Code, IntelliJ, Eclispse, GitHub Copilot, .NET 8 SDK |

---

## 🎯 Learning Objectives

- Build a RESTful .NET 8 Web API (`ProductService`)
- Implement endpoints: list all, get by ID, add product
- Generate unit tests with **Copilot**; enhance with **Bogus** for fake/edge-case data
- Serve an interactive HTML UI for local testing
- Run and validate via browser, Swagger, and unit test console

---

## ✅ Step 1: Scaffold the .NET API Project
> *Copilot Prompt:\
> Generate the CLI commands to create a new .NET 8 Web API project called `ProductService` in the current directory.\
> Then create the folders: `Models`, `Services`, `Controllers`, and `wwwroot` inside the project folder.*

**✅ Expected CLI:**
```bash
dotnet new webapi -n ProductService
cd ProductService
mkdir Models Services Controllers wwwroot
```

**✅ Expected Outcome - Project Structure:**
```
ProductService/
├── Controllers/
│   └── WeatherForecastController.cs
├── Models/
├── Services/
├── wwwroot/
├── ProductService.csproj
├── Program.cs
├── appsettings.Development.json
├── appsettings.json
├── WeatherForecast.cs
└── Properties/
    └── launchSettings.json
```

---

## ✅ Step 2: Implement Model, Service, and Controller (with Copilot Prompts)

### 2.1 Create the Product Model

> *Copilot Prompt:\
> Create a new file `Product.cs` in `/workspaces/ghcp-course/session1/dotnet/ProductService/Models`\
> `Product.cs` includes the record `Product`.  
> The record should have properties: `Id` (int), `Name` (string), `Price` (decimal).  
> Use the `record` keyword for concise syntax.  
> Add a namespace of `ProductService.Models`.*

**✅ Expected Outcome:**

```csharp
namespace ProductService.Models
{
    public record Product(int Id, string Name, decimal Price);
}
```

---

### 2.2 Implement ProductService

> *Copilot Prompt:\
> Create a `ProductService` class in `Services/ProductService.cs`.  
> Add a private readonly `List<Product>` initialized with two products (e.g., Laptop, Phone).  
> Provide methods:\
> `GetAll` (returns all products)\
> `GetById(int id)` (returns the product with that ID or null)\
> `Add(Product product)` (Get the last added product (with correct ID) and increment new id by 1).  
> Use namespace `ProductService.Services`.*
> Use namespace `ProductService.Services`.*

**✅ Expected Outcome:**

```csharp
using ProductService.Models;
using System.Collections.Generic;
using System.Linq;

namespace ProductService.Services
{
    public class ProductService
    {
        private readonly List<Product> _products = new()
        {
            new Product(1, "Laptop", 1200.00m),
            new Product(2, "Phone", 800.00m)
        };

        public List<Product> GetAll()
        {
            return _products;
        }

        public Product? GetById(int id)
        {
            return _products.FirstOrDefault(p => p.Id == id);
        }

        public void Add(Product product)
        {
            _products.Add(product);
        }
    }
}
```

---

### 2.3 Create the Controller

> *Copilot Prompt:  
> Generate a `ProductController` class in `Controllers/ProductController.cs`.  
> Annotate with `[ApiController]` and `[Route("api/[controller]")]`.  
> Inject a `ProductService` instance (direct or via DI).  
> Implement:  
> `GET /api/product` (returns all products as JSON)  
> `GET /api/product/{id}` (returns product by ID or 404)  
> `POST /api/product` (accepts Product, adds to list, returns CreatedAtAction).  
> Use correct using statements and namespace `ProductService.Controllers`.*

**✅ Expected Outcome:**

```csharp
using Microsoft.AspNetCore.Mvc;
using ProductService.Models;
using ProductService.Services;

namespace ProductService.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class ProductController : ControllerBase
    {
        private readonly ProductService.Services.ProductService _service;

        public ProductController(ProductService.Services.ProductService service)
        {
            _service = service;
        }

        [HttpGet]
        public ActionResult<IEnumerable<Product>> Get()
        {
            return Ok(_service.GetAll());
        }

        [HttpGet("{id}")]
        public ActionResult<Product> Get(int id)
        {
            var product = _service.GetById(id);
            if (product == null)
                return NotFound();
            return Ok(product);
        }

        [HttpPost]
        public ActionResult<Product> Post(Product product)
        {
            _service.Add(product);
            return CreatedAtAction(nameof(Get), new { id = product.Id }, product);
        }
    }
}
```

---

## ✅ Step 3: Serve a Static HTML UI (`wwwroot/index.html`)

> *Copilot Prompt:\
> Create a simple `index.html` file for `wwwroot` that:  
> Shows "ProductService" title  
> Lets users fetch all products, fetch by ID, and add a product (name + price fields)  
> Calls the API endpoints (`/api/product`, `/api/product/{id}`) using `fetch`  
> Displays results in `<pre>` tags  
> Keep the JavaScript inline in the file.*

**✅ Expected Outcome:**

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>ProductService</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 2em; }
        input, button { margin: 0.2em; }
        pre { background: #f4f4f4; padding: 1em; }
    </style>
</head>
<body>
    <h1>ProductService</h1>

    <section>
        <button onclick="fetchAll()">Fetch All Products</button>
        <input type="number" id="fetchId" placeholder="Product ID">
        <button onclick="fetchById()">Fetch By ID</button>
    </section>

    <section>
        <h3>Add Product</h3>
        <input type="text" id="addName" placeholder="Name">
        <input type="number" id="addPrice" placeholder="Price" step="0.01">
        <button onclick="addProduct()">Add</button>
    </section>

    <h3>Result:</h3>
    <pre id="result"></pre>

    <script>
        function fetchAll() {
            fetch('/api/product')
                .then(res => res.json())
                .then(data => document.getElementById('result').textContent = JSON.stringify(data, null, 2))
                .catch(err => document.getElementById('result').textContent = err);
        }

        function fetchById() {
            const id = document.getElementById('fetchId').value;
            if (!id) return;
            fetch(`/api/product/${id}`)
                .then(res => {
                    if (!res.ok) throw new Error('Not found');
                    return res.json();
                })
                .then(data => document.getElementById('result').textContent = JSON.stringify(data, null, 2))
                .catch(err => document.getElementById('result').textContent = err);
        }

        function addProduct() {
            const name = document.getElementById('addName').value;
            const price = parseFloat(document.getElementById('addPrice').value);
            if (!name || isNaN(price)) return;
            fetch('/api/product', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ id: 0, name, price })
            })
            .then(res => res.json())
            .then(data => document.getElementById('result').textContent = JSON.stringify(data, null, 2))
            .catch(err => document.getElementById('result').textContent = err);
        }
    </script>
</body>
</html>
```

---

## ✅ Step 4: Run and Test the API Locally

> *Copilot Prompt:\
> Update my `Program.cs` so that my ASP.NET Core app serves static files from the `wwwroot` folder, with `index.html` as the default page.  
> Add the following middleware after building the app and before `app.Run();`:  
> `app.UseDefaultFiles();`  
> `app.UseStaticFiles();`  
> `app.MapControllers();`  
> Make sure the rest of my API and Swagger setup stays the same.*

**✅ Expected Outcome:**
*The following are added to `Program.cs`*

```csharp
app.UseDefaultFiles();
app.UseStaticFiles();
app.MapControllers();
```

---

## ✅ Step 5: Run and Test the API Locally

> *Copilot Prompt:\
> How do I run and test my .NET 8 Web API and static HTML UI locally using the CLI?*

**✅ Expected Outcome:**

```bash
dotnet run
```

> *Copilot Prompt:\
> List the API endpoints for my ProductService controller so I can test them with curl, Postman, or a browser.  
> GET all products: `/api/product`  
> GET product by ID: `/api/product/{id}`  
> Show the full sample URLs using `https://myserver.com`.*

**✅ Expected Outcome:**

- GET all products: https://myserver.com/api/product
- GET product by {id}: https://myserver.com/api/product/{id}

---

## ✅ Step 6: Add Unit Tests with Copilot + Bogus

### 5.1 Create the Test Project

> *Copilot Prompt:\
> How do I add an xUnit test project for my .NET 8 solution and reference the main API project (e.g. dotnet)?\
> Also, add the `Bogus` NuGet package for fake data.*

**✅ Expected Outcome:**

```bash
cd /<path-to-dir>/dotnet
dotnet new xunit -n ProductService.Tests
cd ProductService.Tests
dotnet add reference ../ProductService/ProductService.csproj
dotnet add package Bogus
```

**✅ Expected Outcome:**

```
ProductService.Tests/
├── ProductService.Tests.csproj
└── ProductServiceTests.cs
```

---

### 5.2 Write Unit Tests in `ProductServiceTests.cs`

> *Copilot Prompt:\
> Create Test file `ProductService.Tests/ProductServiceTests.cs`, in ProductService.Tests.
> Write xUnit tests for ProductService.  
> Use `Bogus` to generate fake Product data  
> Cover:  
> GetAll returns non-empty list  
> GetById returns correct Product for valid ID  
> GetById returns null for unknown ID  
> Add inserts a Product (verify GetAll includes it)  
> Add with random Product from Bogus  
> (Bonus) Add with negative price or empty name (edge case)*

**✅ Expected Outcome:**

```csharp
using ProductService.Models;
using ProductService.Services;
using Bogus;
using Xunit;
using System.Linq;

public class ProductServiceTests
{
    private ProductService.Services.ProductService CreateService() => new();

    private Product GenerateFakeProduct()
    {
        var faker = new Faker<Product>()
            .CustomInstantiator(f => new Product(
                0,
                f.Commerce.ProductName(),
                f.Random.Decimal(1, 1000)
            ));
        return faker.Generate();
    }

    [Fact]
    public void GetAll_ReturnsNonEmptyList()
    {
        var service = CreateService();
        var products = service.GetAll();
        Assert.NotEmpty(products);
    }

    [Fact]
    public void GetById_ReturnsCorrectProduct_ForValidId()
    {
        var service = CreateService();
        var product = service.GetById(1);
        Assert.NotNull(product);
        Assert.Equal(1, product.Id);
    }

    [Fact]
    public void GetById_ReturnsNull_ForUnknownId()
    {
        var service = CreateService();
        var product = service.GetById(9999);
        Assert.Null(product);
    }

    [Fact]
    public void Add_InsertsProduct_VerifyGetAllIncludesIt()
    {
        var service = CreateService();
        var newProduct = new Product(0, "TestProduct", 123.45m);
        service.Add(newProduct);
        Assert.Contains(service.GetAll(), p => p.Name == "TestProduct" && p.Price == 123.45m);
    }

    [Fact]
    public void Add_WithRandomProductFromBogus()
    {
        var service = CreateService();
        var fakeProduct = GenerateFakeProduct();
        service.Add(fakeProduct);
        Assert.Contains(service.GetAll(), p => p.Name == fakeProduct.Name && p.Price == fakeProduct.Price);
    }

    [Fact]
    public void Add_WithNegativePriceOrEmptyName_EdgeCase()
    {
        var service = CreateService();
        var negativePriceProduct = new Product(0, "BadProduct", -50m);
        var emptyNameProduct = new Product(0, "", 10m);

        service.Add(negativePriceProduct);
        service.Add(emptyNameProduct);

        Assert.Contains(service.GetAll(), p => p.Name == "BadProduct" && p.Price == -50m);
        Assert.Contains(service.GetAll(), p => p.Name == "" && p.Price == 10m);
    }
}
```

---

### 5.3 Run the Unit Tests

> *Copilot Prompt:\
> Change directory (`cd`) to the `ProductService.Tests` folder (from any location).  
> Add the `Bogus` NuGet package (latest available version) to the test project using the CLI.  
> Then, run all xUnit tests from the CLI for the project.*


**✅ Expected Outcome:**

```bash
cd /<path-to-dir>/ProductService.Tests
dotnet test
```

All tests should pass and output should show in the terminal.

---

## ✅ Step 7: Add GitHub Actions CI for Build & Test

> *Copilot Prompt:\
> Provide CLI to create`.github/workflows/ci.yml` file in the root github repository.  
> Generate a GitHub Actions workflow in `ci.yaml` that:  
> Uses the latest Ubuntu runner  
> Checks out my code  
> Sets up .NET 8  
> Restores dependencies  
> Builds the solution  
> Runs all tests with `dotnet test` in /workspaces/ghcp-course/session1/dotnet/ProductService.Tests 
> The workflow should work for a .NET 8 Web API with a separate test project.*

**✅ Expected Outcome:**

```bash
cd /workspaces/ghcp-course
mkdir -p .github/workflows
touch .github/workflows/ci.yml
```

```yaml
name: .NET 8 CI

on:
  push:
    branches: [ "main" ]
  pull_request:
    branches: [ "main" ]

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - name: Checkout code
        uses: actions/checkout@v3

      - name: Setup .NET 8
        uses: actions/setup-dotnet@v3
        with:
          dotnet-version: '8.0.x'

      - name: Restore dependencies
        run: dotnet restore

      - name: Build
        run: dotnet build --no-restore --configuration Release

      - name: Test
        run: dotnet test --no-build --configuration Release
```

---

## ✅ Summary Table

| Task                    | Tool / Copilot Prompt                | ✅ Expected Outcome                   |
|-------------------------|--------------------------------------|--------------------------------------|
| Build .NET API          | Scaffold + Copilot                   | Working .NET API with endpoints      |
| Create Model/Service    | Copilot (elaborate prompts)          | Well-structured model/service        |
| Write Unit Tests        | Copilot + Bogus (elaborate prompts)  | Automated xUnit tests                |
| Local UI                | Copilot (HTML prompt)                | Manual test page in browser          |
| CI                      | Copilot (YAML prompt)                | GitHub Actions build+test            |
| Test via Browser        | None                                 | API and UI reachable in browser      |

---