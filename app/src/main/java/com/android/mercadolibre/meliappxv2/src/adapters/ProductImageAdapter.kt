package com.android.mercadolibre.meliappxv2.src.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mercadolibre.meliappxv2.databinding.ProductImageItemBinding
import com.android.mercadolibre.meliappxv2.src.model.ProductImage

class ProductImageAdapter(private val productImages: List<ProductImage>?) : RecyclerView.Adapter<ProductImageAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ProductImageItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        val productImage = productImages!![position]
        itemViewHolder.bind(productImage)
    }

    override fun getItemCount(): Int {
        return productImages?.size ?: 0
    }

    class ItemViewHolder(private val binding: ProductImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productImage: ProductImage?) {
            binding.productImage = productImage
            binding.executePendingBindings()
        }
    }
}