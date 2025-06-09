package catergoo.model;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

/**
 * Kelas yang merepresentasikan paket snack
 * Contoh: Paket Risol, Paket Kue, dll.
 * Mengimplementasikan interface Customizable dengan pilihan yang lebih fleksibel
 */

public class SnackPackage extends MenuItem implements Customizeable {
    // Map pilihan snack dengan key sebagai kategori dan value sebagai list pilihan
    private Map<String, List<String>> snackChoices;
    
    // Map pilihan snack yang dipilih user
    private Map<String, String> selectedSnacks;

    /**
     * Constructor untuk inisialisasi paket snack
     * @param itemName Nama paket
     * @param pricePerPax Harga per porsi
     * @param minOrder Minimal pemesanan (dalam pcs)
     * @param description Deskripsi paket
     * @param imagePath Path gambar paket
     * @param snackChoices Map pilihan snack (key: kategori, value: list pilihan)
     */
    public SnackPackage(String itemName, double pricePerPax, int minOrder, 
                       String description, String imagePath,
                       Map<String, List<String>> snackChoices) {
        // Memanggil constructor parent class (MenuItem)
        super(itemName, pricePerPax, minOrder, description, imagePath, true);
        this.snackChoices = snackChoices;
    }

}
