package com.android.mercadolibre.meliappxv2.src.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mercadolibre.meliappxv2.R;
import com.android.mercadolibre.meliappxv2.databinding.FragmentProductDetailBinding;
import com.android.mercadolibre.meliappxv2.src.adapters.ProductAttributesAdapter;
import com.android.mercadolibre.meliappxv2.src.adapters.ProductImageAdapter;
import com.android.mercadolibre.meliappxv2.src.api.GetDataService;
import com.android.mercadolibre.meliappxv2.src.api.RetrofitClientInstance;
import com.android.mercadolibre.meliappxv2.src.model.ProductAttribute;
import com.android.mercadolibre.meliappxv2.src.model.ProductDetail;
import com.android.mercadolibre.meliappxv2.src.model.ProductImage;
import com.android.mercadolibre.meliappxv2.src.viewmodel.BaseMlViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class ProductDetailFragment extends Fragment {

    private BaseMlViewModel baseMlViewModel;

    private FragmentProductDetailBinding fragmentProductDetailBinding;


    public ProductDetailFragment() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getActivity() != null) {
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
        }
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        baseMlViewModel = new ViewModelProvider(getActivity(), new ViewModelProvider.NewInstanceFactory()).get(BaseMlViewModel.class);
        fragmentProductDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false);
        fragmentProductDetailBinding.setLifecycleOwner(this);
        fragmentProductDetailBinding.recyclerViewProductImageHorizontal.setHasFixedSize(true);
        fragmentProductDetailBinding.recyclerViewProductDetail.setHasFixedSize(true);
        fragmentProductDetailBinding.recyclerViewProductImageHorizontal.setAdapter(new ProductImageAdapter(new ArrayList<>()));
        fragmentProductDetailBinding.recyclerViewProductDetail.setAdapter(new ProductAttributesAdapter(new ArrayList<>()));

        // Using for LiveData
        final Observer<Boolean> newQueryProductDetail = aBoolean  -> {
            if (aBoolean) {
                // Load
                fragmentProductDetailBinding.progressBarProductDetail.setVisibility(View.VISIBLE);
                fragmentProductDetailBinding.txtViewProductCharacteristic.setVisibility(View.INVISIBLE);
                fragmentProductDetailBinding.linearLayoutDescripcion.setVisibility(View.INVISIBLE);
                getProductDetail();
            }else{
                updateProductDetail();
            }
        };

        baseMlViewModel.getNewQueryProductDetail().observe(getViewLifecycleOwner(), newQueryProductDetail);
        return fragmentProductDetailBinding.getRoot();
    }

    private void updateProductDetail(){

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        fragmentProductDetailBinding.txtViewProductName.setText(Html.fromHtml(baseMlViewModel.getProductDetail().title));
        fragmentProductDetailBinding.txtViewProductPrice.setText(baseMlViewModel.getProductDetail().getPriceFormatter() != null ? "$ " + baseMlViewModel.getProductDetail().getPriceFormatter() : "");
        fragmentProductDetailBinding.txtViewProductWarranty.setText(baseMlViewModel.getProductDetail().warranty);

        RecyclerView recyclerViewPictures = fragmentProductDetailBinding.recyclerViewProductImageHorizontal;

        List<ProductImage> productImageList = new ArrayList<>();

        if (baseMlViewModel.getProductDetail() != null) {
            productImageList.addAll(baseMlViewModel.getProductDetail().productImages);
        }

        ProductImageAdapter productImageAdapter = new ProductImageAdapter(productImageList);
        recyclerViewPictures.setAdapter(productImageAdapter);

        RecyclerView recyclerViewAttribute = fragmentProductDetailBinding.recyclerViewProductDetail;
        recyclerViewAttribute.setLayoutManager(linearLayoutManager);

        List<ProductAttribute> productAttributeList = new ArrayList<>();

        if (baseMlViewModel.getProductDetail() != null) {
            productAttributeList.addAll(baseMlViewModel.getProductDetail().productAttributes);
        }

        ProductAttributesAdapter productAttributesAdapter = new ProductAttributesAdapter(productAttributeList);
        recyclerViewAttribute.setAdapter(productAttributesAdapter);

        fragmentProductDetailBinding.progressBarProductDetail.setVisibility(View.INVISIBLE);
        fragmentProductDetailBinding.txtViewProductCharacteristic.setVisibility(View.VISIBLE);
        fragmentProductDetailBinding.linearLayoutDescripcion.setVisibility(View.VISIBLE);
    }

    private void getProductDetail() {

        final Call<ProductDetail> searchListCall = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class).productDetail(baseMlViewModel.getProductSelected().id);
        searchListCall.enqueue(new Callback<ProductDetail>() {
            @Override
            public void onResponse(@NotNull Call<ProductDetail> call, @NotNull retrofit2.Response<ProductDetail> response) {
                if(response.isSuccessful() && response.body() != null) {
                    baseMlViewModel.setProductDetail(response.body());
                    baseMlViewModel.setVisibilityProgressBar(false);
                    fragmentProductDetailBinding.progressBarProductDetail.setVisibility(View.INVISIBLE);
                    fragmentProductDetailBinding.txtViewProductCharacteristic.setVisibility(View.VISIBLE);
                    baseMlViewModel.setNewQueryProductDetail(false);
                    Log.i("log_getProductDetail", "call isSuccessful");
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProductDetail> call, @NotNull Throwable throwable) {
                call.cancel();
                fragmentProductDetailBinding.progressBarProductDetail.setVisibility(View.GONE);
                Log.i("log_getProductDetail", "call onFailure");

                // Error to call, show AlertDialog from user.
                new AlertDialog.Builder(
                        getContext())
                        .setTitle(Html.fromHtml(getString(R.string.alert_dialog_onFailure_call_title)))
                        .setMessage(Html.fromHtml(getString(R.string.alert_dialog_onFailure_call_message)))
                        .setPositiveButton(Html.fromHtml(getString(R.string.alert_dialog_onFailure_call_accepts)), null)
                        .setIcon(android.R.drawable.ic_dialog_alert) // Default icon to alert
                        .show();
            }
        });
    }
}