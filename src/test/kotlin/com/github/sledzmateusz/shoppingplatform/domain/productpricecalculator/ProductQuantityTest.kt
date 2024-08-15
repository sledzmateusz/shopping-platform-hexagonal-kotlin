package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductQuantityTest {

  @Test
  fun `should create quantity with valid value`() {
    val validValue = 50

    val quantity = ProductQuantity.of(validValue)

    assertNotNull(quantity)
    assertEquals(validValue, quantity.value)
  }

  @Test
  fun `should throw IllegalArgumentException for value below 1`() {
    val invalidValue = 0

    val exception = assertThrows<InvalidProductQuantityException> {
      ProductQuantity.of(invalidValue)
    }

    assertEquals("Quantity must be between 1 and 100", exception.message)
  }

  @Test
  fun `should throw IllegalArgumentException for value above 100`() {
    val invalidValue = 101

    val exception = assertThrows<InvalidProductQuantityException> {
      ProductQuantity.of(invalidValue)
    }

    assertEquals("Quantity must be between 1 and 100", exception.message)
  }

  @Test
  fun `should create quantity with edge values`() {
    val minQuantity = ProductQuantity.of(1)
    val maxQuantity = ProductQuantity.of(100)

    assertEquals(1, minQuantity.value)
    assertEquals(100, maxQuantity.value)
  }
}
