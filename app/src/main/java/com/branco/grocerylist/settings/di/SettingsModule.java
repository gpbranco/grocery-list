package com.branco.grocerylist.settings.di;

import android.content.Context;

import com.branco.grocerylist.R;
import com.branco.grocerylist.common.di.ActivityScope;
import com.branco.grocerylist.common.manager.UserSettingsManager;
import com.branco.grocerylist.common.repository.UserSettingsRepository;
import com.branco.grocerylist.settings.presenter.SettingsPresenter;
import com.branco.grocerylist.settings.service.ExchangeRateApi;
import com.branco.grocerylist.settings.service.ExchangeRateService;
import com.branco.grocerylist.settings.service.ExchangeRateServiceInMemory;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

/**
 * Created by guilhermebranco on 5/6/17.
 */

@Module
public class SettingsModule {

    @ActivityScope
    @Provides
    public OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @ActivityScope
    @Provides
    public ExchangeRateService provideExchangeRateService(OkHttpClient client, Context context) {
        return new ExchangeRateApi(client, context.getString(R.string.rates_url), new Gson());
    }

    @ActivityScope
    @Provides
    public SettingsPresenter provideSettingsPresenter(
            UserSettingsManager userSettingsManager,
            ExchangeRateService exchangeRateService,
            UserSettingsRepository userSettingsRepository,
            Context context) {
        return new SettingsPresenter(userSettingsManager, exchangeRateService, userSettingsRepository, context);
    }
}
