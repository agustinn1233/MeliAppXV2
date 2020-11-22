package com.android.mercadolibre.meliappxv2.src.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.mercadolibre.meliappxv2.R;
import com.android.mercadolibre.meliappxv2.databinding.FragmentProductSearchBinding;
import com.android.mercadolibre.meliappxv2.src.adapters.ProductsAdapter;
import com.android.mercadolibre.meliappxv2.src.api.GetDataService;
import com.android.mercadolibre.meliappxv2.src.api.RetrofitClientInstance;
import com.android.mercadolibre.meliappxv2.src.model.ProductSearch;
import com.android.mercadolibre.meliappxv2.src.viewmodel.BaseMlViewModel;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.android.mercadolibre.meliappxv2.src.tools.utils.Constants.SEARCH_LIMIT_INT;

public class ProductSearchFragment extends Fragment {

    private BaseMlViewModel baseMlViewModel;

    private FragmentProductSearchBinding fragmentProductSearchBinding;

    private boolean loading = true;

    private boolean first = true;

    LinearLayoutManager linearLayoutManager;

    public ProductSearchFragment() {
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
        fragmentProductSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_search, container, false);
        fragmentProductSearchBinding.setLifecycleOwner(this);
        linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentProductSearchBinding.recyclerViewProducts.setLayoutManager(linearLayoutManager);
        fragmentProductSearchBinding.recyclerViewProducts.setHasFixedSize(true);

        // Set DividerItemDecoration same to MercadoLibre.
        fragmentProductSearchBinding.recyclerViewProducts.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Using for LiveData
        final Observer<Boolean> newQueryProductSearch = aBoolean  -> {
            if (aBoolean) {
                getProducts();
            } else {
                productSearch();
            }
        };

        baseMlViewModel.getNewQueryProductList().observe(getViewLifecycleOwner(), newQueryProductSearch);

        if(!first) {
            fragmentProductSearchBinding.featureMercadoPagoAdvertising.setVisibility(View.GONE);
            fragmentProductSearchBinding.featureBestSummerAirAdvertising.setVisibility(View.GONE);
        } else {
            fragmentProductSearchBinding.featureMercadoPagoAdvertising.setVisibility(View.VISIBLE);
            fragmentProductSearchBinding.featureBestSummerAirAdvertising.setVisibility(View.VISIBLE);
            first = false;
        }

        return fragmentProductSearchBinding.getRoot();
    }

    private void productSearch() {
        // Product Search field.
        fragmentProductSearchBinding.ProductSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()) {
                    baseMlViewModel.setProductSearchResult(new ProductSearch());
                    baseMlViewModel.setQuery(query);
                    baseMlViewModel.setNewQueryProductList(true);
                } else {
                    Toast.makeText(getActivity(), "Debe ingresar un texto para realizar la busqueda.", Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                return false;
            }
        });

        fragmentProductSearchBinding.recyclerViewProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int visibleItemCount;
                int totalItemCount;
                int pastVisibleItems;

                if(loading) {
                    if(dy > 0) {
                        visibleItemCount = linearLayoutManager.getChildCount();
                        totalItemCount = linearLayoutManager.getItemCount();
                        pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition();

                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            loading = false;
                            baseMlViewModel.getProductSearchResult().getPaging().setOffset(baseMlViewModel.getProductSearchResult().getPaging().getOffset() + SEARCH_LIMIT_INT);
                            baseMlViewModel.setNewQueryProductList(false);
                            Log.v("log_onScrolled_pSearch", "Reached Last Item");
                        }
                    }
                }
            }
        });

        // Load screen product search, set in true setNewQueryProductDetail
        baseMlViewModel.setNewQueryProductDetail(true);

        fragmentProductSearchBinding.ProductSearchView.setQuery(baseMlViewModel.getQuery(), false);
        ProductsAdapter productsAdapter = new ProductsAdapter(baseMlViewModel, getActivity().getSupportFragmentManager());
        fragmentProductSearchBinding.recyclerViewProducts.setAdapter(productsAdapter);
    }

    private void getProducts() {
        fragmentProductSearchBinding.progressBarSearch.setVisibility(View.VISIBLE);

        final Call<ProductSearch> searchListCall = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class).productSearch(baseMlViewModel.getQuery(), baseMlViewModel.getProductSearchResult().getPaging().getTotal(), baseMlViewModel.getProductSearchResult().getPaging().getOffset(), SEARCH_LIMIT_INT);
        searchListCall.enqueue(new Callback<ProductSearch>() {
            @Override
            public void onResponse(@NotNull Call<ProductSearch> call, @NotNull Response<ProductSearch> response) {
                if(response.isSuccessful() && response.body() != null) {
                    baseMlViewModel.setProductSearchResult(response.body());
                    ProductsAdapter productsAdapter = new ProductsAdapter(baseMlViewModel, getActivity().getSupportFragmentManager());
                    fragmentProductSearchBinding.recyclerViewProducts.setAdapter(productsAdapter);
                    fragmentProductSearchBinding.progressBarSearch.setVisibility(View.GONE);
                    fragmentProductSearchBinding.featureMercadoPagoAdvertising.setVisibility(View.GONE);
                    fragmentProductSearchBinding.featureBestSummerAirAdvertising.setVisibility(View.GONE);
                    baseMlViewModel.setNewQueryProductList(false);
                    Log.i("log_getProducts", "call isSuccessful");
                }
            }

            @Override
            public void onFailure(@NotNull Call<ProductSearch> call, @NotNull Throwable t) {
                call.cancel();
                fragmentProductSearchBinding.progressBarSearch.setVisibility(View.GONE);
                Log.i("log_getProducts", "call onFailure");

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