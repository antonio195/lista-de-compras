package com.antoniocostadossantos.listadecompras.domain.repositories

import com.antoniocostadossantos.listadecompras.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    suspend fun newProduct(newProduct: Product): Long
    suspend fun deleteProduct(newProduct: Product)
}