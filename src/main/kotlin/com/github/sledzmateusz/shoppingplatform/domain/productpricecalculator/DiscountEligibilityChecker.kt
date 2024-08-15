package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

class DiscountEligibilityChecker(discountsProvider: DiscountsProvider) {

  private val amountBasedDiscounts = discountsProvider.getAmountBasedDiscounts().sortedByDescending { it.threshold.value }

  fun getEligibleDiscounts(product: Product.RegularProduct): List<Discount> {
    val amountBasedDiscount = amountBasedDiscounts.firstOrNull { product.quantity.value >= it.threshold.value  }
    return amountBasedDiscount?.let { listOf(it) } ?: emptyList()
  }
}
