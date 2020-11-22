package com.android.mercadolibre.meliappxv2.src.api

import com.android.mercadolibre.meliappxv2.src.tools.utils.Constants
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientInstance {
    private var retrofit: Retrofit? = null
    @JvmStatic
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                val gson = GsonBuilder()
                        .setLenient()
                        .create()
                retrofit = Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()
            }
            return retrofit
        }
}