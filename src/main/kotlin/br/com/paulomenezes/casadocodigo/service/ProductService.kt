package br.com.paulomenezes.casadocodigo.service

import br.com.paulomenezes.casadocodigo.domain.BookType
import br.com.paulomenezes.casadocodigo.domain.Product
import br.com.paulomenezes.casadocodigo.domain.ProductPrice
import br.com.paulomenezes.casadocodigo.repository.ProductPriceRepository
import br.com.paulomenezes.casadocodigo.repository.ProductRepository
import br.com.paulomenezes.casadocodigo.web.form.ProductForm
import br.com.paulomenezes.casadocodigo.web.mapper.toEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val productPriceRepository: ProductPriceRepository
) {

    fun findAll() = productRepository.findAll()

    fun findById(id: Long) = productRepository.findById(id).orElse(null)

    fun findPricesByProductId(productId: Long): List<ProductPrice> =
        productPriceRepository.findByProductId(productId)

    @Transactional
    fun create(form: ProductForm): Product {
        val product = productRepository.save(form.toEntity())
        createPrices(product)
        return product
    }

    @Transactional
    fun update(id: Long, form: ProductForm): Product {
        val existing = productRepository.findById(id)
            .orElseThrow { NoSuchElementException("Produto não encontrado com id: $id") }

        existing.title = form.title
        existing.description = form.description
        existing.author = form.author
        existing.price = form.price

        return productRepository.save(existing)
    }

    @Transactional
    fun delete(id: Long): Boolean {
        if (productRepository.existsById(id)) {
            productPriceRepository.findByProductId(id).forEach { productPriceRepository.delete(it) }
            productRepository.deleteById(id)
            return true
        }
        return false
    }

    private fun createPrices(product: Product) {
        BookType.entries.forEach { type ->
            productPriceRepository.save(
                ProductPrice(
                    bookType = type,
                    priceValue = product.price,
                    product = product
                )
            )
        }
    }
}