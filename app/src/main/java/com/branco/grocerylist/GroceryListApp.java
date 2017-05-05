package com.branco.grocerylist;

import com.branco.grocerylist.common.di.ApplicationComponent;
import com.branco.grocerylist.common.di.ApplicationModule;
import com.branco.grocerylist.common.di.DaggerApplicationComponent;

import android.app.Application;

public class GroceryListApp extends Application {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    applicationComponent = DaggerApplicationComponent
        .builder()
        .applicationModule(new ApplicationModule(this))
        .build();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
