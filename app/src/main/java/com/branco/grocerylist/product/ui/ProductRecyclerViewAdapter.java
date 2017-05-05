package com.branco.grocerylist.product.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.branco.grocerylist.R;
import com.branco.grocerylist.common.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder> {

  private final List<Product> products = new ArrayList<>();
  private final OnItemClickListener listener;

  public ProductRecyclerViewAdapter(OnItemClickListener listener) {
    this.listener = listener;
  }

  public void setProducts(List<Product> products) {
    this.products.clear();
    this.products.addAll(products);
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_product, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.item = products.get(position);
    holder.name.setText(products.get(position).getName());
    holder.price.setText(products.get(position).getPrice().toString());

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
    return products.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View view;
    public final TextView name;
    public final TextView price;
    public Product item;

    public ViewHolder(View view) {
      super(view);
      this.view = view;
      name = (TextView) view.findViewById(R.id.id);
      price = (TextView) view.findViewById(R.id.content);
    }

    @Override
    public String toString() {
      return super.toString() + " '" + price.getText() + "'";
    }
  }

  public interface OnItemClickListener {
    void onClick(Product product);
  }
}
