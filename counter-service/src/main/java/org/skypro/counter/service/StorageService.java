package org.skypro.counter.service;

import org.skypro.counter.exception.NoSuchProductException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StorageService {

    private static final List<String> PRODUCTS = List.of(
            "Ноутбук",
            "Смартфон",
            "Планшет",
            "Монитор",
            "Клавиатура"
    );

    public String getProductById(long id) {
        if (id < 1 || id > PRODUCTS.size()) {
            throw new NoSuchProductException("Product with id " + id + " not found");
        }
        return PRODUCTS.get((int) id - 1);
    }
}
