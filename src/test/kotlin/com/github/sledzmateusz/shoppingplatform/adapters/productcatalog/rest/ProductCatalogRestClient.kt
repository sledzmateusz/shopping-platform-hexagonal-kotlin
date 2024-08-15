package com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

class ProductCatalogRestClient(
  private val testRestTemplate: TestRestTemplate
) {

  fun getProductBy(productId: TestProductId): ResponseEntity<ProductResponse> =
    testRestTemplate.getForEntity(productId.toUrl(), ProductResponse::class.java)

  fun getNotExistingProduct(productId: TestProductId): ResponseEntity<String> =
    testRestTemplate.getForEntity(productId.toUrl(), String::class.java)

  private fun TestProductId.toUrl() = "/products/$this"
}

typealias TestProductId = String
