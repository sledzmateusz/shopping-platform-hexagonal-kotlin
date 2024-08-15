package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.client

import com.github.sledzmateusz.shoppingplatform.domain.ProductCatalogFacade
import com.github.sledzmateusz.shoppingplatform.domain.ProductDto
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.CatalogProduct
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.ProductCatalogClient
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import org.springframework.stereotype.Component

@Component
class InternalProductCatalogClient(private val facade: ProductCatalogFacade) : ProductCatalogClient {

  override fun getProduct(id: ProductId): CatalogProduct {
    val product = facade.getProductBy(id)
    return product.toDomain()
  }
}

private fun ProductDto.toDomain() = CatalogProduct(id = this.id, basePrice = this.basePrice)
