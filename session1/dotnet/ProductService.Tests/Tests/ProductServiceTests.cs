using Xunit;
using ProductService.Services;
using ProductService.Models;
using Bogus;
using System.Linq;

namespace ProductService.Tests
{
    public class ProductServiceTests
    {
        private readonly ProductService.Services.ProductService _service;

        public ProductServiceTests()
        {
            _service = new ProductService.Services.ProductService();
        }

        [Fact]
        public void GetAll_ReturnsNonEmptyList()
        {
            var products = _service.GetAll();
            Assert.NotEmpty(products);
        }

        [Fact]
        public void GetById_ReturnsCorrectProduct()
        {
            var first = _service.GetAll().First();
            var result = _service.GetById(first.Id);
            Assert.NotNull(result);
            Assert.Equal(first.Id, result.Id);
            Assert.Equal(first.Name, result.Name);
        }

        [Fact]
        public void GetById_ReturnsNullForUnknownId()
        {
            var result = _service.GetById(-999);
            Assert.Null(result);
        }

        [Fact]
        public void Add_InsertsProduct()
        {
            var countBefore = _service.GetAll().Count;
            var newProduct = new Product(0, "Tablet", 199.99m);
            var added = _service.Add(newProduct);
            var all = _service.GetAll();
            Assert.Equal(countBefore + 1, all.Count);
            Assert.Contains(all, p => p.Id == added.Id && p.Name == "Tablet");
        }

        [Fact]
        public void Add_WithBogusProduct()
        {
            var faker = new Faker<Product>()
                .CustomInstantiator(f =>
                    new Product(0, f.Commerce.ProductName(), f.Random.Decimal(1, 1000))
                );
            var fakeProduct = faker.Generate();
            var added = _service.Add(fakeProduct);
            Assert.True(added.Id > 0);
            Assert.Equal(fakeProduct.Name, added.Name);
        }

        [Fact]
        public void Add_WithNegativePriceOrEmptyName()
        {
            var badProduct1 = new Product(0, "", 100m);
            var badProduct2 = new Product(0, "Bad", -50m);

            var added1 = _service.Add(badProduct1);
            var added2 = _service.Add(badProduct2);

            Assert.Equal("", added1.Name);
            Assert.Equal(-50m, added2.Price);
        }
    }
}