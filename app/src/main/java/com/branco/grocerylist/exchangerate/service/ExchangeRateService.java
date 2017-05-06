package com.branco.grocerylist.exchangerate.service;

import com.branco.grocerylist.common.model.ExchangeRate;

import rx.Observable;

public interface ExchangeRateService {

  Observable<ExchangeRate> requestExchageRates();
}
