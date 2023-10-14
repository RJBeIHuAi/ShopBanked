package com.bh.shopbanked.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*

@Entity
data class CartItem(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var quantity: Int,

    @ManyToOne
    @JoinColumn(name = "product_id")
    var product: Product? = null,

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "shopping_cart_id")
    var shoppingCart: ShoppingCart? = null
)
