package br.com.paulomenezes.casadocodigo.web.form

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal


data class ProductForm(

    val id: Long? = null,

    @field:NotBlank(message = "O título é obrigatório")
    val title: String = "",

    @field:NotBlank(message = "A descrição é obrigatória")
    val description: String = "",

    @field:NotBlank(message = "O autor é obrigatório")
    val author: String = "",

    @field:NotNull(message = "O preço é obrigatório")
    @field:DecimalMin(value = "0.01", message = "O preço deve ser maior que zero")
    val price: BigDecimal? = null
)
