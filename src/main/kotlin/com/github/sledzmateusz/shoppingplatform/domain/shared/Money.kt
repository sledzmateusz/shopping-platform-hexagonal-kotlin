package com.github.sledzmateusz.shoppingplatform.domain.shared

import java.math.BigDecimal
import java.util.Currency

data class Money(val amount: BigDecimal, val currency: Currency = Currency.getInstance("PLN")) {

  companion object {
    fun zero(currency: Currency = Currency.getInstance("PLN")): Money {
      return Money(BigDecimal.ZERO, currency)
    }
  }

  operator fun minus(other: Money): Money {
    checkSameCurrency(other)
    return Money(this.amount - other.amount, this.currency)
  }

  operator fun times(multiplier: Int): Money {
    return Money(this.amount * BigDecimal(multiplier), this.currency)
  }

  fun coerceAtLeast(min: Money): Money {
    return if (this.amount < min.amount) min else this
  }

  private fun checkSameCurrency(other: Money) {
    if (this.currency != other.currency) {
      throw CurrencyMismatchException(this.currency, other.currency)
    }
  }
}

class CurrencyMismatchException(current: Currency, other: Currency) :
  RuntimeException("Cannot operate on different currencies: ${current.currencyCode} and ${other.currencyCode}")
