package com.github.sledzmateusz.shoppingplatform.adapters.rest

import com.github.sledzmateusz.shoppingplatform.adapters.logging.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("products")
class ProductController {

  private val log = logger();
  private val dummyResponse: ProductResponse = ProductResponse(
    id = "aac7d817-93f0-4f6f-92c4-6752c95d23b0",
    name = "Sony WH-1000XM4 Wireless Noise-Cancelling Headphones"
  )

  @GetMapping("{id}")
  fun getProduct(@PathVariable("id") productId: ProductId): ProductResponse {
    log.info("GET product info $productId")
    if (productId.raw == dummyResponse.id) {
      return dummyResponse
    } else {
      throw ProductNotFoundException(productId);
    }
  }
}

data class ProductResponse(val id: String, val name: String)
data class ProductId(val raw: String)
class ProductNotFoundException(productId: ProductId) : RuntimeException("Product $productId not found")
