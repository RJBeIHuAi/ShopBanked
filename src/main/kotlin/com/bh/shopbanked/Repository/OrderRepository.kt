package com.bh.shopbanked.Repository

import com.bh.shopbanked.entity.Order
import com.bh.shopbanked.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    // 这里可以添加自定义的查询方法，根据需要
    fun findByUser(user: User): List<Order>
}