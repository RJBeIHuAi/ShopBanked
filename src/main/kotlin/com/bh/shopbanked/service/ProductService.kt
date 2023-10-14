package com.bh.shopbanked.service

import com.bh.shopbanked.Exception.ProductNotFoundException
import com.bh.shopbanked.Repository.CategoryRepository
import com.bh.shopbanked.Repository.ProductRepository
import com.bh.shopbanked.entity.Category
import com.bh.shopbanked.entity.Product
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ProductService @Autowired constructor(
    private val productRepository: ProductRepository,
    private val categoryRepository: CategoryRepository
) {
    // 根据商品ID获取商品
    fun getProductById(productId: Long): Product {
        return productRepository.findById(productId).orElseThrow { ProductNotFoundException("Product not found") }
    }
    fun addProduct(product: Product): Product {
        // 检查分类是否已经存在
        var category = product.category?.name?.let { categoryRepository.findByName(it) }
        if (category == null) {
            // 如果分类不存在，创建新的分类
            category = Category(name = product.category?.name!!)
            category = categoryRepository.save(category)
        }
        // 使用现有或新创建的分类
        product.category = category
        return productRepository.save(product)
    }

    fun deleteProduct(productId: Long) {
        productRepository.deleteById(productId)
    }

    fun getProductByName(name: String): List<Product> {
        return productRepository.findByName(name)
    }
    // 获取所有商品列表
    fun getAllProducts(): List<Product> {
        return productRepository.findAll()
    }

    // 更新商品信息
    fun updateProduct(productId: Long, updatedProduct: Product): Product {
        val existingProduct = getProductById(productId)

        // 更新商品信息
        existingProduct.name = updatedProduct.name
        existingProduct.description = updatedProduct.description
        existingProduct.price = updatedProduct.price
        existingProduct.imageUrl = updatedProduct.imageUrl
        existingProduct.category = updatedProduct.category // 可选

        // 可以添加其他业务逻辑，如数据验证等

        return productRepository.save(existingProduct)
    }

    // 根据分类名获取商品列表
    fun getProductsByCategoryName(categoryName: String): List<Product> {
        val category = categoryRepository.findByName(categoryName)

        if (category != null) {
            return productRepository.findByCategory(category)
        } else {
            // 如果分类不存在，可以根据业务需求返回一个空列表或抛出异常
            return emptyList()
        }
    }

}