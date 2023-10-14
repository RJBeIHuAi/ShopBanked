package com.bh.shopbanked.service

import com.bh.shopbanked.Repository.UserRepository
import com.bh.shopbanked.entity.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired constructor(
    private val userRepository: UserRepository
) {
    fun registerUser(username: String, email: String, password: String): User {
        val user = User(username = username, email = email, password = password)
        return userRepository.saveAndFlush(user)
    }

    fun login(username: String, password: String): User? {
        return userRepository.login(username, password)
    }

    fun getUserWithShoppingCartAndOrders(userId: Long): User {
        return userRepository.findUserWithShoppingCartAndOrders(userId)
    }
}