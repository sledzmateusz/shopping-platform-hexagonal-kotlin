package com.github.sledzmateusz.shoppingplatform.domain.shared

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import java.util.Currency
import kotlin.test.assertEquals

class MoneyTest {

  private val plnCurrency = Currency.getInstance("PLN")
  private val usdCurrency = Currency.getInstance("USD")

  @Test
  fun `minus operator should subtract amounts of the same currency`() {
    val money1 = Money(BigDecimal(100), plnCurrency)
    val money2 = Money(BigDecimal(50), plnCurrency)

    val result = money1 - money2

    assertEquals(BigDecimal(50), result.amount)
    assertEquals(plnCurrency, result.currency)
  }

  @Test
  fun `minus operator should throw CurrencyMismatchException for different currencies`() {
    val money1 = Money(BigDecimal(100), plnCurrency)
    val money2 = Money(BigDecimal(50), usdCurrency)

    val exception = assertThrows<CurrencyMismatchException> {
      money1 - money2
    }

    assertEquals("Cannot operate on different currencies: PLN and USD", exception.message)
  }

  @Test
  fun `times operator should multiply amount by given multiplier`() {
    val money = Money(BigDecimal(100), plnCurrency)

    val result = money * 5

    assertEquals(BigDecimal(500), result.amount)
    assertEquals(plnCurrency, result.currency)
  }

  @Test
  fun `returns zero price with default PLN currency`() {
    val zero = Money.zero()

    assertEquals(BigDecimal.ZERO, zero.amount)
    assertEquals(plnCurrency, zero.currency)
  }

  @Test
  fun `return zero price with specified currency`() {
    val zero = Money.zero(usdCurrency)

    assertEquals(BigDecimal.ZERO, zero.amount)
    assertEquals(usdCurrency, zero.currency)
  }

  @Test
  fun `coerceAtLeast should return minimum amount when original amount is less than minimum`() {
    val money = Money(BigDecimal.valueOf(-99), plnCurrency)
    val minMoney = Money.zero(plnCurrency)

    val result = money.coerceAtLeast(minMoney)

    assertEquals(minMoney, result)
  }

  @Test
  fun `coerceAtLeast should return original amount when amount is greater than minimum`() {
    val money = Money(BigDecimal.valueOf(100), plnCurrency)
    val minMoney = Money.zero(plnCurrency)

    val result = money.coerceAtLeast(minMoney)

    assertEquals(money, result)
  }
}