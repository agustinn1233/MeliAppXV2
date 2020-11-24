package com.android.mercadolibre.meliappxv2.src.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.mercadolibre.meliappxv2.databinding.ProductCharacteristicsItemBinding
import com.android.mercadolibre.meliappxv2.src.model.ProductCharacteristics



class ProductCharacteristicsAdapter(private val productCharacteristics: List<ProductCharacteristics>?) : RecyclerView.Adapter<ProductCharacteristicsAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ProductCharacteristicsItemBinding.inflate(layoutInflater, parent, false)
        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        val productAttribute = productCharacteristics!![position]
        itemViewHolder.bind(productAttribute)
    }


    override fun getItemCount(): Int {
        return if (productCharacteristics?.size!! > limitAttributes) {
            limitAttributes
        } else {
            this.productCharacteristics.size
        }
    }

    class ItemViewHolder(private val productAttributesItemBinding: ProductCharacteristicsItemBinding) : RecyclerView.ViewHolder(productAttributesItemBinding.root) {
        fun bind(productCharacteristics: ProductCharacteristics?) {
            productAttributesItemBinding.productCharacteristics = productCharacteristics
            productAttributesItemBinding.executePendingBindings()
        }
    }

    companion object {
        private const val limitAttributes = 12
    }
}