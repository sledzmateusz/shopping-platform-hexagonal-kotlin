package com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest

import com.github.sledzmateusz.shoppingplatform.Application
import com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest.ProductResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus


@SpringBootTest(
  classes = [Application::class, ServletWebServerFactoryAutoConfiguration::class],
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
internal class ProductControllerTest @Autowired constructor(
  private val testRestTemplate: TestRestTemplate,
) {

  @Test
  fun `should return 404 when product does not exist`() {
    val nonExistingProductId = "non-existent-product-id"

    val response = testRestTemplate.getForEntity("/products/$nonExistingProductId", String::class.java)

    assertThat(response.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    assertThat(response.body).isEqualTo("Product ProductId(raw=non-existent-product-id) not found")
  }

  @Test
  fun `should return product details when product exists`() {
    val expectedProduct = ProductResponse(
      id = "aac7d817-93f0-4f6f-92c4-6752c95d23b0",
      name = "Sony WH-1000XM4 Wireless Noise-Cancelling Headphones"
    )

    val response = testRestTemplate.getForEntity("/products/${expectedProduct.id}", ProductResponse::class.java)

    assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
    assertThat(response.body).isNotNull
    assertThat(response.body?.id).isEqualTo(expectedProduct.id)
    assertThat(response.body?.name).isEqualTo(expectedProduct.name)
  }
}