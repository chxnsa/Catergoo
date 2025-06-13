package catergoo.model;

import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class FoodPackage extends MenuItem implements Customizable {

    private List<String> vegetableOptions;
    private List<String> sideDishOptions;
    private List<String> fruitOptions;
    private boolean includeCrackers;

    private String selectedVegetable;
    private String selectedSideDish;
    private String selectedFruit;
    private boolean selectedIncludeCrackers;

    public FoodPackage(String itemName, double pricePerPax, int minOrder,
            String description, String imagePath) {
        super(itemName, pricePerPax, minOrder, description, imagePath, false);
        this.vegetableOptions = null;
        this.sideDishOptions = null;
        this.fruitOptions = null;
        this.includeCrackers = false;
    }

    public FoodPackage(String itemName, double pricePerPax, int minOrder,
            String description, String imagePath,
            List<String> vegetableOptions, List<String> sideDishOptions,
            List<String> fruitOptions, boolean includeCrackers) {
        super(itemName, pricePerPax, minOrder, description, imagePath, true);
        this.vegetableOptions = vegetableOptions;
        this.sideDishOptions = sideDishOptions;
        this.fruitOptions = fruitOptions;
        this.includeCrackers = includeCrackers;
    }

    @Override
    public String displayInfo() {
        return String.format("[%s] - Rp %.0f/pax (Min: %d pax)",
                getItemName(), getPricePerPax(), getMinOrder());
    }

    @Override
    public String getSelectedCustomizations() {
        if (!isCustomizable()) {
            return "Paket standar (tidak bisa dikustomisasi)";
        }

        StringBuilder sb = new StringBuilder();
        if (selectedVegetable != null)
            sb.append(selectedVegetable).append(", ");
        if (selectedSideDish != null)
            sb.append(selectedSideDish).append(", ");
        if (selectedFruit != null)
            sb.append(selectedFruit).append(", ");
        sb.append(selectedIncludeCrackers ? "Dengan Kerupuk" : "Tanpa Kerupuk");
        return sb.toString();
    }

    @Override
    public void applyCustomizations(Map<String, String> selections) {
        if (isCustomizable()) {
            this.selectedVegetable = selections.get("vegetable");
            this.selectedSideDish = selections.get("sideDish");
            this.selectedFruit = selections.get("fruit");
            this.selectedIncludeCrackers = Boolean.parseBoolean(selections.get("includeCrackers"));
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

    public List<String> getVegetableOptions() {
        return vegetableOptions;
    }

    public List<String> getSideDishOptions() {
        return sideDishOptions;
    }

    public List<String> getFruitOptions() {
        return fruitOptions;
    }

    public boolean isIncludeCrackers() {
        return includeCrackers;
    }
}