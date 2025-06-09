package catergoo.model;
import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public interface Customizeable {
    // Mengembalikan panel kustomisasi (diimplementasikan di JavaFX)
    // Node getCustomizationPanel();
    
    // Menerapkan kustomisasi yang dipilih
    void applyCustomizations(Map<String, String> selections);
    
    // Mengembalikan deskripsi kustomisasi yang dipilih
    String getSelectedCustomizations();
}