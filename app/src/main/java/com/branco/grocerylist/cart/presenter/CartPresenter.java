package com.branco.grocerylist.cart.presenter;

import com.branco.grocerylist.cart.interactor.CartInteractor;
import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.cart.model.ProductCounter;
import com.branco.grocerylist.cart.ui.CartView;
import com.google.gson.Gson;

import android.os.Bundle;
import android.util.Log;

import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class CartPresenter {

  private static final String TAG = CartPresenter.class.getSimpleName();

  private CartInteractor cartInteractor;
  private CartView cartView;
  private CompositeSubscription subscriptions = new CompositeSubscription();

  public CartPresenter(CartInteractor cartInteractor) {
    this.cartInteractor = cartInteractor;
  }

  private Subscription subscribeToCartUpdates() {
    return this.cartInteractor
        .cartUpdatedObservable()
        .subscribe(new Subscriber<Cart>() {
          @Override
          public void onCompleted() {
            Log.d(TAG, "cartUpdatedObservable.onCompleted");
          }

          @Override
          public void onError(Throwable error) {
            Log.e(TAG, "cartUpdatedObservable.onError", error);
          }

          @Override
          public void onNext(Cart cart) {
            Log.d(TAG, "cartUpdatedObservable.onNext");
            cartView.showProducts(cart.getProductCounterList());
            cartView.showCartTotal(cart);
          }
        });
  }

  public void attach(CartView cartView) {
    this.cartView = cartView;
    subscriptions.add(subscribeToCartUpdates());
  }

  public void detach() {
    cartView = null;
    if (subscriptions == null || subscriptions.isUnsubscribed()) {
      return;
    }
    subscriptions.unsubscribe();
  }

  public void clicked(ProductCounter toBeRemoved) {
      cartInteractor.removeProduct(toBeRemoved.getId());
  }

  public void restoreState(String json) {
    if (json == null || json.isEmpty()) {
      return;
    }
    Cart cart = new Gson().fromJson(json, Cart.class);
    if (cart == null) {
      return;
    }
    cartInteractor.setCurrentState(cart);
    cartView.showProducts(cart.getProductCounterList());
    cartView.showCartTotal(cart);
  }

  public void storeState(Bundle outState) {
    String json = new Gson().toJson(cartInteractor.getCurrentState());
    outState.putString("key", json);
  }
}
