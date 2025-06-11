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

    private Map<String, List<String>> snackChoices;
    private Map<String, String> selectedSnacks;

    
    //Constructor untuk inisialisasi paket snack yang TIDAK bisa dikustomisasi
    public SnackPackage(String itemName, double pricePerPax, int minOrder,
                       String description, String imagePath) {
        // Memanggil constructor parent class (MenuItem) dengan isCustomizable = false
        super(itemName, pricePerPax, minOrder, description, imagePath, false);
        this.snackChoices = null; // Tidak ada pilihan kustomisasi
        this.selectedSnacks = null; // Tidak ada pilihan yang dipilih
    }

    //Constructor untuk inisialisasi paket snack yang bisa dikustomisasi
    public SnackPackage(String itemName, double pricePerPax, int minOrder, 
                       String description, String imagePath,
                       Map<String, List<String>> snackChoices) {
        // Memanggil constructor parent class (MenuItem)
        super(itemName, pricePerPax, minOrder, description, imagePath, true);
        this.snackChoices = snackChoices;
    }

    /**
     * Menampilkan informasi dasar paket snack
     * Mengembalikan string informasi paket dalam format "[Nama] - Rp [Harga]/pax (Min: [MinOrder] pcs)"
     */
    @Override
    public String displayInfo() {
        return String.format("[%s] - Rp %.0f/pax (Min: %d pcs)", 
               getItemName(), getPricePerPax(), getMinOrder());
    }

    /**
     * Mendapatkan deskripsi kustomisasi snack yang dipilih
     * Mengembalikan String yang menggabungkan semua pilihan kustomisasi snack
     */
    @Override
    public String getSelectedCustomizations() {
        StringBuilder sb = new StringBuilder();
        if (selectedSnacks != null) {
            selectedSnacks.forEach((category, choice) -> 
                sb.append(category).append(": ").append(choice).append(", "));
            return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
        }
        return "";
    }

    /**
     * Menerapkan pilihan kustomisasi snack dari user
     * Map berisi pilihan kustomisasi dengan key sebagai kategori snack
     */
    @Override
    public void applyCustomizations(Map<String, String> selections) {
        this.selectedSnacks = selections;
    }

    /**
     * Override method untuk menghitung tanggal minimal booking
     * Jumlah porsi yang dipesan
     * Mengembalikan jumlah tanggal minimal booking (H-2 jika >50 pcs, H-1 jika ≤50 pcs)
     */
    @Override
    public LocalDate getMinBookingDate(int quantity) {
        LocalDate today = LocalDate.now();
        return quantity > 50 ? today.plusDays(2) : today.plusDays(1);
    }

    // Getter untuk pilihan snack
    public Map<String, List<String>> getSnackChoices() { return snackChoices; }
}