package com.branco.grocerylist.cart.presenter;

import com.branco.grocerylist.cart.interactor.CartInteractor;
import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.cart.model.ProductCounter;
import com.branco.grocerylist.cart.ui.CartView;
import com.branco.grocerylist.cart.ui.model.CartViewData;
import com.branco.grocerylist.common.repository.UserSettingsRepository;
import com.google.gson.Gson;

import android.os.Bundle;
import android.util.Log;

import rx.Subscriber;
import rx.Subscription;
import rx.functions.Func1;
import rx.subscriptions.CompositeSubscription;

import java.text.DecimalFormat;

public class CartPresenter {

    public static final String CART_STATE_KEY = "com.branco.grocerylist.cart.presenter.cart_state_key";
    private static final String TAG = CartPresenter.class.getSimpleName();

    private UserSettingsRepository userSettingsRepository;
    private CartInteractor cartInteractor;
    private CartView cartView;
    private DecimalFormat formatter;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    public CartPresenter(CartInteractor cartInteractor, UserSettingsRepository userSettingsRepository) {
        this.cartInteractor = cartInteractor;
        this.userSettingsRepository = userSettingsRepository;
        formatter = new DecimalFormat("#0.00");
    }

    public void attach(CartView cartView) {
        this.cartView = cartView;
        subscriptions = new CompositeSubscription();
        subscriptions.add(subscribeToCartUpdates());
        cartInteractor.reload();
    }

    public void detach() {
        cartView = null;
        if (subscriptions == null) {
            return;
        }
        subscriptions.unsubscribe();
        subscriptions = null;
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
        CartViewData cartViewData = new CartViewData(cart, formatter, userSettingsRepository.getCurrentExchangeRate().getCurrency());
        cartView.showProducts(cartViewData.getSelectedProducts());
        cartView.showCartTotal(cartViewData);
    }

    public void storeState(Bundle outState) {
        String json = new Gson().toJson(cartInteractor.getCurrentState());
        outState.putString(CART_STATE_KEY, json);
    }

    private Subscription subscribeToCartUpdates() {
        return this.cartInteractor
                .cartUpdatedObservable()
                .map(new Func1<Cart, CartViewData>() {
                    @Override
                    public CartViewData call(Cart cart) {
                        return new CartViewData(cart, formatter, userSettingsRepository.getCurrentExchangeRate().getCurrency());
                    }
                })
                .subscribe(new Subscriber<CartViewData>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "cartUpdatedObservable.onCompleted");
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "cartUpdatedObservable.onError", error);
                    }

                    @Override
                    public void onNext(CartViewData cart) {
                        Log.d(TAG, "cartUpdatedObservable.onNext");
                        cartView.showProducts(cart.getSelectedProducts());
                        cartView.showCartTotal(cart);
                    }
                });
    }
}
