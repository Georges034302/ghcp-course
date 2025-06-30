# 🧪 GHCP Lab: Build, Test & Serve a RESTful API with Copilot + Bogus + Local UI

## 🎯 Lab Objective

Participants will:

✅ Build a `.NET 6` Web API (`ProductService`)  
✅ Implement endpoints to get all, get by ID, and add a product  
✅ Use **GitHub Copilot** to generate unit tests  
✅ Enhance tests using **Bogus** for fake data and edge-case validation  
✅ Serve a simple `index.html` UI alongside the API  
✅ Test the entire app locally via browser + Swagger + unit test console

---

## 🧰 Prerequisites

| Tool                    | Requirement                        |
|-------------------------|-------------------------------------|
| .NET SDK                | Version 6.0 or later                |
| Visual Studio Code      | Latest version                      |
| GitHub Copilot (VS Code Extension) | Installed and signed in with GitHub |
| C# Extension for VS Code| Installed                           |
| Git                     | Optional                            |

---

## ✅ PART 1: Create API Project and Implement Endpoints

### 🔧 Step 1: Scaffold the Project

```bash
dotnet new webapi -n ProductService
cd ProductService
code .
```

### 📁 Create Folder Structure

```bash
mkdir Models Services Controllers wwwroot
```

### 🧾 Step 2: Add Domain Model (`Models/Product.cs`)

```csharp
namespace ProductService.Models
{
    public record Product(int Id, string Name, decimal Price);
}
```

### 🔧 Step 3: Add Service Logic (`Services/ProductService.cs`)

```csharp
using ProductService.Models;

namespace ProductService.Services
{
    public class ProductService
    {
        private readonly List<Product> _products = new()
        {
            new Product(1, "Laptop", 1200.00M),
            new Product(2, "Phone", 800.00M)
        };

        public IEnumerable<Product> GetAll() => _products;

        public Product? GetById(int id) => _products.FirstOrDefault(p => p.Id == id);

        public void Add(Product product) => _products.Add(product);
    }
}
```

### 🌐 Step 4: Add API Controller (`Controllers/ProductController.cs`)

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
        private readonly ProductService _service = new();

        [HttpGet]
        public IActionResult Get() => Ok(_service.GetAll());

        [HttpGet("{id}")]
        public IActionResult Get(int id)
        {
            var product = _service.GetById(id);
            return product is null ? NotFound() : Ok(product);
        }

        [HttpPost]
        public IActionResult Post(Product product)
        {
            _service.Add(product);
            return CreatedAtAction(nameof(Get), new { id = product.Id }, product);
        }
    }
}
```

---

## ✅ PART 2: Add Static HTML UI (`wwwroot/index.html`)

```html
<!DOCTYPE html>
<html>
<head>
  <title>ProductService UI</title>
</head>
<body>
  <h1>ProductService</h1>
  <h2>Get All Products</h2>
  <button onclick="getAll()">Fetch Products</button>
  <pre id="all"></pre>
  <h2>Get Product by ID</h2>
  <input id="pid" placeholder="Enter ID" />
  <button onclick="getById()">Fetch Product</button>
  <pre id="one"></pre>
  <h2>Add Product</h2>
  <input id="name" placeholder="Name" />
  <input id="price" placeholder="Price" />
  <button onclick="addProduct()">Add</button>
  <pre id="add"></pre>

  <script>
    async function getAll() {
      const res = await fetch('/api/product');
      document.getElementById('all').innerText = JSON.stringify(await res.json(), null, 2);
    }
    async function getById() {
      const id = document.getElementById('pid').value;
      const res = await fetch(`/api/product/${id}`);
      document.getElementById('one').innerText = res.ok ? JSON.stringify(await res.json(), null, 2) : "Not found";
    }
    async function addProduct() {
      const name = document.getElementById('name').value;
      const price = parseFloat(document.getElementById('price').value);
      const res = await fetch('/api/product', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ id: Date.now(), name, price })
      });
      document.getElementById('add').innerText = res.ok ? "Added!" : "Failed";
    }
  </script>
</body>
</html>
```

### 🔧 Modify `Program.cs`

```csharp
app.UseDefaultFiles();
app.UseStaticFiles();
app.MapControllers();
```

### ▶️ Run the App Locally

```bash
dotnet run
```

---

## ✅ PART 3: Add Unit Tests with Copilot + Bogus

### 🧪 Step 1: Create Test Project

```bash
dotnet new xunit -n ProductService.Tests
cd ProductService.Tests
dotnet add reference ../ProductService/ProductService.csproj
dotnet add package Bogus
```

### 🧪 Step 2: Create `ProductServiceTests.cs`

```csharp
using ProductService.Models;
using ProductService.Services;
using Bogus;
using Xunit;

public class ProductServiceTests
{
    private Product GenerateFakeProduct()
    {
        var faker = new Faker<Product>()
            .CustomInstantiator(f => new Product(
                f.Random.Int(1000, 9999),
                f.Commerce.ProductName(),
                f.Random.Decimal(10, 999)));

        return faker.Generate();
    }

    // test GetAll returns a non-empty list of products
    // test GetById returns correct product for valid ID
    // test GetById returns null for unknown ID
    // test Add inserts a product and GetAll includes it
    // test Add with random product using Bogus
    // test Add with negative price
    // test Add with empty product name
}
```

### 🤖 Copilot Prompts and Explanations

Type these comments in the test class to trigger Copilot suggestions:

- `// test GetAll returns a non-empty list of products`
- `// test GetById returns correct product for valid ID`
- `// test GetById returns null for unknown ID`
- `// test Add inserts a product and GetAll includes it`
- `// test Add with random product using Bogus`
- `// test Add with negative price`
- `// test Add with empty product name`

### ✅ Run Tests

```bash
dotnet test
```

---

## ✅ Lab Summary

✔ Built a .NET 6 RESTful API with 3 endpoints  
✔ Served index.html via wwwroot  
✔ Used GitHub Copilot to scaffold unit tests  
✔ Used Bogus to create edge-case test data  
✔ Validated results via browser and `dotnet test`

### 👨‍💻 Author: Georges Bou Ghantous
This repository delivers GitHub Copilot training for Java & .NET developers, showcasing real-world workflows for test automation, secure coding, documentation, and CI/CD using VS Code and GitHub Actions.
