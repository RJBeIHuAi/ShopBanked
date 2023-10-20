package com.bh.shopbanked.controller

import com.bh.shopbanked.entity.Order
import com.bh.shopbanked.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/orders")
class OrderController @Autowired constructor(
    private val orderService: OrderService
) {
    @PostMapping("/create")
    fun createOrder(
        @RequestParam userId: Long,
        @RequestParam productId: Long,
        @RequestParam quantity: Int
    ): ResponseEntity<Map<String, Any>> {
        orderService.createOrder(userId, productId, quantity)

        // Prepare the response data
        val responseData = mapOf("msg" to "Order created successfully")

        return ResponseEntity.ok(responseData)
    }
    @GetMapping("/user/{userId}")
    fun getOrdersByUserId(@PathVariable userId: Long): ResponseEntity<List<Order>> {
        val orders = orderService.getOrdersByUserId(userId)

        return if (orders.isNotEmpty()) {
            ResponseEntity.ok(orders)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}