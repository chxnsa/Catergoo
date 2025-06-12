package catergoo.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public abstract class MenuItem {
    private String itemName;
    private double pricePerPax;
    private int minOrder;
    private String description;
    private String imagePath;
    private boolean isCustomizable;
    private Set<LocalDate> bookedDates;

    public MenuItem(String itemName, double pricePerPax, int minOrder, String description, String imagePath,
            boolean isCustomizable) {
        this.itemName = itemName;
        this.pricePerPax = pricePerPax;
        this.minOrder = minOrder;
        this.description = description;
        this.imagePath = imagePath;
        this.isCustomizable = isCustomizable;
        this.bookedDates = new HashSet<>();
    }

    public abstract String displayInfo();

    public abstract double calculatePrice(int quantity); // FIXED: Added quantity parameter

    public boolean isDateAvailable(LocalDate date) {
        return !bookedDates.contains(date) && !date.isBefore(getMinBookingDate(1));
    }

    public void bookDate(LocalDate date) {
        bookedDates.add(date);
    }

    public LocalDate getMinBookingDate(int quantity) {
        LocalDate today = LocalDate.now();
        return quantity > 50 ? today.plusDays(2) : today.plusDays(1);
    }

    public String getItemName() {
        return itemName;
    }

    public double getPricePerPax() {
        return pricePerPax;
    }

    public int getMinOrder() {
        return minOrder;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public boolean isCustomizable() {
        return isCustomizable;
    }

    public Set<LocalDate> getBookedDates() {
        return bookedDates;
    }
}