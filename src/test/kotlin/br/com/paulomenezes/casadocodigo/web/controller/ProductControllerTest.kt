package br.com.paulomenezes.casadocodigo.web.controller

import br.com.paulomenezes.casadocodigo.domain.Product
import br.com.paulomenezes.casadocodigo.service.ProductService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import java.math.BigDecimal

@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockkBean
    lateinit var productService: ProductService

    @Test
    fun `list should return products view`() {
        every { productService.findAll() } returns emptyList()

        mockMvc.perform(get("/products"))
            .andExpect(status().isOk)
            .andExpect(view().name("products/list"))
            .andExpect(model().attributeExists("products"))
    }

    @Test
    fun `form should return new product form`() {
        mockMvc.perform(get("/products/form"))
            .andExpect(status().isOk)
            .andExpect(view().name("products/form"))
            .andExpect(model().attributeExists("productForm"))
    }

    @Test
    fun `save should create product and redirect to detail`() {
        val product = Product(id = 1L, title = "Test", description = "Desc", author = "Autor", price = BigDecimal("99.90"))
        every { productService.create(any()) } returns product

        mockMvc.perform(
            post("/products")
                .param("title", "Test")
                .param("description", "Desc")
                .param("author", "Autor")
                .param("price", "99.90")
        )
            .andExpect(status().isFound)
            .andExpect(redirectedUrl("/products/1"))
    }

    @Test
    fun `save with invalid data should return to form`() {
        mockMvc.perform(
            post("/products")
                .param("title", "")
                .param("description", "")
                .param("author", "")
        )
            .andExpect(status().isOk)
            .andExpect(view().name("products/form"))
    }

    @Test
    fun `show should return product detail`() {
        val product = Product(id = 1L, title = "Test", description = "Desc", author = "Autor", price = BigDecimal("99.90"))
        every { productService.findById(1L) } returns product
        every { productService.findPricesByProductId(1L) } returns emptyList()

        mockMvc.perform(get("/products/1"))
            .andExpect(status().isOk)
            .andExpect(view().name("products/show"))
            .andExpect(model().attributeExists("product"))
    }

    @Test
    fun `show with non-existent id should redirect to list`() {
        every { productService.findById(999L) } returns null

        mockMvc.perform(get("/products/999"))
            .andExpect(status().isFound)
            .andExpect(redirectedUrl("/products"))
    }

    @Test
    fun `delete should remove product and redirect`() {
        every { productService.delete(1L) } returns true

        mockMvc.perform(get("/products/1/delete"))
            .andExpect(status().isFound)
            .andExpect(redirectedUrl("/products"))
    }
}
