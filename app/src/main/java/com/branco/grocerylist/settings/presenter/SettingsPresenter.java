package com.branco.grocerylist.settings.presenter;

import android.content.Context;
import android.util.Log;

import com.branco.grocerylist.R;
import com.branco.grocerylist.common.manager.UserSettingsManager;
import com.branco.grocerylist.common.model.ExchangeRate;
import com.branco.grocerylist.common.repository.UserSettingsRepository;
import com.branco.grocerylist.settings.service.ExchangeRateService;
import com.branco.grocerylist.settings.ui.SettingsView;

import java.util.List;

import rx.Observable;
import rx.Single;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by guilhermebranco on 5/6/17.
 */

public class SettingsPresenter {

    private static final String TAG = SettingsPresenter.class.getSimpleName();

    private final ExchangeRateService exchangeRateService;
    private final Context context;
    private SettingsView settingsView;
    private UserSettingsManager userSettingsManager;
    private UserSettingsRepository userSettingsRepository;
    private List<ExchangeRate> availableCurrencies;
    private CompositeSubscription subscriptions = new CompositeSubscription();

    public SettingsPresenter(
            UserSettingsManager userSettingsManager,
            ExchangeRateService exchangeRateService,
            UserSettingsRepository userSettingsRepository,
            Context context) {
        this.userSettingsManager = userSettingsManager;
        this.exchangeRateService = exchangeRateService;
        this.userSettingsRepository = userSettingsRepository;
        this.context = context;
    }

    public void init() {
        settingsView.showCurrentCurrency(userSettingsRepository.getCurrentExchangeRate());
    }

    public void loadAvailableCurrencies() {
        exchangeRateService
                .requestExchangeRates()
                .doOnSuccess(new Action1<List<ExchangeRate>>() {
                    @Override
                    public void call(List<ExchangeRate> exchangeRates) {
                        availableCurrencies = exchangeRates;
                    }
                })
                .flatMap(new Func1<List<ExchangeRate>, Single<List<String>>>() {
                    @Override
                    public Single<List<String>> call(List<ExchangeRate> exchangeRates) {
                        return Observable.from(exchangeRates)
                                .map(new Func1<ExchangeRate, String>() {
                                    @Override
                                    public String call(ExchangeRate exchangeRate) {
                                        return exchangeRate.getCurrency();
                                    }
                                })
                                .toList()
                                .toSingle();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<String>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable error) {
                        Log.e(TAG, "onError", error);
                        settingsView.showError(context.getString(R.string.generic_error_message));
                    }

                    @Override
                    public void onNext(List<String> exchangeRates) {
                        Log.d(TAG, "onNext");
                        settingsView.showAvailableCurrencies(exchangeRates);
                    }
                });
    }

    public void changeCurrentCurrency(int position) {
        ExchangeRate selected = availableCurrencies.get(position);
        userSettingsManager.updateCurrentExchangeRate(selected);
        settingsView.showCurrentCurrency(selected);
    }

    public void attach(SettingsView settingsView) {
        this.settingsView = settingsView;
    }

    public void detach() {
        this.settingsView = null;
        if (subscriptions == null || subscriptions.isUnsubscribed()) {
            return;
        }
        subscriptions.unsubscribe();
    }
}
