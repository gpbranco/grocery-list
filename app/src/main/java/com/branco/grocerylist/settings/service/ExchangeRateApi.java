package com.branco.grocerylist.settings.service;

import android.provider.Telephony;
import android.util.Log;

import com.branco.grocerylist.common.model.ExchangeRate;
import com.branco.grocerylist.settings.api.RatesResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Single;

/**
 * Created by guilhermebranco on 5/6/17.
 */

public class ExchangeRateApi implements ExchangeRateService {

    private static final String TAG = ExchangeRateApi.class.getSimpleName();

    private Gson gson;
    private OkHttpClient client;
    private String url;

    public ExchangeRateApi(OkHttpClient okHttpClient, String url, Gson gson) {
        this.client = okHttpClient;
        this.url = url;
        this.gson = gson;
    }

    @Override
    public Single<List<ExchangeRate>> requestExchangeRates() {
        final Request request = new Request.Builder()
                .url(url)
                .build();

        return Single.defer(new Callable<Single<List<ExchangeRate>>>() {
            @Override
            public Single<List<ExchangeRate>> call() throws Exception {
                try {
                    Response response = client.newCall(request).execute();
                    String result = response.body().string();
                    Log.d(TAG, "requestExchangeRates response: " + result);
                    return Single.just(convertResponse(gson.fromJson(result, RatesResponse.class)));
                } catch (IOException error) {
                    Log.e(TAG, "Error during requestExchangeRates request: ", error);
                    return Single.error(error);
                }
            }
        });
    }

    private List<ExchangeRate> convertResponse(RatesResponse response) {
        List<ExchangeRate> rates = new ArrayList<>();
        for (String key : response.rates.keySet()) {
            ExchangeRate exchangeRate = new ExchangeRate(key, response.rates.get(key));
            rates.add(exchangeRate);
        }
        return rates;
    }
}
