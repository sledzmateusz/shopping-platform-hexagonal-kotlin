package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

data class DiscountThreshold private constructor(val value: Int) {
  init {
    require(value in 1..100) {
      throw InvalidDiscountThreshold()
    }
  }

  companion object {
    fun of(value: Int): DiscountThreshold {
      return DiscountThreshold(value)
    }
  }
}

class InvalidDiscountThreshold : RuntimeException("Discount threshold must be between 1 and 100")
