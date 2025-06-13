package catergoo.view.component;

import catergoo.util.UIUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class NavigationBar {
    private HBox navbar;
    private String activeTab;
    private Runnable onHomeClick;
    private Runnable onKeranjangClick;
    private Runnable onRiwayatClick;
    private Runnable onLogoutClick;

    public NavigationBar(String activeTab) {
        this.activeTab = activeTab;
        initializeNavBar();
    }

    private void initializeNavBar() {
        navbar = new HBox();
        navbar.setAlignment(Pos.CENTER_LEFT);
        navbar.setPadding(new Insets(15, 30, 15, 30));
        navbar.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR + ";");
        navbar.setPrefHeight(60);

        // Create logo container for better positioning
        HBox logoContainer = new HBox();
        logoContainer.setAlignment(Pos.CENTER_LEFT);

        // Try to load logo image
        ImageView logoImage = UIUtil.createImageView("/images/logo/catergoo-logo.png", 240, 60);

        if (logoImage.getImage() != null) {
            // Logo loaded successfully
            logoImage.setPreserveRatio(true);
            logoImage.setSmooth(true);
            logoImage.setCache(true);
            logoContainer.getChildren().add(logoImage);
        } else {
            // Fallback to text logo if image not found
            Label logoText = new Label("Catergoo");
            logoText.setStyle("-fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");
            logoContainer.getChildren().add(logoText);
        }

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        HBox navItems = new HBox(40);
        navItems.setAlignment(Pos.CENTER);

        Label homeNav = createNavItem("Home", activeTab.equals("Home"));
        homeNav.setOnMouseClicked(e -> {
            if (onHomeClick != null)
                onHomeClick.run();
        });

        Label keranjangNav = createNavItem("Keranjang", activeTab.equals("Keranjang"));
        keranjangNav.setOnMouseClicked(e -> {
            if (onKeranjangClick != null)
                onKeranjangClick.run();
        });

        Label riwayatNav = createNavItem("Riwayat", activeTab.equals("Riwayat"));
        riwayatNav.setOnMouseClicked(e -> {
            if (onRiwayatClick != null)
                onRiwayatClick.run();
        });

        navItems.getChildren().addAll(homeNav, keranjangNav, riwayatNav);

        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 16px; -fx-cursor: hand;" +
                        "-fx-border-color: transparent; -fx-background-radius: 0;");
        logoutBtn.setOnAction(e -> {
            if (onLogoutClick != null)
                onLogoutClick.run();
        });

        // Add all elements to navbar
        navbar.getChildren().addAll(logoContainer, spacer1, navItems, spacer2, logoutBtn);
    }

    private Label createNavItem(String text, boolean isActive) {
        Label navItem = new Label(text);
        String baseStyle = "-fx-text-fill: white; -fx-font-size: 16px; -fx-cursor: hand;";
        String activeStyle = isActive ? " -fx-underline: true; -fx-font-weight: bold;" : "";
        navItem.setStyle(baseStyle + activeStyle);

        navItem.setOnMouseEntered(e -> {
            if (!isActive) {
                navItem.setStyle(baseStyle + " -fx-underline: true;");
            }
        });

        navItem.setOnMouseExited(e -> {
            navItem.setStyle(baseStyle + activeStyle);
        });

        return navItem;
    }

    public HBox getNavBar() {
        return navbar;
    }

    public void setOnHomeClick(Runnable onHomeClick) {
        this.onHomeClick = onHomeClick;
    }

    public void setOnKeranjangClick(Runnable onKeranjangClick) {
        this.onKeranjangClick = onKeranjangClick;
    }

    public void setOnRiwayatClick(Runnable onRiwayatClick) {
        this.onRiwayatClick = onRiwayatClick;
    }

    public void setOnLogoutClick(Runnable onLogoutClick) {
        this.onLogoutClick = onLogoutClick;
    }
}