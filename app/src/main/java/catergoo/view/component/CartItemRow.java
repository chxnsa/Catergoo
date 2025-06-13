package catergoo.view.component;

import catergoo.model.CartItem;
import catergoo.util.DateUtil;
import catergoo.util.UIUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class CartItemRow {
    private HBox row;
    private CartItem cartItem;
    private Runnable onDeleteClick;

    public CartItemRow(CartItem cartItem) {
        this.cartItem = cartItem;
        initializeRow();
    }

    private void initializeRow() {
        row = new HBox(15);
        row.setPadding(new Insets(15));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: white; -fx-background-radius: 10;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        ImageView itemImage = UIUtil.createImageView(cartItem.getMenuItem().getImagePath(), 80, 80);
        if (itemImage.getImage() == null) {
            itemImage = UIUtil.createImageView("/images/placeholder/food-placeholder.jpg", 80, 80);
        }
        itemImage.setStyle("-fx-background-radius: 8;");

        VBox itemInfo = new VBox(5);
        HBox.setHgrow(itemInfo, Priority.ALWAYS);

        Label nameLabel = new Label(cartItem.getMenuItem().getItemName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label dateLabel = new Label("Tanggal: " + DateUtil.formatDate(cartItem.getDeliveryDate()));
        dateLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        if (cartItem.getCustomizations() != null && !cartItem.getCustomizations().isEmpty()) {
            Label customLabel = new Label("Kustom: " + cartItem.getCustomizations());
            customLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");
            customLabel.setWrapText(true);
            itemInfo.getChildren().add(customLabel);
        }

        Label quantityLabel = new Label("Jumlah Porsi: " + cartItem.getQuantity() + " pax");
        quantityLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        if (cartItem.getSpecialNotes() != null && !cartItem.getSpecialNotes().isEmpty()) {
            Label notesLabel = new Label("Catatan: " + cartItem.getSpecialNotes());
            notesLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");
            notesLabel.setWrapText(true);
            itemInfo.getChildren().add(notesLabel);
        }

        Label subtotalLabel = new Label(UIUtil.formatCurrency(cartItem.getSubTotal()));
        subtotalLabel
                .setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        itemInfo.getChildren().addAll(nameLabel, dateLabel, quantityLabel, subtotalLabel);

        VBox actionButtons = new VBox(5);
        actionButtons.setAlignment(Pos.CENTER_RIGHT);

        Button deleteBtn = UIUtil.createButton("Hapus", 80, "secondary-button");
        deleteBtn.setStyle("-fx-background-color: " + UIUtil.ERROR_COLOR +
                "; -fx-text-fill: white; -fx-background-radius: 5;");
        deleteBtn.setOnAction(e -> {
            if (onDeleteClick != null)
                onDeleteClick.run();
        });

        actionButtons.getChildren().addAll(deleteBtn);

        row.getChildren().addAll(itemImage, itemInfo, actionButtons);
    }

    public HBox getRow() {
        return row;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setOnDeleteClick(Runnable onDeleteClick) {
        this.onDeleteClick = onDeleteClick;
    }
}