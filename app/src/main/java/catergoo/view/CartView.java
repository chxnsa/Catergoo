package catergoo.view;

import catergoo.view.component.CartItemRow;
import catergoo.view.component.NavigationBar;
import catergoo.manager.SessionManager;
import catergoo.model.CartItem;
import catergoo.model.User;
import catergoo.util.UIUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import java.util.List;

public class CartView {
    private SceneManager sceneManager;
    private BorderPane root;
    private VBox cartItemsContainer;
    private Label totalLabel;
    private Button checkoutButton;

    private PaymentView paymentView;

    public CartView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.paymentView = new PaymentView(sceneManager, this);
        initializeView();
    }

    private void initializeView() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: " + UIUtil.LIGHT_GRAY + ";");

        NavigationBar navbar = new NavigationBar("Keranjang");
        navbar.setOnHomeClick(() -> sceneManager.showHomeView());
        navbar.setOnKeranjangClick(() -> sceneManager.showCartView());
        navbar.setOnRiwayatClick(() -> sceneManager.showHistoryView());
        navbar.setOnLogoutClick(() -> sceneManager.logout());

        root.setTop(navbar.getNavBar());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + UIUtil.LIGHT_GRAY + "; -fx-background-color: transparent;");

        cartItemsContainer = new VBox(15);
        cartItemsContainer.setPadding(new Insets(30, 30, 20, 30)); // Top, Right, Bottom, Left

        scrollPane.setContent(cartItemsContainer);

        root.setCenter(scrollPane);

        VBox bottomSection = createBottomSection();
        root.setBottom(bottomSection);
    }

    private VBox createBottomSection() {
        VBox bottomSection = new VBox(20);
        bottomSection.setPadding(new Insets(30));
        bottomSection.setStyle("-fx-background-color: white; -fx-background-radius: 15;");

        HBox totalContainer = new HBox();
        totalContainer.setAlignment(Pos.CENTER_LEFT);
        totalContainer.setSpacing(15);

        Label totalTextLabel = new Label("Total Keseluruhan :");
        totalTextLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        totalLabel = new Label(UIUtil.formatCurrency(0));
        totalLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        totalContainer.getChildren().addAll(totalTextLabel, totalLabel);

        checkoutButton = new Button("Konfirmasi Pemesanan");
        checkoutButton.setPrefSize(300, 50);
        checkoutButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 25; -fx-cursor: hand;");
        checkoutButton.setOnAction(e -> handleCheckout());

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER_RIGHT);
        buttonContainer.getChildren().add(checkoutButton);

        bottomSection.getChildren().addAll(totalContainer, buttonContainer);

        return bottomSection;
    }

    private void loadCartItems() {
        cartItemsContainer.getChildren().clear();

        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            Label emptyLabel = new Label("Tidak ada pengguna yang login");
            emptyLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;");
            cartItemsContainer.getChildren().add(emptyLabel);
            updateTotal();
            return;
        }

        List<CartItem> cartItems = currentUser.getCurrentCart();

        if (cartItems.isEmpty()) {
            Label emptyLabel = new Label("Keranjang kosong");
            emptyLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;");
            cartItemsContainer.getChildren().add(emptyLabel);
        } else {
            for (CartItem item : cartItems) {
                CartItemRow row = new CartItemRow(item);
                row.setOnDeleteClick(() -> deleteCartItem(item));
                cartItemsContainer.getChildren().add(row.getRow());
            }
        }

        updateTotal();
    }

    private void deleteCartItem(CartItem item) {
        boolean confirm = UIUtil.showConfirmation("Hapus Item",
                "Apakah Anda yakin ingin menghapus item ini dari keranjang?");

        if (confirm) {
            User currentUser = SessionManager.getCurrentUser();
            if (currentUser != null) {
                currentUser.removeFromCart(item);
                refreshView();
            }
        }
    }

    private void updateTotal() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            totalLabel.setText(UIUtil.formatCurrency(0));
            checkoutButton.setDisable(true);
            return;
        }

        double total = 0;
        for (CartItem item : currentUser.getCurrentCart()) {
            total += item.getSubTotal();
        }

        totalLabel.setText(UIUtil.formatCurrency(total));
        checkoutButton.setDisable(currentUser.getCurrentCart().isEmpty());
    }

    private void handleCheckout() {
        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null || currentUser.getCurrentCart().isEmpty()) {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.WARNING,
                    "Keranjang Kosong", "Tidak ada item dalam keranjang");
            return;
        }

        paymentView.showPaymentView(currentUser.getCurrentCart());
    }

    public void refreshView() {
        loadCartItems();
    }

    public Parent getView() {
        return root;
    }
}