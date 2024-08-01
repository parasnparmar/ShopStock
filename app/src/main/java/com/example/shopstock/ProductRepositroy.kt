package com.example.shopstock

import android.content.Context

class ProductRepositroy(context: Context) {
    private val productDao: ProductDao = ProductDB.getDatabase(context).productDao()

    suspend fun insert(product: Product){
        productDao.insert(product)
    }
    suspend fun getAllProducts(): List<Product>{
        return productDao.getAllProducts()

    }
}