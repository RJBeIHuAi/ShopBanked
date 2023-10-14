package com.bh.shopbanked.controller

import com.bh.shopbanked.service.OrderService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


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
        val order = orderService.createOrder(userId, productId, quantity)
        val orderItem = order.orderItems.firstOrNull() // Assuming there's only one order item per order

        // Prepare the response data
        val responseData = mutableMapOf<String, Any>()
        responseData["msg"] = "Order created successfully"

        // If an order item is present, include its details
        orderItem?.let {
            val user = it.order?.user
            val product = it.product

            responseData["orderItem"] = mapOf(
                "quantity" to it.quantity,
                "totalPrice" to it.price,
                "user" to mapOf(
                    "userId" to user?.id,
                    "username" to user?.username
                    // Add other user-related fields here as needed
                ),
                "product" to mapOf(
                    "productId" to product?.id,
                    "productName" to product?.name,
                    "producePrice" to product?.price
                    // Add other product-related fields here as needed
                )
            )
        }

        return ResponseEntity.ok(responseData)
    }
}