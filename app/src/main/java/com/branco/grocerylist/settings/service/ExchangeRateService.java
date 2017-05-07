package com.branco.grocerylist.settings.service;


import com.branco.grocerylist.common.model.ExchangeRate;

import java.util.List;

import rx.Single;

public interface ExchangeRateService {

  Single<List<ExchangeRate>> requestExchangeRates();
}
