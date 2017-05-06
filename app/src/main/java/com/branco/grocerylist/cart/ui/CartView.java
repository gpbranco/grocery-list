package com.branco.grocerylist.cart.ui;

import com.branco.grocerylist.cart.ui.model.CartViewData;
import com.branco.grocerylist.cart.ui.model.SelectedProductViewData;

import java.util.List;

public interface CartView {

  void showProducts(List<SelectedProductViewData> product);
  void showCartTotal(CartViewData cart);
}
