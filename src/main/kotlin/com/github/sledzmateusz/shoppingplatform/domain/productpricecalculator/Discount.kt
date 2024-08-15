package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.shared.Money

sealed class Discount {

  data class AmountBasedDiscount(
    val amount: Money,
    val threshold: DiscountThreshold
  ): Discount() {
    companion object {
      fun of(amount: Money, threshold: DiscountThreshold): AmountBasedDiscount {
        return AmountBasedDiscount(amount, threshold)
      }
    }
  }

  data class PercentageBasedDiscount(
    val discountPercentage: DiscountPercentage
  ) : Discount()
}

