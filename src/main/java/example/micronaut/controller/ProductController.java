package example.micronaut.controller;

import example.micronaut.model.Product;
import example.micronaut.service.ProductService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.validation.Validated;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Controller("/products")
@Validated
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Get
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Get("/{id}")
    public HttpResponse<Product> getProductById(@PathVariable Integer id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(HttpResponse::ok).orElse(HttpResponse.notFound());
    }

    @Post("/")
    public HttpResponse<Product> createProduct(@Body @Valid Product product) {
        Product createdProduct = productService.createProduct(product);
        return HttpResponse.status(HttpStatus.CREATED).body(createdProduct);
    }
}
