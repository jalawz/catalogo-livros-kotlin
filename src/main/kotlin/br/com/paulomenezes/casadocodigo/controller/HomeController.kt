package br.com.paulomenezes.casadocodigo.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping
    fun home(model: Model): String {
        model.addAttribute("pageTitle", "Casa do Código")
        model.addAttribute("highLightMessage", "Spring MVC moderno com Kotlin e Thymeleaf")
        return "home"
    }
}