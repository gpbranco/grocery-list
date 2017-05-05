package com.branco.grocerylist.cart.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.branco.grocerylist.R;
import com.branco.grocerylist.cart.model.ProductCounter;

import java.util.ArrayList;
import java.util.List;

public class SelectedProductRecyclerViewAdapter extends RecyclerView.Adapter<SelectedProductRecyclerViewAdapter.ViewHolder> {

  private List<ProductCounter> selectedProducts = new ArrayList<>();
  private final OnItemClickListener listener;

  public SelectedProductRecyclerViewAdapter(OnItemClickListener listener) {
    this.listener = listener;
  }

  public void setSelectedProducts(List<ProductCounter> selectedProducts) {
    this.selectedProducts.clear();
    this.selectedProducts.addAll(selectedProducts);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_selectedproduct, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.item = selectedProducts.get(position);
    holder.name.setText(selectedProducts.get(position).getName());
    holder.total.setText(selectedProducts.get(position).getTotal().toString());

    holder.view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (null != listener) {
          listener.onClick(holder.item);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return selectedProducts.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public final TextView name;
    public final TextView total;
    public ProductCounter item;

    public ViewHolder(View view) {
      super(view);
      this.view = view;
      name = (TextView) view.findViewById(R.id.name);
      total = (TextView) view.findViewById(R.id.total);
    }

    @Override
    public String toString() {
      return super.toString() + " '" + total.getText() + "'";
    }
  }

  public interface OnItemClickListener {
    void onClick(ProductCounter product);
  }
}
