package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class DiscountPercentageTest {

  @Test
  fun `should create DiscountPercentage with valid value`() {
    val validPercentage = 25.0

    val discountPercentage = DiscountPercentage.of(validPercentage)

    assertNotNull(discountPercentage)
    assertEquals(validPercentage, discountPercentage.value)
  }

  @Test
  fun `should throw IllegalArgumentException for negative percentage`() {
    val negativePercentage = -5.0

    val exception = assertThrows<IllegalArgumentException> {
      DiscountPercentage.of(negativePercentage)
    }

    assertEquals("Discount percentage must be between 0 and 100", exception.message)
  }

  @Test
  fun `should throw IllegalArgumentException for percentage greater than 100`() {
    val tooHighPercentage = 100.1

    val exception = assertThrows<IllegalArgumentException> {
      DiscountPercentage.of(tooHighPercentage)
    }

    assertEquals("Discount percentage must be between 0 and 100", exception.message)
  }

  @Test
  fun `should create DiscountPercentage for edge values 0 and 100`() {
    val zeroPercentage = DiscountPercentage.of(0.0)
    val hundredPercentage = DiscountPercentage.of(100.0)

    assertNotNull(zeroPercentage)
    assertEquals(0.0, zeroPercentage.value)

    assertNotNull(hundredPercentage)
    assertEquals(100.0, hundredPercentage.value)
  }
}