package com.branco.grocerylist.cart.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.branco.grocerylist.R;
import com.branco.grocerylist.cart.presenter.CartPresenter;
import com.branco.grocerylist.cart.ui.model.CartViewData;
import com.branco.grocerylist.cart.ui.model.SelectedProductViewData;

import java.util.List;

public class SelectedProductFragment extends Fragment implements
    SelectedProductRecyclerViewAdapter.OnItemClickListener, CartView {

  private CartPresenter cartPresenter;
  private RecyclerView list;
  private SelectedProductRecyclerViewAdapter adapter;
  private String savedCartState;
  private CartPresenterProvider presenterProvider;

  public static SelectedProductFragment newInstance(CartPresenter cartPresenter) {
    SelectedProductFragment fragment = new SelectedProductFragment();
    return fragment;
  }

  @Override
  public void onClick(SelectedProductViewData product) {
    cartPresenter.clicked(product.getProduct());
  }

  @Override
  public void showProducts(List<SelectedProductViewData> selectedProducts) {
    adapter.setSelectedProducts(selectedProducts);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void showCartTotal(CartViewData cart) {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (savedInstanceState == null) {
      return;
    }
    savedCartState = savedInstanceState.getString(CartPresenter.CART_STATE_KEY);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof CartPresenterProvider) {
      presenterProvider = ((CartPresenterProvider) context);
    } else {
      throw new RuntimeException(context.toString()
          + " must implement CartPresenterProvider");
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_selectedproduct_list, container, false);

    Context context = view.getContext();
    list = (RecyclerView) view.findViewById(R.id.list);
    list.setLayoutManager(new LinearLayoutManager(context));
    adapter = new SelectedProductRecyclerViewAdapter(this);
    list.setAdapter(adapter);

    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    cartPresenter = presenterProvider.getCartPresenter();
    cartPresenter.attach(this);
    cartPresenter.restoreState(savedCartState);
  }

  @Override
  public void onStop() {
    super.onStop();
    cartPresenter.detach();
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    cartPresenter.storeState(outState);
  }

  public interface CartPresenterProvider {
    CartPresenter getCartPresenter();
  }
}
