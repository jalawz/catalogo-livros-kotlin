package br.com.paulomenezes.casadocodigo.service

import br.com.paulomenezes.casadocodigo.domain.Product
import br.com.paulomenezes.casadocodigo.repository.ProductPriceRepository
import br.com.paulomenezes.casadocodigo.repository.ProductRepository
import br.com.paulomenezes.casadocodigo.web.form.ProductForm
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.util.Optional

class ProductServiceTest {

    private val productRepository: ProductRepository = mockk()
    private val productPriceRepository: ProductPriceRepository = mockk()
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        productService = ProductService(productRepository, productPriceRepository)
    }

    @Test
    fun `create should save product and create prices for each book type`() {
        val form = ProductForm(title = "Kotlin", description = "Desc", author = "Autor", price = BigDecimal("99.90"))
        val savedProduct = Product(id = 1L, title = "Kotlin", description = "Desc", author = "Autor", price = BigDecimal("99.90"))

        every { productRepository.save(any()) } returns savedProduct
        every { productPriceRepository.save(any()) } answers { firstArg() }

        val result = productService.create(form)

        assertEquals("Kotlin", result.title)
        assertEquals(1L, result.id)
        verify { productRepository.save(any()) }
        verify(exactly = 3) { productPriceRepository.save(any()) }
    }

    @Test
    fun `update should modify existing product`() {
        val existing = Product(id = 1L, title = "Old", description = "Old desc", author = "Old", price = BigDecimal("50.00"))
        val form = ProductForm(id = 1L, title = "New", description = "New desc", author = "New", price = BigDecimal("75.00"))

        every { productRepository.findById(1L) } returns Optional.of(existing)
        every { productRepository.save(any()) } answers { firstArg() }

        val result = productService.update(1L, form)

        assertEquals("New", result.title)
        assertEquals("New desc", result.description)
        assertEquals(BigDecimal("75.00"), result.price)
    }

    @Test
    fun `findById should return product when found`() {
        val product = Product(id = 1L, title = "Test", description = "Desc", author = "A", price = BigDecimal("10.00"))
        every { productRepository.findById(1L) } returns Optional.of(product)

        val result = productService.findById(1L)

        assertEquals("Test", result?.title)
    }

    @Test
    fun `findById should return null when not found`() {
        every { productRepository.findById(1L) } returns Optional.empty()

        val result = productService.findById(1L)

        assertNull(result)
    }

    @Test
    fun `delete should return true when product exists`() {
        every { productRepository.existsById(1L) } returns true
        every { productPriceRepository.findByProductId(1L) } returns emptyList()
        every { productRepository.deleteById(1L) } returns Unit

        val result = productService.delete(1L)

        assertTrue(result)
        verify { productRepository.deleteById(1L) }
    }

    @Test
    fun `delete should return false when product does not exist`() {
        every { productRepository.existsById(1L) } returns false

        val result = productService.delete(1L)

        assertFalse(result)
        verify(exactly = 0) { productRepository.deleteById(1L) }
    }
}
