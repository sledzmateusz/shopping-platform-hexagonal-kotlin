package com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest

import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity


class ProductCatalogRestClient(
  private val testRestTemplate: TestRestTemplate
) {

  fun getProductBy(productId: TestProductId): ResponseEntity<TestProductResponse> =
    testRestTemplate.getForEntity(productId.toUrl(), TestProductResponse::class.java)

  fun getNotExistingProduct(productId: TestProductId): ResponseEntity<String> =
    testRestTemplate.getForEntity(productId.toUrl(), String::class.java)

  private fun TestProductId.toUrl() = "/products/$this"
}

typealias TestProductId = String
data class TestProductResponse(val id: String, val name: String)
