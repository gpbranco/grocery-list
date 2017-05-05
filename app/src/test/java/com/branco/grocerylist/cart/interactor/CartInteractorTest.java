package com.branco.grocerylist.cart.interactor;

import com.branco.grocerylist.cart.manager.Calculator;
import com.branco.grocerylist.cart.manager.CartManager;
import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.common.model.ExchangeRate;
import com.branco.grocerylist.common.model.Product;
import com.branco.grocerylist.common.repository.UserSettingsRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import rx.observers.TestSubscriber;
import rx.schedulers.Schedulers;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class CartInteractorTest {

    @Mock
    private UserSettingsRepository userSettingsRepository;

    private CartInteractor cartInteractor;
    private TestSubscriber<Cart> testSubscriber;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        BDDMockito.given(userSettingsRepository.getCurrentExchangeRate()).willReturn(new ExchangeRate("", BigDecimal.ZERO));
        cartInteractor = new CartInteractor(
            new CartManager(),
            userSettingsRepository,
            new Calculator(),
            Schedulers.immediate(),
            Schedulers.immediate());
        testSubscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldNotifyObserversWhenProductAdded() {
        Product one = new Product(1, "", BigDecimal.ONE);
        Product two = new Product(2, "", BigDecimal.ONE);
        cartInteractor
                .cartUpdatedObservable()
                .subscribe(testSubscriber);

        cartInteractor.addProduct(one);
        cartInteractor.addProduct(two);

        testSubscriber.assertValueCount(2);
    }

    @Test
    public void shouldNotifyObserversWhenProductRemoved() {
        Product product = new Product(1, "", BigDecimal.ONE);
        cartInteractor
                .cartUpdatedObservable()
                .subscribe(testSubscriber);

        cartInteractor.addProduct(product);
        cartInteractor.removeProduct(product.getId());

        testSubscriber.assertValueCount(2);
    }
}
