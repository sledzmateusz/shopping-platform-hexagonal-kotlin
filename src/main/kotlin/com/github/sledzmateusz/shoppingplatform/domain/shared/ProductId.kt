package com.github.sledzmateusz.shoppingplatform.domain.shared

data class ProductId(val raw: String) {

  init {
    require(raw.isValidUUID()) { throw InvalidProductIdException(raw) }
  }
}

class InvalidProductIdException(raw: String) : RuntimeException("Invalid Product ID: $raw")
