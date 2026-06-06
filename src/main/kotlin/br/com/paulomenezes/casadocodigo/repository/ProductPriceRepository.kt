package br.com.paulomenezes.casadocodigo.repository

import br.com.paulomenezes.casadocodigo.domain.ProductPrice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductPriceRepository : JpaRepository<ProductPrice, Long> {

    fun findByProductId(productId: Long): List<ProductPrice>
}
