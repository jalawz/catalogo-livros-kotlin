package br.com.paulomenezes.casadocodigo.web.controller

import br.com.paulomenezes.casadocodigo.service.ProductService
import br.com.paulomenezes.casadocodigo.web.form.ProductForm
import br.com.paulomenezes.casadocodigo.web.mapper.toForm
import jakarta.validation.Valid
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping
    fun list(model: Model): String {
        val products = productService.findAll()
        model.addAttribute("pageTitle", "Produtos")
        model.addAttribute("products", products)
        return "products/list"
    }

    @GetMapping("/form")
    fun form(model: Model): String {
        model.addAttribute("pageTitle", "Novo Produto")
        model.addAttribute("productForm", ProductForm())
        return "products/form"
    }

    @PostMapping
    fun save(
        @Valid @ModelAttribute("productForm") productForm: ProductForm,
        bindingResult: BindingResult,
        model: Model,
        redirectAttributes: RedirectAttributes
    ): String {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pageTitle", getFormTitle(productForm.id))
            return "products/form"
        }

        val isEditing = productForm.id != null
        val product = if (isEditing) {
            productService.update(productForm.id!!, productForm)
        } else {
            productService.create(productForm)
        }

        redirectAttributes.addFlashAttribute("successMessage",
            if (isEditing) "Produto atualizado com sucesso!"
            else "Produto criado com sucesso!")

        return "redirect:/products/${product.id}"
    }

    @GetMapping("/{id}/edit")
    fun editForm(@PathVariable id: Long, model: Model): String {
        val product = productService.findById(id) ?: return "redirect:/products"
        model.addAttribute("pageTitle", "Editar Produto")
        model.addAttribute("productForm", product.toForm())
        return "products/form"
    }

    @GetMapping("/{id}")
    fun show(@PathVariable id: Long, model: Model): String {
        val product = productService.findById(id) ?: return "redirect:/products"
        val prices = productService.findPricesByProductId(id)

        model.addAttribute("pageTitle", product.title)
        model.addAttribute("product", product)
        model.addAttribute("prices", prices)

        return "products/show"
    }

    @GetMapping("/{id}/delete")
    fun delete(@PathVariable id: Long, redirectAttributes: RedirectAttributes): String {
        val deleted = productService.delete(id)

        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Produto excluído com sucesso!")
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Produto não encontrado.")
        }

        return "redirect:/products"
    }

    private fun getFormTitle(id: Long?): String =
        if (id != null) "Editar Produto" else "Novo Produto"
}