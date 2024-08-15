package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.AmountBasedDiscount
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.PercentageBasedDiscount

interface DiscountsProvider {
  fun getAmountBasedDiscounts(): List<AmountBasedDiscount>
  fun getPercentageBasedDiscounts(): List<PercentageBasedDiscount>
}
