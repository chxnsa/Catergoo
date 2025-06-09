package catergoo.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
  // Atribut dari kelas Order
  private String orderId;
  private User user; // Referensi ke pengguna yang memesan
  private List<CartItem> items;
  private double totalAmount;
  private String deliveryAddress;
  private LocalDateTime orderDate;
  private String paymentMethod;
  private String paymentProofPath;
  private String status;
}
