package catergoo.model;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Kelas abstrak yang merepresentasikan item menu dasar
 * Menyediakan atribut dan method dasar yang dimiliki oleh semua item menu
 */

public abstract class MenuItem {
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

    /**
     * Method abstrak untuk menampilkan informasi item
     * @return String informasi item yang akan ditampilkan ke pengguna
     */
    public abstract String displayInfo();

    /**
     * Mengecek ketersediaan tanggal untuk pemesanan
     * @param date Tanggal yang ingin dicek
     * @return true jika tanggal tersedia, false jika sudah dibooking
     */
    public boolean isDateAvailable(LocalDate date) {
        // Tanggal tidak tersedia jika sudah dibooking atau sebelum tanggal minimal booking
        return !bookedDates.contains(date) && !date.isBefore(getMinBookingDate(1));
    }

    /**
     * Membooking tanggal untuk item ini
     * @param date Tanggal yang akan dibooking
     */
    public void bookDate(LocalDate date) {
        bookedDates.add(date);
    }

    /**
     * Menghitung tanggal minimal booking berdasarkan quantity
     * @param quantity Jumlah porsi yang dipesan
     * @return Tanggal minimal yang bisa dipilih untuk pemesanan
     */
    public LocalDate getMinBookingDate(int quantity) {
        LocalDate today = LocalDate.now();
        // Jika pesanan > 50 pax, minimal booking H-2, jika tidak H-1
        return quantity > 50 ? today.plusDays(2) : today.plusDays(1);
    }

    // =========== Getter Methods ===========
    public String getItemName() { return itemName; }
    public double getPricePerPax() { return pricePerPax; }
    public int getMinOrder() { return minOrder; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public boolean isCustomizable() { return isCustomizable; }
    public Set<LocalDate> getBookedDates() { return bookedDates; }
}
