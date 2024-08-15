package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.ProductPriceDto
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Product.DiscountedProduct
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.AmountBasedDiscount.Companion as AmountBasedDiscount
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Product.RegularProduct.Companion as RegularProduct

class ProductPriceCalculator(
  private val productClient: ProductCatalogClient,
  private val discountService: DiscountService
) {

  private val discount: Discount = AmountBasedDiscount.of(10.0)

  fun calculatePrice(productId: ProductId, quantity: ProductQuantity): ProductPriceDto {
    val regularProduct = productClient.getProduct(productId).toRegularProduct(quantity)
    val discountedProduct = discountService.applyDiscount(regularProduct, discount)
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
