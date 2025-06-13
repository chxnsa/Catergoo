package catergoo.view.component;

import catergoo.model.MenuItem;
import catergoo.util.UIUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

public class MenuItemCard {
    private VBox card;
    private MenuItem menuItem;
    private Runnable onCardClick;

    public MenuItemCard(MenuItem menuItem, double cardWidth) {
        this.menuItem = menuItem;
        initializeCard(cardWidth);
    }

    private void initializeCard(double cardWidth) {
        card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(cardWidth);
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2); " +
                        "-fx-cursor: hand;");

        ImageView imageView = UIUtil.createImageView(menuItem.getImagePath(), cardWidth - 30, 150);
        if (imageView.getImage() == null) {
            imageView = UIUtil.createImageView("/images/placeholder/food-placeholder.jpg", cardWidth - 30, 150);
        }
        imageView.setStyle("-fx-background-radius: 8;");

        Label nameLabel = new Label(menuItem.getItemName());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: black;");
        nameLabel.setWrapText(true);

        HBox priceBox = new HBox();
        priceBox.setAlignment(Pos.CENTER_LEFT);
        priceBox.setSpacing(10);

        Label priceLabel = new Label(UIUtil.formatCurrency(menuItem.getPricePerPax()) + "/pax");
        priceLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + UIUtil.PRIMARY_COLOR + ";");

        String minOrderText = "min order " + menuItem.getMinOrder() +
                (menuItem.getClass().getSimpleName().equals("SnackPackage") ? "pcs" : "pax");
        Label minOrderLabel = new Label(minOrderText);
        minOrderLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        priceBox.getChildren().addAll(priceLabel, minOrderLabel);

        card.getChildren().addAll(imageView, nameLabel, priceBox);

        UIUtil.scaleOnHover(card);

        card.setOnMouseClicked(e -> {
            if (onCardClick != null) {
                onCardClick.run();
            }
        });
    }

    public VBox getCard() {
        return card;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setOnCardClick(Runnable onCardClick) {
        this.onCardClick = onCardClick;
    }
}