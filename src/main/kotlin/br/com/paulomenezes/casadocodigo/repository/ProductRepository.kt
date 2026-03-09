package br.com.paulomenezes.casadocodigo.repository

import br.com.paulomenezes.casadocodigo.domain.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, Long>