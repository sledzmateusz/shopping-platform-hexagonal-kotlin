package com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.inmemorydb

import com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest.ProductNotFoundException
import com.github.sledzmateusz.shoppingplatform.domain.productcatalog.Product
import com.github.sledzmateusz.shoppingplatform.domain.productcatalog.ProductRepository
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class InMemoryProductRepository : ProductRepository {

  private val productsMap = hashMapOf(
    productEntryFrom(
      id = ProductId("aac7d817-93f0-4f6f-92c4-6752c95d23b0"),
      name = "Sony WH-1000XM4 Wireless Noise-Cancelling Headphones",
      price = 599.99
    ),
    productEntryFrom(
      id = ProductId("aac7d817-93f0-4f6f-92c4-6752c95d23b1"),
      name = "Apple MacBook Pro 16-inch (2021)",
      price = 11999.99
    ),
    productEntryFrom(
      id = ProductId("aac7d817-93f0-4f6f-92c4-6752c95d23b2"),
      name = "Samsung Galaxy S21 Ultra 5G",
      price = 5299.99
    ),
    productEntryFrom(
      id = ProductId("aac7d817-93f0-4f6f-92c4-6752c95d23b3"),
      name = "Bose QuietComfort 35 II Wireless Bluetooth Headphones",
      price = 1299.00
    ),
    productEntryFrom(
      id = ProductId("aac7d817-93f0-4f6f-92c4-6752c95d23b4"),
      name = "Sony Alpha 7C Full-Frame Mirrorless Camera",
      price = 7699.99
    ),
  )

  override fun getById(id: ProductId): Product {
    val product = productsMap[id] ?: throw ProductNotFoundException(id)
    return product
  }
}

private fun productEntryFrom(id: ProductId = ProductId.new(), name: String, price: Double): Pair<ProductId, Product> {
  return id to Product(id, name, Money.from(BigDecimal.valueOf(price)))
}
