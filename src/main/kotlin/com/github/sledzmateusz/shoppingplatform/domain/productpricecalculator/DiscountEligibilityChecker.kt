package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

class DiscountEligibilityChecker(discountsProvider: DiscountsProvider) {

  private val amountBasedDiscounts =
    discountsProvider.getAmountBasedDiscounts().sortedByDescending { it.threshold.value }

  private val percentageBasedDiscounts =
    discountsProvider.getPercentageBasedDiscounts()

  fun getEligibleDiscounts(product: Product.RegularProduct): List<Discount> {
    val eligibleAmountBasedDiscount = amountBasedDiscounts.firstOrNull { product.quantity.value >= it.threshold.value }
    val eligiblePercentageBasedDiscounts = percentageBasedDiscounts.filter { product.id in it.productIds }

    return listOfNotNull(eligibleAmountBasedDiscount) + eligiblePercentageBasedDiscounts
  }
}
