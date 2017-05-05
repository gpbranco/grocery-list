package com.branco.grocerylist.cart.interactor;

import com.branco.grocerylist.cart.manager.Calculator;
import com.branco.grocerylist.cart.manager.CartManager;
import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.cart.model.ProductCounter;
import com.branco.grocerylist.common.model.Product;
import com.branco.grocerylist.common.repository.UserSettingsRepository;

import java.math.BigDecimal;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class CartInteractor {

    private final CartManager cartManager;
    private UserSettingsRepository userSettingsRepository;
    private Calculator calculator;
    private PublishSubject<Cart> subject = PublishSubject.create();
    private Scheduler subscribeOn;
    private Scheduler observeOn;

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
        subject.onNext(new Cart(cartManager.getProducts(), cartManager.getTotal()));
    }

    public void removeProduct(Product product) {
        if (product == null) {
            return;
        }
        cartManager.removeProduct(product.getId());
        subject.onNext(new Cart(cartManager.getProducts(), cartManager.getTotal()));
    }
}
