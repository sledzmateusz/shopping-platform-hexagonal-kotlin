package com.github.sledzmateusz.shoppingplatform

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApplicationRunner

fun main(args: Array<String>) {
  runApplication<ApplicationRunner>(*args)
}
