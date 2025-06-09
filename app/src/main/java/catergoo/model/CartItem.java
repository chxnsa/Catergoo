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
}
