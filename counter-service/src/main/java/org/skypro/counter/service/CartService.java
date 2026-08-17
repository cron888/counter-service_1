package org.skypro.counter.service;

import org.skypro.counter.domain.Product;
import org.skypro.counter.domain.ProductBasket;
import org.skypro.counter.exception.NoSuchProductException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final StorageService storageService;
    private final ProductBasket productBasket;

    public CartService(StorageService storageService, ProductBasket productBasket) {
        this.storageService = storageService;
        this.productBasket = productBasket;
    }

    public void addProductToCart(long productId) {
        Product product = storageService.getProductById(productId);
        productBasket.addProduct(product);
    }

    public void removeProductFromCart(long productId) {
        productBasket.removeProduct(productId);
    }

    public List<Product> getUserBasket() {
        return productBasket.getProducts();
    }

    public void clearBasket() {
        productBasket.clear();
    }

    public boolean isBasketEmpty() {
        return productBasket.isEmpty();
    }
}
