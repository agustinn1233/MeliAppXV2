package com.android.mercadolibre.meliappxv2.src.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mercadolibre.meliappxv2.databinding.ProductImageItemBinding
import com.android.mercadolibre.meliappxv2.src.model.ProductImage

@Suppress("UNREACHABLE_CODE")
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
        when {
            productImages?.size!! > 0 -> {
                return productImages.size
            }
            else -> {
                return 0
                Log.w("log_getProductImage", "No se retornan imagenes del articulo.")
            }
        }
    }

    class ItemViewHolder(private val binding: ProductImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(productImage: ProductImage?) {
            binding.productImage = productImage
            binding.executePendingBindings()
        }
    }
}