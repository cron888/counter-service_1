package org.skypro.counter.service;

import org.skypro.counter.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {

    private final StorageService storageService;

    public SearchService(StorageService storageService) {
        this.storageService = storageService;
    }

    public List<Product> searchByName(String name) {
        List<Product> allProducts = storageService.getAllProducts();
        if (name == null || name.trim().isEmpty()) {
            return allProducts;
        }
        String lowerName = name.toLowerCase();
        return allProducts.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerName))
                .collect(Collectors.toList());
    }

    public List<Product> searchByCategory(String category) {
        List<Product> allProducts = storageService.getAllProducts();
        if (category == null || category.trim().isEmpty()) {
            return allProducts;
        }
        String lowerCategory = category.toLowerCase();
        return allProducts.stream()
                .filter(p -> p.getCategory().toLowerCase().equals(lowerCategory))
                .collect(Collectors.toList());
    }

    public List<Product> search(String query) {
        List<Product> allProducts = storageService.getAllProducts();
        if (query == null || query.trim().isEmpty()) {
            return allProducts;
        }
        String lowerQuery = query.toLowerCase();
        return allProducts.stream()
                .filter(p -> p.getName().toLowerCase().contains(lowerQuery)
                        || p.getCategory().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }
}
