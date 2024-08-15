package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.property

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.AmountBasedDiscount
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.PercentageBasedDiscount
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountPercentage
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountThreshold
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.DiscountsProvider
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class PropertyBasedDiscountProvider(discountProperties: DiscountProperties) : DiscountsProvider {

  private val amountBasedDiscounts: List<AmountBasedDiscount> = discountProperties
    .amountBased
    .map { config ->
      require(config.amount > BigDecimal.ZERO) { "Discount amount must be greater than zero: $config" }

      AmountBasedDiscount(
        amount = Money.from(config.amount),
        threshold = DiscountThreshold.of(config.threshold)
      )
    }

  private val percentageBasedDiscounts: List<PercentageBasedDiscount> = discountProperties
    .percentageBased
    .map { config ->
      val productIds = config.productIds.map { ProductId(it) }.toSet()
      require(productIds.isNotEmpty()) { "Percentage-based discount must have at least one product ID" }

      PercentageBasedDiscount(
        discountPercentage = DiscountPercentage.of(config.percentage),
        productIds = productIds,
      )
    }

  override fun getAmountBasedDiscounts(): List<AmountBasedDiscount> = amountBasedDiscounts

  override fun getPercentageBasedDiscounts(): List<PercentageBasedDiscount> = percentageBasedDiscounts
}

@Component
@ConfigurationProperties(prefix = "discounts")
class DiscountProperties {
  var amountBased: List<AmountBasedDiscountConfig> = emptyList()
  var percentageBased: List<PercentageBasedDiscountConfig> = emptyList()
}

data class AmountBasedDiscountConfig(
  val amount: BigDecimal,
  val threshold: Int
)

data class PercentageBasedDiscountConfig(
  val productIds: List<String>,
  val percentage: Int
)
