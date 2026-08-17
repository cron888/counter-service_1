package org.skypro.counter.controller;

import org.skypro.counter.dto.ShopError;
import org.skypro.counter.exception.NoSuchProductException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ShopControllerAdvice {

    @ExceptionHandler(NoSuchProductException.class)
    public ResponseEntity<ShopError> handleNoSuchProductException(NoSuchProductException ex) {
        ShopError error = new ShopError("PRODUCT_NOT_FOUND", ex.getMessage());
        return ResponseEntity.status(404).body(error);
    }
}
