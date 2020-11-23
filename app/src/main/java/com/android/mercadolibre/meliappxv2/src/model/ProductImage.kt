package com.android.mercadolibre.meliappxv2.src.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.android.mercadolibre.meliappxv2.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.gson.annotations.SerializedName

class ProductImage(@field:SerializedName("id") val id: String, @field:SerializedName("url") val url: String) {
    companion object {
        @JvmStatic
        @BindingAdapter("imageListProduct")
        fun loadProductImage(imageView: ImageView, imageURL: String?) {
            Glide.with(imageView.context)
                    .load(imageURL)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_image_not_supported_24)
                    .into(imageView)
        }
    }
}