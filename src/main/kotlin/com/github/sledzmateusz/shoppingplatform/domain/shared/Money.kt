package com.github.sledzmateusz.shoppingplatform.domain.shared

import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Currency

data class Money private constructor(
  val amount: BigDecimal,
  val currency: Currency = Currency.getInstance("PLN")
) {

  companion object {
    fun from(amount: BigDecimal, currency: Currency = Currency.getInstance("PLN")): Money {
      val roundedAmount = amount.withScale()
      return Money(roundedAmount, currency)
    }

    fun zero(currency: Currency = Currency.getInstance("PLN")): Money {
      return Money(BigDecimal.ZERO.withScale(), currency)
    }
  }

  operator fun minus(other: Money): Money {
    checkSameCurrency(other)
    return Money(this.amount - other.amount, this.currency)
  }

  operator fun times(multiplier: Int): Money {
    return Money(this.amount * BigDecimal(multiplier), this.currency).withScale()
  }

  fun coerceAtLeast(min: Money): Money {
    return if (this.amount < min.amount) min else this
  }

  fun adjustByPercentage(percentage: BigDecimal): Money {
    val adjustmentFactor = percentage.withScale()
      .divide(BigDecimal(100), 2, RoundingMode.HALF_UP)
    val adjustmentAmount = this.amount.multiply(adjustmentFactor).withScale()
    return this - from(adjustmentAmount, this.currency)
  }

  private fun checkSameCurrency(other: Money) {
    if (this.currency != other.currency) {
      throw CurrencyMismatchException(this.currency, other.currency)
    }
  }

  private fun withScale(): Money {
    return this.copy(amount = this.amount.setScale(2, RoundingMode.HALF_UP))
  }
}

private fun BigDecimal.withScale() = this.setScale(2, RoundingMode.HALF_UP)

class CurrencyMismatchException(current: Currency, other: Currency) :
  RuntimeException("Cannot operate on different currencies: ${current.currencyCode} and ${other.currencyCode}")
