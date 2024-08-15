package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

interface DiscountsProvider {
  fun getAmountBasedDiscounts(): List<Discount.AmountBasedDiscount>
}
