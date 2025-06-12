package catergoo.model;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class SnackPackage extends MenuItem implements Customizable {

    private Map<String, List<String>> snackChoices;
    private Map<String, String> selectedSnacks;

    public SnackPackage(String itemName, double pricePerPax, int minOrder,
            String description, String imagePath) {

        super(itemName, pricePerPax, minOrder, description, imagePath, false);
        this.snackChoices = null;
        this.selectedSnacks = null;
    }

    public SnackPackage(String itemName, double pricePerPax, int minOrder,
            String description, String imagePath,
            Map<String, List<String>> snackChoices) {

        super(itemName, pricePerPax, minOrder, description, imagePath, true);
        this.snackChoices = snackChoices;
    }

    @Override
    public String displayInfo() {
        return String.format("[%s] - Rp %.0f/pax (Min: %d pcs)",
                getItemName(), getPricePerPax(), getMinOrder());
    }

    @Override
    public String getSelectedCustomizations() {
        if (!isCustomizable()) {
            return "Paket standar (tidak bisa dikustomisasi)";
        }

        StringBuilder sb = new StringBuilder();
        if (selectedSnacks != null && !selectedSnacks.isEmpty()) {
            selectedSnacks.forEach((category, choice) -> sb.append(category).append(": ").append(choice).append(", "));
            return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
        }
        return "Belum ada kustomisasi yang dipilih";
    }

    @Override
    public void applyCustomizations(Map<String, String> selections) {
        if (isCustomizable()) {
            this.selectedSnacks = selections;
        }
    }

    @Override
    public LocalDate getMinBookingDate(int quantity) {
        LocalDate today = LocalDate.now();
        return quantity > 50 ? today.plusDays(2) : today.plusDays(1);
    }

    public double calculatePrice(int quantity) {
        return getPricePerPax() * quantity;
    }

    public Map<String, List<String>> getSnackChoices() {
        return snackChoices;
    }
}