package com.android.mercadolibre.meliappxv2.src.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.android.mercadolibre.meliappxv2.src.model.Product;
import com.android.mercadolibre.meliappxv2.src.model.ProductDetail;
import com.android.mercadolibre.meliappxv2.src.model.ProductSearch;

public class BaseMlViewModel extends ViewModel {

    private final MutableLiveData<String> query;
    private final MutableLiveData<ProductSearch> productSearchResult;
    private final MutableLiveData<Product> productSelected;
    private final MutableLiveData<ProductDetail> productDetail;
    private final MutableLiveData<Boolean> newQueryProductList, newQueryProductDetail, visibilityProgressBar;


    public BaseMlViewModel() {
        query = new MutableLiveData<>();
        productSearchResult = new MutableLiveData<>();
        productSelected = new MutableLiveData<>();
        productDetail = new MutableLiveData<>();
        newQueryProductList = new MutableLiveData<>();
        newQueryProductDetail = new MutableLiveData<>();
        visibilityProgressBar = new MutableLiveData<>();

        // Set Default values.
        productSearchResult.setValue(new ProductSearch());
        newQueryProductList.setValue(false);
        newQueryProductDetail.setValue(true);
        visibilityProgressBar.setValue(true);
    }

    public MutableLiveData<Boolean> getNewQueryProductList() {
        return newQueryProductList;
    }

    public void setNewQueryProductList(Boolean newQueryProductList) {
        this.newQueryProductList.setValue(newQueryProductList);
    }

    public LiveData<Boolean> getNewQueryProductDetail() {
        return newQueryProductDetail;
    }

    public void setNewQueryProductDetail(Boolean newQueryProductDetail) {
        this.newQueryProductDetail.setValue(newQueryProductDetail);
    }

    public Product getProductSelected() {
        return productSelected.getValue();
    }

    public void setProductSelected(Product productSelected) {
        this.productSelected.setValue(productSelected);
    }

    public String getQuery() {
        return query.getValue();
    }

    public void setQuery(String query) {
        this.query.setValue(query);
    }

    public void setVisibilityProgressBar(Boolean visibilityProgressBar) {
        this.visibilityProgressBar.setValue(visibilityProgressBar);
    }

    public ProductDetail getProductDetail() {
        return productDetail.getValue();
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail.setValue(productDetail);
    }

    public ProductSearch getProductSearchResult() {
        return productSearchResult.getValue();
    }

    public void setProductSearchResult(ProductSearch productSearchResult) {
        this.productSearchResult.setValue(productSearchResult);
    }

}
