package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.rest

import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.testutils.IntegrationTest
import com.github.sledzmateusz.shoppingplatform.testutils.Randomizer
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
  fun `should return discounted product for any productId`() {
    val randomProductId = randomProductId()

    val response = testRestTemplate.postForEntity("/products/$randomProductId/calculate-price", CalculateProductPriceRequest(1), CalculateProductPriceResponse::class.java)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).isNotNull()
    assertThat(response.body?.totalPrice).isEqualTo(Money(BigDecimal.valueOf(500)))
    assertThat(response.body?.appliedDiscounts).isEqualTo(listOf(DiscountResponse("16e24a73-b38e-4f82-be4e-da968d56c9a5")))
  }
}
