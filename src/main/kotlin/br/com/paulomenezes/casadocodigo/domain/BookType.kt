package br.com.paulomenezes.casadocodigo.domain

enum class BookType(
    val displayName: String
) {
    EBOOK("E-book"),
    PRINTED("Impresso"),
    COMBO("Combo")
}