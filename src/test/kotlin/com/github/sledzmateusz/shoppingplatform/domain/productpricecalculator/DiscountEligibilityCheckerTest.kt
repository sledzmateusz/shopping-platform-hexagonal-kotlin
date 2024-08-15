package com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator

import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Discount.AmountBasedDiscount
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.Product.RegularProduct
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class DiscountEligibilityCheckerTest {

  private val discountForTenItems = AmountBasedDiscount(Money(BigDecimal(50)), threshold = DiscountThreshold.of(10))
  private val discountForFiveItems = AmountBasedDiscount(Money(BigDecimal(30)), threshold = DiscountThreshold.of(5))
  private val discountForTwoItem = AmountBasedDiscount(Money(BigDecimal(10)), threshold = DiscountThreshold.of(2))

  private val discountsProvider = object : DiscountsProvider {
    override fun getAmountBasedDiscounts(): List<AmountBasedDiscount> {
      return listOf(
        discountForTenItems,
        discountForFiveItems,
        discountForTwoItem
      )
    }
  }

  private val discountEligibilityChecker = DiscountEligibilityChecker(discountsProvider)

  @Test
  fun `should return highest eligible discount for product with quantity greater than highest threshold`() {
    val product = RegularProduct(
      id = ProductId.new(),
      baseUnitPrice = Money(BigDecimal(100)),
      quantity = ProductQuantity.of(15)
    )

    val discounts = discountEligibilityChecker.getEligibleDiscounts(product)

    assertEquals(1, discounts.size)
    assertEquals(discountForTenItems, discounts.first())
  }

  @Test
  fun `should return discount with threshold exactly matching product quantity`() {
    val product = RegularProduct(
      id = ProductId.new(),
      baseUnitPrice = Money(BigDecimal(100)),
      quantity = ProductQuantity.of(10)
    )

    val discounts = discountEligibilityChecker.getEligibleDiscounts(product)

    assertEquals(1, discounts.size)
    assertEquals(discountForTenItems, discounts.first())
  }

  @Test
  fun `should return lowest eligible discount for product with quantity matching lowest threshold`() {
    val product = RegularProduct(
      id = ProductId.new(),
      baseUnitPrice = Money(BigDecimal(100)),
      quantity = ProductQuantity.of(3)
    )

    val discounts = discountEligibilityChecker.getEligibleDiscounts(product)

    assertEquals(1, discounts.size)
    assertEquals(discountForTwoItem, discounts.first())
  }

  @Test
  fun `should return no discounts if product quantity is below any threshold`() {
    val product = RegularProduct(
      id = ProductId.new(),
      baseUnitPrice = Money(BigDecimal(100)),
      quantity = ProductQuantity.of(1)
    )

    val discounts = discountEligibilityChecker.getEligibleDiscounts(product)


    assertEquals(0, discounts.size)
  }
}