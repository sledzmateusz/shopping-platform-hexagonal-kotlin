package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.spring

import com.github.sledzmateusz.shoppingplatform.domain.ProductPriceCalculatorFacade
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountService
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.ProductCatalogClient
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.ProductPriceCalculator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductPriceCalculatorConfiguration {

  @Bean
  fun discountService(): DiscountService {
    return DiscountService()
  }

  @Bean
  fun productPriceCalculator(
    productCatalogClient: ProductCatalogClient,
    discountService: DiscountService
  ): ProductPriceCalculator {
    return ProductPriceCalculator(productCatalogClient, discountService)
  }

  @Bean
  fun productPriceCalculatorFacade(calculator: ProductPriceCalculator): ProductPriceCalculatorFacade {
    return ProductPriceCalculatorFacade(calculator)
  }
}
