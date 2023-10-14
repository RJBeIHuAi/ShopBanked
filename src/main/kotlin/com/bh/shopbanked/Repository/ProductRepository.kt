package com.bh.shopbanked.Repository

import com.bh.shopbanked.entity.Category
import com.bh.shopbanked.entity.Product
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository: JpaRepository<Product, Long> {
    // 自定义方法，根据商品名称查询商品
    fun findByName(name: String): List<Product>
    // 根据商品的类别查询商品列表
    fun findByCategory(category: Category): List<Product>
}