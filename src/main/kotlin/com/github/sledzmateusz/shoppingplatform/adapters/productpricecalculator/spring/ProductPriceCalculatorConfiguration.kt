package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.spring

import com.github.sledzmateusz.shoppingplatform.domain.ProductPriceCalculatorFacade
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.BestDiscountSelector
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
  fun bestDiscountSelector(discountService: DiscountService): BestDiscountSelector {
    return BestDiscountSelector(discountService)
  }

  @Bean
  fun productPriceCalculator(
    productCatalogClient: ProductCatalogClient,
    discountService: DiscountService,
    bestDiscountSelector: BestDiscountSelector,
    discountEligibilityChecker: DiscountEligibilityChecker,
  ): ProductPriceCalculator {
    return ProductPriceCalculator(productCatalogClient, bestDiscountSelector, discountEligibilityChecker)
  }

  @Bean
  fun productPriceCalculatorFacade(calculator: ProductPriceCalculator): ProductPriceCalculatorFacade {
    return ProductPriceCalculatorFacade(calculator)
  }
}
