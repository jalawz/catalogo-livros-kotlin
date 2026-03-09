package br.com.paulomenezes.casadocodigo.config

import br.com.paulomenezes.casadocodigo.domain.Product
import br.com.paulomenezes.casadocodigo.repository.ProductRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.math.BigDecimal

@Configuration
class DataLoader {

    @Bean
    fun loadInitialProducts(productRepository: ProductRepository) = CommandLineRunner {
        if (productRepository.count() == 0L) {
            productRepository.saveAll(
                listOf(
                    Product(
                        title = "Kotlin para Web",
                        description = "Uma introdução prática ao desenvolvimento web moderno com Kotlin.",
                        author = "Paulo Menezes",
                        price = BigDecimal("79.90")
                    ),
                    Product(
                        title = "Spring Boot na Prática",
                        description = "Construindo aplicações robustas com Spring Boot, MVC e JPA.",
                        author = "Casa do Código",
                        price = BigDecimal("89.90")
                    ),
                    Product(
                        title = "Thymeleaf Essencial",
                        description = "Templates server-side com organização, fragments e boas práticas.",
                        author = "Equipe Editorial",
                        price = BigDecimal("59.90")
                    )
                )
            )
        }
    }
}