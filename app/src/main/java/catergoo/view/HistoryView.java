package catergoo.view;

import catergoo.view.component.NavigationBar;
import catergoo.manager.SessionManager;
import catergoo.model.Order;
import catergoo.model.User;
import catergoo.util.DateUtil;
import catergoo.util.UIUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import java.util.List;

public class HistoryView {
    private SceneManager sceneManager;
    private BorderPane root;
    private VBox ordersContainer;

    public HistoryView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        initializeView();
    }

    private void initializeView() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: " + UIUtil.LIGHT_GRAY + ";");

        NavigationBar navbar = new NavigationBar("Riwayat");
        navbar.setOnHomeClick(() -> sceneManager.showHomeView());
        navbar.setOnKeranjangClick(() -> sceneManager.showCartView());
        navbar.setOnRiwayatClick(() -> sceneManager.showHistoryView());
        navbar.setOnLogoutClick(() -> sceneManager.logout());

        root.setTop(navbar.getNavBar());

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));

        Label titleLabel = new Label("Riwayat Pesanan");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: " + UIUtil.LIGHT_GRAY + "; -fx-background-color: transparent;");

        ordersContainer = new VBox(15);
        ordersContainer.setPadding(new Insets(20));

        scrollPane.setContent(ordersContainer);

        mainContent.getChildren().addAll(titleLabel, scrollPane);
        root.setCenter(mainContent);
    }

    private void loadOrderHistory() {
        ordersContainer.getChildren().clear();

        User currentUser = SessionManager.getCurrentUser();
        if (currentUser == null) {
            Label emptyLabel = new Label("Tidak ada pengguna yang login");
            emptyLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;");
            ordersContainer.getChildren().add(emptyLabel);
            return;
        }

        List<Order> orderHistory = currentUser.getOrderHistory();

        if (orderHistory.isEmpty()) {
            VBox emptyContainer = new VBox(20);
            emptyContainer.setAlignment(Pos.CENTER);
            emptyContainer.setPadding(new Insets(50));

            Label emptyLabel = new Label("Belum ada riwayat pesanan");
            emptyLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: gray;");

            Button shopButton = new Button("Mulai Berbelanja");
            shopButton.setPrefSize(200, 40);
            shopButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                    "; -fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;" +
                    "; -fx-background-radius: 20; -fx-cursor: hand;");
            shopButton.setOnAction(e -> sceneManager.showHomeView());

            emptyContainer.getChildren().addAll(emptyLabel, shopButton);
            ordersContainer.getChildren().add(emptyContainer);
        } else {

            orderHistory.sort((o1, o2) -> o2.getOrderDate().compareTo(o1.getOrderDate()));

            for (Order order : orderHistory) {
                VBox orderCard = createOrderCard(order);
                ordersContainer.getChildren().add(orderCard);
            }
        }
    }

    private VBox createOrderCard(Order order) {
        VBox orderCard = new VBox(15);
        orderCard.setPadding(new Insets(20));
        orderCard.setStyle("-fx-background-color: white; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);");

        HBox headerContainer = new HBox();
        headerContainer.setAlignment(Pos.CENTER_LEFT);
        headerContainer.setSpacing(15);

        VBox orderInfo = new VBox(5);
        HBox.setHgrow(orderInfo, Priority.ALWAYS);

        Label orderIdLabel = new Label(order.getOrderId());
        orderIdLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label orderDateLabel = new Label("Tanggal: " + DateUtil.formatDateTime(order.getOrderDate()));
        orderDateLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        Label orderTotalLabel = new Label("SubTotal: " + UIUtil.formatCurrency(order.getTotalAmount()));
        orderTotalLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        orderInfo.getChildren().addAll(orderIdLabel, orderDateLabel, orderTotalLabel);

        Label statusBadge = UIUtil.createStatusBadge(order.getStatus());

        headerContainer.getChildren().addAll(orderInfo, statusBadge);

        HBox itemsPreview = new HBox(10);
        itemsPreview.setAlignment(Pos.CENTER_LEFT);

        if (!order.getItems().isEmpty()) {
            ImageView firstItemImage = UIUtil.createImageView(
                    order.getItems().get(0).getMenuItem().getImagePath(), 60, 60);
            if (firstItemImage.getImage() == null) {
                firstItemImage = UIUtil.createImageView("/images/placeholder/food-placeholder.jpg", 60, 60);
            }
            firstItemImage.setStyle("-fx-background-radius: 8;");

            VBox itemDetails = new VBox(3);

            Label itemNameLabel = new Label(order.getItems().get(0).getMenuItem().getItemName());
            itemNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            String itemsText = order.getItems().size() == 1 ? "1 item" : order.getItems().size() + " items";
            Label itemCountLabel = new Label(itemsText);
            itemCountLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

            itemDetails.getChildren().addAll(itemNameLabel, itemCountLabel);

            itemsPreview.getChildren().addAll(firstItemImage, itemDetails);
        }

        HBox actionContainer = new HBox();
        actionContainer.setAlignment(Pos.CENTER_RIGHT);

        Button detailButton = new Button("Lihat Detail");
        detailButton.setPrefSize(100, 30);
        detailButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 12px;" +
                "; -fx-background-radius: 15; -fx-cursor: hand;");
        detailButton.setOnAction(e -> showOrderDetail(order));

        actionContainer.getChildren().add(detailButton);

        orderCard.getChildren().addAll(headerContainer, itemsPreview, actionContainer);

        return orderCard;
    }

    private void showOrderDetail(Order order) {

        VBox detailContainer = new VBox(15);
        detailContainer.setPadding(new Insets(20));
        detailContainer.setStyle("-fx-background-color: white;");

        Label titleLabel = new Label("Detail Pesanan " + order.getOrderId());
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label statusLabel = new Label("Status: " + order.getStatus());
        statusLabel.setStyle("-fx-font-size: 16px;");

        Label dateLabel = new Label("Tanggal Pesanan: " + DateUtil.formatDateTime(order.getOrderDate()));
        dateLabel.setStyle("-fx-font-size: 14px;");

        Label addressLabel = new Label("Alamat Pengiriman: " + order.getDeliveryAddress());
        addressLabel.setStyle("-fx-font-size: 14px;");
        addressLabel.setWrapText(true);

        Label paymentLabel = new Label("Metode Pembayaran: " + order.getPaymentMethod());
        paymentLabel.setStyle("-fx-font-size: 14px;");

        Label itemsHeaderLabel = new Label("Items Pesanan:");
        itemsHeaderLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox itemsList = new VBox(10);
        for (var item : order.getItems()) {
            HBox itemRow = new HBox(10);
            itemRow.setAlignment(Pos.CENTER_LEFT);

            Label itemLabel = new Label(item.getMenuItem().getItemName() +
                    " (" + item.getQuantity() + " pax) - " +
                    UIUtil.formatCurrency(item.getSubTotal()));
            itemLabel.setStyle("-fx-font-size: 14px;");

            itemRow.getChildren().add(itemLabel);
            itemsList.getChildren().add(itemRow);
        }

        Label totalLabel = new Label("Total: " + UIUtil.formatCurrency(order.getTotalAmount()));
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        detailContainer.getChildren().addAll(
                titleLabel, statusLabel, dateLabel, addressLabel, paymentLabel,
                itemsHeaderLabel, itemsList, totalLabel);

        ScrollPane scrollPane = new ScrollPane(detailContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefSize(600, 500);

        UIUtil.showAlert(javafx.scene.control.Alert.AlertType.INFORMATION,
                "Detail Pesanan", detailContainer.toString());
    }

    public void refreshView() {
        loadOrderHistory();
    }

    public Parent getView() {
        return root;
    }
}