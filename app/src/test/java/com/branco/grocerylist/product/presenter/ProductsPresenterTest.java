package com.branco.grocerylist.product.presenter;

import com.branco.grocerylist.common.model.Product;
import com.branco.grocerylist.product.interactor.ProductsInteractor;
import com.branco.grocerylist.product.ui.ProductsView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.mockito.BDDMockito.*;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class ProductsPresenterTest {

    @Mock
    ProductsInteractor productsInteractor;

    @Mock
    ProductsView producsView;

    ProductsPresenter productsPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productsPresenter = new ProductsPresenter(producsView, productsInteractor);
    }

    @Test
    public void shouldNotifyViewAfterLoading() {
        List<Product> products = new ArrayList<>();
        given(productsInteractor.loadProducts()).willReturn(Observable.just(products));

        productsPresenter.loadProducts();

        verify(producsView).hideLoading();
        verify(producsView).showProducts(products);
    }
}
