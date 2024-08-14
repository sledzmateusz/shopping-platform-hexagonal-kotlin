package com.github.sledzmateusz.shoppingplatform.domain.shared

import java.math.BigDecimal
import java.util.Currency

data class Money(val amount: BigDecimal, val currency: Currency = Currency.getInstance("PLN"))