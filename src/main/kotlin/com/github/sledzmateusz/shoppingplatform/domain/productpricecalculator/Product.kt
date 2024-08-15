package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId

sealed class Product {

  data class RegularProduct(
    val id: ProductId,
    private val baseUnitPrice: Money,
    val quantity: ProductQuantity
  ) : Product() {

    companion object {
      fun from(catalogProduct: CatalogProduct, quantity: ProductQuantity): RegularProduct {
        return RegularProduct(
          id = catalogProduct.id,
          baseUnitPrice = catalogProduct.basePrice,
          quantity = quantity
        )
      }
    }

    fun getBaseTotalPrice(): Money {
      val baseTotalAmount = baseUnitPrice * quantity.value
      return baseTotalAmount
    }
  }

  class DiscountedProduct private constructor(
    val id: ProductId,
    val discountedTotalPrice: Money,
    val baseTotalPrice: Money,
  ) : Product() {

    companion object {
      fun create(
        regularProduct: RegularProduct,
        discountedTotalPrice: Money,
      ): DiscountedProduct {
        return DiscountedProduct(
          id = regularProduct.id,
          discountedTotalPrice = discountedTotalPrice,
          baseTotalPrice = regularProduct.getBaseTotalPrice()
        )
      }
    }
  }
}
