package com.example.shopstock

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ProductRepositroy = ProductRepositroy(application)
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun insertProduct(product: Product){
        viewModelScope.launch {
            repository.insert(product)
            _products.value = repository.getAllProducts()
        }
    }
    fun getAllProducts(){
        viewModelScope.launch {
            _products.value = repository.getAllProducts()
        }

    }
}