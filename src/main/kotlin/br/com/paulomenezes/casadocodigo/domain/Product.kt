package br.com.paulomenezes.casadocodigo.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

@Entity
@Table(name = "products")
class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false, length = 150)
    var title: String = "",

    @Column(nullable = false, length = 1000)
    var description: String = "",

    @Column(nullable = false, length = 120)
    var author: String = "",

    @Column(nullable = false, precision = 10, scale = 2)
    var price: BigDecimal? = BigDecimal.ZERO
)