package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.rest

import com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest.TestProductId
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.ResponseEntity

class ProductPriceCalculatorRestClient(
  private val testRestTemplate: TestRestTemplate
) {

  fun calculateProductPrice(productId: TestProductId, quantity: Int): ResponseEntity<TestProductPriceResponse> =
    testRestTemplate.postForEntity(
      productId.toUrl(),
      TestCalculateProductPriceRequest(quantity),
      TestProductPriceResponse::class.java
    )

  fun calculateProductPriceError(productId: TestProductId, quantity: Int): ResponseEntity<String> =
    testRestTemplate.postForEntity(
      productId.toUrl(),
      TestCalculateProductPriceRequest(quantity),
      String::class.java
    )


  private fun TestProductId.toUrl() = "/products/$this/calculate-price"
}

data class TestCalculateProductPriceRequest(val quantity: Int)
data class TestProductPriceResponse(
  val regularTotalPrice: Money,
  val discountedTotalPrice: Money?,
)
