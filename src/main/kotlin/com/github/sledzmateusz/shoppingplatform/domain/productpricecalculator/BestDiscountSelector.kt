package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Product.RegularProduct

class BestDiscountSelector(private val discountService: DiscountService) {

  fun getByLowestTotalPrice(product: RegularProduct, applicableDiscounts: List<Discount>): Product {
    val discountedProductWithLowestTotalPrice =
      applicableDiscounts
        .map { discount -> discountService.applyDiscount(product, discount) }
        .minByOrNull { it.discountedTotalPrice.amount }

    return discountedProductWithLowestTotalPrice ?: product
  }
}
