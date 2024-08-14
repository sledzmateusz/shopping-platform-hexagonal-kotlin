package com.github.sledzmateusz.shoppingplatform.domain.shared

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID

class UUIDExtensionsTest {

  @ParameterizedTest
  @ValueSource(strings = [
    "aac7d817-93f0-4f6f-92c4-6752c95d23b0",
    "00000000-0000-0000-0000-000000000000",
  ])
  fun `should return true for valid UUID strings`(uuid: String) {
    assertTrue(uuid.isValidUUID())
  }

  @Test
  fun `should return true for valid generated UUID strings`() {
    val validUUID = UUID.randomUUID().toString()

    assertTrue(validUUID.isValidUUID())
  }


  @Test
  fun `should return false for invalid UUID string`() {
    val invalidUUID = "invalid-uuid-string"

    assertFalse(invalidUUID.isValidUUID())
  }

  @Test
  fun `should return false for empty string`() {
    val emptyString = ""

    assertFalse(emptyString.isValidUUID())
  }
}
