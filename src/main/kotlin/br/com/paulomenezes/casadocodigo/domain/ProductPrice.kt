package br.com.paulomenezes.casadocodigo.domain

import jakarta.persistence.Column
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "product_price")
class ProductPrice(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    var bookType: BookType = BookType.EBOOK,

    @Column(name = "price_value", nullable = false, precision = 10, scale = 2)
    var priceValue: BigDecimal? = null,

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    var product: Product? = null
)