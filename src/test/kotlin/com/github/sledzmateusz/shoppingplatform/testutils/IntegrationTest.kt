package com.github.sledzmateusz.shoppingplatform.testutils

import com.github.sledzmateusz.shoppingplatform.ApplicationRunner
import com.github.sledzmateusz.shoppingplatform.adapters.productcatalog.rest.ProductCatalogRestClientTestConfig
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(
  classes = [ApplicationRunner::class, ServletWebServerFactoryAutoConfiguration::class],
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ContextConfiguration(classes = [ProductCatalogRestClientTestConfig::class])
annotation class IntegrationTest
