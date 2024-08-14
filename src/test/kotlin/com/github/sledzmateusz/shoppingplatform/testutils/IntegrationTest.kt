package com.github.sledzmateusz.shoppingplatform.testutils

import com.github.sledzmateusz.shoppingplatform.Application
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(
  classes = [Application::class, ServletWebServerFactoryAutoConfiguration::class],
  webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
annotation class IntegrationTest
