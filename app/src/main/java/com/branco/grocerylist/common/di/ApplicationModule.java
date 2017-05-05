package com.branco.grocerylist.common.di;

import com.branco.grocerylist.cart.manager.Calculator;
import com.branco.grocerylist.common.manager.UserSettingsManager;
import com.branco.grocerylist.common.repository.UserSettingsRepository;
import com.branco.grocerylist.common.repository.UserSettingsRepositoryInMemory;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class ApplicationModule {

  private Context app;

  public ApplicationModule(Context context) {
    this.app = context;
  }

  @Singleton
  @Provides
  public Context provideContext() {
    return app;
  }

  @Singleton
  @Provides
  public Calculator provideCalculator() {
    return new Calculator();
  }

  @Singleton
  @Provides
  public UserSettingsRepository provideUserSettingsRepository() {
    return new UserSettingsRepositoryInMemory();
  }

  @Singleton
  @Provides
  public UserSettingsManager provideUserSettingsManager(UserSettingsRepository
      userSettingsRepository) {
    return new UserSettingsManager(userSettingsRepository);
  }

  @Singleton
  @Provides
  @Named("default.subscribe.scheduler")
  public Scheduler provideSubscribeOnScheduler() {
    return Schedulers.io();
  }

  @Singleton
  @Provides
  @Named("default.observe.scheduler")
  public Scheduler provideObserverOnScheduler() {
    return AndroidSchedulers.mainThread();
  }
}
