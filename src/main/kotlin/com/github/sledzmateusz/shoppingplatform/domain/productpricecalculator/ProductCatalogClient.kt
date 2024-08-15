package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId

interface ProductCatalogClient {
  fun getProduct(id: ProductId): CatalogProduct
}

data class CatalogProduct(val id: ProductId, val basePrice: Money)
