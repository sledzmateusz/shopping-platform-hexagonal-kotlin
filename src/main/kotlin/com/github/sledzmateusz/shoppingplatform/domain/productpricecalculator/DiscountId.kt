package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import com.github.sledzmateusz.shoppingplatform.domain.shared.isValidUUID
import java.util.UUID

data class DiscountId(val raw: String) {
  init {
    require(raw.isValidUUID()) { throw InvalidDiscountIdException(raw) }
  }

  companion object {
    fun new(): ProductId {
      val uuid = UUID.randomUUID().toString()
      return ProductId(uuid)
    }
  }
}

class InvalidDiscountIdException(raw: String) : RuntimeException("Invalid Discount ID: $raw")
