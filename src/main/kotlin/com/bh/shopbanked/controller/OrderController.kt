package com.bh.shopbanked.controller

import com.bh.shopbanked.Exception.OrderNotFoundException
import com.bh.shopbanked.entity.Order
import com.bh.shopbanked.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
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

    @DeleteMapping("/delete/{orderId}")
    fun deleteOrder(@PathVariable orderId: Long): ResponseEntity<String> {
        return try {
            orderService.deleteOrderById(orderId)
            ResponseEntity.ok("Order with ID $orderId has been deleted.")
        } catch (e: OrderNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found.")
        }
    }
}