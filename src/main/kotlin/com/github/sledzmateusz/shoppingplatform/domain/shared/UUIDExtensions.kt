package com.github.sledzmateusz.shoppingplatform.domain.shared

import java.util.UUID

fun String.isValidUUID(): Boolean {
  return try {
    UUID.fromString(this)
    true
  } catch (ex: IllegalArgumentException) {
    false
  }
}