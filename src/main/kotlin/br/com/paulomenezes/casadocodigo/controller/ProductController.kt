package br.com.paulomenezes.casadocodigo.controller

import br.com.paulomenezes.casadocodigo.domain.Product
import br.com.paulomenezes.casadocodigo.repository.ProductRepository
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping

@Controller
class ProductController(
    private val productRepository: ProductRepository
) {

    @GetMapping("/products")
    fun list(model: Model): String {
        val products = productRepository.findAll()
        model.addAttribute("pageTitle", "Produtos")
        model.addAttribute("products", products)
        return "products/list"
    }

    @GetMapping("/products/form")
    fun form(model: Model): String {
        model.addAttribute("pageTitle", "Novo Produto")
        model.addAttribute("product", Product())
        return "products/form"
    }

    @PostMapping("/products")
    fun save(
        @Valid @ModelAttribute product: Product,
        bindingResult: BindingResult,
        model: Model
    ): String {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", "Novo produto")
            return "products/form"
        }

        productRepository.save(product)
        return "redirect:/products"
    }

    @GetMapping("/products/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val product = productRepository.findById(id)

        if (product.isEmpty) {
            return "redirect:/products"
        }

        model.addAttribute("pageTitle", product.get().author)
        model.addAttribute("product", product.get())

        return "products/show"
    }
}