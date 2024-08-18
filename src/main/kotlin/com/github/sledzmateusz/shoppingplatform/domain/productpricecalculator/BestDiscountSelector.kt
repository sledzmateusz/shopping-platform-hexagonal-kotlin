package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Product.RegularProduct

class BestDiscountSelector(private val discountApplier: DiscountApplier) {

  fun getByLowestTotalPrice(product: RegularProduct, applicableDiscounts: List<Discount>): Product {
    val discountedProductWithLowestTotalPrice =
      applicableDiscounts
        .map { discount -> discountApplier.apply(product, discount) }
        .minByOrNull { it.discountedTotalPrice.amount }

    return discountedProductWithLowestTotalPrice ?: product
  }
}
