package com.github.sledzmateusz.shoppingplatform.testutils

import com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest.TestProductId
import java.util.UUID

object Randomizer {

  fun randomProductId(): TestProductId {
    return UUID.randomUUID().toString()
  }

  fun invalidProductId(): TestProductId {
    return "invalid-UUID-string"
  }
}
