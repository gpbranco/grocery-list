package com.branco.grocerylist.cart.interactor;

import com.branco.grocerylist.cart.manager.Calculator;
import com.branco.grocerylist.cart.manager.CartManager;
import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.cart.model.ProductCounter;
import com.branco.grocerylist.common.model.ExchangeRate;
import com.branco.grocerylist.common.model.Product;
import com.branco.grocerylist.common.repository.UserSettingsRepository;

import java.math.BigDecimal;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class CartInteractor {

    private final CartManager cartManager;
    private UserSettingsRepository userSettingsRepository;
    private Calculator calculator;
    private BehaviorSubject<Cart> subject = BehaviorSubject.create();
    private Scheduler subscribeOn;
    private Scheduler observeOn;
    private Cart currentState;

    public CartInteractor(CartManager cartManager, UserSettingsRepository userSettingsRepository, Calculator calculator, Scheduler subscribeOn, Scheduler observeOn) {
        this.cartManager = cartManager;
        this.userSettingsRepository = userSettingsRepository;
        this.calculator = calculator;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public Observable<Cart> cartUpdatedObservable() {
        return subject
                .asObservable()
                .flatMap(new Func1<Cart, Observable<Cart>>() {
                    @Override
                    public Observable<Cart> call(final Cart cart) {
                        return Observable
                                .from(cart.getProductCounterList())
                                .map(new Func1<ProductCounter, ProductCounter>() {
                                    @Override
                                    public ProductCounter call(ProductCounter productCounter) {
                                        return productCounter;
                                    }
                                })
                                .map(new Func1<ProductCounter, ProductCounter>() {
                                    @Override
                                    public ProductCounter call(ProductCounter productCounter) {
                                        BigDecimal exchanged = calculator.exchange(productCounter.getTotal(), userSettingsRepository.getCurrentExchangeRate().getRate());
                                        return productCounter.convertedTotal(exchanged);
                                    }
                                })
                                .toList()
                                .map(new Func1<List<ProductCounter>, Cart>() {
                                    @Override
                                    public Cart call(List<ProductCounter> productCounters) {
                                        BigDecimal exchanged = calculator.exchange(cart.getTotal(), userSettingsRepository.getCurrentExchangeRate().getRate());
                                        return new Cart(productCounters, exchanged);
                                    }
                                });
                    }
                })
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
    }

    public void addProduct(Product product) {
        if (product == null) {
            return;
        }
        cartManager.addProduct(product);
        currentState = new Cart(cartManager.getProducts(), cartManager.getTotal());
        subject.onNext(currentState);
    }

    public void removeProduct(int productId) {
        cartManager.removeProduct(productId);
        currentState = new Cart(cartManager.getProducts(), cartManager.getTotal());
        subject.onNext(currentState);
    }

    public Cart getCurrentState() {
        return currentState;
    }

    public void setCurrentState(Cart currentState) {
        this.currentState = currentState;
        this.cartManager.reset(currentState.getProductCounterList());
    }

    public void reload() {
        if (currentState == null){
            return;
        }
        subject.onNext(currentState);
    }
}
