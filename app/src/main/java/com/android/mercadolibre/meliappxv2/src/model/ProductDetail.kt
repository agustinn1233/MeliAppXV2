package com.android.mercadolibre.meliappxv2.src.model

import com.android.mercadolibre.meliappxv2.src.tools.utils.Generic.fromHtml
import com.google.gson.annotations.SerializedName

class ProductDetail {
    @SerializedName("id")
    var id: String? = null

    @JvmField
    @SerializedName("title")
    var title: String? = null

    @SerializedName("price")
    var price: String? = null

    @JvmField
    @SerializedName("warranty")
    var warranty: String? = null

    @JvmField
    @SerializedName("pictures")
    var productImages: List<ProductImage>? = null

    @JvmField
    @SerializedName("attributes")
    var productCharacteristics: List<ProductCharacteristics>? = null

    constructor()

    val priceFormatter: String
        get() = Product.currencyFormat(fromHtml(price))

    constructor(id: String?, title: String?, price: String?, warranty: String?, productImages: List<ProductImage>?, productCharacteristics: List<ProductCharacteristics>?) {
        this.id = id
        this.title = title
        this.price = price
        this.warranty = warranty
        this.productImages = productImages
        this.productCharacteristics = productCharacteristics
    }

    constructor(productDetailFromJson: ProductDetail) {
        id = productDetailFromJson.id
        title = productDetailFromJson.title
        price = productDetailFromJson.price
        warranty = productDetailFromJson.warranty
        productImages = productDetailFromJson.productImages
        productCharacteristics = productDetailFromJson.productCharacteristics
    }
}