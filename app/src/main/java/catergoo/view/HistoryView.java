package catergoo.view;

import catergoo.view.component.NavigationBar;
import catergoo.manager.SessionManager;
import catergoo.model.Order;
import catergoo.model.User;
import catergoo.model.CartItem;
import catergoo.util.DateUtil;
import catergoo.util.UIUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.Parent;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
            // Sort orders by date (newest first)
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

        Label orderTotalLabel = new Label("Total: " + UIUtil.formatCurrency(order.getTotalAmount()));
        orderTotalLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: gray;");

        orderInfo.getChildren().addAll(orderIdLabel, orderDateLabel, orderTotalLabel);

        Label statusBadge = UIUtil.createStatusBadge(order.getStatus());

        headerContainer.getChildren().addAll(orderInfo, statusBadge);

        HBox itemsPreview = new HBox(10);
        itemsPreview.setAlignment(Pos.CENTER_LEFT);

        // Debug: Print order items count
        System.out.println("Order " + order.getOrderId() + " has " + order.getItems().size() + " items");

        if (order.getItems() != null && !order.getItems().isEmpty()) {
            // Get the first item from THIS specific order
            CartItem firstOrderItem = order.getItems().get(0);

            // Debug: Print item details
            System.out.println("First item: " + firstOrderItem.getMenuItem().getItemName());

            ImageView firstItemImage = UIUtil.createImageView(
                    firstOrderItem.getMenuItem().getImagePath(), 60, 60);
            if (firstItemImage.getImage() == null) {
                firstItemImage = UIUtil.createImageView("/images/placeholder/food-placeholder.jpg", 60, 60);
            }

            // Add rounded corners to image
            Rectangle clip = new Rectangle(60, 60);
            clip.setArcWidth(12);
            clip.setArcHeight(12);
            firstItemImage.setClip(clip);

            VBox itemDetails = new VBox(3);

            Label itemNameLabel = new Label(firstOrderItem.getMenuItem().getItemName());
            itemNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            String itemsText;
            if (order.getItems().size() == 1) {
                itemsText = "1 item";
            } else {
                int additionalItems = order.getItems().size() - 1;
                itemsText = "dan " + additionalItems + " item lainnya";
            }
            Label itemCountLabel = new Label(itemsText);
            itemCountLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

            itemDetails.getChildren().addAll(itemNameLabel, itemCountLabel);

            itemsPreview.getChildren().addAll(firstItemImage, itemDetails);
        } else {
            // Fallback if no items found
            Label noItemsLabel = new Label("Tidak ada item ditemukan");
            noItemsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");
            itemsPreview.getChildren().add(noItemsLabel);
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
        // Create modal stage for detail view
        Stage detailStage = new Stage();
        detailStage.initModality(Modality.APPLICATION_MODAL);
        detailStage.initOwner(sceneManager.getPrimaryStage());
        detailStage.setTitle("Detail Pesanan");
        detailStage.setResizable(false);

        VBox detailContainer = new VBox(20);
        detailContainer.setPadding(new Insets(25));
        detailContainer.setStyle("-fx-background-color: white;");
        detailContainer.setPrefWidth(600);

        // Header
        Label titleLabel = new Label("Detail Pesanan " + order.getOrderId());
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        // Order info
        VBox orderInfoSection = new VBox(8);

        Label statusLabel = new Label("Status: ");
        statusLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        HBox statusContainer = new HBox(10);
        statusContainer.setAlignment(Pos.CENTER_LEFT);
        statusContainer.getChildren().addAll(statusLabel, UIUtil.createStatusBadge(order.getStatus()));

        Label dateLabel = new Label("Tanggal Pesanan: " + DateUtil.formatDateTime(order.getOrderDate()));
        dateLabel.setStyle("-fx-font-size: 14px;");

        Label addressLabel = new Label("Alamat Pengiriman:");
        addressLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        Label addressValueLabel = new Label(order.getDeliveryAddress());
        addressValueLabel.setStyle("-fx-font-size: 14px;");
        addressValueLabel.setWrapText(true);

        Label paymentLabel = new Label("Metode Pembayaran: " + order.getPaymentMethod());
        paymentLabel.setStyle("-fx-font-size: 14px;");

        orderInfoSection.getChildren().addAll(statusContainer, dateLabel, addressLabel, addressValueLabel,
                paymentLabel);

        Label itemsHeaderLabel = new Label("Item Pesanan:");
        itemsHeaderLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        ScrollPane itemsScrollPane = new ScrollPane();
        itemsScrollPane.setFitToWidth(true);
        itemsScrollPane.setPrefHeight(200);
        itemsScrollPane.setStyle("-fx-background-color: transparent;");

        VBox itemsList = new VBox(10);
        itemsList.setPadding(new Insets(10));

        System.out.println("Detail view showing " + order.getItems().size() + " items for order " + order.getOrderId());

        for (CartItem item : order.getItems()) {

            System.out.println("Item: " + item.getMenuItem().getItemName() + " - Qty: " + item.getQuantity());

            HBox itemRow = new HBox(15);
            itemRow.setAlignment(Pos.CENTER_LEFT);
            itemRow.setPadding(new Insets(10));
            itemRow.setStyle("-fx-background-color: " + UIUtil.LIGHT_GRAY + "; -fx-background-radius: 8;");

            ImageView itemImage = UIUtil.createImageView(item.getMenuItem().getImagePath(), 50, 50);
            if (itemImage.getImage() == null) {
                itemImage = UIUtil.createImageView("/images/placeholder/food-placeholder.jpg", 50, 50);
            }

            Rectangle clip = new Rectangle(50, 50);
            clip.setArcWidth(10);
            clip.setArcHeight(10);
            itemImage.setClip(clip);

            VBox itemInfo = new VBox(3);
            HBox.setHgrow(itemInfo, Priority.ALWAYS);

            Label itemNameLabel = new Label(item.getMenuItem().getItemName());
            itemNameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

            Label quantityLabel = new Label("Jumlah: " + item.getQuantity() + " pax");
            quantityLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

            Label deliveryDateLabel = new Label("Tanggal: " + DateUtil.formatDate(item.getDeliveryDate()));
            deliveryDateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

            if (item.getCustomizations() != null && !item.getCustomizations().isEmpty()) {
                Label customLabel = new Label("Kustom: " + item.getCustomizations());
                customLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");
                customLabel.setWrapText(true);
                itemInfo.getChildren().add(customLabel);
            }

            if (item.getSpecialNotes() != null && !item.getSpecialNotes().isEmpty()) {
                Label notesLabel = new Label("Catatan: " + item.getSpecialNotes());
                notesLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");
                notesLabel.setWrapText(true);
                itemInfo.getChildren().add(notesLabel);
            }

            itemInfo.getChildren().addAll(itemNameLabel, quantityLabel, deliveryDateLabel);

            Label priceLabel = new Label(UIUtil.formatCurrency(item.getSubTotal()));
            priceLabel.setStyle(
                    "-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

            itemRow.getChildren().addAll(itemImage, itemInfo, priceLabel);
            itemsList.getChildren().add(itemRow);
        }

        itemsScrollPane.setContent(itemsList);

        // Total
        HBox totalContainer = new HBox();
        totalContainer.setAlignment(Pos.CENTER_RIGHT);
        totalContainer.setPadding(new Insets(10, 0, 0, 0));

        Label totalLabel = new Label("Total: " + UIUtil.formatCurrency(order.getTotalAmount()));
        totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        totalContainer.getChildren().add(totalLabel);

        // Close button
        Button closeButton = new Button("Tutup");
        closeButton.setPrefSize(150, 40);
        closeButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 20; -fx-cursor: hand;");
        closeButton.setOnAction(e -> detailStage.close());

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().add(closeButton);

        detailContainer.getChildren().addAll(
                titleLabel,
                orderInfoSection,
                itemsHeaderLabel,
                itemsScrollPane,
                totalContainer,
                buttonContainer);

        Scene detailScene = new Scene(detailContainer);
        detailStage.setScene(detailScene);
        detailStage.showAndWait();
    }

    public void refreshView() {
        loadOrderHistory();
    }

    public Parent getView() {
        return root;
    }
}