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
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class WelcomeView {
    private SceneManager sceneManager;
    private StackPane root;

    public WelcomeView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        initializeView();
    }

    private void initializeView() {

        StackPane background = UIUtil.createWelcomeBackground("/images/background/food-bg.jpg");

        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER_LEFT);
        content.setPadding(new Insets(30));

        Label welcomeTitle = new Label("Welcome to Catergoo!");
        welcomeTitle.setStyle("-fx-font-size: 48px; -fx-font-weight: bold; -fx-text-fill: white;");
        welcomeTitle.setPadding(new Insets(10, 10, 10, 0));

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

        VBox buttonContainer = new VBox(15);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(30, 0, 0, 0));

        Button loginButton = new Button("Masuk");
        loginButton.setPrefSize(200, 50);
        loginButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 25; -fx-cursor: hand;");
        loginButton.setOnAction(e -> {

            fadeTransition(() -> sceneManager.showLoginView());
        });

        Button registerButton = new Button("Daftar");
        registerButton.setPrefSize(200, 50);
        registerButton.setStyle("-fx-background-color: transparent; -fx-text-fill: white;" +
                "; -fx-font-size: 18px; -fx-font-weight: bold; -fx-border-color: white;" +
                "; -fx-border-width: 2; -fx-background-radius: 25; -fx-border-radius: 25;" +
                "; -fx-cursor: hand;");
        registerButton.setOnAction(e -> {

            fadeTransition(() -> sceneManager.showRegisterView());
        });

        Button exitButton = new Button("Keluar");
        exitButton.setPrefSize(200, 50);
        exitButton.setStyle("-fx-background-color: rgba(244, 67, 54, 0.8); -fx-text-fill: white;" +
                "; -fx-font-size: 14px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 20; -fx-cursor: hand;");
        exitButton.setOnAction(e -> {
            boolean confirm = UIUtil.showConfirmation("Keluar Aplikasi",
                    "Apakah Anda yakin ingin keluar dari aplikasi?");
            if (confirm) {
                sceneManager.exitApplication();
            }
        });

        buttonContainer.getChildren().addAll(loginButton, registerButton, exitButton);

        content.getChildren().addAll(
                welcomeTitle,
                description1, description2, description3,
                description4, description5, description6);

        HBox welcomeContent = new HBox(30);
        welcomeContent.setAlignment(Pos.CENTER);
        welcomeContent.getChildren().addAll(content, buttonContainer);

        root = new StackPane();
        root.getChildren().addAll(background, welcomeContent);
        StackPane.setAlignment(welcomeContent, Pos.CENTER);

        welcomeContent.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(800), welcomeContent);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        fadeIn.play();
    }

    private void fadeTransition(Runnable action) {
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), root);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(e -> action.run());
        fadeOut.play();
    }

    public Parent getView() {
        return root;
    }
}