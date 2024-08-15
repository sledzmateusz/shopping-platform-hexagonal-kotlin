package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.ProductPriceDto
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Product.DiscountedProduct
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Product.RegularProduct.Companion as RegularProduct

class ProductPriceCalculator(
  private val productClient: ProductCatalogClient,
  private val discountService: DiscountService,
  private val discountEligibilityChecker: DiscountEligibilityChecker,
) {

  fun calculatePrice(productId: ProductId, quantity: ProductQuantity): ProductPriceDto {
    val regularProduct = productClient.getProduct(productId).toRegularProduct(quantity)
    val eligibleDiscounts = discountEligibilityChecker.getEligibleDiscounts(regularProduct)
    val discountedProduct = eligibleDiscounts
      .map { discountService.applyDiscount(regularProduct, it) }
      .firstOrNull() ?: regularProduct //take regular product if there is no applicable discount

    return discountedProduct.toDto()
  }
}

private fun CatalogProduct.toRegularProduct(quantity: ProductQuantity) =
  RegularProduct.from(this, quantity)

fun Product.toDto(): ProductPriceDto {
  return when (this) {
    is DiscountedProduct -> ProductPriceDto(
      id = this.id,
      regularTotalAmount = this.baseTotalPrice,
      discountedTotalAmount = this.discountedTotalPrice,
    )

    is Product.RegularProduct -> ProductPriceDto(
      id = this.id,
      regularTotalAmount = this.getBaseTotalPrice(),
      discountedTotalAmount = null
    )
  }
}
