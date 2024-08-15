package com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest

import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.testutils.IntegrationTest
import com.github.sledzmateusz.shoppingplatform.testutils.Randomizer.invalidProductId
import com.github.sledzmateusz.shoppingplatform.testutils.Randomizer.randomProductId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import java.math.BigDecimal


@IntegrationTest
internal class ProductControllerTest @Autowired constructor(
  private val productCatalogRestClient: ProductCatalogRestClient,
) {

  @Test
  fun `should return 404 when product does not exist`() {
    val nonExistingProductId = randomProductId()

    val response = productCatalogRestClient.getNotExistingProduct(nonExistingProductId)

    assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    assertThat(response.body).isEqualTo("Product ProductId(raw=$nonExistingProductId) not found")
  }

  @Test
  fun `should return 400 for invalid product id`() {
    val invalidProductId = invalidProductId()

    val response = productCatalogRestClient.getNotExistingProduct(invalidProductId)

    assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    assertThat(response.body).isEqualTo("Invalid Product ID: $invalidProductId")
  }

  @Test
  fun `should return product details when product exists`() {
    val expectedProduct = TestProductResponse(
      id = "aac7d817-93f0-4f6f-92c4-6752c95d23b0",
      name = "Sony WH-1000XM4 Wireless Noise-Cancelling Headphones",
      price = Money.from(BigDecimal(599.99))
    )

    val response = productCatalogRestClient.getProductBy(expectedProduct.id)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).isNotNull
    assertThat(response.body?.id).isEqualTo(expectedProduct.id)
    assertThat(response.body?.name).isEqualTo(expectedProduct.name)
    assertThat(response.body?.price).isEqualTo(expectedProduct.price)
  }
}