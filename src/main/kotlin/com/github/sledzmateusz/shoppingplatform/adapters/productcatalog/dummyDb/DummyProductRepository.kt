package com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.dummyDb

import com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest.ProductNotFoundException
import com.github.sledzmateusz.shoppingplatform.domain.productcatalog.Product
import com.github.sledzmateusz.shoppingplatform.domain.productcatalog.ProductRepository
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
class DummyProductRepository : ProductRepository {

  private val dummyResponse: Product = Product(
    id = ProductId("aac7d817-93f0-4f6f-92c4-6752c95d23b0"),
    name = "Sony WH-1000XM4 Wireless Noise-Cancelling Headphones",
    price = Money(amount = BigDecimal.valueOf(500))
  )

  override fun getById(id: ProductId): Product {
    if (id == dummyResponse.id) {
      return dummyResponse
    } else {
      throw ProductNotFoundException(id);
    }
  }
}
