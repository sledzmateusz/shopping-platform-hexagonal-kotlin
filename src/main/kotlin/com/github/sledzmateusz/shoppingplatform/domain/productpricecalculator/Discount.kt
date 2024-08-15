package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import java.math.BigDecimal
import java.util.Currency

sealed class Discount {

  data class AmountBasedDiscount(
    val amount: Money
  ): Discount() {
    companion object {
      fun of(value: Double, currency: Currency = Currency.getInstance("PLN")): AmountBasedDiscount {
        return AmountBasedDiscount(Money(BigDecimal.valueOf(value), currency))
      }
    }
  }

  data class PercentageBasedDiscount(
    val discountPercentage: DiscountPercentage
  ) : Discount()
}

data class DiscountPercentage private constructor(val value: Double) {

  init {
    require(value in 0.0..100.0) { "Discount percentage must be between 0 and 100" }
  }

  companion object {
    fun of(value: Double): DiscountPercentage {
      return DiscountPercentage(value)
    }
  }
}
