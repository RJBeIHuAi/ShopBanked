package com.bh.shopbanked.Repository

import com.bh.shopbanked.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface UserRepository:JpaRepository<User,Long> {
    // 根据用户名查询用户
    fun findByUsername(username: String): User?

    // 自定义方法，注册用户并返回结果
    fun saveAndFlush(user: User): User

    // 自定义方法，登录时根据用户名和密码查询用户
    @Query("SELECT u FROM User u WHERE u.username = :username AND u.password = :password")
    fun login(@Param("username") username: String, @Param("password") password: String): User?

    // 自定义方法，根据用户ID查询用户的购物车和订单
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.shoppingCart LEFT JOIN FETCH u.orders WHERE u.id = :userId")
    fun findUserWithShoppingCartAndOrders(@Param("userId") userId: Long): User
}