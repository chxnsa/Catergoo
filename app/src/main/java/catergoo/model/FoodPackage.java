package catergoo.model;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

/**
 * Kelas yang merepresentasikan paket makanan utama
 * Contoh: Paket Daging, Paket Ayam, dll.
 * Mengimplementasikan interface Customizable untuk mendukung kustomisasi
 */

public class FoodPackage {
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
}
