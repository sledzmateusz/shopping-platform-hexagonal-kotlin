package com.github.sledzmateusz.shoppingplatform.domain.productcatalog

import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId

interface ProductRepository {
  fun getById(id: ProductId): Product
}
