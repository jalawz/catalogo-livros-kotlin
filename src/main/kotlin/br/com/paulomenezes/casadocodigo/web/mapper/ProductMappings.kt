package br.com.paulomenezes.casadocodigo.web.mapper

import br.com.paulomenezes.casadocodigo.domain.Product
import br.com.paulomenezes.casadocodigo.web.form.ProductForm

fun ProductForm.toEntity(): Product =
    Product(
        id = id,
        title = title,
        description = description,
        author = author,
        price = price
    )

fun Product.toForm(): ProductForm =
    ProductForm(
        id = id,
        title = title,
        description = description,
        author = author,
        price = price
    )