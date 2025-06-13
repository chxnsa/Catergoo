package catergoo.view;

import catergoo.manager.SessionManager;
import catergoo.util.UIUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

public class LoginView {
    private SceneManager sceneManager;
    private StackPane root;
    private TextField usernameField;
    private PasswordField passwordField;

    public LoginView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        initializeView();
    }

    private void initializeView() {

        StackPane background = UIUtil.createWelcomeBackground("/images/background/food-bg.jpg");

        VBox loginModal = UIUtil.createModal(450, 450);

        Label titleLabel = new Label("Masuk");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");

        VBox usernameContainer = new VBox(8);
        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        usernameField = UIUtil.createTextField("");
        usernameField.setPrefHeight(45);
        usernameField.setStyle("-fx-font-size: 14px; -fx-background-radius: 8;");

        usernameContainer.getChildren().addAll(usernameLabel, usernameField);

        VBox passwordContainer = new VBox(8);
        Label passwordLabel = new Label("Kata Sandi");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        passwordField = UIUtil.createPasswordField("");
        passwordField.setPrefHeight(45);
        passwordField.setStyle("-fx-font-size: 14px; -fx-background-radius: 8;");

        passwordContainer.getChildren().addAll(passwordLabel, passwordField);

        Button loginButton = new Button("Masuk");
        loginButton.setPrefSize(380, 50);
        loginButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 25; -fx-cursor: hand;");
        loginButton.setOnAction(e -> handleLogin());

        HBox registerContainer = new HBox(5);
        registerContainer.setAlignment(Pos.CENTER);

        Label registerPrompt = new Label("Belum punya akun?");
        registerPrompt.setStyle("-fx-font-size: 14px;");

        Label registerLink = new Label("Daftar");
        registerLink.setStyle("-fx-font-size: 14px; -fx-text-fill: " + UIUtil.BLUE_LINK +
                "; -fx-cursor: hand; -fx-underline: true;");
        registerLink.setOnMouseClicked(e -> {
            fadeTransition(() -> sceneManager.showRegisterView());
        });

        registerContainer.getChildren().addAll(registerPrompt, registerLink);

        HBox navButtons = new HBox(10);
        navButtons.setAlignment(Pos.CENTER);

        Button backButton = new Button("Kembali");
        backButton.setPrefSize(120, 35);
        backButton.setStyle("-fx-background-color: " + UIUtil.LIGHT_GRAY +
                "; -fx-text-fill: black; -fx-background-radius: 15;");
        backButton.setOnAction(e -> {
            fadeTransition(() -> sceneManager.showWelcomeView());
        });

        navButtons.getChildren().addAll(backButton);

        loginModal.getChildren().addAll(
                titleLabel,
                usernameContainer,
                passwordContainer,
                loginButton,
                registerContainer,
                navButtons);

        loginModal.setAlignment(Pos.CENTER);

        root = new StackPane();
        root.getChildren().addAll(background, loginModal);
        StackPane.setAlignment(loginModal, Pos.CENTER);

        passwordField.setOnAction(e -> handleLogin());

        loginModal.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), loginModal);
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

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.WARNING,
                    "Peringatan", "Username dan password harus diisi!");
            return;
        }

        boolean loginSuccess = SessionManager.loginUser(username, password);

        if (loginSuccess) {

            usernameField.clear();
            passwordField.clear();

            fadeTransition(() -> sceneManager.showHomeView());
        } else {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Login Gagal", "Username atau password salah!");
        }
    }

    public Parent getView() {
        return root;
    }
}