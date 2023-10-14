package com.bh.shopbanked.Repository

import com.bh.shopbanked.entity.ShoppingCart
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ShoppingCartRepository : JpaRepository<ShoppingCart, Long> {
    // 根据用户ID查找购物车
    fun findByUserId(userId: Long): Optional<ShoppingCart>
}