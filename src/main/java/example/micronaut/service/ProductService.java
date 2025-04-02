package example.micronaut.service;

import example.micronaut.model.Product;
import example.micronaut.repository.ProductRepository;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Integer id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
}
