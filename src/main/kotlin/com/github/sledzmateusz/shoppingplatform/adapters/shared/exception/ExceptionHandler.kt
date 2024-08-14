package com.github.sledzmateusz.shoppingplatform.adapters.shared.exception

import com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest.ProductNotFoundException
import com.github.sledzmateusz.shoppingplatform.domain.shared.InvalidProductIdException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandler {

  @ExceptionHandler(ProductNotFoundException::class)
  fun handleProductNotFound(ex: ProductNotFoundException): ResponseEntity<String> =
    ResponseEntity(ex.message, HttpStatus.NOT_FOUND)

  @ExceptionHandler(InvalidProductIdException::class)
  fun handleInvalidProductId(ex: InvalidProductIdException): ResponseEntity<String> =
    ResponseEntity(ex.message, HttpStatus.BAD_REQUEST)
}
