package com.branco.grocerylist.home.di;

import com.branco.grocerylist.MainActivity;
import com.branco.grocerylist.cart.di.CartModule;
import com.branco.grocerylist.common.di.ActivityScope;
import com.branco.grocerylist.product.di.ProductsModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(
    modules = { CartModule.class, ProductsModule.class, HomeModule.class }
)
public interface HomeComponent {

  void inject(MainActivity mainActivity);
}
