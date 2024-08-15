package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.rest

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean

@TestConfiguration
class ProductPriceCalculatorRestClientTestConfig {

  @Bean
  fun productPriceCalculatorRestClient(testRestTemplate: TestRestTemplate): ProductPriceCalculatorRestClient {
    return ProductPriceCalculatorRestClient(testRestTemplate)
  }
}
