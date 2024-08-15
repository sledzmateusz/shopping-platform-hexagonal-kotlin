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
    val money1 = Money.from(BigDecimal(100), plnCurrency)
    val money2 = Money.from(BigDecimal(50), plnCurrency)

    val result = money1 - money2

    assertEquals(BigDecimal(50).setScale(2), result.amount)
    assertEquals(plnCurrency, result.currency)
  }

  @Test
  fun `minus operator should throw CurrencyMismatchException for different currencies`() {
    val money1 = Money.from(BigDecimal(100), plnCurrency)
    val money2 = Money.from(BigDecimal(50), usdCurrency)

    val exception = assertThrows<CurrencyMismatchException> {
      money1 - money2
    }

    assertEquals("Cannot operate on different currencies: PLN and USD", exception.message)
  }

  @Test
  fun `times operator should multiply amount by given multiplier`() {
    val money = Money.from(BigDecimal(100), plnCurrency)

    val result = money * 5

    assertEquals(BigDecimal(500).setScale(2), result.amount)
    assertEquals(plnCurrency, result.currency)
  }

  @Test
  fun `returns zero price with default PLN currency`() {
    val zero = Money.zero()

    assertEquals(BigDecimal.ZERO.setScale(2), zero.amount)
    assertEquals(plnCurrency, zero.currency)
  }

  @Test
  fun `return zero price with specified currency`() {
    val zero = Money.zero(usdCurrency)

    assertEquals(BigDecimal.ZERO.setScale(2), zero.amount)
    assertEquals(usdCurrency, zero.currency)
  }

  @Test
  fun `coerceAtLeast should return minimum amount when original amount is less than minimum`() {
    val money = Money.from(BigDecimal.valueOf(-99), plnCurrency)
    val minMoney = Money.zero(plnCurrency)

    val result = money.coerceAtLeast(minMoney)

    assertEquals(minMoney, result)
  }

  @Test
  fun `coerceAtLeast should return original amount when amount is greater than minimum`() {
    val money = Money.from(BigDecimal.valueOf(100), plnCurrency)
    val minMoney = Money.zero(plnCurrency)

    val result = money.coerceAtLeast(minMoney)

    assertEquals(money, result)
  }

  @Test
  fun `should round correctly when adjusting by a percentage with more than two decimals`() {
    val originalAmount = Money.from(BigDecimal("123.456")) // Rounded to 123.46
    val percentage = BigDecimal("12.345")  // Rounded to 12.35%

    val result = originalAmount.adjustByPercentage(percentage)

    // 0.12 * 123.46 = 14.82 - adjustmentAmount
    // 123.46 - 14.82 = 108.64 - final result
    assertEquals(BigDecimal("108.64"), result.amount)
  }

  @Test
  fun `should adjust by 0 percent`() {
    val originalAmount = Money.from(BigDecimal("300.00"))
    val percentage = BigDecimal("0")

    val result = originalAmount.adjustByPercentage(percentage)

    assertEquals(BigDecimal("300.00"), result.amount)
  }

  @Test
  fun `should adjust by 100 percent`() {
    val originalAmount = Money.from(BigDecimal("300.00"))
    val percentage = BigDecimal("100")

    val result = originalAmount.adjustByPercentage(percentage)

    assertEquals(BigDecimal("0.00"), result.amount)
  }
}