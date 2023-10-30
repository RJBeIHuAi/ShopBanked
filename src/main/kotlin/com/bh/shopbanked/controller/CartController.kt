package com.bh.shopbanked.controller

import com.bh.shopbanked.Exception.CartItemNotFoundException
import com.bh.shopbanked.Exception.InsufficientCartItemQuantityException
import com.bh.shopbanked.Exception.ShoppingCartNotFoundException
import com.bh.shopbanked.Exception.UserNotFoundException
import com.bh.shopbanked.Repository.UserRepository
import com.bh.shopbanked.entity.ShoppingCart
import com.bh.shopbanked.service.CartService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/cart")
class CartController @Autowired constructor(
    private val cartService: CartService,
    private val userRepository: UserRepository // 可能需要用户信息
) {
    // 添加商品到购物车
    @PostMapping("/add")
    fun addToCart(
        @RequestParam userId: Long,
        @RequestParam productId: Long,
        @RequestParam quantity: Int
    ): ResponseEntity<Map<String, Any>> {
        cartService.addToCart(userId, productId, quantity)
        val response = mapOf("msg" to "Product added to cart successfully", "code" to 200)
        return ResponseEntity.ok(response)
    }

    // 查看购物车
    @GetMapping("/view/{userId}")
    fun getCartByUserId(@PathVariable userId: Long): ResponseEntity<ShoppingCart> {
        return try {
            val shoppingCart = cartService.getCartByUserId(userId)
            ResponseEntity.ok(shoppingCart)
        } catch (e: UserNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    // 从购物车移除商品
    @DeleteMapping("/remove")
    fun removeFromCart(
        @RequestParam userId: Long,
        @RequestParam productId: Long
    ): ResponseEntity<Map<String, Any>> {
        cartService.removeFromCart(userId, productId)
        val response = mapOf("msg" to "Product removed from cart successfully", "code" to 200)
        return ResponseEntity.ok(response)
    }
    @PutMapping("/decrease")
    fun decreaseCartItemQuantity(
        @RequestParam userId: Long,
        @RequestParam productId: Long,
        @RequestParam quantityToDecrease: Int
    ): ResponseEntity<String> {
        return try {
            cartService.decreaseCartItemQuantity(userId, productId, quantityToDecrease)
            ResponseEntity.ok("Cart item quantity decreased successfully.")
        } catch (e: UserNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.")
        } catch (e: ShoppingCartNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Shopping cart not found.")
        } catch (e: CartItemNotFoundException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart item not found.")
        } catch (e: InsufficientCartItemQuantityException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient quantity in the cart.")
        }
    }

}
