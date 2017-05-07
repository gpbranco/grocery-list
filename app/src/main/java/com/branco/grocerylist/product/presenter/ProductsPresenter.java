package com.branco.grocerylist.product.presenter;

import android.content.Context;
import android.util.Log;

import com.branco.grocerylist.R;
import com.branco.grocerylist.cart.interactor.CartInteractor;
import com.branco.grocerylist.common.model.Product;
import com.branco.grocerylist.product.interactor.ProductsInteractor;
import com.branco.grocerylist.product.ui.ProductsView;
import com.branco.grocerylist.product.ui.model.ProductViewData;

import java.text.DecimalFormat;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class ProductsPresenter {

    private static final String TAG = ProductsPresenter.class.getSimpleName();

    private ProductsView productsView;
    private ProductsInteractor productsInteractor;
    private CartInteractor cartInteractor;
    private Subscription subscription;
    private Context context;
    private DecimalFormat formatter = new DecimalFormat("#0.00");

    public ProductsPresenter(
        ProductsInteractor productsInteractor,
        CartInteractor cartInteractor,
        Context context) {

        this.productsInteractor = productsInteractor;
        this.cartInteractor = cartInteractor;
        this.context = context;
    }

    public void attach(ProductsView productsView) {
      this.productsView = productsView;
    }

    public void loadProducts() {
        unsubscribe();
        subscription = productsInteractor
                .loadProducts()
                .flatMap(new Func1<List<Product>, Observable<List<ProductViewData>>>() {
                    @Override
                    public Observable<List<ProductViewData>> call(List<Product> products) {
                        return Observable
                                .from(products)
                                .map(new Func1<Product, ProductViewData>() {
                                    @Override
                                    public ProductViewData call(Product product) {
                                        return new ProductViewData(product, formatter);
                                    }
                                })
                                .toList();
                    }
                })
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        productsView.showLoading();
                    }
                })
                .subscribe(new Subscriber<List<ProductViewData>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "loadProducts.onCompleted");
                        productsView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "loadProducts.onError", error);
                        productsView.hideLoading();
                        productsView.showErrorMessage(context.getString(R.string.generic_error_message));
                    }

                    @Override
                    public void onNext(List<ProductViewData> products) {
                        Log.d(TAG, "loadProducts.onNext");
                        productsView.showProducts(products);
                    }
                });
    }

    public void detach() {
      productsView = null;
      unsubscribe();
    }

    private void unsubscribe() {
        if (subscription == null) {
            return;
        }
        subscription.unsubscribe();
        subscription = null;
    }

    public void clicked(ProductViewData product) {
    cartInteractor.addProduct(product.getProduct());
  }
}
