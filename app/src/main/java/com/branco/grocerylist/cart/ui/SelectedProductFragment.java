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
import com.branco.grocerylist.cart.model.Cart;
import com.branco.grocerylist.cart.model.ProductCounter;
import com.branco.grocerylist.cart.presenter.CartPresenter;

import java.util.List;

public class SelectedProductFragment extends Fragment implements
    SelectedProductRecyclerViewAdapter.OnItemClickListener, CartView {

  private CartPresenter cartPresenter;
  private RecyclerView list;
  private SelectedProductRecyclerViewAdapter adapter;

  public static SelectedProductFragment newInstance(CartPresenter cartPresenter) {
    SelectedProductFragment fragment = new SelectedProductFragment();
    fragment.cartPresenter = cartPresenter;
    return fragment;
  }

  @Override
  public void onClick(ProductCounter product) {
    cartPresenter.clicked(product);
  }

  @Override
  public void showProducts(List<ProductCounter> selectedProducts) {
    adapter.setSelectedProducts(selectedProducts);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void showCartTotal(Cart cart) {

  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
    cartPresenter.attach(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    cartPresenter.detach();
  }
}
