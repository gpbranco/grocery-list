package com.branco.grocerylist.common.model;

import java.math.BigDecimal;

/**
 * Created by guilhermebranco on 5/4/17.
 */

public class ExchangeRate {

    private String currency;
    private BigDecimal rate;

    public ExchangeRate(String currency, BigDecimal rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
