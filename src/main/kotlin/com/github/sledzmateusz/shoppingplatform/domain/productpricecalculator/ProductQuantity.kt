package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

data class ProductQuantity private constructor(val value: Int) {

  init {
    require(value in 1..100) {
      throw InvalidProductQuantityException()
    }
  }

  companion object {
    fun of(value: Int): ProductQuantity {
      return ProductQuantity(value)
    }
  }
}

class InvalidProductQuantityException :
  RuntimeException("Quantity must be between 1 and 100")
