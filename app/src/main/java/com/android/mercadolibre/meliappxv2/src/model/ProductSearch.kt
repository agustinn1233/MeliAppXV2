package com.android.mercadolibre.meliappxv2.src.model

import com.google.gson.annotations.SerializedName
import java.util.*

class ProductSearch {
    @SerializedName("results")
    var products: List<Product> = ArrayList()

    @SerializedName("paging")
    var paging: Paging

    class Paging(@field:SerializedName("offset") var offset: Int, @field:SerializedName("total") var total: Int)

    init {
        paging = Paging(0, 0)
    }
}