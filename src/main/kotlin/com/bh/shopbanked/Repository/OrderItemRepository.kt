package com.bh.shopbanked.Repository

import com.bh.shopbanked.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderItemRepository : JpaRepository<OrderItem,Long> {
}