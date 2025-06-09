package catergoo.model;
import java.util.Map;

/**
 * Interface untuk item menu yang bisa dikustomisasi
 */

public interface Customizeable {
    // Mengembalikan panel kustomisasi (diimplementasikan di JavaFX)
    // Node getCustomizationPanel();
    
    /**
     * Menerapkan kustomisasi yang dipilih
     * @param selections Map berisi pilihan kustomisasi
     */
    void applyCustomizations(Map<String, String> selections);
    
    /**
     * @return Deskripsi kustomisasi yang dipilih
     */
    String getSelectedCustomizations();
}