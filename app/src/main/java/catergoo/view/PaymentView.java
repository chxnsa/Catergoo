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
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.File;
import java.util.List;

public class PaymentView {
    private SceneManager sceneManager;
    private CartView cartView;
    private Stage modalStage;
    private List<CartItem> cartItems;
    private double totalAmount;

    // Form controls
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

        // Create main container - more compact and vertical
        VBox mainContainer = new VBox(15);
        mainContainer.setPadding(new Insets(20));
        mainContainer.setPrefSize(450, 600); // Even more compact
        mainContainer.setStyle("-fx-background-color: white;");
        mainContainer.setAlignment(Pos.TOP_CENTER);

        // Title
        Label titleLabel = new Label("Pembayaran");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        // Payment type section
        VBox paymentTypeSection = createPaymentTypeSection();

        // Bank selection section
        VBox bankSection = createBankSection();

        // Payment details section (more compact)
        VBox paymentDetailsSection = createPaymentDetailsSection();

        // Address section
        VBox addressSection = createAddressSection();

        // Upload section
        VBox uploadSection = createUploadSection();

        // Action buttons
        VBox buttonSection = createButtonSection();

        mainContainer.getChildren().addAll(
                titleLabel,
                paymentTypeSection,
                bankSection,
                paymentDetailsSection,
                addressSection,
                uploadSection,
                buttonSection);

        Scene scene = new Scene(mainContainer);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    private VBox createPaymentTypeSection() {
        VBox section = new VBox(8);

        paymentTypeGroup = new ToggleGroup();

        RadioButton fullPayment = UIUtil.createStyledRadioButton("Full Payment");
        fullPayment.setToggleGroup(paymentTypeGroup);
        fullPayment.setSelected(true);
        fullPayment.setOnAction(e -> updatePaymentAmount());

        RadioButton dpPayment = UIUtil.createStyledRadioButton("DP 30%");
        dpPayment.setToggleGroup(paymentTypeGroup);
        dpPayment.setOnAction(e -> updatePaymentAmount());

        section.getChildren().addAll(fullPayment, dpPayment);
        return section;
    }

    private VBox createBankSection() {
        VBox section = new VBox(12);

        Label bankLabel = new Label("Transfer Via");
        bankLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

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

        VBox radioContainer = new VBox(4);
        radioContainer.getChildren().addAll(bcaBank, mandiriBank, bniBank, dana);

        section.getChildren().addAll(bankLabel, radioContainer);
        return section;
    }

    private VBox createPaymentDetailsSection() {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        section.setPrefWidth(400);

        // Payment amount - fully vertical and centered
        VBox amountContainer = new VBox(3);
        amountContainer.setAlignment(Pos.CENTER);

        Label amountTextLabel = new Label("Nominal Pembayaran");
        amountTextLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #666;");

        paymentAmountLabel = new Label(UIUtil.formatCurrency(totalAmount));
        paymentAmountLabel
                .setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        amountContainer.getChildren().addAll(amountTextLabel, paymentAmountLabel);

        // Account number - fully vertical and centered
        VBox accountContainer = new VBox(3);
        accountContainer.setAlignment(Pos.CENTER);

        Label accountTextLabel = new Label("Rekening Tujuan");
        accountTextLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #666;");

        accountNumberLabel = new Label("BCA 098123765341");
        accountNumberLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        accountContainer.getChildren().addAll(accountTextLabel, accountNumberLabel);

        // Payment instructions - compact
        Label instructionsLabel = new Label("Tata Cara Pembayaran");
        instructionsLabel.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #666;");

        VBox instructionsContainer = new VBox(2);
        instructionsContainer.setPadding(new Insets(6));
        instructionsContainer.setStyle("-fx-background-color: " + UIUtil.LIGHT_GRAY + "; -fx-background-radius: 5;");
        instructionsContainer.setPrefWidth(400);

        Label instruction1 = new Label("1. Masukkan Nomor Rekening Tujuan");
        instruction1.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        Label instruction2 = new Label(
                "   Sistem akan menampilkan nomor rekening sesuai pilihan bank untuk transfer pembayaran.");
        instruction2.setStyle("-fx-font-size: 9px;");
        instruction2.setWrapText(true);

        Label instruction3 = new Label("2. Transfer Sesuai Nominal");
        instruction3.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        Label instruction4 = new Label(
                "   Lakukan transfer sejumlah total tagihan yang tertera, pastikan tidak kurang atau lebih. Simpan ...");
        instruction4.setStyle("-fx-font-size: 9px;");
        instruction4.setWrapText(true);

        Label instruction5 = new Label("3. Unggah Bukti Pembayaran");
        instruction5.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

        Label instruction6 = new Label(
                "   Setelah melakukan transfer, unggah tangkapan layar (screenshot) bukti pembayaran melalui fit...");
        instruction6.setStyle("-fx-font-size: 9px;");
        instruction6.setWrapText(true);

        Label finalNote = new Label(
                "Setelah melakukan pembayaran, tunggu maksimal 1x24 jam untuk konfirmasi pesanan anda.");
        finalNote.setStyle("-fx-font-size: 9px; -fx-font-style: italic;");
        finalNote.setWrapText(true);

        instructionsContainer.getChildren().addAll(
                instruction1, instruction2, instruction3, instruction4,
                instruction5, instruction6, finalNote);

        section.getChildren().addAll(amountContainer, accountContainer, instructionsLabel, instructionsContainer);
        return section;
    }

