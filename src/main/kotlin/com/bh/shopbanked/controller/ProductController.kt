package com.bh.shopbanked.controller

import com.bh.shopbanked.entity.Product
import com.bh.shopbanked.service.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController @Autowired constructor(
    private val productService: ProductService
) {
    // 根据商品ID获取商品
    @GetMapping("/{productId}")
    fun getProductById(@PathVariable productId: Long): ResponseEntity<Product> {
        val product = productService.getProductById(productId)
        return ResponseEntity.ok(product)
    }
    @PostMapping("/add")
    fun addProduct(@RequestBody product: Product): ResponseEntity<Map<String, Any>> {
        val addedProduct = productService.addProduct(product)
        val response = mapOf("msg" to "Product added successfully", "code" to 200, "product" to addedProduct)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/delete/{productId}")
    fun deleteProduct(@PathVariable productId: Long): ResponseEntity<Map<String, Any>> {
        productService.deleteProduct(productId)
        val response = mapOf("msg" to "Product deleted successfully", "code" to 200)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/search")
    fun searchProductByName(@RequestParam name: String): ResponseEntity<Map<String, Any>> {
        val products = productService.getProductByName(name)
        val response = mapOf("msg" to "Product search successful", "code" to 200, "products" to products)
        return ResponseEntity.ok(response)
    }
    @GetMapping("/all")
    fun getAllProducts(): ResponseEntity<List<Product>> {
        val products = productService.getAllProducts()
        return ResponseEntity.ok(products)
    }
    // 根据分类名获取商品列表
    @GetMapping("/category")
    fun getProductsByCategoryName(@RequestParam categoryName: String): ResponseEntity<List<Product>> {
        val products = productService.getProductsByCategoryName(categoryName)
        return ResponseEntity.ok(products)
    }
}






