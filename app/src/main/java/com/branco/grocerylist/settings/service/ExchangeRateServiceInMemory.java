package com.branco.grocerylist.settings.service;

import com.branco.grocerylist.common.model.ExchangeRate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import rx.Single;

/**
 * Created by guilhermebranco on 5/6/17.
 */

public class ExchangeRateServiceInMemory implements ExchangeRateService {

    @Override
    public Single<List<ExchangeRate>> requestExchangeRates() {
        return Single.just(rates());
    }

    private List<ExchangeRate> rates() {
        List<ExchangeRate> rates = new ArrayList<>();
        rates.add(new ExchangeRate("EUR", new BigDecimal(1)));
        rates.add(new ExchangeRate("AUD", new BigDecimal(1.4832)));
        rates.add(new ExchangeRate("BRL", new BigDecimal(3.4917)));
        rates.add(new ExchangeRate("CAD", new BigDecimal(1.5094)));
        rates.add(new ExchangeRate("USD", new BigDecimal(1.0961)));
        return rates;
    }
}
