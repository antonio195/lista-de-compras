package com.antoniocostadossantos.listadecompras.data.repositories

import com.antoniocostadossantos.listadecompras.data.database.ProductDao
import com.antoniocostadossantos.listadecompras.domain.model.Product
import com.antoniocostadossantos.listadecompras.domain.repositories.ProductRepository

class ProductRepositoryImpl(
    private val productDao: ProductDao
) : ProductRepository {
    override fun getAllProducts() = productDao.getAll()
    override suspend fun newProduct(newProduct: Product) = productDao.insertProduct(newProduct)
    override suspend fun deleteProduct(newProduct: Product) = productDao.deleteProduct(newProduct)
}