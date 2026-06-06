package br.com.paulomenezes.casadocodigo.web.exception

import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(NoSuchElementException::class)
    fun handleNotFound(
        e: NoSuchElementException,
        redirectAttributes: RedirectAttributes
    ): String {
        redirectAttributes.addFlashAttribute("errorMessage", e.message ?: "Recurso não encontrado.")
        return "redirect:/products"
    }
}
