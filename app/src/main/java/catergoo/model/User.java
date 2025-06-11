package catergoo.model;

import java.util.ArrayList;
import java.util.List;

public class User {
  private String username;
  private String password;
  private List<CartItem> currentCart;
  private List<Order> orderHistory;

  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.currentCart = new ArrayList<>();
    this.orderHistory = new ArrayList<>();
  }

  public void addToCart(CartItem item) {
    this.currentCart.add(item);
  }

  public void removeFromCart(CartItem item) {
    this.currentCart.remove(item);
  }

  public void clearCart() {
    this.currentCart.clear();
  }

  public void addToHistory(Order order) {
    this.orderHistory.add(order);
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public List<CartItem> getCurrentCart() {
    return currentCart;
  }

  public List<Order> getOrderHistory() {
    return orderHistory;
  }
}
