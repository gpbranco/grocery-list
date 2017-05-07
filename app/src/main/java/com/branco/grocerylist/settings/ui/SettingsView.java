package com.branco.grocerylist.settings.ui;

import com.branco.grocerylist.common.model.ExchangeRate;

import java.util.List;

/**
 * Created by guilhermebranco on 5/6/17.
 */

public interface SettingsView {

    void showAvailableCurrencies(List<String> exchangeRates);

    void showError(String errorMessage);

    void showCurrentCurrency(ExchangeRate currentExchangeRate);
}
