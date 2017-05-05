package com.branco.grocerylist.product.ui;

import static android.view.View.*;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.branco.grocerylist.R;
import com.branco.grocerylist.common.model.Product;
import com.branco.grocerylist.product.presenter.ProductsPresenter;

import java.util.List;

/**
 * A fragment representing a list of {@link com.branco.grocerylist.common.model.Product}.
 */
public class ProductFragment extends Fragment
    implements ProductRecyclerViewAdapter.OnItemClickListener, ProductsView {

  private ProductsPresenter presenter;

  private ProductRecyclerViewAdapter adapter;
  private RecyclerView list;
  private TextView error;
  private ProgressBar progressBar;

  public static ProductFragment newInstance(ProductsPresenter productsPresenter) {
    ProductFragment fragment = new ProductFragment();
    fragment.presenter = productsPresenter;
    return fragment;
  }

  @Override
  public void showLoading() {
    progressBar.setVisibility(VISIBLE);
    error.setVisibility(GONE);
    list.setVisibility(GONE);
  }

  @Override
  public void hideLoading() {
    progressBar.setVisibility(GONE);
    error.setVisibility(GONE);
    list.setVisibility(VISIBLE);
  }

  @Override
  public void showProducts(List<Product> products) {
    adapter.setProducts(products);
    adapter.notifyDataSetChanged();
  }

  @Override
  public void showErrorMessage(String errorMessage) {
    progressBar.setVisibility(GONE);
    error.setVisibility(VISIBLE);
    list.setVisibility(GONE);
    error.setText(errorMessage);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_product_list, container, false);
    list = (RecyclerView)view.findViewById(R.id.list);
    progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
    error = (TextView)view.findViewById(R.id.errorTxt);
    adapter = new ProductRecyclerViewAdapter(this);
    Context context = list.getContext();
    list.setLayoutManager(new LinearLayoutManager(context));
    list.setAdapter(adapter);
    return view;
  }

  @Override
  public void onResume() {
    super.onResume();
    presenter.attach(this);
    presenter.loadProducts();
  }

  @Override
  public void onStop() {
    super.onStop();
    presenter.detach();
  }

  @Override
  public void onClick(Product product) {
    Toast.makeText(getContext(), "clicked: " + product.getName(), Toast.LENGTH_SHORT).show();
    presenter.clicked(product);
  }
}
