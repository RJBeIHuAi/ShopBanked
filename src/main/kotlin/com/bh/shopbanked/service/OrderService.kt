package com.bh.shopbanked.service

import com.bh.shopbanked.Exception.UserNotFoundException
import com.bh.shopbanked.Repository.OrderItemRepository
import com.bh.shopbanked.Repository.OrderRepository
import com.bh.shopbanked.Repository.UserRepository
import com.bh.shopbanked.entity.Order
import com.bh.shopbanked.entity.OrderItem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class OrderService  @Autowired constructor(
    private val userRepository: UserRepository,
    private val productService: ProductService,
    private val orderRepository: OrderRepository,
) {
    @Transactional
    fun createOrder(userId: Long, productId: Long, quantity: Int): Order {
        // 1. 根据用户ID获取用户信息
        val user = userRepository.findById(userId)
            .orElseThrow { UserNotFoundException("User not found") }

        // 2. 根据商品ID获取商品信息
        val product = productService.getProductById(productId)

        // 计算总价
        val totalPrice = product.price * quantity

        // 创建订单
        val order = Order(
            orderNumber = generateOrderNumber(), // 生成订单号的逻辑（自定义实现）
            orderDate = LocalDate.now(),
            user = user,
        )

        // 创建订单项
        val orderItem = OrderItem(
            quantity = quantity,
            price = totalPrice,
            product = product,
            order = order
        )

        // 将订单项添加到订单中
        order.orderItems.add(orderItem)

        // 保存订单到数据库
        orderRepository.save(order)

        return order
    }

    // 生成订单号的方法
    private fun generateOrderNumber(): String {
        // 实现随机生成订单号的逻辑，可以根据需求进行自定义
        // 例如，可以生成一个包含时间戳和随机数的唯一字符串
        val timestamp = System.currentTimeMillis()
        val random = (Math.random() * 10000).toInt()
        return "ORDER-$timestamp-$random"
    }
}