package com.bh.shopbanked.entity


import jakarta.persistence.*

@Entity
@Table(name = "Product")
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    var name: String,
    var description: String,
    var price: Double,
    var imageUrl: String, // 商品图片URL，可以是存储在服务器上的路径或外部链接

    // 商品和分类之间的多对一关系（假设商品有一个分类）
    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: Category? = null
)