package org.skypro.counter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.counter.domain.Product;

<<<<<<< HEAD
import java.util.Arrays;
=======
>>>>>>> CSHW
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("SearchService Tests")
@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    private StorageService storageService;

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        searchService = new SearchService(storageService);
    }

    @Nested
    @DisplayName("searchByName")
    class SearchByNameTests {

        @Test
        @DisplayName("Возвращает пустой список, если StorageService пуст")
        void searchByName_emptyStorage_returnsEmptyList() {
            when(storageService.getAllProducts()).thenReturn(Collections.emptyList());

            List<Product> result = searchService.searchByName("Ноутбук");

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Возвращает пустой список, если нет подходящих товаров")
        void searchByName_noMatchingProducts_returnsEmptyList() {
            List<Product> products = List.of(
                    new Product(1, "Ноутбук", "Электроника", 59990.0),
                    new Product(2, "Смартфон", "Электроника", 39990.0)
            );
            when(storageService.getAllProducts()).thenReturn(products);

            List<Product> result = searchService.searchByName("Телевизор");

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Возвращает товар при совпадении названия")
        void searchByName_matchingProduct_returnsProduct() {
            List<Product> products = List.of(
                    new Product(1, "Ноутбук", "Электроника", 59990.0),
                    new Product(2, "Смартфон", "Электроника", 39990.0)
            );
            when(storageService.getAllProducts()).thenReturn(products);

            List<Product> result = searchService.searchByName("Ноутбук");

            assertEquals(1, result.size());
            assertEquals("Ноутбук", result.get(0).getName());
        }

        @Test
        @DisplayName("Игнорирует регистр при поиске")
        void searchByName_caseInsensitive_searchesIgnoreCase() {
            List<Product> products = List.of(
                    new Product(1, "Ноутбук", "Электроника", 59990.0),
                    new Product(2, "Смартфон", "Электроника", 39990.0)
            );
            when(storageService.getAllProducts()).thenReturn(products);

            List<Product> result = searchService.searchByName("ноутбук");

            assertEquals(1, result.size());
            assertEquals("Ноутбук", result.get(0).getName());
        }

        @Test
        @DisplayName("Возвращает все товары при пустом запросе")
        void searchByName_emptyQuery_returnsAllProducts() {
            List<Product> products = List.of(
                    new Product(1, "Ноутбук", "Электроника", 59990.0),
                    new Product(2, "Смартфон", "Электроника", 39990.0)
            );
            when(storageService.getAllProducts()).thenReturn(products);

            List<Product> result = searchService.searchByName("");

            assertEquals(2, result.size());
        }

        @Test
        @DisplayName("Возвращает все товары при null запросе")
        void searchByName_nullQuery_returnsAllProducts() {
            List<Product> products = List.of(
                    new Product(1, "Ноутбук", "Электроника", 59990.0)
            );
            when(storageService.getAllProducts()).thenReturn(products);

            List<Product> result = searchService.searchByName(null);

            assertEquals(1, result.size());
        }
    }

    @Nested
    @DisplayName("searchByCategory")
    class SearchByCategoryTests {

        @Test
        @DisplayName("Возвращает пустой список, если нет товаров в категории")
        void searchByCategory_noProductsInCategory_returnsEmptyList() {
            List<Product> products = List.of(
                    new Product(1, "Ноутбук", "Электроника", 59990.0)
            );
            when(storageService.getAllProducts()).thenReturn(products);

            List<Product> result = searchService.searchByCategory("Мебель");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Возвращает товары по категории")
        void searchByCategory_matchingCategory_returnsProducts() {
            List<Product> products = List.of(
                    new Product(1, "Ноутбук", "Электроника", 59990.0),
                    new Product(2, "Смартфон", "Электроника", 39990.0),
                    new Product(3, "Клавиатура", "Аксессуары", 4990.0)
            );
            when(storageService.getAllProducts()).thenReturn(products);

            List<Product> result = searchService.searchByCategory("Электроника");

            assertEquals(2, result.size());
        }
    }

    @Nested
    @DisplayName("search")
    class SearchTests {

        @Test
        @DisplayName("Ищет по названию и категории")
        void search_queriesByNameAndCategory() {
            List<Product> products = List.of(
                    new Product(1, "Ноутбук", "Электроника", 59990.0),
                    new Product(2, "Мышь", "Аксессуары", 1990.0)
            );
            when(storageService.getAllProducts()).thenReturn(products);

            List<Product> resultByName = searchService.search("Мышь");
            List<Product> resultByCategory = searchService.search("Электроника");

            assertEquals(1, resultByName.size());
            assertEquals("Мышь", resultByName.get(0).getName());

            assertEquals(1, resultByCategory.size());
            assertEquals("Ноутбук", resultByCategory.get(0).getName());
        }
    }
}
