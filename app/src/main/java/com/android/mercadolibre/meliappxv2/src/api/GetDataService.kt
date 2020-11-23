package com.android.mercadolibre.meliappxv2.src.api

import com.android.mercadolibre.meliappxv2.src.model.ProductDetail
import com.android.mercadolibre.meliappxv2.src.model.ProductSearch
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetDataService {
    
    @GET("sites/MLA/search")
    fun productSearch(@Query("q") query: String?, @Query("total") total: Int?, @Query("offset") offset: Int?, @Query("limit") limit: Int?): Call<ProductSearch?>?

    @GET("items/{id}")
    fun productDetail(@Path("id") id: String?): Call<ProductDetail?>?
}