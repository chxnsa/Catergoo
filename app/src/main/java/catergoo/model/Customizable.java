
package catergoo.model;

import java.util.Map;

public interface Customizable {

    void applyCustomizations(Map<String, String> selections);

    String getSelectedCustomizations();
}