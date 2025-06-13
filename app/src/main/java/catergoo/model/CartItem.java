package catergoo.model;

import java.time.LocalDate;

public class CartItem {
  private MenuItem menuItem;
  private int quantity;
  private LocalDate deliveryDate;
  private String specialNotes;
  private String customizations;
  private double subTotal;

  public CartItem(MenuItem menuItem, int quantity, LocalDate deliveryDate, String customizations,
      String specialNotes) {
    this.menuItem = menuItem;
    this.quantity = quantity;
    this.deliveryDate = deliveryDate;
    this.customizations = customizations;
    this.specialNotes = specialNotes;
    this.subTotal = menuItem.calculatePrice(quantity);
  }

  public void updateQuantity(int newQuantity) {
    this.quantity = newQuantity;
    this.subTotal = this.menuItem.calculatePrice(newQuantity);
  }

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