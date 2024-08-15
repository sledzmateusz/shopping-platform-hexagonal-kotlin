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
  private val testRestTemplate: TestRestTemplate,
) {

  @Test
  fun `should return discounted product if quantity meets or exceeds discount threshold`() {
    val expectedProductId = "aac7d817-93f0-4f6f-92c4-6752c95d23b0"

    val response = testRestTemplate.postForEntity("/products/$expectedProductId/calculate-price", CalculateProductPriceRequest(2), ProductPriceResponse::class.java)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).isNotNull()
    assertThat(response.body?.regularTotalPrice).isEqualTo(Money(BigDecimal.valueOf(1199.98)))
    assertThat(response.body?.discountedTotalPrice).isEqualTo(Money(BigDecimal.valueOf(1189.98)))
  }

  @Test
  fun `should return null discountedTotalPrice when quantity is below discount threshold`() {
    val expectedProductId = "aac7d817-93f0-4f6f-92c4-6752c95d23b0"

    val response = testRestTemplate.postForEntity("/products/$expectedProductId/calculate-price", CalculateProductPriceRequest(1), ProductPriceResponse::class.java)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).isNotNull()
    assertThat(response.body?.regularTotalPrice).isEqualTo(Money(BigDecimal.valueOf(599.99)))
    assertThat(response.body?.discountedTotalPrice).isNull()
  }

  @Test
  fun `should return 400 for invalid product quantity`() {
    val expectedProductId = "aac7d817-93f0-4f6f-92c4-6752c95d23b0"

    val response = testRestTemplate.postForEntity("/products/$expectedProductId/calculate-price", CalculateProductPriceRequest(0), String::class.java)

    assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    assertThat(response.body).isEqualTo("Quantity must be between 1 and 100")
  }
}
