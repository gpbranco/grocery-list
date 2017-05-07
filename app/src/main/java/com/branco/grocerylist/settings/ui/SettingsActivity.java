package com.branco.grocerylist.settings.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.branco.grocerylist.GroceryListApp;
import com.branco.grocerylist.R;
import com.branco.grocerylist.cart.di.CartModule;
import com.branco.grocerylist.home.di.HomeComponent;
import com.branco.grocerylist.home.di.HomeModule;
import com.branco.grocerylist.product.di.ProductsModule;
import com.branco.grocerylist.settings.di.SettingsComponent;
import com.branco.grocerylist.settings.di.SettingsModule;
import com.branco.grocerylist.settings.presenter.SettingsPresenter;

import javax.inject.Inject;

public class SettingsActivity extends AppCompatActivity implements AvailableCurrencyFragment.SettingsPresenterProvider {

    @Inject
    SettingsPresenter settingsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        inject();
    }

    private void inject() {
        SettingsComponent component = ((GroceryListApp) getApplicationContext())
                .getApplicationComponent()
                .plus(new SettingsModule());
        component.inject(this);
    }

    @Override
    public SettingsPresenter getSettingsPresenter() {
        return settingsPresenter;
    }
}
