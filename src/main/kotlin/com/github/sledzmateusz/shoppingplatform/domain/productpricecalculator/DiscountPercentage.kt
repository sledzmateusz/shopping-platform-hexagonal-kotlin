package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

data class DiscountPercentage private constructor(val value: Double) {

  init {
    require(value in 0.0..100.0) { throw InvalidDiscountPercentage() }
  }

  companion object {
    fun of(value: Double): DiscountPercentage {
      return DiscountPercentage(value)
    }
  }
}

class InvalidDiscountPercentage : RuntimeException("Discount percentage must be between 0 and 100")