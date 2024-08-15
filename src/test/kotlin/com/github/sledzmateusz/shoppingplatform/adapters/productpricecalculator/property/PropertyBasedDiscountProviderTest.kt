package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.property

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.InvalidDiscountPercentage
import com.github.sledzmateusz.shoppingplatform.testutils.Randomizer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import kotlin.test.assertEquals

class PropertyBasedDiscountProviderTest {

  @Test
  fun `should throw IllegalArgumentException when discount amount is negative`() {
    val invalidDiscountProperties = DiscountProperties().apply {
      amountBased = listOf(
        AmountBasedDiscountConfig(amount = BigDecimal(-10), threshold = 10)
      )
    }

    assertThrows<IllegalArgumentException> {
      PropertyBasedDiscountProvider(invalidDiscountProperties)
    }
  }

  @Test
  fun `should throw IllegalArgumentException when discount amount is equal to zero`() {
    val invalidDiscountProperties = DiscountProperties().apply {
      amountBased = listOf(
        AmountBasedDiscountConfig(amount = BigDecimal.ZERO, threshold = 5),
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

  @Test
  fun `should not throw for empty discount configurations`() {
    val emptyDiscountProperties = DiscountProperties()

    val discountProvider = PropertyBasedDiscountProvider(emptyDiscountProperties)

    assertEquals(0, discountProvider.getAmountBasedDiscounts().size)
  }

  @Test
  fun `should throw IllegalArgumentException when percentage based discount does not have any product ID`() {
    val invalidDiscountProperties = DiscountProperties().apply {
      percentageBased = listOf(
        PercentageBasedDiscountConfig(productIds = emptyList(), percentage = 10)
      )
    }

    assertThrows<IllegalArgumentException> {
      PropertyBasedDiscountProvider(invalidDiscountProperties)
    }
  }

  @Test
  fun `should throw IllegalArgumentException when percentage based discount is greater than 100`() {
    val invalidDiscountProperties = DiscountProperties().apply {
      percentageBased = listOf(
        PercentageBasedDiscountConfig(productIds = listOf(Randomizer.randomProductId()), percentage = 101)
      )
    }

    assertThrows<InvalidDiscountPercentage> {
      PropertyBasedDiscountProvider(invalidDiscountProperties)
    }
  }

  @Test
  fun `should throw IllegalArgumentException when percentage based discount is negative`() {
    val invalidDiscountProperties = DiscountProperties().apply {
      percentageBased = listOf(
        PercentageBasedDiscountConfig(productIds = listOf(Randomizer.randomProductId()), percentage = -10)
      )
    }

    assertThrows<InvalidDiscountPercentage> {
      PropertyBasedDiscountProvider(invalidDiscountProperties)
    }
  }
}
