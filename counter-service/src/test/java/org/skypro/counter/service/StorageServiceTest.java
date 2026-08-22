package org.skypro.counter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.skypro.counter.domain.Product;
import org.skypro.counter.exception.NoSuchProductException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("StorageService Tests")
class StorageServiceTest {

    private StorageService storageService;

    @BeforeEach
    void setUp() {
        storageService = new StorageService();
    }

    @Nested
    @DisplayName("getProductById")
    class GetProductByIdTests {

        @Test
        @DisplayName("Возвращает товар при существующем ID")
        void getProductById_existingId_returnsProduct() {
            Product product = storageService.getProductById(1);

            assertNotNull(product);
            assertEquals(1, product.getId());
            assertEquals("Ноутбук", product.getName());
        }

        @Test
        @DisplayName("Возвращает товар при последнем существующем ID")
        void getProductById_lastExistingId_returnsProduct() {
            Product product = storageService.getProductById(5);

            assertNotNull(product);
            assertEquals(5, product.getId());
            assertEquals("Клавиатура", product.getName());
        }

        @Test
        @DisplayName("Выбрасывает NoSuchProductException при ID = 0")
        void getProductById_zeroId_throwsException() {
            assertThrows(NoSuchProductException.class, () -> {
                storageService.getProductById(0);
            });
        }

        @Test
        @DisplayName("Выбрасывает NoSuchProductException при отрицательном ID")
        void getProductById_negativeId_throwsException() {
            assertThrows(NoSuchProductException.class, () -> {
                storageService.getProductById(-5);
            });
        }

        @Test
        @DisplayName("Выбрасывает NoSuchProductException при ID больше доступных")
        void getProductById_idGreaterThanAvailable_throwsException() {
            assertThrows(NoSuchProductException.class, () -> {
                storageService.getProductById(100);
            });
        }
    }

    @Nested
    @DisplayName("getAllProducts")
    class GetAllProductsTests {

        @Test
        @DisplayName("Возвращает список всех товаров")
        void getAllProducts_returnsAllProducts() {
            List<Product> products = storageService.getAllProducts();

            assertNotNull(products);
            assertEquals(5, products.size());
        }

        @Test
        @DisplayName("Возвращает Products корректного типа")
        void getAllProducts_returnsCorrectProductTypes() {
            List<Product> products = storageService.getAllProducts();

            products.forEach(product -> {
                assertNotNull(product.getId());
                assertNotNull(product.getName());
                assertNotNull(product.getCategory());
                assertTrue(product.getPrice() >= 0);
            });
        }
    }
}
