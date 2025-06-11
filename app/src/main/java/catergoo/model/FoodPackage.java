package catergoo.model;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

/**
 * Kelas yang merepresentasikan paket makanan utama
 * Contoh: Paket Daging, Paket Ayam, dll.
 * Mengimplementasikan interface Customizable untuk mendukung kustomisasi
 */
public class FoodPackage extends MenuItem implements Customizeable {
    // Daftar pilihan kustomisasi
    private List<String> vegetableOptions;  // Pilihan sayur (Capcay, Tumis Buncis, dll)
    private List<String> sideDishOptions;   // Pilihan lauk tambahan (Tempe Oreg, Perkedel)
    private List<String> fruitOptions;      // Pilihan buah (Pisang, Semangka)
    private boolean includeCrackers;        // Flag apakah menyertakan kerupuk
    
    // Pilihan kustomisasi yang dipilih user
    private String selectedVegetable;
    private String selectedSideDish;
    private String selectedFruit;
    private boolean selectedIncludeCrackers;

    //Constructor untuk paket makanan yang TIDAK bisa dikustomisasi
    public FoodPackage(String itemName, double pricePerPax, int minOrder,
                      String description, String imagePath) {
        // Memanggil constructor parent dengan isCustomizable = false
        super(itemName, pricePerPax, minOrder, description, imagePath, false);
        this.vegetableOptions = null;
        this.sideDishOptions = null;
        this.fruitOptions = null;
        this.includeCrackers = false;
    }

    //Constructor untuk paket makanan yang bisa dikustomisasi
    public FoodPackage(String itemName, double pricePerPax, int minOrder, 
                      String description, String imagePath,
                      List<String> vegetableOptions, List<String> sideDishOptions, 
                      List<String> fruitOptions, boolean includeCrackers) {
        // Memanggil constructor parent class (MenuItem)
        super(itemName, pricePerPax, minOrder, description, imagePath, true);
        this.vegetableOptions = vegetableOptions;
        this.sideDishOptions = sideDishOptions;
        this.fruitOptions = fruitOptions;
        this.includeCrackers = includeCrackers;
    }

    /**
     * Menampilkan informasi dasar paket makanan
     * Mengembalikan String informasi paket dalam format "[Nama] - Rp [Harga]/pax (Min: [MinOrder] pax)"
     */
    @Override
    public String displayInfo() {
        return String.format("[%s] - Rp %.0f/pax (Min: %d pax)", 
               getItemName(), getPricePerPax(), getMinOrder());
    }

    /**
     * Mendapatkan deskripsi kustomisasi yang dipilih
     * Mengembalikan String yang menggabungkan semua pilihan kustomisasi
     */
    @Override
    public String getSelectedCustomizations() {
        if (!isCustomizable()) {
            return "Paket standar (tidak bisa dikustomisasi)";
        }
        
        StringBuilder sb = new StringBuilder();
        if (selectedVegetable != null) sb.append(selectedVegetable).append(", ");
        if (selectedSideDish != null) sb.append(selectedSideDish).append(", ");
        if (selectedFruit != null) sb.append(selectedFruit).append(", ");
        sb.append(selectedIncludeCrackers ? "Dengan Kerupuk" : "Tanpa Kerupuk");
        return sb.toString();
    }

    /**
     * Menerapkan pilihan kustomisasi dari user
     * Selection Map berisi pilihan kustomisasi dengan key:
     *                   "vegetable", "sideDish", "fruit", "includeCrackers"
     */
    @Override
    public void applyCustomizations(Map<String, String> selections) {
        if (isCustomizable()) {
            this.selectedVegetable = selections.get("vegetable");
            this.selectedSideDish = selections.get("sideDish");
            this.selectedFruit = selections.get("fruit");
            this.selectedIncludeCrackers = Boolean.parseBoolean(selections.get("includeCrackers"));
        }
    }

    /**
     * Override method untuk menghitung tanggal minimal booking
     * Menghitung Jumlah porsi yang dipesan
     * Mengembalikan Tanggal minimal booking (H-2 jika >50 pax, H-1 jika ≤50 pax)
     */
    @Override
    public LocalDate getMinBookingDate(int quantity) {
        LocalDate today = LocalDate.now();
        return quantity > 50 ? today.plusDays(2) : today.plusDays(1);
    }

    /**
     * Menghitung total harga berdasarkan jumlah porsi
     * Menghitung Jumlah porsi yang dipesan
     * Mengembalikan Total harga (pricePerPax * quantity)
     */
    public double calculatePrice(int quantity) {
        return getPricePerPax() * quantity;
    }

    // =========== Getter Methods ===========
    public List<String> getVegetableOptions() { return vegetableOptions; }
    public List<String> getSideDishOptions() { return sideDishOptions; }
    public List<String> getFruitOptions() { return fruitOptions; }
    public boolean isIncludeCrackers() { return includeCrackers; }
}