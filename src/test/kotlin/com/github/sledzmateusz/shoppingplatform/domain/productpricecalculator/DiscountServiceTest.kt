package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class DiscountServiceTest {

  private val discountService = DiscountService()
  private val givenProduct = Product.RegularProduct(
    id = ProductId.new(),
    baseUnitPrice = Money(BigDecimal(10.0)),
    quantity = ProductQuantity.of(10)
  )

  @Test
  fun `should return ZERO price if AmountBasedDiscount exceeds total price`() {
    val givenDiscount = Discount.AmountBasedDiscount(Money(BigDecimal(1000.0)))

    val result = discountService.applyDiscount(givenProduct, givenDiscount)

    assertEquals(Money.zero(), result.discountedTotalPrice)
    assertEquals(Money(BigDecimal(100.0)), result.baseTotalPrice)
  }

  @Test
  fun `should throw exception for PercentageBasedDiscount`() {
    val givenDiscount = Discount.PercentageBasedDiscount(DiscountPercentage.of(10.0))

    val exception = assertThrows<NotImplementedError> { discountService.applyDiscount(givenProduct, givenDiscount) }

    assertEquals("An operation is not implemented.", exception.message)
  }

  @Test
  fun `should return DiscountedProduct for AmountBasedDiscount`() {
    val givenDiscount = Discount.AmountBasedDiscount(Money(BigDecimal(10.0)))

    val result = discountService.applyDiscount(givenProduct, givenDiscount)

    assertEquals(Money(BigDecimal(90.0)), result.discountedTotalPrice)
    assertEquals(Money(BigDecimal(100.0)), result.baseTotalPrice)
  }
}
