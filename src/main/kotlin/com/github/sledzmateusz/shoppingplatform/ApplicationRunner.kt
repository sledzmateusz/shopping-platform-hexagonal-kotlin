package com.github.sledzmateusz.shoppingplatform

import com.github.sledzmateusz.shoppingplatform.adapters.productpricecalculator.property.DiscountProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(DiscountProperties::class)
class ApplicationRunner

fun main(args: Array<String>) {
  runApplication<ApplicationRunner>(*args)
}
