package com.branco.grocerylist.home.di;

import com.branco.grocerylist.cart.interactor.CartInteractor;
import com.branco.grocerylist.cart.presenter.CartPresenter;
import com.branco.grocerylist.common.di.ActivityScope;
import com.branco.grocerylist.product.interactor.ProductsInteractor;
import com.branco.grocerylist.product.presenter.ProductsPresenter;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

  @ActivityScope
  @Provides
  public CartPresenter provideCartPresenter(CartInteractor cartInteractor) {
    return new CartPresenter(cartInteractor);
  }

  @ActivityScope
  @Provides
  public ProductsPresenter provideProductsPresenter(
      ProductsInteractor productsInteractor,
      CartInteractor cartInteractor,
      Context context) {
    return new ProductsPresenter(productsInteractor, cartInteractor, context);
  }
}
