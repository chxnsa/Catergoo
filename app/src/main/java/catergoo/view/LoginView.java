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
        // Create background with food image
        StackPane background = UIUtil.createWelcomeBackground("/images/background/food-bg.jpg");

        // Create login modal
        VBox loginModal = UIUtil.createModal(450, 400);

        // Title
        Label titleLabel = new Label("Masuk");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Username field
        VBox usernameContainer = new VBox(8);
        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        usernameField = UIUtil.createTextField("");
        usernameField.setPrefHeight(45);
        usernameField.setStyle("-fx-font-size: 14px; -fx-background-radius: 8;");

        usernameContainer.getChildren().addAll(usernameLabel, usernameField);

        // Password field
        VBox passwordContainer = new VBox(8);
        Label passwordLabel = new Label("Kata Sandi");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        passwordField = UIUtil.createPasswordField("");
        passwordField.setPrefHeight(45);
        passwordField.setStyle("-fx-font-size: 14px; -fx-background-radius: 8;");

        passwordContainer.getChildren().addAll(passwordLabel, passwordField);

        // Login button
        Button loginButton = new Button("Masuk");
        loginButton.setPrefSize(380, 50);
        loginButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 25; -fx-cursor: hand;");
        loginButton.setOnAction(e -> handleLogin());

        // Register link
        HBox registerContainer = new HBox(5);
        registerContainer.setAlignment(Pos.CENTER);

        Label registerPrompt = new Label("Belum punya akun?");
        registerPrompt.setStyle("-fx-font-size: 14px;");

        Label registerLink = new Label("Daftar");
        registerLink.setStyle("-fx-font-size: 14px; -fx-text-fill: " + UIUtil.BLUE_LINK +
                "; -fx-cursor: hand; -fx-underline: true;");
        registerLink.setOnMouseClicked(e -> sceneManager.showRegisterView());

        registerContainer.getChildren().addAll(registerPrompt, registerLink);

        // Add all elements to modal
        loginModal.getChildren().addAll(
                titleLabel,
                usernameContainer,
                passwordContainer,
                loginButton,
                registerContainer);

        // Center the modal
        loginModal.setAlignment(Pos.CENTER);

        // Create root with background and modal
        root = new StackPane();
        root.getChildren().addAll(background, loginModal);
        StackPane.setAlignment(loginModal, Pos.CENTER);

        // Add enter key support
        passwordField.setOnAction(e -> handleLogin());
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
            // Clear fields
            usernameField.clear();
            passwordField.clear();

            // Navigate to home
            sceneManager.showHomeView();
        } else {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Login Gagal", "Username atau password salah!");
        }
    }

    public Parent getView() {
        return root;
    }
}