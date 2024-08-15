package com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.spring

import com.github.sledzmateusz.shoppingplatform.domain.ProductCatalogFacade
import com.github.sledzmateusz.shoppingplatform.domain.productcatalog.ProductRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductCatalogConfiguration {

  @Bean
  fun productCatalogFacade(productRepository: ProductRepository): ProductCatalogFacade {
    return ProductCatalogFacade(productRepository)
  }
}