    private VBox createAddressSection() {
        VBox section = new VBox(6);
        section.setAlignment(Pos.CENTER);

        Label addressLabel = new Label("Alamat Pengiriman");
        addressLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");

        addressArea = UIUtil.createTextArea("Masukkan alamat lengkap pengiriman...", 2);
        addressArea.setPrefWidth(400);
        addressArea.setMaxWidth(400);

        section.getChildren().addAll(addressLabel, addressArea);
        return section;
    }

    private VBox createUploadSection() {
        VBox section = new VBox(6);
        section.setAlignment(Pos.CENTER);

        uploadButton = new Button("Upload Bukti Pembayaran ↑");
        uploadButton.setPrefSize(200, 32);
        uploadButton.setStyle("-fx-background-color: " + UIUtil.SECONDARY_COLOR +
                "; -fx-text-fill: " + UIUtil.ACCENT_COLOR + "; -fx-font-size: 12px;" +
                "; -fx-background-radius: 8; -fx-cursor: hand;");
        uploadButton.setOnAction(e -> handleFileUpload());

        section.getChildren().add(uploadButton);
        return section;
    }

    private VBox createButtonSection() {
        VBox buttonSection = new VBox(10);
        buttonSection.setAlignment(Pos.CENTER);

        Button cancelButton = new Button("Batal");
        cancelButton.setPrefSize(200, 32);
        cancelButton.setStyle("-fx-background-color: " + UIUtil.LIGHT_GRAY +
                "; -fx-text-fill: black; -fx-background-radius: 8;");
        cancelButton.setOnAction(e -> modalStage.close());

        Button confirmButton = new Button("Konfirmasi Pemesanan");
        confirmButton.setPrefSize(200, 32);
        confirmButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 12px; -fx-font-weight: bold;" +
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
                    "; -fx-text-fill: white; -fx-font-size: 13px;" +
                    "; -fx-background-radius: 8; -fx-cursor: hand;");
        }
    }

    private void handleConfirmOrder() {
        // Validate input
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

        // Get selected payment method
        RadioButton selectedBank = (RadioButton) bankGroup.getSelectedToggle();
        String paymentMethod = selectedBank != null ? selectedBank.getText() : "Bank BCA";

        // Create order
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser != null) {
            String orderId = DateUtil.generateOrderId();

            Order order = new Order(
                    orderId,
                    currentUser,
                    cartItems,
                    totalAmount,
                    address,
                    paymentMethod,
                    selectedProofFile.getAbsolutePath());

            // Add to user's order history
            currentUser.addToHistory(order);

            // Clear cart
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