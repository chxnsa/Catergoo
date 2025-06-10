package catergoo.model;

import java.time.LocalDate;

public class CartItem {
  // Atribut dari kelas CartItem
  private MenuItem menuItem;
  private int quantity;
  private LocalDate deliveryDate;
  private String specialNotes;
  private String customizations;
  private double subTotal;

  /**
   * Constructor untuk menginisialisasi atribut dan menghitung subTotal.
   */
  public CartItem(MenuItem menuItem, int quantity, LocalDate deliveryDate, String customizations, String specialNotes) {
    this.menuItem = menuItem;
    this.quantity = quantity;
    this.deliveryDate = deliveryDate;
    this.customizations = customizations;
    this.specialNotes = specialNotes;
    // Menghitung subtotal saat objek dibuat
    this.subTotal = menuItem.calculatePrice(quantity);
  }

  /**
   * Mengupdate kuantitas dan menghitung ulang subtotal.
   */
  public void updateQuantity(int newQuantity) {
    this.quantity = newQuantity;
    this.subTotal = this.menuItem.calculatePrice(newQuantity);
  }

  // --- Getters untuk semua atribut ---
  public MenuItem getMenuItem() {
    return menuItem;
  }

  public int getQuantity() {
    return quantity;
  }

  public LocalDate getDeliveryDate() {
    return deliveryDate;
  }

  public String getSpecialNotes() {
    return specialNotes;
  }

  public String getCustomizations() {
    return customizations;
  }

  public double getSubTotal() {
    return subTotal;
  }
}
