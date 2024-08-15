package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.rest

import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.testutils.IntegrationTest
import com.github.sledzmateusz.shoppingplatform.testutils.Randomizer.invalidProductId
import com.github.sledzmateusz.shoppingplatform.testutils.Randomizer.randomProductId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import java.math.BigDecimal

@IntegrationTest
internal class ProductPriceCalculatorControllerTest @Autowired constructor(
  private val productPriceCalculatorRestClient: ProductPriceCalculatorRestClient,
) {

  // Sony Alpha 7C - 7699.99 PLN
  private val productIdWithoutPercentageDiscount = "aac7d817-93f0-4f6f-92c4-6752c95d23b4"

  // Sony WH-1000XM4 - 599.99 PLN
  private val productIdWith10PercentageDiscount = "aac7d817-93f0-4f6f-92c4-6752c95d23b0"

  @Test
  fun `should return discounted product if quantity meets or exceeds discount threshold`() {
    val response = productPriceCalculatorRestClient.calculateProductPrice(productIdWithoutPercentageDiscount,2)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).isNotNull()
    assertThat(response.body?.regularTotalPrice).isEqualTo(Money.from(BigDecimal.valueOf(15399.98)))
    assertThat(response.body?.discountedTotalPrice).isEqualTo(Money.from(BigDecimal.valueOf(15389.98)))
  }

  @Test
  fun `should return null discountedTotalPrice when quantity is below discount threshold and percentage discount is not applicable`() {
    val response = productPriceCalculatorRestClient.calculateProductPrice(productIdWithoutPercentageDiscount,1)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).isNotNull()
    assertThat(response.body?.regularTotalPrice).isEqualTo(Money.from(BigDecimal.valueOf(7699.99)))
    assertThat(response.body?.discountedTotalPrice).isNull()
  }

  @Test
  fun `should return discounted product if percentage discount is applicable and quantity is below discount threshold`() {
    val response = productPriceCalculatorRestClient.calculateProductPrice(productIdWith10PercentageDiscount,1)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).isNotNull()
    assertThat(response.body?.regularTotalPrice).isEqualTo(Money.from(BigDecimal.valueOf(599.99)))
    assertThat(response.body?.discountedTotalPrice).isEqualTo(Money.from(BigDecimal.valueOf(539.99)))
  }

  @Test
  fun `should return discounted product with the lowest total price if multiple discounts are applicable`() {
    val response = productPriceCalculatorRestClient.calculateProductPrice(productIdWith10PercentageDiscount,2)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).isNotNull()
    // 599.99 * 2 = 1199.98
    assertThat(response.body?.regularTotalPrice).isEqualTo(Money.from(BigDecimal.valueOf(1199.98)))
    // percentage based discount - 1199.98 * 0.9 = 1079.98
    // amount based discount - 1199.98 - 10 = 1189.98
    assertThat(response.body?.discountedTotalPrice).isEqualTo(Money.from(BigDecimal.valueOf(1079.98)))
  }

  @Test
  fun `should return 404 when product does not exist`() {
    val nonExistingProductId = randomProductId()

    val response = productPriceCalculatorRestClient.calculateProductPriceError(nonExistingProductId, 1)

    assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    assertThat(response.body).isEqualTo("Product ProductId(raw=$nonExistingProductId) not found")
  }

  @Test
  fun `should return 400 for invalid product id`() {
    val invalidProductId = invalidProductId()

    val response = productPriceCalculatorRestClient.calculateProductPriceError(invalidProductId, 1)

    assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    assertThat(response.body).isEqualTo("Invalid Product ID: $invalidProductId")
  }

  @Test
  fun `should return 400 for invalid product quantity`() {
    val response = productPriceCalculatorRestClient.calculateProductPriceError(productIdWith10PercentageDiscount,0)


    assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    assertThat(response.body).isEqualTo("Quantity must be between 1 and 100")
  }
}

