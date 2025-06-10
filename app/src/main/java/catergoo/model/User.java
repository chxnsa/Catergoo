package catergoo.model;

import java.util.ArrayList;
import java.util.List;

public class User {
  // Atribut dari kelas User
  private String username;
  private String password;
  private List<CartItem> currentCart; // Keranjang pribadi user
  private List<Order> orderHistory; // Riwayat pesanan user

  /**
   * Constructor untuk kelas User.
   */
  public User(String username, String password) {
    this.username = username;
    this.password = password;
    this.currentCart = new ArrayList<>();
    this.orderHistory = new ArrayList<>();
  }

  // --- Method untuk mengelola keranjang dan riwayat ---

  /**
   * Menambahkan item ke keranjang belanja.
   * 
   * @param item Item yang akan ditambahkan.
   */
  public void addToCart(CartItem item) {
    this.currentCart.add(item);
  }

  /**
   * Menghapus item dari keranjang belanja.
   * 
   * @param item Item yang akan dihapus.
   */
  public void removeFromCart(CartItem item) {
    this.currentCart.remove(item);
  }

  /**
   * Mengosongkan keranjang belanja.
   */
  public void clearCart() {
    this.currentCart.clear();
  }

  /**
   * Menambahkan pesanan ke riwayat pesanan.
   * 
   * @param order Pesanan yang telah dikonfirmasi.
   */
  public void addToHistory(Order order) {
    this.orderHistory.add(order);
  }

  // --- Getters ---
  // Getter untuk semua atribut
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
