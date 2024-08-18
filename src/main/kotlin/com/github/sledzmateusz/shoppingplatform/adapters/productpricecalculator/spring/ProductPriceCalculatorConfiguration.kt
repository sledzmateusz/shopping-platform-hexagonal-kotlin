package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.spring

import com.github.sledzmateusz.shoppingplatform.domain.ProductPriceCalculatorFacade
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.BestDiscountSelector
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountEligibilityChecker
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountApplier
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountsProvider
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.ProductCatalogClient
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.ProductPriceCalculator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ProductPriceCalculatorConfiguration {

  @Bean
  fun discountApplier(): DiscountApplier {
    return DiscountApplier()
  }

  @Bean
  fun discountEligibilityChecker(discountProvider: DiscountsProvider): DiscountEligibilityChecker {
    return DiscountEligibilityChecker(discountProvider)
  }

  @Bean
  fun bestDiscountSelector(discountApplier: DiscountApplier): BestDiscountSelector {
    return BestDiscountSelector(discountApplier)
  }

  @Bean
  fun productPriceCalculator(
    productCatalogClient: ProductCatalogClient,
    discountApplier: DiscountApplier,
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
