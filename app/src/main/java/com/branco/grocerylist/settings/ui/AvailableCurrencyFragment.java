package com.branco.grocerylist.settings.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.branco.grocerylist.R;
import com.branco.grocerylist.common.model.ExchangeRate;
import com.branco.grocerylist.settings.presenter.SettingsPresenter;

import java.util.List;

public class AvailableCurrencyFragment extends Fragment implements SettingsView{

    private SettingsPresenterProvider provider;
    private SettingsPresenter settingsPresenter;
    private TextView currency;
    private TextView error;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_availablecurrency, container, false);
        currency = (TextView) view.findViewById(R.id.currency);
        error = (TextView) view.findViewById(R.id.error);
        Button changeButton = (Button) view.findViewById(R.id.change);
        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                settingsPresenter.loadAvailableCurrencies();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SettingsPresenterProvider) {
            provider = (SettingsPresenterProvider) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SettingsPresenterProvider");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        settingsPresenter = provider.getSettingsPresenter();
        settingsPresenter.attach(this);
        settingsPresenter.init();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        provider = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        settingsPresenter.detach();
    }

    @Override
    public void showAvailableCurrencies(List<String> exchangeRates) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle(R.string.available_currencies_title)
                .setItems(exchangeRates.toArray(new CharSequence[exchangeRates.size()]), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        settingsPresenter.changeCurrentCurrency(position);
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showError(String errorMessage) {
        error.setVisibility(View.VISIBLE);
        currency.setVisibility(View.GONE);
    }

    @Override
    public void showCurrentCurrency(ExchangeRate currentExchangeRate) {
        error.setVisibility(View.GONE);
        currency.setVisibility(View.VISIBLE);
        currency.setText(currentExchangeRate.getCurrency());
    }

    public interface SettingsPresenterProvider {
        SettingsPresenter getSettingsPresenter();
    }
}
