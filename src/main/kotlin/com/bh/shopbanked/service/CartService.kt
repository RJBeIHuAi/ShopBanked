package com.bh.shopbanked.service

import com.bh.shopbanked.Exception.ShoppingCartNotFoundException
import com.bh.shopbanked.Exception.UserNotFoundException
import com.bh.shopbanked.Repository.CartItemRepository
import com.bh.shopbanked.Repository.ShoppingCartRepository
import com.bh.shopbanked.Repository.UserRepository
import com.bh.shopbanked.entity.CartItem
import com.bh.shopbanked.entity.ShoppingCart
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CartService @Autowired constructor(
    private val userRepository: UserRepository,
    private val productService: ProductService,
    private val shoppingCartRepository: ShoppingCartRepository,
    private val cartItemRepository: CartItemRepository
) {
    @Transactional
    fun addToCart(userId: Long, productId: Long, quantity: Int) {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException("User not found") }

        // 获取商品信息
        val product = productService.getProductById(productId)

        // 获取用户的购物车
        val shoppingCart = user.shoppingCart ?: ShoppingCart(user = user)

        // 检查购物车中是否已经有相同的商品
        val existingCartItem = shoppingCart.cartItems.find { it.product == product }

        if (existingCartItem != null) {
            // 更新购物车项的数量
            existingCartItem.quantity += quantity
        } else {
            // 创建新的购物车项
            val cartItem = CartItem(quantity = quantity, product = product, shoppingCart = shoppingCart)
            shoppingCart.cartItems.add(cartItem)
        }

        // 更新购物车
        shoppingCartRepository.save(shoppingCart)
    }
    @Transactional
    fun removeFromCart(userId: Long, productId: Long) {
        val user = userRepository.findById(userId).orElseThrow { UserNotFoundException("User not found") }

        val shoppingCart = user.shoppingCart ?: throw ShoppingCartNotFoundException("Shopping cart not found")

        val cartItemToRemove = shoppingCart.cartItems.find { it.product?.id == productId }
        if (cartItemToRemove != null) {
            // 从购物车中移除购物车项
            shoppingCart.cartItems.remove(cartItemToRemove)

            // 删除购物车项
            cartItemRepository.delete(cartItemToRemove)
            // 可以根据需要进行其他处理，例如更新购物车的总价等

            // 保存购物车
            shoppingCartRepository.save(shoppingCart)
        }
    }

    fun getCartByUserId(userId: Long): ShoppingCart? {
        val user = userRepository.findById(userId)
        if (user.isPresent) {
            return user.get().shoppingCart
        } else {
            throw UserNotFoundException("User not found")
        }
    }
}


