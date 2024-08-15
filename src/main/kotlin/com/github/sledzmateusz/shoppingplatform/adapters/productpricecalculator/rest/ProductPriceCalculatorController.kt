package com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.rest

import com.github.sledzmateusz.shoppingplatform.adapters.shared.logging.logger
import com.github.sledzmateusz.shoppingplatform.domain.ProductPriceCalculatorFacade
import com.github.sledzmateusz.shoppingplatform.domain.ProductPriceDto
import com.github.sledzmateusz.shoppingplatform.domain.productpricecalculator.ProductQuantity
import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("products/{id}/calculate-price")
class ProductPriceCalculatorController(private val facade: ProductPriceCalculatorFacade) {

  companion object {
    private val log = logger()
  }

  @PostMapping
  fun calculateProductPrice(
    @PathVariable("id") id: ProductId,
    @RequestBody request: CalculateProductPriceRequest
  ): ProductPriceResponse {
    log.info("Calculate product price $id $request")
    val productPrice = facade.getProductPrice(id, ProductQuantity.of(request.quantity))
    return productPrice.toResponse()
  }
}

data class CalculateProductPriceRequest(val quantity: Int)
data class ProductPriceResponse(
  val regularTotalPrice: Money,
  val discountedTotalPrice: Money?,
)

private fun ProductPriceDto.toResponse() =
  ProductPriceResponse(
    regularTotalPrice = this.regularTotalAmount,
    discountedTotalPrice = this.discountedTotalAmount
  )
