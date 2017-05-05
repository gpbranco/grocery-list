package com.branco.grocerylist.common.di;

import com.branco.grocerylist.cart.di.CartModule;
import com.branco.grocerylist.home.di.HomeComponent;
import com.branco.grocerylist.home.di.HomeModule;
import com.branco.grocerylist.product.di.ProductsModule;

import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(
    modules = { ApplicationModule.class }
)
public interface ApplicationComponent {

  HomeComponent plus(CartModule cartModule, ProductsModule productsModule, HomeModule homeModule);
}
