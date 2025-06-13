package catergoo.view;

import catergoo.util.UIUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

public class WelcomeView {
    private SceneManager sceneManager;
    private StackPane root;

    public WelcomeView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        initializeView();
    }

    private void initializeView() {
        // Create background with food image
        StackPane background = UIUtil.createWelcomeBackground("/images/background/food-bg.jpg");

        // Create main content
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(30));

        // Welcome title
        Label welcomeTitle = new Label("Welcome to Catergoo!");
        welcomeTitle.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: white;");
        welcomeTitle.setPadding(new Insets(10, 10, 10, 0));

        // Description text
        Label description1 = new Label("Nikmati kemudahan memesan");
        description1.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        Label description2 = new Label("hidangan lezat dan bergizi untuk");
        description2.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        Label description3 = new Label("berbagai acara spesial Anda.");
        description3.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");

        Label description4 = new Label("Pilih menu favorit, tentukan tanggal");
        description4.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-opacity: 0.9;");

        Label description5 = new Label("pengantaran, dan biarkan kami yang");
        description5.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-opacity: 0.9;");

        Label description6 = new Label("mengurus sisanya.");
        description6.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-opacity: 0.9;");

        // Action buttons
        VBox buttonContainer = new VBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(30, 0, 0, 0));

        Button loginButton = new Button("Masuk");
        loginButton.setPrefSize(200, 50);
        loginButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 25; -fx-cursor: hand;");
        loginButton.setOnAction(e -> sceneManager.showLoginView());

        Button registerButton = new Button("Daftar");
        registerButton.setPrefSize(200, 50);
        registerButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;" +
                "; -fx-font-size: 18px; -fx-font-weight: bold; -fx-border-color: white;" +
                "; -fx-border-width: 2; -fx-background-radius: 25; -fx-border-radius: 25;" +
                "; -fx-cursor: hand;");
        registerButton.setOnAction(e -> sceneManager.showRegisterView());

        buttonContainer.getChildren().addAll(loginButton, registerButton);

        // Add all elements to content
        content.getChildren().addAll(
                welcomeTitle,
                description1, description2, description3,
                description4, description5, description6);

        HBox welcomeContent = new HBox(30);
        welcomeContent.setAlignment(Pos.CENTER);

        welcomeContent.getChildren().addAll(
                content, buttonContainer);
        // Create root with background and content
        root = new StackPane();
        root.getChildren().addAll(background, welcomeContent);
        StackPane.setAlignment(welcomeContent, Pos.CENTER);
    }

    public Parent getView() {
        return root;
    }
}