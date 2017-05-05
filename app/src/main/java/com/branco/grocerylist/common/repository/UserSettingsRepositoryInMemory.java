package com.branco.grocerylist.common.repository;

import com.branco.grocerylist.common.model.ExchangeRate;

import java.math.BigDecimal;

public class UserSettingsRepositoryInMemory implements UserSettingsRepository {

  private static final ExchangeRate DEFAULT = new ExchangeRate("EUR", BigDecimal.ONE);
  private ExchangeRate currentExchangeRate;

  @Override
  public void setCurrentExchangeRate(ExchangeRate exchangeRate) {
    this.currentExchangeRate = exchangeRate;
  }

  @Override
  public ExchangeRate getCurrentExchangeRate() {
    return currentExchangeRate == null ? DEFAULT : currentExchangeRate;
  }
}
