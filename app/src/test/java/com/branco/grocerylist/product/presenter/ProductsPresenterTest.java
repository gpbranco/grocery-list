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

import android.content.Context;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class ProductsPresenterTest {

    @Mock
    ProductsInteractor productsInteractor;

    @Mock
    ProductsView producsView;

    @Mock
    Context context;

    ProductsPresenter productsPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        productsPresenter = new ProductsPresenter(producsView, productsInteractor, context);
    }

    @Test
    public void shouldNotifyViewAfterLoading() {
        List<Product> products = new ArrayList<>();
        given(productsInteractor.loadProducts()).willReturn(Observable.just(products));

        productsPresenter.loadProducts();

        verify(producsView).showLoading();
        verify(producsView).hideLoading();
        verify(producsView).showProducts(products);
    }

    @Test
    public void shouldShowErrorWhenRequestFails() {
        given(context.getString(anyInt())).willReturn("Error!");
        given(productsInteractor.loadProducts()).willReturn(Observable.<List<Product>>error(new Exception()));

        productsPresenter.loadProducts();

        verify(producsView).hideLoading();
        verify(producsView).showErrorMessage(anyString());
    }
}
