package catergoo.view;

import catergoo.manager.SessionManager;
import catergoo.model.CartItem;
import catergoo.model.Order;
import catergoo.model.User;
import catergoo.util.DateUtil;
import catergoo.util.UIUtil;
import catergoo.util.ValidationUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PaymentView {
    private SceneManager sceneManager;
    private CartView cartView;
    private Stage modalStage;
    private List<CartItem> cartItems;
    private double totalAmount;

    private ToggleGroup paymentTypeGroup;
    private ToggleGroup bankGroup;
    private TextArea addressArea;
    private Label paymentAmountLabel;
    private Label accountNumberLabel;
    private Button uploadButton;
    private File selectedProofFile;

    public PaymentView(SceneManager sceneManager, CartView cartView) {
        this.sceneManager = sceneManager;
        this.cartView = cartView;
        initializeModal();
    }

    private void initializeModal() {
        modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(sceneManager.getPrimaryStage());
        modalStage.setTitle("Pembayaran");
        modalStage.setResizable(false);
    }

    public void showPaymentView(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        this.totalAmount = cartItems.stream().mapToDouble(CartItem::getSubTotal).sum();

        VBox mainContainer = new VBox(20);
        mainContainer.setPadding(new Insets(25));
        mainContainer.setPrefSize(550, 700);
        mainContainer.setStyle("-fx-background-color: white;");
        mainContainer.setAlignment(Pos.TOP_CENTER);

        Label titleLabel = new Label("Pembayaran");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        HBox firstHBox = createFirstHBox();

        HBox secondHBox = createSecondHBox();

        VBox addressSection = createAddressSection();

        VBox uploadSection = createUploadSection();

        HBox buttonSection = createButtonSection();

        mainContainer.getChildren().addAll(
                titleLabel,
                firstHBox,
                secondHBox,
                addressSection,
                uploadSection,
                buttonSection);

        Scene scene = new Scene(mainContainer);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    private HBox createFirstHBox() {
        HBox firstHBox = new HBox(20);
        firstHBox.setAlignment(Pos.CENTER);
        firstHBox.setPrefWidth(500);

        VBox leftSide = new VBox(5);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.setPrefWidth(250);

        Label amountTextLabel = new Label("Nominal Pembayaran");
        amountTextLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #666;");

        paymentAmountLabel = new Label(UIUtil.formatCurrency(totalAmount));
        paymentAmountLabel
                .setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        leftSide.getChildren().addAll(amountTextLabel, paymentAmountLabel);

        VBox rightSide = new VBox(8);
        rightSide.setAlignment(Pos.CENTER_LEFT);
        rightSide.setPrefWidth(250);

        paymentTypeGroup = new ToggleGroup();

        RadioButton fullPayment = UIUtil.createStyledRadioButton("Full Payment");
        fullPayment.setToggleGroup(paymentTypeGroup);
        fullPayment.setSelected(true);
        fullPayment.setOnAction(e -> updatePaymentAmount());

        RadioButton dpPayment = UIUtil.createStyledRadioButton("DP 30%");
        dpPayment.setToggleGroup(paymentTypeGroup);
        dpPayment.setOnAction(e -> updatePaymentAmount());

        rightSide.getChildren().addAll(fullPayment, dpPayment);

        firstHBox.getChildren().addAll(leftSide, rightSide);
        return firstHBox;
    }

    private HBox createSecondHBox() {
        HBox secondHBox = new HBox(20);
        secondHBox.setAlignment(Pos.TOP_CENTER);
        secondHBox.setPrefWidth(500);

        VBox leftSide = new VBox(10);
        leftSide.setAlignment(Pos.CENTER);
        leftSide.setPrefWidth(250);

        Label accountTextLabel = new Label("Rekening Tujuan");
        accountTextLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #666;");

        accountNumberLabel = new Label("BCA 098123765341");
        accountNumberLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label instructionsLabel = new Label("Tata Cara Pembayaran");
        instructionsLabel.setStyle("-fx-font-size: 12px; -fx-font-weight: bold; -fx-text-fill: #666;");

        VBox instructionsContainer = new VBox(3);
        instructionsContainer.setPadding(new Insets(8));
        instructionsContainer.setStyle("-fx-background-color: " + UIUtil.LIGHT_GRAY + "; -fx-background-radius: 5;");
        instructionsContainer.setPrefWidth(240);

        Label instruction1 = new Label("1. Transfer ke rekening tujuan");
        instruction1.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        Label instruction2 = new Label("2. Sesuaikan nominal dengan tagihan");
        instruction2.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        Label instruction3 = new Label("3. Simpan bukti pembayaran");
        instruction3.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        Label instruction4 = new Label("4. Upload bukti di form ini");
        instruction4.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        Label finalNote = new Label("Konfirmasi maksimal 1x24 jam setelah pembayaran.");
        finalNote.setStyle("-fx-font-size: 9px; -fx-font-style: italic;");
        finalNote.setWrapText(true);

        instructionsContainer.getChildren().addAll(instruction1, instruction2, instruction3, instruction4, finalNote);

        leftSide.getChildren().addAll(accountTextLabel, accountNumberLabel, instructionsLabel, instructionsContainer);

        VBox rightSide = new VBox(8);
        rightSide.setAlignment(Pos.CENTER_LEFT);
        rightSide.setPrefWidth(250);

        Label bankLabel = new Label("Transfer Via");
        bankLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        bankGroup = new ToggleGroup();

        RadioButton bcaBank = UIUtil.createStyledRadioButton("Bank BCA");
        bcaBank.setToggleGroup(bankGroup);
        bcaBank.setSelected(true);
        bcaBank.setOnAction(e -> updateAccountNumber());

        RadioButton mandiriBank = UIUtil.createStyledRadioButton("Bank Mandiri");
        mandiriBank.setToggleGroup(bankGroup);
        mandiriBank.setOnAction(e -> updateAccountNumber());

        RadioButton bniBank = UIUtil.createStyledRadioButton("Bank BNI");
        bniBank.setToggleGroup(bankGroup);
        bniBank.setOnAction(e -> updateAccountNumber());

        RadioButton dana = UIUtil.createStyledRadioButton("DANA");
        dana.setToggleGroup(bankGroup);
        dana.setOnAction(e -> updateAccountNumber());

        rightSide.getChildren().addAll(bankLabel, bcaBank, mandiriBank, bniBank, dana);

        secondHBox.getChildren().addAll(leftSide, rightSide);
        return secondHBox;
    }

    private VBox createAddressSection() {
        VBox section = new VBox(8);
        section.setAlignment(Pos.CENTER);
        section.setPrefWidth(500);

        Label addressLabel = new Label("Alamat Pengiriman");
        addressLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        addressArea = UIUtil.createTextArea("Masukkan alamat lengkap pengiriman...", 3);
        addressArea.setPrefWidth(480);
        addressArea.setMaxWidth(480);

        section.getChildren().addAll(addressLabel, addressArea);
        return section;
    }

    private VBox createUploadSection() {
        VBox section = new VBox(8);
        section.setAlignment(Pos.CENTER);
        section.setPrefWidth(500);

        Label uploadLabel = new Label("Upload Bukti Pembayaran");
        uploadLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        uploadButton = new Button("Pilih File Bukti Pembayaran ↑");
        uploadButton.setPrefSize(300, 40);
        uploadButton.setStyle("-fx-background-color: " + UIUtil.SECONDARY_COLOR +
                "; -fx-text-fill: " + UIUtil.ACCENT_COLOR + "; -fx-font-size: 14px;" +
                "; -fx-background-radius: 8; -fx-cursor: hand;");
        uploadButton.setOnAction(e -> handleFileUpload());

        section.getChildren().addAll(uploadLabel, uploadButton);
        return section;
    }

    private HBox createButtonSection() {
        HBox buttonSection = new HBox(15);
        buttonSection.setAlignment(Pos.CENTER);
        buttonSection.setPrefWidth(500);

        Button cancelButton = new Button("Batal");
        cancelButton.setPrefSize(300, 40);
        cancelButton.setStyle("-fx-background-color: " + UIUtil.LIGHT_GRAY +
                "; -fx-text-fill: black; -fx-font-size: 14px; -fx-background-radius: 8;");
        cancelButton.setOnAction(e -> modalStage.close());

        Button confirmButton = new Button("Konfirmasi Pemesanan");
        confirmButton.setPrefSize(300, 40);
        confirmButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 8; -fx-cursor: hand;");
        confirmButton.setOnAction(e -> handleConfirmOrder());

        buttonSection.getChildren().addAll(cancelButton, confirmButton);
        return buttonSection;
    }

    private void updatePaymentAmount() {
        RadioButton selected = (RadioButton) paymentTypeGroup.getSelectedToggle();
        if (selected != null) {
            if (selected.getText().equals("DP 30%")) {
                double dpAmount = totalAmount * 0.3;
                paymentAmountLabel.setText(UIUtil.formatCurrency(dpAmount));
            } else {
                paymentAmountLabel.setText(UIUtil.formatCurrency(totalAmount));
            }
        }
    }

    private void updateAccountNumber() {
        RadioButton selected = (RadioButton) bankGroup.getSelectedToggle();
        if (selected != null) {
            switch (selected.getText()) {
                case "Bank BCA":
                    accountNumberLabel.setText("BCA 098123765341");
                    break;
                case "Bank Mandiri":
                    accountNumberLabel.setText("Mandiri 120098765341");
                    break;
                case "Bank BNI":
                    accountNumberLabel.setText("BNI 046523765341");
                    break;
                case "DANA":
                    accountNumberLabel.setText("DANA 081234567890");
                    break;
            }
        }
    }

    private void handleFileUpload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pilih Bukti Pembayaran");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        selectedProofFile = fileChooser.showOpenDialog(modalStage);

        if (selectedProofFile != null) {
            uploadButton.setText("✓ " + selectedProofFile.getName());
            uploadButton.setStyle("-fx-background-color: " + UIUtil.SUCCESS_COLOR +
                    "; -fx-text-fill: white; -fx-font-size: 14px;" +
                    "; -fx-background-radius: 8; -fx-cursor: hand;");
        }
    }

    private void handleConfirmOrder() {

        String address = addressArea.getText().trim();

        if (!ValidationUtil.isValidAddress(address)) {
            UIUtil.showAlert(Alert.AlertType.WARNING, "Alamat Tidak Valid",
                    "Masukkan alamat pengiriman yang valid (minimal 10 karakter)");
            return;
        }

        if (selectedProofFile == null) {
            UIUtil.showAlert(Alert.AlertType.WARNING, "Bukti Pembayaran",
                    "Silakan upload bukti pembayaran terlebih dahulu");
            return;
        }

        RadioButton selectedBank = (RadioButton) bankGroup.getSelectedToggle();
        String paymentMethod = selectedBank != null ? selectedBank.getText() : "Bank BCA";

        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            String orderId = DateUtil.generateOrderId();

            List<CartItem> orderItems = new ArrayList<>();
            for (CartItem item : cartItems) {
                CartItem orderItem = new CartItem(
                        item.getMenuItem(),
                        item.getQuantity(),
                        item.getDeliveryDate(),
                        item.getCustomizations(),
                        item.getSpecialNotes());
                orderItems.add(orderItem);
            }
            Order order = new Order(
                    orderId,
                    currentUser,
                    orderItems,
                    totalAmount,
                    address,
                    paymentMethod,
                    selectedProofFile.getAbsolutePath());

            currentUser.addToHistory(order);

            currentUser.clearCart();

            UIUtil.showAlert(Alert.AlertType.INFORMATION, "Pesanan Berhasil",
                    "Pesanan berhasil dibuat dengan ID: " + orderId +
                            "\nSilakan tunggu konfirmasi dari admin.");

            modalStage.close();
            cartView.refreshView();
            sceneManager.showHistoryView();
        }
    }
}