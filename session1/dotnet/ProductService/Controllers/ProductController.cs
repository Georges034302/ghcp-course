using Microsoft.AspNetCore.Mvc;
using ProductService;
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
            var added = _service.GetAll().Last(); // Get the last added product (with correct ID)
            return CreatedAtAction(nameof(Get), new { id = added.Id }, added);
        }
    }
}