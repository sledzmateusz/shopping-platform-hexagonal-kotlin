package com.github.sledzmateusz.shoppingplatform.domain

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.ProductPriceCalculator
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.ProductQuantity
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId

class ProductPriceCalculatorFacade(private val priceCalculator: ProductPriceCalculator) {

  fun getProductPrice(productId: ProductId, quantity: ProductQuantity): ProductPriceDto {
    return priceCalculator.calculatePrice(productId, quantity)
  }
}

data class ProductPriceDto(val id: ProductId, val regularTotalAmount: Money, val discountedTotalAmount: Money?)

