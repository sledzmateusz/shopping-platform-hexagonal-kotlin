package com.github.sledzmateusz.shoppingplatform.domain.shared

import org.junit.jupiter.api.Assertions.assertDoesNotThrow
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.UUID

class ProductIdTest {

  @Test
  fun `should create ProductId with valid UUID`() {
    val validUUID = "aac7d817-93f0-4f6f-92c4-6752c95d23b0"

    val productId = ProductId(validUUID)

    assertEquals(validUUID, productId.raw)
  }

  @Test
  fun `should create ProductId with valid UUID when using new() method`() {
    val productId = ProductId.new()

    assertNotNull(productId)
    assertNotNull(productId.raw)
    assertDoesNotThrow {
      UUID.fromString(productId.raw)
    }
  }

  @Test
  fun `should throw IllegalArgumentException for invalid UUID string`() {
    val invalidUUID = "invalid-uuid-string"

    val exception = assertThrows<InvalidProductIdException> {
      ProductId(invalidUUID)
    }

    assertEquals("Invalid Product ID: $invalidUUID", exception.message)
  }

  @Test
  fun `should throw IllegalArgumentException for UUID with missing dashes`() {
    val invalidUUID = "aac7d81793f04f6f92c46752c95d23b0"

    val exception = assertThrows<InvalidProductIdException> {
      ProductId(invalidUUID)
    }

    assertEquals("Invalid Product ID: $invalidUUID", exception.message)
  }

  @Test
  fun `should throw IllegalArgumentException for empty string`() {
    val emptyString = ""

    val exception = assertThrows<InvalidProductIdException> {
      ProductId(emptyString)
    }

    assertEquals("Invalid Product ID: $emptyString", exception.message)
  }

  @Test
  fun `should create ProductId with valid uppercase UUID`() {
    val validUUID = "AAC7D817-93F0-4F6F-92C4-6752C95D23B0"

    val productId = ProductId(validUUID)

    assertEquals(validUUID, productId.raw)
  }
}
