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
            int nextId = _products.Any() ? _products.Max(p => p.Id) + 1 : 1;
            var newProduct = product with { Id = nextId };
            _products.Add(newProduct);
        }
    }
}