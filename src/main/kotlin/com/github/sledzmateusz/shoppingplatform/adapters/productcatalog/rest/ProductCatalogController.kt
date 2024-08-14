package com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest

import com.github.sledzmateusz.shoppingplatform.adapters.shared.logging.logger
import com.github.sledzmateusz.shoppingplatform.domain.ProductCatalogFacade
import com.github.sledzmateusz.shoppingplatform.domain.ProductDto
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("products/{id}")
class ProductController(private val facade: ProductCatalogFacade) {

  companion object {
    private val log = logger()
  }

  @GetMapping
  fun getProduct(@PathVariable("id") productId: ProductId): ProductResponse {
    log.info("GET product info $productId")
    return facade.getProductBy(productId).toResponse()
  }
}

data class ProductResponse(val id: String, val name: String)
fun ProductDto.toResponse() = ProductResponse(id = this.id, name = this.name)

class ProductNotFoundException(productId: ProductId) : RuntimeException("Product $productId not found")
