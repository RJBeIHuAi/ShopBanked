package com.bh.shopbanked.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*

@Entity
@Table(name = "user")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val username: String,
    val email: String,
    val password: String, // 在实际应用中应当对密码进行加密存储

    // 与购物车的一对一关系
    @OneToOne(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    var shoppingCart: ShoppingCart? = null,

    // 与订单的一对多关系
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    var orders: MutableList<Order> = mutableListOf(),

    // 与地址卡片的一对多关系
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JsonManagedReference
    var locationCards: MutableList<LocationData> = mutableListOf()

)
