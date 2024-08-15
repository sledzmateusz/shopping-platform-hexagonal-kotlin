package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.property

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import kotlin.test.assertEquals

class PropertyBasedDiscountProviderTest {

  @Test
  fun `should throw IllegalArgumentException when discount amount is less than or equal to zero`() {
    val invalidDiscountProperties = DiscountProperties().apply {
      amountBased = listOf(
        AmountBasedDiscountConfig(amount = BigDecimal.ZERO, threshold = 5),
        AmountBasedDiscountConfig(amount = BigDecimal(-10), threshold = 10)
      )
    }

    assertThrows<IllegalArgumentException> {
      PropertyBasedDiscountProvider(invalidDiscountProperties)
    }
  }

  @Test
  fun `should not throw exception for valid discount configurations`() {
    val validDiscountProperties = DiscountProperties().apply {
      amountBased = listOf(
        AmountBasedDiscountConfig(amount = BigDecimal(50), threshold = 10),
        AmountBasedDiscountConfig(amount = BigDecimal(30), threshold = 5)
      )
    }

    val discountProvider = PropertyBasedDiscountProvider(validDiscountProperties)
    assertEquals(2, discountProvider.getAmountBasedDiscounts().size)
  }
}