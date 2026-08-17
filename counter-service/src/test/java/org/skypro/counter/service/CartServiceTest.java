package org.skypro.counter.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skypro.counter.domain.Product;
import org.skypro.counter.domain.ProductBasket;
import org.skypro.counter.exception.NoSuchProductException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("CartService Tests")
@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    @Mock
    private StorageService storageService;

    @Mock
    private ProductBasket productBasket;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        cartService = new CartService(storageService, productBasket);
    }

    @Nested
    @DisplayName("addProductToCart")
    class AddProductToCartTests {

        @Test
        @DisplayName("Выбрасывает исключение при добавлении несуществующего товара")
        void addProductToCart_nonExistentProduct_throwsException() {
            when(storageService.getProductById(999L)).thenThrow(
                    new NoSuchProductException("Product with id 999 not found")
            );

            assertThrows(NoSuchProductException.class, () -> {
                cartService.addProductToCart(999L);
            });

            verify(productBasket, never()).addProduct(any());
        }

        @Test
        @DisplayName("Добавляет существующий товар в корзину через ProductBasket")
        void addProductToCart_existingProduct_callsBasketAddProduct() {
            Product product = new Product(1, "Ноутбук", "Электроника", 59990.0);
            when(storageService.getProductById(1L)).thenReturn(product);

            cartService.addProductToCart(1L);

            verify(storageService).getProductById(1L);
            ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
            verify(productBasket).addProduct(captor.capture());

            Product capturedProduct = captor.getValue();
            assertEquals(1, capturedProduct.getId());
            assertEquals("Ноутбук", capturedProduct.getName());
            assertEquals(59990.0, capturedProduct.getPrice());
        }

        @Test
        @DisplayName("Добавляет несколько товаров в корзину")
        void addProductToCart_multipleProducts_callsBasketAddProductForEach() {
            Product product1 = new Product(1, "Ноутбук", "Электроника", 59990.0);
            Product product2 = new Product(2, "Смартфон", "Электроника", 39990.0);

            when(storageService.getProductById(1L)).thenReturn(product1);
            when(storageService.getProductById(2L)).thenReturn(product2);

            cartService.addProductToCart(1L);
            cartService.addProductToCart(2L);

            verify(productBasket, times(2)).addProduct(any());
        }

        @Test
        @DisplayName("Выбрасывает исключение при отрицательном ID")
        void addProductToCart_negativeId_throwsException() {
            when(storageService.getProductById(-1L)).thenThrow(
                    new NoSuchProductException("Product with id -1 not found")
            );

            assertThrows(NoSuchProductException.class, () -> {
                cartService.addProductToCart(-1L);
            });

            verify(productBasket, never()).addProduct(any());
        }
    }

    @Nested
    @DisplayName("removeProductFromCart")
    class RemoveProductFromCartTests {

        @Test
        @DisplayName("Удаляет товар из корзины")
        void removeProductFromCart_existingProduct_callsBasketRemove() {
            cartService.removeProductFromCart(1L);

            verify(productBasket).removeProduct(1L);
        }
    }

    @Nested
    @DisplayName("getUserBasket")
    class GetUserBasketTests {

        @Test
        @DisplayName("Возвращает пустой список, если корзина пуста")
        void getUserBasket_emptyBasket_returnsEmptyList() {
            when(productBasket.getProducts()).thenReturn(Collections.emptyList());

            List<Product> result = cartService.getUserBasket();

            assertNotNull(result);
            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Возвращает товары, если корзина заполнена")
        void getUserBasket_nonEmptyBasket_returnsProducts() {
            Product product1 = new Product(1, "Ноутбук", "Электроника", 59990.0);
            Product product2 = new Product(2, "Смартфон", "Электроника", 39990.0);
            List<Product> products = Arrays.asList(product1, product2);

            when(productBasket.getProducts()).thenReturn(products);

            List<Product> result = cartService.getUserBasket();

            assertNotNull(result);
            assertEquals(2, result.size());
            assertEquals("Ноутбук", result.get(0).getName());
            assertEquals("Смартфон", result.get(1).getName());
        }
    }

    @Nested
    @DisplayName("clearBasket")
    class ClearBasketTests {

        @Test
        @DisplayName("Очищает корзину")
        void clearBasket_callsBasketClear() {
            cartService.clearBasket();

            verify(productBasket).clear();
        }
    }

    @Nested
    @DisplayName("isBasketEmpty")
    class IsBasketEmptyTests {

        @Test
        @DisplayName("Возвращает true, если корзина пуста")
        void isBasketEmpty_emptyBasket_returnsTrue() {
            when(productBasket.isEmpty()).thenReturn(true);

            boolean result = cartService.isBasketEmpty();

            assertTrue(result);
        }

        @Test
        @DisplayName("Возвращает false, если корзина не пуста")
        void isBasketEmpty_nonEmptyBasket_returnsFalse() {
            when(productBasket.isEmpty()).thenReturn(false);

            boolean result = cartService.isBasketEmpty();

            assertFalse(result);
        }
    }
}
