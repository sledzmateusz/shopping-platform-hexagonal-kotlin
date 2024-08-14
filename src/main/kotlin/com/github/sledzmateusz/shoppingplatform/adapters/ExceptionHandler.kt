package com.github.sledzmateusz.shoppingplatform.adapters

import com.github.sledzmateusz.shoppingplatform.adapters.rest.ProductNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

  @ExceptionHandler(ProductNotFoundException::class)
  fun handleProductNotFound(ex: ProductNotFoundException): ResponseEntity<String> =
    ResponseEntity(ex.message, HttpStatus.NOT_FOUND)
}
