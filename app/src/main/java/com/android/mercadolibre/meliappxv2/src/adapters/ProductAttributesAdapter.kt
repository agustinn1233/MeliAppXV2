package com.android.mercadolibre.meliappxv2.src.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mercadolibre.meliappxv2.databinding.ProductAttributesItemBinding
import com.android.mercadolibre.meliappxv2.src.model.ProductAttribute

class ProductAttributesAdapter(private val productAttributes: List<ProductAttribute>?) : RecyclerView.Adapter<ProductAttributesAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ProductAttributesItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        val productAttribute = productAttributes!![position]
        itemViewHolder.bind(productAttribute)
    }

    override fun getItemCount(): Int {
        return productAttributes?.size ?: 0
    }

    class ItemViewHolder(private val productAttributesItemBinding: ProductAttributesItemBinding) : RecyclerView.ViewHolder(productAttributesItemBinding.root) {
        fun bind(productAttribute: ProductAttribute?) {
            productAttributesItemBinding.productAttribute = productAttribute
            productAttributesItemBinding.executePendingBindings()
        }
    }
}