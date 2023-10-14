package com.bh.shopbanked.controller

import com.bh.shopbanked.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class   UserController @Autowired constructor(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun registerUser(
        @RequestParam username: String,
        @RequestParam email: String,
        @RequestParam password: String
    ): ResponseEntity<Map<String, Any>> {
        val user = userService.registerUser(username, email, password)
        val response = mapOf("msg" to "User registered successfully", "code" to 200, "user" to user)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/login")
    fun loginUser(
        @RequestParam username: String,
        @RequestParam password: String
    ): ResponseEntity<Map<String, Any>> {
        val user = userService.login(username, password)
        return if (user != null) {
            val response = mapOf("msg" to "Login successful", "code" to 200, "user" to user)
            ResponseEntity.ok(response)
        } else {
            val response = mapOf("msg" to "Login failed", "code" to 401)
            ResponseEntity.status(401).body(response)
        }
    }

    @GetMapping("/{userId}/details")
    fun getUserDetails(@PathVariable userId: Long): ResponseEntity<Map<String, Any>> {
        val user = userService.getUserWithShoppingCartAndOrders(userId)
        val response = mapOf("msg" to "User details retrieved successfully", "code" to 200, "user" to user)
        return ResponseEntity.ok(response)
    }
}