package catergoo.manager;

import catergoo.model.MenuItem;
import catergoo.model.FoodPackage;
import catergoo.model.SnackPackage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuManager {

    private static List<MenuItem> defaultMenuItems = new ArrayList<>();

    public static void initializeDefaultMenu() {
        if (!defaultMenuItems.isEmpty()) {
            return;
        }

        createFoodPackages();

        createSnackPackages();
    }

    private static void createFoodPackages() {

        FoodPackage paketDaging = new FoodPackage(
                "Paket Daging",
                25000.0,
                20,
                "Paket nasi dengan lauk daging sapi pilihan",
                "/images/menu/paket-daging.jpg",
                Arrays.asList("Capcay", "Tumis Buncis", "Sayur Asem"),
                Arrays.asList("Tempe Oreg", "Perkedel", "Tahu Bacem"),
                Arrays.asList("Pisang", "Semangka", "Jeruk"),
                true);

        FoodPackage paketAyam = new FoodPackage(
                "Paket Ayam",
                20000.0,
                15,
                "Paket nasi dengan ayam goreng crispy",
                "/images/menu/paket-ayam.jpg",
                Arrays.asList("Capcay", "Tumis Kangkung", "Sayur Sop"),
                Arrays.asList("Tempe Goreng", "Tahu Crispy", "Sambal Goreng Ati"),
                Arrays.asList("Pisang", "Apel", "Melon"),
                true);

        FoodPackage paketIkan = new FoodPackage(
                "Paket Ikan Spesial",
                30000.0,
                25,
                "Paket nasi dengan ikan bakar bumbu kecap, sayur asem, tempe goreng, kerupuk, dan buah potong. Menu sudah ditetapkan dan tidak bisa dikustomisasi.",
                "/images/menu/paket-ikan.jpg");

        defaultMenuItems.add(paketDaging);
        defaultMenuItems.add(paketAyam);
        defaultMenuItems.add(paketIkan);
    }

    private static void createSnackPackages() {

        Map<String, List<String>> risolChoices = new HashMap<>();
        risolChoices.put("Risol", Arrays.asList("Risol Mayo", "Risol Ragout", "Risol Ayam"));
        risolChoices.put("Minuman", Arrays.asList("Teh Manis", "Air Mineral", "Jus Jeruk"));

        SnackPackage paketRisol = new SnackPackage(
                "Paket Risol",
                8000.0,
                25,
                "Paket snack risol dengan minuman",
                "/images/menu/paket-risol.jpg",
                risolChoices);

        Map<String, List<String>> kueChoices = new HashMap<>();
        kueChoices.put("Kue", Arrays.asList("Donat", "Cupcake", "Brownies"));
        kueChoices.put("Minuman", Arrays.asList("Kopi", "Teh", "Susu"));

        SnackPackage paketKue = new SnackPackage(
                "Paket Kue",
                12000.0,
                20,
                "Paket kue dan minuman untuk coffee break",
                "/images/menu/paket-kue.jpg",
                kueChoices);

        SnackPackage paketGorengan = new SnackPackage(
                "Paket Gorengan Tradisional",
                6000.0,
                30,
                "Paket gorengan tradisional berisi: tahu isi, tempe mendoan, bakwan jagung, pisang goreng, dan teh manis hangat. Menu sudah ditetapkan.",
                "/images/menu/paket-gorengan.jpg");

        defaultMenuItems.add(paketRisol);
        defaultMenuItems.add(paketKue);
        defaultMenuItems.add(paketGorengan);
    }

    public static List<MenuItem> getAvailableMenuItems() {
        return new ArrayList<>(defaultMenuItems);
    }

    public static MenuItem getMenuItemByName(String name) {
        if (name == null)
            return null;

        return defaultMenuItems.stream()
                .filter(item -> item.getItemName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static List<MenuItem> getMenuItemsByCategory(String category) {
        if ("Semua".equals(category)) {
            return getAvailableMenuItems();
        }

        List<MenuItem> filtered = new ArrayList<>();
        for (MenuItem item : defaultMenuItems) {
            if (matchesCategory(item, category)) {
                filtered.add(item);
            }
        }

        return filtered;
    }

    private static boolean matchesCategory(MenuItem item, String category) {
        switch (category) {
            case "Nasi Kotak":
                return item instanceof FoodPackage;
            case "Kotak Snack":
                return item instanceof SnackPackage;
            case "Paket":
                return item.getItemName().contains("Paket");
            case "Customizable":
                return item.isCustomizable();
            case "Non-Customizable":
                return !item.isCustomizable();
            default:
                return false;
        }
    }

    public static int getTotalMenuItems() {
        return defaultMenuItems.size();
    }

    public static boolean isMenuInitialized() {
        return !defaultMenuItems.isEmpty();
    }

    public static void clearMenu() {
        defaultMenuItems.clear();
    }

    public static List<MenuItem> getCustomizableMenuItems() {
        List<MenuItem> customizable = new ArrayList<>();
        for (MenuItem item : defaultMenuItems) {
            if (item.isCustomizable()) {
                customizable.add(item);
            }
        }
        return customizable;
    }

    public static List<MenuItem> getNonCustomizableMenuItems() {
        List<MenuItem> nonCustomizable = new ArrayList<>();
        for (MenuItem item : defaultMenuItems) {
            if (!item.isCustomizable()) {
                nonCustomizable.add(item);
            }
        }
        return nonCustomizable;
    }
}