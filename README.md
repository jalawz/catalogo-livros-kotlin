# Catalogo Livros Kotlin

`Catalogo Livros Kotlin` is a practical reinterpretation of the **Spring MVC** book by Casa do Codigo, rebuilt with a modern stack using **Kotlin + Spring Boot + Thymeleaf**.

The project keeps the core learning goals from the book while updating language, framework versions, and project structure.

## Purpose

This application reproduces the main concepts from the original material through a simple web catalog for books/products, focusing on:

- MVC architecture
- Controllers and routing
- Form validation
- Persistence with Spring Data JPA
- Server-side rendering with Thymeleaf

## Tech Stack

- Kotlin
- Spring Boot
- Spring Web
- Spring Data JPA
- Thymeleaf
- H2 Database
- Gradle (Kotlin DSL)

## Running Locally

```bash
./gradlew bootRun
```

Then open:

- App: `http://localhost:8080`
- H2 Console: `http://localhost:8080/h2-console`

## Project Notes

- This is a study-oriented project inspired by Casa do Codigo's content.
- It is not a line-by-line reproduction; it includes adaptations in language, structure, and dependencies.
