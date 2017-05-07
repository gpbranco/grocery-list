package com.branco.grocerylist.settings.di;

import android.content.Context;

import com.branco.grocerylist.common.di.ActivityScope;
import com.branco.grocerylist.common.manager.UserSettingsManager;
import com.branco.grocerylist.common.repository.UserSettingsRepository;
import com.branco.grocerylist.settings.presenter.SettingsPresenter;
import com.branco.grocerylist.settings.service.ExchangeRateService;
import com.branco.grocerylist.settings.service.ExchangeRateServiceInMemory;

import dagger.Module;
import dagger.Provides;

/**
 * Created by guilhermebranco on 5/6/17.
 */

@Module
public class SettingsModule {

    @ActivityScope
    @Provides
    public ExchangeRateService provideExchangeRateService() {
        return new ExchangeRateServiceInMemory();
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
