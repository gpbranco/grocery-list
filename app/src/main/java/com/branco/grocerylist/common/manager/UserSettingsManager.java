package com.branco.grocerylist.common.manager;

import com.branco.grocerylist.common.model.ExchangeRate;
import com.branco.grocerylist.common.repository.UserSettingsRepository;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class UserSettingsManager {

  private UserSettingsRepository userSettingsRepository;
  private BehaviorSubject<ExchangeRate> subject;

  public UserSettingsManager(UserSettingsRepository userSettingsRepository) {
    this.userSettingsRepository = userSettingsRepository;
    this.subject = BehaviorSubject.create(this.userSettingsRepository.getCurrentExchangeRate());
  }

  public void updateCurrentExchangeRate(ExchangeRate exchangeRate) {
    userSettingsRepository.setCurrentExchangeRate(exchangeRate);
    subject.onNext(exchangeRate);
  }

  public Observable<ExchangeRate> exchangeRateObservable() {
    return subject.asObservable();
  }
}
