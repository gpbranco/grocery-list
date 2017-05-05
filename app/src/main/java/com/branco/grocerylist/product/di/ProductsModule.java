package com.branco.grocerylist.product.di;

import com.branco.grocerylist.cart.manager.Calculator;
import com.branco.grocerylist.common.di.ActivityScope;
import com.branco.grocerylist.common.repository.UserSettingsRepository;
import com.branco.grocerylist.product.interactor.ProductsInteractor;
import com.branco.grocerylist.product.service.ProductService;
import com.branco.grocerylist.product.service.ProductServiceInMemory;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;

import javax.inject.Named;

@Module
public class ProductsModule {

  @ActivityScope
  @Provides
  public ProductService provideProductService() {
    return new ProductServiceInMemory();
  }

  @ActivityScope
  @Provides
  public ProductsInteractor ProductsInteractor(
      ProductService productService,
      UserSettingsRepository userSettingsRepository,
      Calculator calculator,
      @Named("default.subscribe.scheduler") Scheduler subscribeOn,
      @Named("default.observe.scheduler") Scheduler observeOn) {
    return new ProductsInteractor(productService, userSettingsRepository, calculator,
        subscribeOn, observeOn);
  }
}
