package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.rest

import com.github.sledzmateusz.shoppingplatform.adapters.shared.logging.logger
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import java.util.UUID

@RestController
@RequestMapping("products/{id}/calculate-price")
class ProductPriceCalculatorController {

  companion object {
    private val log = logger()
  }

  @PostMapping
  fun calculateProductPrice(
    @PathVariable("id") id: ProductId,
    @RequestBody request: CalculateProductPriceRequest
  ): CalculateProductPriceResponse {
    log.info("Calculate product price $id $request")
    return CalculateProductPriceResponse(
      totalPrice = Money(BigDecimal.valueOf(500)),
      appliedDiscounts = listOf(DiscountResponse("16e24a73-b38e-4f82-be4e-da968d56c9a5"))
    )
  }
}

data class CalculateProductPriceRequest(val quantity: Int)
data class CalculateProductPriceResponse(
  val totalPrice: Money,
  val appliedDiscounts: List<DiscountResponse>? = emptyList()
)

data class DiscountResponse(val id: String)
