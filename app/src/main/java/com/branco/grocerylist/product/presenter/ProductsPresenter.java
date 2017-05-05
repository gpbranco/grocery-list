package com.branco.grocerylist.product.presenter;

import android.content.Context;
import android.util.Log;

import com.branco.grocerylist.R;
import com.branco.grocerylist.cart.interactor.CartInteractor;
import com.branco.grocerylist.common.model.Product;
import com.branco.grocerylist.product.interactor.ProductsInteractor;
import com.branco.grocerylist.product.ui.ProductsView;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class ProductsPresenter {

    private static final String TAG = ProductsPresenter.class.getSimpleName();

    private ProductsView productsView;
    private ProductsInteractor productsInteractor;
    private CartInteractor cartInteractor;
    private CompositeSubscription subscriptions = new CompositeSubscription();
    private Context context;

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
        subscriptions.add(productsInteractor
                .loadProducts()
                .doOnSubscribe(new Action0() {
                  @Override
                  public void call() {
                    productsView.showLoading();
                  }
                })
                .subscribe(new Subscriber<List<Product>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "loadProducts.onCompleted");
                        productsView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "loadProducts.onError", error);
                        productsView.hideLoading();
                        productsView.showErrorMessage(context.getString(R.string.load_products_error_message));
                    }

                    @Override
                    public void onNext(List<Product> products) {
                        Log.d(TAG, "loadProducts.onNext");
                        productsView.showProducts(products);
                    }
                }));
    }



    public void detach() {
      productsView = null;
      if (subscriptions == null || subscriptions.isUnsubscribed()) {
        return;
      }
      subscriptions.unsubscribe();
    }

  public void clicked(Product product) {
    cartInteractor.addProduct(product);
  }
}
