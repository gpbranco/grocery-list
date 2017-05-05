package com.branco.grocerylist.common.manager;

import static org.mockito.BDDMockito.*;

import com.branco.grocerylist.common.model.ExchangeRate;
import com.branco.grocerylist.common.repository.UserSettingsRepository;

import rx.observers.TestSubscriber;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class UserSettingsManagerTest {

  @Mock
  private UserSettingsRepository userSettingsRepository;

  private UserSettingsManager userSettingsManager;

  private ExchangeRate exchangeRateOne;

  private ExchangeRate exchangeRateTwo;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    exchangeRateOne = new ExchangeRate("TEST_1", BigDecimal.ONE);
    exchangeRateTwo = new ExchangeRate("TEST_2", BigDecimal.ONE);
    given(userSettingsRepository.getCurrentExchangeRate()).willReturn(exchangeRateOne);
    userSettingsManager = new UserSettingsManager(userSettingsRepository);
  }

  @Test
  public void shouldNotifyAboutUserExchangeRateChanges() {
    TestSubscriber<ExchangeRate> testSubscriber = new TestSubscriber<>();
    userSettingsManager
        .exchangeRateObservable()
        .subscribe(testSubscriber);

    userSettingsManager.updateCurrentExchangeRate(exchangeRateTwo);

    testSubscriber.assertValues(exchangeRateOne, exchangeRateTwo);
  }
}
