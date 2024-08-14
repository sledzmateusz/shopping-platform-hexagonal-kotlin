package com.github.sledzmateusz.shoppingplatform.domain.productcatalog

import com.github.sledzmateusz.shoppingplatform.domain.shared.Money
import com.github.sledzmateusz.shoppingplatform.domain.shared.ProductId

data class Product(val id: ProductId, val name: String, val price: Money)
