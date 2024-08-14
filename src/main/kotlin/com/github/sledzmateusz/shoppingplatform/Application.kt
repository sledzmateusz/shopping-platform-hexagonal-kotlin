package com.github.sledzmateusz.shoppingplatform

import com.github.sledzmateusz.shoppingplatform.domain.ProductCatalogFacade
import com.github.sledzmateusz.shoppingplatform.domain.productcatalog.ProductRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class Application

fun main(args: Array<String>) {
  runApplication<Application>(*args)
}


@Configuration
class ProductCatalogConfiguration {

  @Bean
  fun productCatalogFacade(productRepository: ProductRepository): ProductCatalogFacade {
    return ProductCatalogFacade(productRepository)
  }
}
