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
}
