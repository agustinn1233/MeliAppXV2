package com.android.mercadolibre.meliappxv2.src.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mercadolibre.meliappxv2.R
import com.android.mercadolibre.meliappxv2.databinding.FragmentProductSearchBinding
import com.android.mercadolibre.meliappxv2.src.adapters.ProductsAdapter
import com.android.mercadolibre.meliappxv2.src.api.GetDataService
import com.android.mercadolibre.meliappxv2.src.api.RetrofitClientInstance.retrofitInstance
import com.android.mercadolibre.meliappxv2.src.model.ProductSearch
import com.android.mercadolibre.meliappxv2.src.tools.utils.Constants.SEARCH_LIMIT_INT
import com.android.mercadolibre.meliappxv2.src.tools.utils.Generic
import com.android.mercadolibre.meliappxv2.src.viewmodel.BaseMlViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ProductSearchFragment : Fragment() {
    private var baseMlViewModel: BaseMlViewModel? = null
    private var fragmentProductSearchBinding: FragmentProductSearchBinding? = null
    private var loading = true
    private var first = true
    var linearLayoutManager: LinearLayoutManager? = null
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
        fragmentProductSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_search, container, false)
        fragmentProductSearchBinding?.lifecycleOwner = this
        linearLayoutManager = LinearLayoutManager(context)
        fragmentProductSearchBinding?.recyclerViewProducts?.layoutManager = linearLayoutManager
        fragmentProductSearchBinding?.recyclerViewProducts?.setHasFixedSize(true)

        // Set DividerItemDecoration same to MercadoLibre.
        fragmentProductSearchBinding?.recyclerViewProducts?.addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))

        // Using for LiveData
        val newQueryProductSearch = Observer { aBoolean: Boolean ->
            if (aBoolean) {
                products
            } else {
                productSearch()
            }
        }
        baseMlViewModel!!.newQueryProductList.observe(viewLifecycleOwner, newQueryProductSearch)
        if (!first) {
            fragmentProductSearchBinding?.featureMercadoPagoAdvertising?.visibility = View.GONE
            fragmentProductSearchBinding?.featureBestSummerAirAdvertising?.visibility = View.GONE
        } else {
            fragmentProductSearchBinding?.featureMercadoPagoAdvertising?.visibility = View.VISIBLE
            fragmentProductSearchBinding?.featureBestSummerAirAdvertising?.visibility = View.VISIBLE
            first = false
        }
        return fragmentProductSearchBinding?.root
    }

    private fun productSearch() {
        // Product Search field.
        fragmentProductSearchBinding!!.ProductSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    baseMlViewModel!!.productSearchResult = ProductSearch()
                    baseMlViewModel!!.query = query
                    baseMlViewModel!!.setNewQueryProductList(true)
                } else {
                    Toast.makeText(activity, "Debe ingresar un texto para realizar la busqueda.", Toast.LENGTH_SHORT).show()
                }
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                return false
            }
        })
        fragmentProductSearchBinding!!.recyclerViewProducts.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int
                val totalItemCount: Int
                val pastVisibleItems: Int
                if (loading) {
                    if (dy > 0) {
                        visibleItemCount = linearLayoutManager!!.childCount
                        totalItemCount = linearLayoutManager!!.itemCount
                        pastVisibleItems = linearLayoutManager!!.findFirstVisibleItemPosition()
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loading = false
                            baseMlViewModel!!.productSearchResult.paging.offset = baseMlViewModel!!.productSearchResult.paging.offset + SEARCH_LIMIT_INT
                            baseMlViewModel!!.setNewQueryProductList(false)
                            Log.v("log_onScrolled_pSearch", "Reached Last Item")
                        }
                    }
                }
            }
        })

        // Load screen product search, set in true setNewQueryProductDetail
        baseMlViewModel!!.setNewQueryProductDetail(true)
        fragmentProductSearchBinding!!.ProductSearchView.setQuery(baseMlViewModel!!.query, false)
        val productsAdapter = ProductsAdapter(baseMlViewModel!!, requireActivity().supportFragmentManager)
        fragmentProductSearchBinding!!.recyclerViewProducts.adapter = productsAdapter
    }

    private val products: Unit
        get() {
            fragmentProductSearchBinding!!.progressBarSearch.visibility = View.VISIBLE
            val searchListCall = Objects.requireNonNull(retrofitInstance)?.create(GetDataService::class.java)?.productSearch(baseMlViewModel!!.query, baseMlViewModel!!.productSearchResult.paging.total, baseMlViewModel!!.productSearchResult.paging.offset, SEARCH_LIMIT_INT)!!
            searchListCall.enqueue(object : Callback<ProductSearch?> {
                override fun onResponse(call: Call<ProductSearch?>, response: Response<ProductSearch?>) {
                    if (response.isSuccessful && response.body() != null) {
                        fragmentProductSearchBinding!!.recyclerViewProducts.visibility = View.VISIBLE
                        if (response.body()!!.products.isNotEmpty()) {
                            baseMlViewModel!!.productSearchResult = response.body()
                            val productsAdapter = ProductsAdapter(baseMlViewModel!!, requireActivity().supportFragmentManager)
                            fragmentProductSearchBinding!!.recyclerViewProducts.adapter = productsAdapter
                            fragmentProductSearchBinding!!.progressBarSearch.visibility = View.GONE
                            fragmentProductSearchBinding!!.featureMercadoPagoAdvertising.visibility = View.GONE
                            fragmentProductSearchBinding!!.featureBestSummerAirAdvertising.visibility = View.GONE
                            baseMlViewModel!!.setNewQueryProductList(false)
                            Log.i("log_getProducts", "call isSuccessful")
                        } else {
                            Toast.makeText(requireActivity(), "No se encontraron productos para la busqueda.", Toast.LENGTH_SHORT).show()
                            fragmentProductSearchBinding!!.progressBarSearch.visibility = View.GONE
                            fragmentProductSearchBinding!!.recyclerViewProducts.visibility = View.GONE
                            fragmentProductSearchBinding!!.featureMercadoPagoAdvertising.visibility = View.VISIBLE
                            fragmentProductSearchBinding!!.featureBestSummerAirAdvertising.visibility = View.VISIBLE
                        }
                    }
                }

                override fun onFailure(call: Call<ProductSearch?>, t: Throwable) {
                    call.cancel()
                    fragmentProductSearchBinding!!.progressBarSearch.visibility = View.GONE
                    Log.i("log_getProducts", "call onFailure")

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