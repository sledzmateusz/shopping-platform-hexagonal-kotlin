package com.github.sledzmateusz.shoppingplatform.domain

import com.github.sledzmateusz.shoppingplatform.domain.productcatalog.Product
import com.github.sledzmateusz.shoppingplatform.domain.productcatalog.ProductRepository
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId

class ProductCatalogFacade(private val repository: ProductRepository) {

  fun getProductBy(id: ProductId): ProductDto {
    val product = repository.getById(id)
    return product.toDto()
  }
}

data class ProductDto(val id: ProductId, val name: String, val basePrice: Money)

fun Product.toDto() = ProductDto(id = this.id, name = this.name, basePrice = this.price)
