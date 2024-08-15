package com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.context.annotation.Bean

@TestConfiguration
class ProductCatalogRestClientTestConfig {

  @Bean
  fun productCatalogRestClient(testRestTemplate: TestRestTemplate): ProductCatalogRestClient {
    return ProductCatalogRestClient(testRestTemplate)
  }
}
