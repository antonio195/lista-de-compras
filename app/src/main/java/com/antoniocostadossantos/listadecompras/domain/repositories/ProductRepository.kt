package com.antoniocostadossantos.listadecompras.domain.repositories

import com.antoniocostadossantos.listadecompras.data.database.ProductDao
import com.antoniocostadossantos.listadecompras.domain.model.Product

class ProductRepository(
    private val productDao: ProductDao
) {

    fun getAllProducts() = productDao.getAll()
    fun getProduct(productId: Long) = productDao.getProduct(productId)

    suspend fun newProduct(newProduct: Product) = productDao.insertProduct(newProduct)
    suspend fun updateProduct(newProduct: Product) = productDao.updateProduct(newProduct)
    suspend fun deleteProduct(newProduct: Product) = productDao.deleteProduct(newProduct)
}