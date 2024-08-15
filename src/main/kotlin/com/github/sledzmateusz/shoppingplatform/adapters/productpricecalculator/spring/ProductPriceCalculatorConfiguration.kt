package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.spring

import com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.property.PropertyBasedDiscountProvider
import com.github.sledzmateusz.shoppingplatform.domain.ProductPriceCalculatorFacade
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountEligibilityChecker
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountService
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountsProvider
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
  fun discountEligibilityChecker(discountProvider: DiscountsProvider): DiscountEligibilityChecker {
    return DiscountEligibilityChecker(discountProvider)
  }

  @Bean
  fun productPriceCalculator(
    productCatalogClient: ProductCatalogClient,
    discountService: DiscountService,
    discountEligibilityChecker: DiscountEligibilityChecker,
  ): ProductPriceCalculator {
    return ProductPriceCalculator(productCatalogClient, discountService, discountEligibilityChecker)
  }

  @Bean
  fun productPriceCalculatorFacade(calculator: ProductPriceCalculator): ProductPriceCalculatorFacade {
    return ProductPriceCalculatorFacade(calculator)
  }
}
