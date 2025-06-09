package catergoo.model;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Kelas abstrak yang merepresentasikan item menu dasar
 * Menyediakan atribut dan method dasar yang dimiliki oleh semua item menu
 */

public class MenuItem {
    // Atribut dasar item menu
    private String itemName;         // Nama item menu
    private double pricePerPax;      // Harga per porsi
    private int minOrder;            // Minimal pemesanan
    private String description;      // Deskripsi item
    private String imagePath;        // Path/lokasi gambar item
    private boolean isCustomizable;  // Flag apakah item bisa dikustomisasi
    private Set<LocalDate> bookedDates; // Set tanggal yang sudah dibooking untuk item ini

    /**
     * Constructor untuk inisialisasi item menu
     * @param itemName Nama item menu
     * @param pricePerPax Harga per porsi
     * @param minOrder Minimal pemesanan
     * @param description Deskripsi item
     * @param imagePath Path/lokasi gambar item
     * @param isCustomizable Flag apakah item bisa dikustomisasi
     */

     public MenuItem(String itemName, double pricePerPax, int minOrder, String description, String imagePath, boolean isCustomizable) {
        this.itemName = itemName;
        this.pricePerPax = pricePerPax;
        this.minOrder = minOrder;
        this.description = description;
        this.imagePath = imagePath;
        this.isCustomizable = isCustomizable;
        this.bookedDates = new HashSet<>();
    }
}
