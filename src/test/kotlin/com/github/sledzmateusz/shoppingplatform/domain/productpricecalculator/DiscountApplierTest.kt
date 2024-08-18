package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.AmountBasedDiscount
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.PercentageBasedDiscount
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Product.RegularProduct
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DiscountApplierTest {

  private val discountApplier = DiscountApplier()
  private val product = RegularProduct(
    id = ProductId.new(),
    baseUnitPrice = Money.from(10.toBigDecimal()),
    quantity = ProductQuantity.of(10)
  )

  @Test
  fun `should return ZERO price if AmountBasedDiscount exceeds total price`() {
    val discount = AmountBasedDiscount(Money.from(1000.toBigDecimal()), DiscountThreshold.of(1))

    val result = discountApplier.apply(product, discount)

    assertEquals(Money.zero(), result.discountedTotalPrice)
    assertEquals(Money.from(100.toBigDecimal()), result.baseTotalPrice)
  }

  @Test
  fun `should return DiscountedProduct for AmountBasedDiscount`() {
    val discount = AmountBasedDiscount(Money.from(10.toBigDecimal()), DiscountThreshold.of(1))

    val result = discountApplier.apply(product, discount)

    assertEquals(Money.from(90.toBigDecimal()), result.discountedTotalPrice)
    assertEquals(Money.from(100.toBigDecimal()), result.baseTotalPrice)
  }

  @Test
  fun `should return ZERO price if PercentageBasedDiscount is 100 percent`() {
    val discount = PercentageBasedDiscount(
      productIds = setOf(product.id),
      discountPercentage = DiscountPercentage.of(100)
    )

    val discountedProduct = discountApplier.apply(product, discount)

    assertEquals(Money.zero(), discountedProduct.discountedTotalPrice)
    assertEquals(Money.from(BigDecimal(100.0)), discountedProduct.baseTotalPrice)
  }

  @Test
  fun `should return original price if PercentageBasedDiscount is 0 percent`() {
    val discount = PercentageBasedDiscount(
      productIds = setOf(product.id),
      discountPercentage = DiscountPercentage.of(0)
    )

    val discountedProduct = discountApplier.apply(product, discount)

    assertEquals(Money.from(100.toBigDecimal()), discountedProduct.discountedTotalPrice)
    assertEquals(Money.from(100.toBigDecimal()), discountedProduct.baseTotalPrice)
  }

  @Test
  fun `should apply PercentageBasedDiscount to total base price`() {
    val testProduct = RegularProduct(
      id = ProductId.new(),
      baseUnitPrice = Money.from(BigDecimal(33.33)),
      quantity = ProductQuantity.of(3)
    )
    val discount = PercentageBasedDiscount(
      productIds = setOf(testProduct.id),
      discountPercentage = DiscountPercentage.of(10)
    )

    val discountedProduct = discountApplier.apply(testProduct, discount)

    assertEquals(Money.from(BigDecimal(89.99)), discountedProduct.discountedTotalPrice)
    assertEquals(Money.from(BigDecimal(99.99)), discountedProduct.baseTotalPrice)
  }
}
