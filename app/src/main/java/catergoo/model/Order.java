package catergoo.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
  private String orderId;
  private User user;
  private List<CartItem> items;
  private double totalAmount;
  private String deliveryAddress;
  private LocalDateTime orderDate;
  private String paymentMethod;
  private String paymentProofPath;
  private String status;

  public Order(String orderId, User user, List<CartItem> items, double totalAmount, String deliveryAddress,
      String paymentMethod, String paymentProofPath) {
    this.orderId = orderId;
    this.user = user;
    this.items = items;
    this.totalAmount = totalAmount;
    this.deliveryAddress = deliveryAddress;
    this.paymentMethod = paymentMethod;
    this.paymentProofPath = paymentProofPath;
    this.orderDate = LocalDateTime.now();
    this.status = "Menunggu Konfirmasi";
  }

  public String getOrderId() {
    return orderId;
  }

  public User getUser() {
    return user;
  }

  public List<CartItem> getItems() {
    return items;
  }

  public double getTotalAmount() {
    return totalAmount;
  }

  public String getDeliveryAddress() {
    return deliveryAddress;
  }

  public LocalDateTime getOrderDate() {
    return orderDate;
  }

  public String getPaymentMethod() {
    return paymentMethod;
  }

  public String getPaymentProofPath() {
    return paymentProofPath;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
