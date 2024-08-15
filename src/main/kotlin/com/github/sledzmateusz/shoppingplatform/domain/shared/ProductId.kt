package com.github.sledzmateusz.shoppingplatform.domain.shared

import java.util.UUID

data class ProductId(val raw: String) {

  init {
    require(raw.isValidUUID()) { throw InvalidProductIdException(raw) }
  }

  companion object {
    fun new(): ProductId {
      val uuid = UUID.randomUUID().toString()
      return ProductId(uuid)
    }
  }
}

class InvalidProductIdException(raw: String) : RuntimeException("Invalid Product ID: $raw")
