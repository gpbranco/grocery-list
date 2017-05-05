package com.branco.grocerylist.product.presenter;

import android.content.Context;
import android.util.Log;

import com.branco.grocerylist.R;
import com.branco.grocerylist.common.model.Product;
import com.branco.grocerylist.product.interactor.ProductsInteractor;
import com.branco.grocerylist.product.ui.ProductsView;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class ProductsPresenter {

    private static final String TAG = ProductsPresenter.class.getSimpleName();

    private ProductsView producsView;
    private ProductsInteractor productsInteractor;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private Context context;

    public ProductsPresenter(ProductsView producsView, ProductsInteractor productsInteractor, Context context) {
        this.producsView = producsView;
        this.productsInteractor = productsInteractor;
        this.context = context;
    }

    public void loadProducts() {
        subscriptions.add(productsInteractor
                .loadProducts()
                .doOnSubscribe(new Action0() {
                  @Override
                  public void call() {
                    producsView.showLoading();
                  }
                })
                .subscribe(new Subscriber<List<Product>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "loadProducts.onCompleted");
                        producsView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "loadProducts.onError", error);
                        producsView.hideLoading();
                        producsView.showErrorMessage(context.getString(R.string.load_products_error_message));
                    }

                    @Override
                    public void onNext(List<Product> products) {
                        Log.d(TAG, "loadProducts.onNext");
                        producsView.showProducts(products);
                    }
                }));
    }

    public void detach() {
      if (subscriptions == null || subscriptions.isUnsubscribed()) {
        return;
      }
      subscriptions.unsubscribe();
    }
}
