package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.AmountBasedDiscount
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.PercentageBasedDiscount
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Product.DiscountedProduct
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money

class DiscountService {

  fun applyDiscount(regularProduct: Product.RegularProduct, discount: Discount): DiscountedProduct {
    when (discount) {
      is AmountBasedDiscount -> {
        val baseTotalPrice = regularProduct.getBaseTotalPrice()
        val discountedTotalPrice = (baseTotalPrice - discount.amount).coerceAtLeast(Money.zero())
        return DiscountedProduct.create(regularProduct, discountedTotalPrice)
      }

      is PercentageBasedDiscount -> {
        val baseTotalPrice = regularProduct.getBaseTotalPrice()
        val discountedTotalPrice = baseTotalPrice.adjustByPercentage(discount.discountPercentage.value.toBigDecimal())
        return DiscountedProduct.create(regularProduct, discountedTotalPrice)
      }
    }
  }
}
