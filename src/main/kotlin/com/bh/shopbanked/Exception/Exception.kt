package com.bh.shopbanked.Exception

class UserNotFoundException(message: String) : RuntimeException(message)
class ShoppingCartNotFoundException(message: String) : RuntimeException(message)
class ProductNotFoundException(message: String) : RuntimeException(message)
class OrderNotFoundException(message: String): RuntimeException(message)
class CartItemNotFoundException(message: String): RuntimeException(message)
class InsufficientCartItemQuantityException(message: String): RuntimeException(message)