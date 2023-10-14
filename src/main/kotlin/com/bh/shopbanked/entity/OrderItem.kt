package com.bh.shopbanked.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
data class OrderItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val quantity: Int,
    val price: Double,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,
    // 订单项和订单之间的多对一关系
    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    val order: Order? = null
)
