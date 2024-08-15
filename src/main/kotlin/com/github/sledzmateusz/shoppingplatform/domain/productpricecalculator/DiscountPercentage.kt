package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

data class DiscountPercentage private constructor(val value: Int) {

  init {
    require(value in 0..100) { throw InvalidDiscountPercentage() }
  }

  companion object {
    fun of(value: Int): DiscountPercentage {
      return DiscountPercentage(value)
    }
  }
}

class InvalidDiscountPercentage : RuntimeException("Discount percentage must be between 0 and 100")
