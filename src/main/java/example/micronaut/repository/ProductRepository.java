package example.micronaut.repository;

import example.micronaut.model.Product;
import jakarta.inject.Singleton;
import java.util.*;

@Singleton
public class ProductRepository {

    private final Map<Integer, Product> productDB = new HashMap<>();

    public ProductRepository() {
        productDB.put(1, new Product(1, "Laptop", 1200.99));
        productDB.put(2, new Product(2, "Mobile", 800.50));
    }

    public List<Product> findAll() {
        return new ArrayList<>(productDB.values());
    }

    public Optional<Product> findById(Integer id) {
        return Optional.ofNullable(productDB.get(id));
    }

    public Product save(Product product) {
        productDB.put(product.getId(), product);
        return product;
    }
}
