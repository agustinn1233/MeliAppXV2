package com.android.mercadolibre.meliappxv2.src.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.android.mercadolibre.meliappxv2.R
import com.android.mercadolibre.meliappxv2.databinding.ProductBinding
import com.android.mercadolibre.meliappxv2.src.adapters.ProductsAdapter.ProductViewHolder
import com.android.mercadolibre.meliappxv2.src.model.Product
import com.android.mercadolibre.meliappxv2.src.ui.ProductDetailFragment
import com.android.mercadolibre.meliappxv2.src.viewmodel.BaseMlViewModel

@Suppress("UNREACHABLE_CODE")
class ProductsAdapter(private val baseMlViewModel: BaseMlViewModel, fragmentManager: FragmentManager) : RecyclerView.Adapter<ProductViewHolder>(), OnItemClickListener {
    private val products: List<Product>?
    private val fragmentManager: FragmentManager
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = ProductBinding.inflate(layoutInflater, parent, false)
        return ProductViewHolder(itemBinding, this)
    }

    override fun onBindViewHolder(itemViewHolder: ProductViewHolder, position: Int) {
        val product = products!![position]
        itemViewHolder.bind(product)
    }

    override fun getItemCount(): Int {
        if (products?.size!! > 0) {
            return products.size
        } else {
            return 0
            Log.w("log_getProducts", "No se retornan articulos.")
        }
    }

    override fun onItemClick(view: View?, position: Int) {
        baseMlViewModel.productSelected = products!![position]
        val productDetailFragment = ProductDetailFragment()
        fragmentManager.beginTransaction()
                .replace(R.id.activity_main_fragment, productDetailFragment)
                .addToBackStack(null)
                .commitAllowingStateLoss()
    }

    class ProductViewHolder(private val productBinding: ProductBinding, private val productViewClickListener: OnItemClickListener) : RecyclerView.ViewHolder(productBinding.root), View.OnClickListener {
        override fun onClick(view: View) {
            productViewClickListener.onItemClick(view, adapterPosition)
        }

        fun bind(product: Product?) {
            productBinding.product = product
            productBinding.executePendingBindings()
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    init {
        products = baseMlViewModel.productSearchResult.products
        this.fragmentManager = fragmentManager
    }
}