using ProductService.Models;
using System.Collections.Generic;
using System.Linq;

namespace ProductService.Services
{
    public class ProductService
    {
        private readonly List<Product> _products = new()
        {
            new Product(1, "Laptop", 999.99m),
            new Product(2, "Phone", 499.99m)
        };

        public List<Product> GetAll()
        {
            return _products;
        }

        public Product? GetById(int id)
        {
            return _products.FirstOrDefault(p => p.Id == id);
        }

        public Product Add(Product product)
        {
            int newId = _products.Any() ? _products.Max(p => p.Id) + 1 : 1;
            var newProduct = product with { Id = newId };
            _products.Add(newProduct);
            return newProduct;
        }
    }
}