package com.branco.grocerylist.cart.di;

import com.branco.grocerylist.cart.interactor.CartInteractor;
import com.branco.grocerylist.cart.manager.Calculator;
import com.branco.grocerylist.cart.manager.CartManager;
import com.branco.grocerylist.common.di.ActivityScope;
import com.branco.grocerylist.common.repository.UserSettingsRepository;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

import javax.inject.Named;

@Module
public class CartModule {

  @ActivityScope
  @Provides
  public CartManager provideCartManager() {
    return new CartManager();
  }

  @ActivityScope
  @Provides
  public CartInteractor provideCartInteractor(
      CartManager cartManager,
      UserSettingsRepository userSettingsRepository,
      Calculator calculator,
      @Named("default.subscribe.scheduler") Scheduler subscribeOn,
      @Named("default.observe.scheduler") Scheduler observeOn) {
    return new CartInteractor(
        cartManager,
        userSettingsRepository,
        calculator,
        subscribeOn,
        observeOn);
  }
}
