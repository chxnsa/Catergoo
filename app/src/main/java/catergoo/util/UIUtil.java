package catergoo.util;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

public class UIUtil {

    public static final String PRIMARY_COLOR = "#D2691E";
    public static final String SECONDARY_COLOR = "#F5F5DC";
    public static final String ACCENT_COLOR = "#8B4513";
    public static final String SUCCESS_COLOR = "#4CAF50";
    public static final String ERROR_COLOR = "#F44336";
    public static final String WARNING_COLOR = "#FF9800";
    public static final String WHITE_COLOR = "#FFFFFF";
    public static final String LIGHT_GRAY = "#F5F5F5";
    public static final String BLUE_LINK = "#007BFF";

    public static final double TITLE_FONT_SIZE = 24.0;
    public static final double SUBTITLE_FONT_SIZE = 18.0;
    public static final double NORMAL_FONT_SIZE = 14.0;
    public static final double SMALL_FONT_SIZE = 12.0;

    public static Button createPrimaryButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("primary-button");
        button.setPrefWidth(120);
        button.setPrefHeight(35);
        return button;
    }

    public static Button createSecondaryButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("secondary-button");
        button.setPrefWidth(120);
        button.setPrefHeight(35);
        return button;
    }

    public static Button createButton(String text, double width, String styleClass) {
        Button button = new Button(text);
        button.setPrefWidth(width);
        button.setPrefHeight(35);
        if (styleClass != null && !styleClass.isEmpty()) {
            button.getStyleClass().add(styleClass);
        }
        return button;
    }

    public static Label createTitleLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("title-label");
        label.setFont(Font.font("System", FontWeight.BOLD, TITLE_FONT_SIZE));
        return label;
    }

    public static Label createSubtitleLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("subtitle-label");
        label.setFont(Font.font("System", FontWeight.SEMI_BOLD, SUBTITLE_FONT_SIZE));
        return label;
    }

    public static Label createLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("System", FontWeight.NORMAL, NORMAL_FONT_SIZE));
        return label;
    }

    public static TextField createTextField(String placeholder) {
        TextField textField = new TextField();
        textField.setPromptText(placeholder);
        textField.setPrefHeight(35);
        textField.getStyleClass().add("custom-text-field");
        return textField;
    }

    public static PasswordField createPasswordField(String placeholder) {
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText(placeholder);
        passwordField.setPrefHeight(35);
        passwordField.getStyleClass().add("custom-text-field");
        return passwordField;
    }

    public static TextArea createTextArea(String placeholder, int rows) {
        TextArea textArea = new TextArea();
        textArea.setPromptText(placeholder);
        textArea.setPrefRowCount(rows);
        textArea.setWrapText(true);
        textArea.getStyleClass().add("custom-text-area");
        return textArea;
    }

    public static <T> ComboBox<T> createComboBox() {
        ComboBox<T> comboBox = new ComboBox<>();
        comboBox.setPrefHeight(35);
        comboBox.getStyleClass().add("custom-combo-box");
        return comboBox;
    }

    public static Spinner<Integer> createIntegerSpinner(int min, int max, int initialValue) {
        Spinner<Integer> spinner = new Spinner<>(min, max, initialValue);
        spinner.setPrefHeight(35);
        spinner.setPrefWidth(100);
        spinner.setEditable(true);
        spinner.getStyleClass().add("custom-spinner");
        return spinner;
    }

    public static HBox createHBox(double spacing, Pos alignment, Node... children) {
        HBox hbox = new HBox(spacing);
        hbox.setAlignment(alignment);
        hbox.getChildren().addAll(children);
        return hbox;
    }

    public static VBox createVBox(double spacing, Pos alignment, Node... children) {
        VBox vbox = new VBox(spacing);
        vbox.setAlignment(alignment);
        vbox.getChildren().addAll(children);
        return vbox;
    }

    public static VBox createContainer(double padding, Node... children) {
        VBox container = new VBox(15);
        container.setPadding(new Insets(padding));
        container.getChildren().addAll(children);
        return container;
    }

    public static GridPane createGridPane(double hgap, double vgap) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(hgap);
        gridPane.setVgap(vgap);
        gridPane.setPadding(new Insets(20));
        return gridPane;
    }

    public static Image loadImage(String imagePath) {
        try {
            InputStream inputStream = UIUtil.class.getResourceAsStream(imagePath);
            if (inputStream != null) {
                return new Image(inputStream);
            } else {
                System.err.println("Image not found: " + imagePath);
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + imagePath + " - " + e.getMessage());
            return null;
        }
    }

    public static ImageView createImageView(String imagePath, double width, double height) {
        Image image = loadImage(imagePath);
        if (image != null) {
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(width);
            imageView.setFitHeight(height);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);
            return imageView;
        }
        return new ImageView();
    }

    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }

    public static String showInputDialog(String title, String message, String defaultValue) {
        TextInputDialog dialog = new TextInputDialog(defaultValue);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setContentText(message);

        Optional<String> result = dialog.showAndWait();
        return result.orElse(null);
    }

    public static File showImageFileChooser(Stage stage, String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp"));
        return fileChooser.showOpenDialog(stage);
    }

    public static String formatCurrency(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));
        return formatter.format(amount);
    }

    public static String formatNumber(double number) {
        NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        return formatter.format(number);
    }

    public static void fadeIn(Node node, Duration duration) {
        FadeTransition fadeTransition = new FadeTransition(duration, node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }

    public static void fadeOut(Node node, Duration duration) {
        FadeTransition fadeTransition = new FadeTransition(duration, node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.play();
    }

    public static void scaleOnHover(Node node) {
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(100), node);
        scaleIn.setToX(1.05);
        scaleIn.setToY(1.05);

        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), node);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        node.setOnMouseEntered(e -> scaleIn.play());
        node.setOnMouseExited(e -> scaleOut.play());
    }

    public static void addStyleClass(Node node, String styleClass, boolean condition) {
        if (condition) {
            node.getStyleClass().add(styleClass);
        } else {
            node.getStyleClass().remove(styleClass);
        }
    }

    public static void setBackgroundColor(Region region, String colorHex) {
        region.setStyle("-fx-background-color: " + colorHex + ";");
    }

    public static Separator createSeparator() {
        Separator separator = new Separator();
        separator.getStyleClass().add("custom-separator");
        return separator;
    }

    public static Region createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        VBox.setVgrow(spacer, Priority.ALWAYS);
        return spacer;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }

    public static void setTooltip(Node node, String text) {
        Tooltip tooltip = new Tooltip(text);
        tooltip.setShowDelay(Duration.millis(500));
        Tooltip.install(node, tooltip);
    }

    public static void centerStage(Stage stage) {
        stage.centerOnScreen();
    }

    public static void setupStage(Stage stage, String title, boolean resizable) {
        stage.setTitle(title);
        stage.setResizable(resizable);
        centerStage(stage);
    }

    public static HBox createNavigationBar(String activeTab) {
        HBox navbar = new HBox();
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(15, 30, 15, 30));
        navbar.setStyle("-fx-background-color: " + PRIMARY_COLOR + ";");
        navbar.setPrefHeight(60);

        Label logo = new Label("Catergoo");
        logo.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        HBox navItems = createHBox(40, Pos.CENTER,
                createNavItem("Home", activeTab.equals("Home")),
                createNavItem("Keranjang", activeTab.equals("Keranjang")),
                createNavItem("Riwayat", activeTab.equals("Riwayat")));

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-cursor: hand;");

        navbar.getChildren().addAll(logo, spacer1, navItems, spacer2, logoutBtn);
        return navbar;
    }

    private static Label createNavItem(String text, boolean isActive) {
        Label navItem = new Label(text);
        String baseStyle = "-fx-text-fill: white; -fx-font-size: 16px; -fx-cursor: hand;";
        String activeStyle = isActive ? " -fx-underline: true;" : "";
        navItem.setStyle(baseStyle + activeStyle);
        return navItem;
    }

    public static VBox createCard(double width, double height, Node... children) {
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setPrefSize(width, height);
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        card.getChildren().addAll(children);
        return card;
    }

    public static VBox createMenuItemCard(String imagePath, String itemName, String price, String minOrder,
            double cardWidth) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(15));
        card.setPrefWidth(cardWidth);
        card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0, 0, 2);");

        ImageView imageView = createImageView(imagePath, cardWidth - 30, 150);
        imageView.setStyle("-fx-background-radius: 8;");

        Label nameLabel = new Label(itemName);
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox priceBox = new HBox();
        priceBox.setAlignment(Pos.CENTER_LEFT);
        priceBox.setSpacing(10);

        Label priceLabel = new Label(price);
        priceLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: " + PRIMARY_COLOR + ";");

        Label minOrderLabel = new Label(minOrder);
        minOrderLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        priceBox.getChildren().addAll(priceLabel, minOrderLabel);

        card.getChildren().addAll(imageView, nameLabel, priceBox);
        return card;
    }

    public static VBox createModal(double width, double height, Node... children) {
        VBox modal = new VBox(20);
        modal.setPadding(new Insets(30));
        modal.setPrefSize(width, height);
        modal.setMaxSize(width, height);
        modal.setStyle(
                "-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 5);");
        modal.getChildren().addAll(children);
        return modal;
    }

    public static Button createFilterButton(String text, boolean isActive) {
        Button button = new Button(text);
        button.setPrefHeight(35);
        button.setPadding(new Insets(8, 20, 8, 20));

        if (isActive) {
            button.setStyle("-fx-background-color: " + PRIMARY_COLOR
                    + "; -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 14px;");
        } else {
            button.setStyle("-fx-background-color: " + LIGHT_GRAY
                    + "; -fx-text-fill: black; -fx-background-radius: 20; -fx-font-size: 14px;");
        }

        button.getStyleClass().add("filter-button");
        return button;
    }

    public static RadioButton createStyledRadioButton(String text) {
        RadioButton radioButton = new RadioButton(text);
        radioButton.setStyle("-fx-text-fill: black; -fx-font-size: 14px;");
        return radioButton;
    }

    public static VBox createFormField(String labelText, Node inputNode) {
        VBox field = new VBox(8);

        Label label = new Label(labelText);
        label.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        field.getChildren().addAll(label, inputNode);
        return field;
    }

    public static VBox createValidatedField(String labelText, TextField textField, String validationMessage) {
        VBox field = createFormField(labelText, textField);

        if (validationMessage != null && !validationMessage.isEmpty()) {
            Label validationLabel = new Label(validationMessage);
            validationLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");
            field.getChildren().add(validationLabel);
        }

        return field;
    }

    public static HBox createCartItemRow(String itemName, String details, String subtotal, ImageView itemImage) {
        HBox row = new HBox(15);
        row.setPadding(new Insets(15));
        row.setAlignment(Pos.CENTER_LEFT);
        row.setStyle("-fx-background-color: " + LIGHT_GRAY + "; -fx-background-radius: 10;");

        if (itemImage != null) {
            itemImage.setFitWidth(80);
            itemImage.setFitHeight(80);
            itemImage.setPreserveRatio(true);
        }

        VBox itemInfo = new VBox(5);

        Label nameLabel = createLabel(itemName);
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

        Label detailsLabel = createLabel(details);
        detailsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        Label subtotalLabel = createLabel(subtotal);
        subtotalLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: " + PRIMARY_COLOR + ";");

        itemInfo.getChildren().addAll(nameLabel, detailsLabel, subtotalLabel);
        VBox.setVgrow(itemInfo, Priority.ALWAYS);

        VBox actionButtons = new VBox(5);
        actionButtons.setAlignment(Pos.CENTER_RIGHT);

        Button editBtn = createButton("Edit", 80, "secondary-button");
        Button deleteBtn = createButton("Hapus", 80, "secondary-button");

        actionButtons.getChildren().addAll(editBtn, deleteBtn);

        if (itemImage != null) {
            row.getChildren().addAll(itemImage, itemInfo, actionButtons);
        } else {
            row.getChildren().addAll(itemInfo, actionButtons);
        }

        return row;
    }

    public static StackPane createBlurBackground(Node content, Node modal) {
        StackPane overlay = new StackPane();

        Region background = new Region();
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        overlay.getChildren().addAll(content, background, modal);
        StackPane.setAlignment(modal, Pos.CENTER);

        return overlay;
    }

    public static Label createLinkLabel(String text, String linkText) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 14px;");

        String fullText = text.replace(linkText, "");
        Label linkLabel = new Label(linkText);
        linkLabel.setStyle(
                "-fx-font-size: 14px; -fx-text-fill: " + BLUE_LINK + "; -fx-cursor: hand; -fx-underline: true;");

        return label;
    }

    public static Label createStatusBadge(String status) {
        Label badge = new Label(status);
        badge.setPadding(new Insets(5, 10, 5, 10));
        badge.setStyle("-fx-background-radius: 15; -fx-font-size: 12px; -fx-font-weight: bold;");

        switch (status.toLowerCase()) {
            case "menunggu konfirmasi":
                badge.setStyle(badge.getStyle() + "-fx-background-color: " + WARNING_COLOR + "; -fx-text-fill: white;");
                break;
            case "diproses":
                badge.setStyle(badge.getStyle() + "-fx-background-color: " + BLUE_LINK + "; -fx-text-fill: white;");
                break;
            case "sedang dikirim":
                badge.setStyle(badge.getStyle() + "-fx-background-color: " + PRIMARY_COLOR + "; -fx-text-fill: white;");
                break;
            case "selesai":
                badge.setStyle(badge.getStyle() + "-fx-background-color: " + SUCCESS_COLOR + "; -fx-text-fill: white;");
                break;
            case "dibatalkan":
                badge.setStyle(badge.getStyle() + "-fx-background-color: " + ERROR_COLOR + "; -fx-text-fill: white;");
                break;
            default:
                badge.setStyle(badge.getStyle() + "-fx-background-color: gray; -fx-text-fill: white;");
        }

        return badge;
    }

    public static StackPane createWelcomeBackground(String imagePath) {
        StackPane background = new StackPane();

        ImageView backgroundImage = new ImageView();
        Image image = loadImage(imagePath);
        if (image != null) {
            backgroundImage.setImage(image);
            backgroundImage.setPreserveRatio(false);
            backgroundImage.setFitWidth(1200);
            backgroundImage.setFitHeight(800);
        }

        Region overlay = new Region();
        overlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6);");

        background.getChildren().addAll(backgroundImage, overlay);
        return background;
    }
}