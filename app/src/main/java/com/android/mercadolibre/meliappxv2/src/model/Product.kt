package com.android.mercadolibre.meliappxv2.src.model

import android.text.Html
import android.text.Spanned
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.mercadolibre.meliappxv2.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.annotations.SerializedName
import java.text.DecimalFormat

class Product {
    @JvmField
    var id: String? = null
    @JvmField
    var thumbnail: String? = null

    @SerializedName("title")
    var description: String? = null
    private var price: String? = null
    private var condition: String? = null

    @SerializedName("sold_quantity")
    var soldQuantity = 0

    @SerializedName("available_quantity")
    var availableQuantity = 0

    @JvmField
    @SerializedName("shipping")
    var shipping: Shipping? = null

    constructor()
    constructor(id: String?, thumbnail: String?, description: String?, price: String?, condition: String?, soldQuantity: Int, availableQuantity: Int, shipping: Shipping?) {
        this.id = id
        this.thumbnail = thumbnail
        this.description = description
        this.price = price
        this.condition = condition
        this.soldQuantity = soldQuantity
        this.availableQuantity = availableQuantity
        this.shipping = shipping
    }

    class Shipping {
        @JvmField
        @SerializedName("free_shipping")
        var freeShipping: String? = null
    }

    val priceFormatter: String
        get() = currencyFormat(Html.fromHtml(price))

    @JvmName("getDescription1")
    fun getDescription(): String {
        // Formateo el texto para mostrarlo
        return Html.fromHtml(description).toString()
    }

    companion object {
        fun currencyFormat(amount: Spanned): String {
            val formatter = DecimalFormat("###,###,##0.00")
            return formatter.format(amount.toString().toDouble())
        }

        @JvmStatic
        @BindingAdapter("imageProduct")
        fun loadImage(imageView: ImageView, imageURL: String?) {
            Glide.with(imageView.context)
                    .load(imageURL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(imageView)
        }
    }
}