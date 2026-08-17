package org.skypro.counter.service;

import org.skypro.counter.domain.Product;
import org.skypro.counter.exception.NoSuchProductException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {

    private static final List<Product> PRODUCTS = List.of(
            new Product(1, "Ноутбук", "Электроника", 59990.0),
            new Product(2, "Смартфон", "Электроника", 39990.0),
            new Product(3, "Планшет", "Электроника", 29990.0),
            new Product(4, "Монитор", "Электроника", 19990.0),
            new Product(5, "Клавиатура", "Аксессуары", 4990.0)
    );

    public Product getProductById(long id) {
        if (id < 1 || id > PRODUCTS.size()) {
            throw new NoSuchProductException("Product with id " + id + " not found");
        }
        return PRODUCTS.get((int) id - 1);
    }

    public List<Product> getAllProducts() {
        return PRODUCTS;
    }
}
