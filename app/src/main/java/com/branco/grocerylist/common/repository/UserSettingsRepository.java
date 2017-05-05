package com.branco.grocerylist.common.repository;

import com.branco.grocerylist.common.model.ExchangeRate;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public interface UserSettingsRepository {

    void setCurrentExchangeRate(ExchangeRate exchangeRate);
    ExchangeRate getCurrentExchangeRate();
}
