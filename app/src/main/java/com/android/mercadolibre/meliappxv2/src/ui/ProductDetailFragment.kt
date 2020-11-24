package com.android.mercadolibre.meliappxv2.src.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.mercadolibre.meliappxv2.R
import com.android.mercadolibre.meliappxv2.databinding.FragmentProductDetailBinding
import com.android.mercadolibre.meliappxv2.src.adapters.ProductAttributesAdapter
import com.android.mercadolibre.meliappxv2.src.adapters.ProductImageAdapter
import com.android.mercadolibre.meliappxv2.src.api.GetDataService
import com.android.mercadolibre.meliappxv2.src.api.RetrofitClientInstance.retrofitInstance
import com.android.mercadolibre.meliappxv2.src.model.ProductAttribute
import com.android.mercadolibre.meliappxv2.src.model.ProductDetail
import com.android.mercadolibre.meliappxv2.src.model.ProductImage
import com.android.mercadolibre.meliappxv2.src.tools.utils.Generic
import com.android.mercadolibre.meliappxv2.src.viewmodel.BaseMlViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProductDetailFragment : Fragment() {
    private var baseMlViewModel: BaseMlViewModel? = null
    private var fragmentProductDetailBinding: FragmentProductDetailBinding? = null
    override fun onResume() {
        super.onResume()
        if (activity != null) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    override fun onPause() {
        super.onPause()
        if (activity != null) {
            requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        baseMlViewModel = ViewModelProvider(requireActivity(), NewInstanceFactory()).get(BaseMlViewModel::class.java)
        //fragmentProductDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        fragmentProductDetailBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_detail,container,false)
        //fragmentProductDetailBinding.setLifecycleOwner(this)
        fragmentProductDetailBinding?.lifecycleOwner = this
        fragmentProductDetailBinding?.recyclerViewProductImageHorizontal?.setHasFixedSize(true)
        fragmentProductDetailBinding?.recyclerViewProductDetail?.setHasFixedSize(true)
        fragmentProductDetailBinding?.recyclerViewProductImageHorizontal?.adapter = ProductImageAdapter(ArrayList())
        fragmentProductDetailBinding?.recyclerViewProductDetail?.adapter = ProductAttributesAdapter(ArrayList())

        // Using for LiveData
        val newQueryProductDetail = Observer { aBoolean: Boolean ->
            if (aBoolean) {
                // Load
                fragmentProductDetailBinding?.progressBarProductDetail?.visibility = View.VISIBLE
                fragmentProductDetailBinding?.txtViewProductCharacteristic?.visibility = View.INVISIBLE
                fragmentProductDetailBinding?.linearLayoutDescripcion?.visibility = View.INVISIBLE
                productDetail
            } else {
                updateProductDetail()
            }
        }
        baseMlViewModel!!.newQueryProductDetail.observe(viewLifecycleOwner, newQueryProductDetail)
        return fragmentProductDetailBinding?.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateProductDetail() {
        val linearLayoutManager = LinearLayoutManager(context)
        fragmentProductDetailBinding!!.txtViewProductName.text = Generic.fromHtml(baseMlViewModel!!.productDetail.title)
        baseMlViewModel!!.productDetail.priceFormatter
        fragmentProductDetailBinding!!.txtViewProductPrice.text = "$ " + baseMlViewModel!!.productDetail.priceFormatter
        fragmentProductDetailBinding!!.txtViewProductWarranty.text = baseMlViewModel!!.productDetail.warranty
        val recyclerViewPictures = fragmentProductDetailBinding!!.recyclerViewProductImageHorizontal
        val productImageList: MutableList<ProductImage> = ArrayList()
        if (baseMlViewModel!!.productDetail != null) (baseMlViewModel!!.productDetail.productImages).let {
            if (it != null) {
                productImageList.addAll(it)
            }
        }
        val productImageAdapter = ProductImageAdapter(productImageList)
        recyclerViewPictures.adapter = productImageAdapter
        val recyclerViewAttribute = fragmentProductDetailBinding!!.recyclerViewProductDetail
        recyclerViewAttribute.layoutManager = linearLayoutManager
        val productAttributeList: MutableList<ProductAttribute> = ArrayList()
        if (baseMlViewModel!!.productDetail != null) {
            (baseMlViewModel!!.productDetail.productAttributes).let {
                if (it != null) {
                    productAttributeList.addAll(it)
                }
            }
        }
        val productAttributesAdapter = ProductAttributesAdapter(productAttributeList)
        recyclerViewAttribute.adapter = productAttributesAdapter
        fragmentProductDetailBinding!!.progressBarProductDetail.visibility = View.INVISIBLE
        fragmentProductDetailBinding!!.txtViewProductCharacteristic.visibility = View.VISIBLE
        fragmentProductDetailBinding!!.linearLayoutDescripcion.visibility = View.VISIBLE
    }

    private val productDetail: Unit
        get() {
            val searchListCall = Objects.requireNonNull(retrofitInstance)?.create(GetDataService::class.java)?.productDetail(baseMlViewModel!!.productSelected.id)!!
            searchListCall.enqueue(object : Callback<ProductDetail?> {
                override fun onResponse(call: Call<ProductDetail?>, response: Response<ProductDetail?>) {
                    if (response.isSuccessful && response.body() != null) {
                        baseMlViewModel!!.productDetail = response.body()
                        baseMlViewModel!!.setVisibilityProgressBar(false)
                        fragmentProductDetailBinding!!.progressBarProductDetail.visibility = View.INVISIBLE
                        fragmentProductDetailBinding!!.txtViewProductCharacteristic.visibility = View.VISIBLE
                        baseMlViewModel!!.setNewQueryProductDetail(false)
                        Log.i("log_getProductDetail", "call isSuccessful")
                    }
                }

                override fun onFailure(call: Call<ProductDetail?>, throwable: Throwable) {
                    call.cancel()
                    fragmentProductDetailBinding!!.progressBarProductDetail.visibility = View.GONE
                    Log.i("log_getProductDetail", "call onFailure")

                    // Error to call, show AlertDialog from user.
                    AlertDialog.Builder(
                            requireContext())
                            .setTitle(Generic.fromHtml(getString(R.string.alert_dialog_onFailure_call_title)))
                            .setMessage(Generic.fromHtml(getString(R.string.alert_dialog_onFailure_call_message)))
                            .setPositiveButton(Generic.fromHtml(getString(R.string.alert_dialog_onFailure_call_accepts)), null)
                            .setIcon(android.R.drawable.ic_dialog_alert) // Default icon to alert
                            .show()
                }
            })
        }
}