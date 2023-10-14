package com.bh.shopbanked.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "Order_table")
data class Order(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val orderNumber: String, // 订单号
    val orderDate: LocalDate, // 订单日期

    // 订单和用户之间的多对一关系
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User? = null,

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    val orderItems: MutableList<OrderItem> = mutableListOf()
)
