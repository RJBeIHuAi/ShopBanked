package com.bh.shopbanked.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "ShoppingCart")
data class ShoppingCart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

// 购物车和用户之间的一对一关系
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    val user: User? = null,

    @OneToMany(mappedBy = "shoppingCart", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    val cartItems: MutableList<CartItem> = mutableListOf()
)

