package org.skypro.counter.domain;

import java.util.List;

public interface ProductBasket {

    void addProduct(Product product);

    void removeProduct(long productId);

    List<Product> getProducts();

    void clear();

    boolean isEmpty();
}
