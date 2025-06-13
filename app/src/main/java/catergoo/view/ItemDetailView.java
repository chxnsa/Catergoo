package catergoo.view;

import catergoo.view.component.CustomizationPanel;
import catergoo.manager.SessionManager;
import catergoo.model.*;
import catergoo.model.MenuItem;
import catergoo.util.DateUtil;
import catergoo.util.UIUtil;
import catergoo.util.ValidationUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class ItemDetailView {
    private SceneManager sceneManager;
    private Stage modalStage;
    private MenuItem currentMenuItem;
    private Spinner<Integer> quantitySpinner;
    private DatePicker datePicker;
    private TextArea notesArea;
    private CustomizationPanel customizationPanel;
    private Label totalPriceLabel;

    public ItemDetailView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        initializeModal();
    }

    private void initializeModal() {
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(sceneManager.getPrimaryStage());
        modalStage.setTitle("Detail Menu");
        modalStage.setResizable(false);
    }

    public void showItemDetail(MenuItem menuItem) {
        this.currentMenuItem = menuItem;

        HBox mainContainer = new HBox(30);
        mainContainer.setPadding(new Insets(30));
        mainContainer.setPrefSize(900, 600);
        mainContainer.setStyle("-fx-background-color: white;");

        VBox leftSide = createLeftSide();

        VBox rightSide = createRightSide();

        mainContainer.getChildren().addAll(leftSide, rightSide);

        Scene scene = new Scene(mainContainer);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    private VBox createLeftSide() {
        VBox leftSide = new VBox(20);
        leftSide.setPrefWidth(400);

        ImageView imageView = UIUtil.createImageView(currentMenuItem.getImagePath(), 350, 200);
        if (imageView.getImage() == null) {
            imageView = UIUtil.createImageView("/images/placeholder/food-placeholder.jpg", 350, 200);
        }
        imageView.setStyle("-fx-background-radius: 10;");

        Label nameLabel = new Label(currentMenuItem.getItemName());
        nameLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        Label priceLabel = new Label(UIUtil.formatCurrency(currentMenuItem.getPricePerPax()) + "/pax");
        priceLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label minOrderLabel = new Label("Min : " + currentMenuItem.getMinOrder() + "pax");
        minOrderLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: gray;");

        Label descLabel = new Label(currentMenuItem.getDescription());
        descLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        descLabel.setWrapText(true);

        leftSide.getChildren().addAll(imageView, nameLabel, priceLabel, minOrderLabel, descLabel);

        return leftSide;
    }

    private VBox createRightSide() {
        VBox rightSide = new VBox(20);
        rightSide.setPrefWidth(450);

        if (currentMenuItem.isCustomizable()) {
            Label customLabel = new Label("Kustom");
            customLabel.setStyle(
                    "-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

            customizationPanel = new CustomizationPanel(currentMenuItem);

            rightSide.getChildren().addAll(customLabel, customizationPanel.getPanel());
        }

        Label orderLabel = new Label("Pemesanan");
        orderLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        HBox quantityBox = new HBox(10);
        quantityBox.setAlignment(Pos.CENTER_LEFT);

        Label quantityLabel = new Label("Jumlah Porsi :");
        quantityLabel.setStyle("-fx-font-size: 16px;");

        quantitySpinner = UIUtil.createIntegerSpinner(currentMenuItem.getMinOrder(), 1000,
                currentMenuItem.getMinOrder());
        quantitySpinner.valueProperty().addListener((obs, oldVal, newVal) -> updateTotalPrice());

        Label paxLabel = new Label("Pax (Min " + currentMenuItem.getMinOrder() + ")");
        paxLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        quantityBox.getChildren().addAll(quantityLabel, quantitySpinner, paxLabel);

        HBox dateBox = new HBox(10);
        dateBox.setAlignment(Pos.CENTER_LEFT);

        Label dateLabel = new Label("Tanggal Pengantaran:");
        dateLabel.setStyle("-fx-font-size: 16px;");

        datePicker = new DatePicker();
        datePicker.setValue(DateUtil.getMinBookingDate(currentMenuItem.getMinOrder()));
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(DateUtil.getMinBookingDate(quantitySpinner.getValue())));
            }
        });

        dateBox.getChildren().addAll(dateLabel, datePicker);

        Label notesLabel = new Label("Catatan Khusus:");
        notesLabel.setStyle("-fx-font-size: 16px;");

        notesArea = UIUtil.createTextArea("Tambahkan catatan khusus untuk pesanan Anda...", 3);
        notesArea.setPrefWidth(400);

        HBox totalBox = new HBox();
        totalBox.setAlignment(Pos.CENTER_LEFT);
        totalBox.setSpacing(10);

        Label totalLabel = new Label("Total Pemesanan :");
        totalLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        totalPriceLabel = new Label(UIUtil.formatCurrency(0));
        totalPriceLabel
                .setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        totalBox.getChildren().addAll(totalLabel, totalPriceLabel);

        Button addToCartBtn = new Button("Tambah ke Keranjang");
        addToCartBtn.setPrefSize(400, 50);
        addToCartBtn.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 25; -fx-cursor: hand;");
        addToCartBtn.setOnAction(e -> handleAddToCart());

        rightSide.getChildren().addAll(
                orderLabel,
                quantityBox,
                dateBox,
                notesLabel, notesArea,
                totalBox,
                addToCartBtn);

        updateTotalPrice();

        return rightSide;
    }

    private void updateTotalPrice() {
        if (quantitySpinner != null && totalPriceLabel != null) {
            int quantity = quantitySpinner.getValue();
            double total = currentMenuItem.calculatePrice(quantity);
            totalPriceLabel.setText(UIUtil.formatCurrency(total));

            if (datePicker != null) {
                LocalDate minDate = DateUtil.getMinBookingDate(quantity);
                if (datePicker.getValue().isBefore(minDate)) {
                    datePicker.setValue(minDate);
                }
            }
        }
    }

    private void handleAddToCart() {
        try {
            int quantity = quantitySpinner.getValue();
            LocalDate deliveryDate = datePicker.getValue();
            String notes = notesArea.getText().trim();

            if (!ValidationUtil.isValidQuantity(quantity, currentMenuItem.getMinOrder())) {
                UIUtil.showAlert(Alert.AlertType.WARNING, "Quantity Tidak Valid",
                        "Minimum pemesanan adalah " + currentMenuItem.getMinOrder() + " porsi");
                return;
            }

            if (deliveryDate == null || deliveryDate.isBefore(DateUtil.getMinBookingDate(quantity))) {
                UIUtil.showAlert(Alert.AlertType.WARNING, "Tanggal Tidak Valid",
                        "Pilih tanggal pengantaran yang valid");
                return;
            }

            String customizations = "";
            Map<String, String> customizationMap = new HashMap<>();

            if (currentMenuItem.isCustomizable() && customizationPanel != null) {
                customizationMap = customizationPanel.getSelections();
                if (currentMenuItem instanceof Customizable) {
                    ((Customizable) currentMenuItem).applyCustomizations(customizationMap);
                    customizations = ((Customizable) currentMenuItem).getSelectedCustomizations();
                }
            }

            CartItem cartItem = new CartItem(currentMenuItem, quantity, deliveryDate, customizations, notes);

            User currentUser = SessionManager.getCurrentUser();
            if (currentUser != null) {
                currentUser.addToCart(cartItem);

                UIUtil.showAlert(Alert.AlertType.INFORMATION, "Berhasil",
                        "Item berhasil ditambahkan ke keranjang!");

                modalStage.close();
            } else {
                UIUtil.showAlert(Alert.AlertType.ERROR, "Error", "User tidak ditemukan!");
            }

        } catch (Exception e) {
            UIUtil.showAlert(Alert.AlertType.ERROR, "Error", "Terjadi kesalahan: " + e.getMessage());
        }
    }
}