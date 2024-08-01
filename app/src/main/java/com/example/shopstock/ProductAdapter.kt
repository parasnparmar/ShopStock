package com.example.shopstock

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shopstock.databinding.StocksViewBinding

class ProductAdapter(private var product: List<Product>): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    inner class ProductViewHolder(private val binding: StocksViewBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.txtStockTitle.text = product.name
            binding.txtStockQuantity.text = product.quantity.toString()
            binding.txtStockPrice.text = product.price.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
      val binding = StocksViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(product[position])
    }

    override fun getItemCount(): Int {
       return product.size
    }

    fun updateProduct(product: List<Product>){
        this.product = product
        notifyDataSetChanged()
    }

}