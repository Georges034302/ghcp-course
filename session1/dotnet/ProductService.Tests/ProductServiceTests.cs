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