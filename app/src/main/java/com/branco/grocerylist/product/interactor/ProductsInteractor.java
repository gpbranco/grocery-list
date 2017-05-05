package com.branco.grocerylist.product.interactor;

import android.support.annotation.NonNull;

import com.branco.grocerylist.cart.manager.Calculator;
import com.branco.grocerylist.common.model.Product;
import com.branco.grocerylist.common.repository.UserSettingsRepository;
import com.branco.grocerylist.product.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Func1;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class ProductsInteractor {

    private ProductService productService;
    private UserSettingsRepository userSettingsRepository;
    private Calculator calculator;
    private Scheduler subscribeOn;
    private Scheduler observeOn;

    public ProductsInteractor(ProductService productService, UserSettingsRepository userSettingsRepository, Calculator calculator, Scheduler subscribeOn, Scheduler observeOn) {
        this.productService = productService;
        this.userSettingsRepository = userSettingsRepository;
        this.calculator = calculator;
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public Observable<List<Product>> loadProducts() {
        return productService
                .requestProducts()
                .flatMap(new Func1<List<Product>, Observable<List<Product>>>() {
                    @Override
                    public Observable<List<Product>> call(List<Product> products) {
                        return Observable
                                .from(products)
                                .map(calculatePriceBasedOnRate())
                                .toList();
                    }
                })
                .subscribeOn(subscribeOn)
                .observeOn(observeOn);
    }

    @NonNull
    private Func1<Product, Product> calculatePriceBasedOnRate() {
        return new Func1<Product, Product>() {
            @Override
            public Product call(Product product) {
                BigDecimal rate = userSettingsRepository.getCurrentExchangeRate().getRate();
                BigDecimal exchanged = calculator.exchange(product.getPrice(), rate);
                return new Product(product.getId(), product.getName(), exchanged);
            }
        };
    }
}
