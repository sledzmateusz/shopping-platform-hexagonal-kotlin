package com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component

@Component
class ProductCatalogRestClient(
  private val restTemplate: TestRestTemplate
) {

  fun getProductBy(productId: TestProductId): ResponseEntity<ProductResponse> =
    restTemplate.getForEntity(productId.toUrl(), ProductResponse::class.java)

  fun getNotExistingProduct(productId: TestProductId): ResponseEntity<String> =
    restTemplate.getForEntity(productId.toUrl(), String::class.java)

  private fun TestProductId.toUrl() = "/products/$this"
}

typealias TestProductId = String
