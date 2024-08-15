package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.property

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.AmountBasedDiscount
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountThreshold
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountsProvider
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class PropertyBasedDiscountProvider(discountProperties: DiscountProperties) : DiscountsProvider {

  private val discounts: List<AmountBasedDiscount> = discountProperties
    .amountBased
    .map { config ->
      require(config.amount > BigDecimal.ZERO) { "Discount amount must be greater than zero: $config" }

      AmountBasedDiscount(
        amount = Money(config.amount),
        threshold = DiscountThreshold.of(config.threshold)
      )
    }

  override fun getAmountBasedDiscounts(): List<AmountBasedDiscount> = discounts
}

@Component
@ConfigurationProperties(prefix = "discounts")
class DiscountProperties {
  var amountBased: List<AmountBasedDiscountConfig> = emptyList()
}

data class AmountBasedDiscountConfig(
  val amount: BigDecimal,
  val threshold: Int
)
