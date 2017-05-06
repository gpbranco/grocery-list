package com.branco.grocerylist.checkout.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.branco.grocerylist.R;
import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.product.presenter.ProductsPresenter;

public class CheckoutFragment extends Fragment implements CheckoutRecyclerViewAdapter.OnItemClickListener {

  private CheckoutPresenterProvider provider;

  public CheckoutFragment() {
  }

  public static CheckoutFragment newInstance() {
    CheckoutFragment fragment = new CheckoutFragment();
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_checkout_list, container, false);

    if (view instanceof RecyclerView) {
      Context context = view.getContext();
      RecyclerView recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      recyclerView.setAdapter(new CheckoutRecyclerViewAdapter(this));
    }
    return view;
  }


  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof CheckoutPresenterProvider) {
      provider = (CheckoutPresenterProvider) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnListFragmentInteractionListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    provider = null;
  }

  @Override
  public void onClick(Cart item) {

  }

  public interface CheckoutPresenterProvider {
    ProductsPresenter getCheckoutPresenter();
  }
}
