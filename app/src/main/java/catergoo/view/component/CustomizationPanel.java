package catergoo.view.component;

import catergoo.model.*;
import catergoo.model.MenuItem;
import catergoo.util.UIUtil;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomizationPanel {
    private VBox panel;
    private MenuItem menuItem;
    private Map<String, ComboBox<String>> comboBoxes;
    private Map<String, ToggleGroup> toggleGroups;

    public CustomizationPanel(MenuItem menuItem) {
        this.menuItem = menuItem;
        this.comboBoxes = new HashMap<>();
        this.toggleGroups = new HashMap<>();
        initializePanel();
    }

    private void initializePanel() {
        panel = new VBox(15);
        panel.setPadding(new Insets(10));

        if (menuItem instanceof FoodPackage) {
            createFoodPackageCustomization((FoodPackage) menuItem);
        } else if (menuItem instanceof SnackPackage) {
            createSnackPackageCustomization((SnackPackage) menuItem);
        }
    }

    private void createFoodPackageCustomization(FoodPackage foodPackage) {

        if (foodPackage.getVegetableOptions() != null && !foodPackage.getVegetableOptions().isEmpty()) {
            VBox vegContainer = createSelectionContainer("Pilih Sayur", foodPackage.getVegetableOptions());
            panel.getChildren().add(vegContainer);
        }

        if (foodPackage.getSideDishOptions() != null && !foodPackage.getSideDishOptions().isEmpty()) {
            VBox sideContainer = createSelectionContainer("Lauk Tambahan", foodPackage.getSideDishOptions());
            panel.getChildren().add(sideContainer);
        }

        if (foodPackage.isIncludeCrackers()) {
            VBox crackersContainer = createCrackersSelection();
            panel.getChildren().add(crackersContainer);
        }

        if (foodPackage.getFruitOptions() != null && !foodPackage.getFruitOptions().isEmpty()) {
            VBox fruitContainer = createSelectionContainer("Pilih Buah", foodPackage.getFruitOptions());
            panel.getChildren().add(fruitContainer);
        }
    }

    private void createSnackPackageCustomization(SnackPackage snackPackage) {
        if (snackPackage.getSnackChoices() == null)
            return;

        for (Map.Entry<String, List<String>> entry : snackPackage.getSnackChoices().entrySet()) {
            String category = entry.getKey();
            List<String> options = entry.getValue();

            VBox categoryContainer = createSelectionContainer(category, options);
            panel.getChildren().add(categoryContainer);
        }
    }

    private VBox createSelectionContainer(String title, List<String> options) {
        VBox container = new VBox(8);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ComboBox<String> comboBox = UIUtil.createComboBox();
        comboBox.getItems().addAll(options);
        comboBox.setValue(options.get(0));
        comboBox.setPrefWidth(350);

        comboBoxes.put(getKeyFromTitle(title), comboBox);

        container.getChildren().addAll(titleLabel, comboBox);
        return container;
    }

    private VBox createCrackersSelection() {
        VBox container = new VBox(8);

        Label titleLabel = new Label("Kerupuk");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ToggleGroup crackersGroup = new ToggleGroup();

        RadioButton withCrackers = UIUtil.createStyledRadioButton("Ya");
        withCrackers.setToggleGroup(crackersGroup);
        withCrackers.setSelected(true);

        RadioButton withoutCrackers = UIUtil.createStyledRadioButton("Tidak");
        withoutCrackers.setToggleGroup(crackersGroup);

        HBox radioContainer = new HBox(20);
        radioContainer.getChildren().addAll(withCrackers, withoutCrackers);

        toggleGroups.put("crackers", crackersGroup);

        container.getChildren().addAll(titleLabel, radioContainer);
        return container;
    }

    private String getKeyFromTitle(String title) {
        switch (title) {
            case "Pilih Sayur":
                return "vegetable";
            case "Lauk Tambahan":
                return "sideDish";
            case "Pilih Buah":
                return "fruit";
            default:
                return title.toLowerCase().replace(" ", "");
        }
    }

    public Map<String, String> getSelections() {
        Map<String, String> selections = new HashMap<>();

        for (Map.Entry<String, ComboBox<String>> entry : comboBoxes.entrySet()) {
            String key = entry.getKey();
            ComboBox<String> comboBox = entry.getValue();
            if (comboBox.getValue() != null) {
                selections.put(key, comboBox.getValue());
            }
        }

        for (Map.Entry<String, ToggleGroup> entry : toggleGroups.entrySet()) {
            String key = entry.getKey();
            ToggleGroup group = entry.getValue();
            if (group.getSelectedToggle() != null) {
                RadioButton selected = (RadioButton) group.getSelectedToggle();
                if (key.equals("crackers")) {
                    selections.put("includeCrackers", String.valueOf(selected.getText().equals("Ya")));
                } else {
                    selections.put(key, selected.getText());
                }
            }
        }

        return selections;
    }

    public VBox getPanel() {
        return panel;
    }
}