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

    /**
     * Constructor untuk inisialisasi paket makanan
     * @param itemName Nama paket
     * @param pricePerPax Harga per porsi
     * @param minOrder Minimal pemesanan
     * @param description Deskripsi paket
     * @param imagePath Path gambar paket
     * @param vegetableOptions List pilihan sayur
     * @param sideDishOptions List pilihan lauk tambahan
     * @param fruitOptions List pilihan buah
     * @param includeCrackers Flag default untuk kerupuk
     */
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
}
