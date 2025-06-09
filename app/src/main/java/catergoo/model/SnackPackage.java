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

    /**
     * Menampilkan informasi dasar paket snack
     * @return String informasi paket dalam format "[Nama] - Rp [Harga]/pax (Min: [MinOrder] pcs)"
     */
    @Override
    public String displayInfo() {
        return String.format("[%s] - Rp %.0f/pax (Min: %d pcs)", 
               getItemName(), getPricePerPax(), getMinOrder());
    }

    /**
     * Mendapatkan deskripsi kustomisasi snack yang dipilih
     * @return String yang menggabungkan semua pilihan kustomisasi snack
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
     * @param selections Map berisi pilihan kustomisasi dengan key sebagai kategori snack
     */
    @Override
    public void applyCustomizations(Map<String, String> selections) {
        this.selectedSnacks = selections;
    }

    /**
     * Override method untuk menghitung tanggal minimal booking
     * @param quantity Jumlah porsi yang dipesan
     * @return Tanggal minimal booking (H-2 jika >50 pcs, H-1 jika ≤50 pcs)
     */
    @Override
    public LocalDate getMinBookingDate(int quantity) {
        LocalDate today = LocalDate.now();
        return quantity > 50 ? today.plusDays(2) : today.plusDays(1);
    }

    // Getter untuk pilihan snack
    public Map<String, List<String>> getSnackChoices() { return snackChoices; }
}
