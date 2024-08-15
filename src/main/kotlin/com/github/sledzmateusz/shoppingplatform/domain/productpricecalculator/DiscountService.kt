package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Product.DiscountedProduct
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money

class DiscountService {

  fun applyDiscount(regularProduct: Product.RegularProduct, discount: Discount): DiscountedProduct {
    when (discount) {
      is Discount.AmountBasedDiscount -> {
        val baseTotalPrice = regularProduct.getBaseTotalPrice()
        val discountedTotalPrice = (baseTotalPrice - discount.amount).coerceAtLeast(Money.zero())
        return DiscountedProduct.create(regularProduct, discountedTotalPrice)
      }
      is Discount.PercentageBasedDiscount -> TODO()
    }
  }
}
