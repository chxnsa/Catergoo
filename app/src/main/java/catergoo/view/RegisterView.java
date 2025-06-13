package catergoo.view;

import catergoo.manager.SessionManager;
import catergoo.model.User;
import catergoo.util.UIUtil;
import catergoo.util.ValidationUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Parent;

public class RegisterView {
    private SceneManager sceneManager;
    private StackPane root;
    private TextField usernameField;
    private PasswordField passwordField;

    public RegisterView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        initializeView();
    }

    private void initializeView() {
        // Create background with food image
        StackPane background = UIUtil.createWelcomeBackground("/images/background/food-bg.jpg");

        // Create register modal
        VBox registerModal = UIUtil.createModal(450, 500);

        // Title
        Label titleLabel = new Label("Daftar");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");

        // Username field
        VBox usernameContainer = new VBox(8);
        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        usernameField = UIUtil.createTextField("");
        usernameField.setPrefHeight(45);
        usernameField.setStyle("-fx-font-size: 14px; -fx-background-radius: 8;");

        Label usernameHint = new Label("Minimal 5 karakter");
        usernameHint.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        usernameContainer.getChildren().addAll(usernameLabel, usernameField, usernameHint);

        // Password field
        VBox passwordContainer = new VBox(8);
        Label passwordLabel = new Label("Kata Sandi");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        passwordField = UIUtil.createPasswordField("");
        passwordField.setPrefHeight(45);
        passwordField.setStyle("-fx-font-size: 14px; -fx-background-radius: 8;");

        Label passwordHint = new Label("Minimal 8 karakter dan terdapat minimal 3 angka");
        passwordHint.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        passwordContainer.getChildren().addAll(passwordLabel, passwordField, passwordHint);

        // Register button
        Button registerButton = new Button("Daftar");
        registerButton.setPrefSize(380, 50);
        registerButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 25; -fx-cursor: hand;");
        registerButton.setOnAction(e -> handleRegister());

        // Login link
        HBox loginContainer = new HBox(5);
        loginContainer.setAlignment(Pos.CENTER);

        Label loginPrompt = new Label("Sudah punya akun?");
        loginPrompt.setStyle("-fx-font-size: 14px;");

        Label loginLink = new Label("Masuk");
        loginLink.setStyle("-fx-font-size: 14px; -fx-text-fill: " + UIUtil.BLUE_LINK +
                "; -fx-cursor: hand; -fx-underline: true;");
        loginLink.setOnMouseClicked(e -> sceneManager.showLoginView());

        loginContainer.getChildren().addAll(loginPrompt, loginLink);

        // Add all elements to modal
        registerModal.getChildren().addAll(
                titleLabel,
                usernameContainer,
                passwordContainer,
                registerButton,
                loginContainer);

        // Center the modal
        registerModal.setAlignment(Pos.CENTER);

        // Create root with background and modal
        root = new StackPane();
        root.getChildren().addAll(background, registerModal);
        StackPane.setAlignment(registerModal, Pos.CENTER);

        // Add enter key support
        passwordField.setOnAction(e -> handleRegister());
    }

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Validate input
        if (username.isEmpty() || password.isEmpty()) {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.WARNING,
                    "Peringatan", "Username dan password harus diisi!");
            return;
        }

        if (!ValidationUtil.isValidUsername(username)) {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.WARNING,
                    "Username Tidak Valid", ValidationUtil.getUsernameErrorMessage());
            return;
        }

        if (!ValidationUtil.isValidPassword(password)) {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.WARNING,
                    "Password Tidak Valid", ValidationUtil.getPasswordErrorMessage());
            return;
        }

        // Check if user already exists
        if (SessionManager.isUserRegistered(username)) {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.WARNING,
                    "Username Sudah Digunakan", "Silakan pilih username yang lain!");
            return;
        }

        // Create new user
        User newUser = new User(username, password);
        boolean registerSuccess = SessionManager.registerUser(newUser);

        if (registerSuccess) {
            // Clear fields
            usernameField.clear();
            passwordField.clear();

            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.INFORMATION,
                    "Registrasi Berhasil", "Akun berhasil dibuat! Silakan login.");

            // Navigate to login
            sceneManager.showLoginView();
        } else {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Registrasi Gagal", "Terjadi kesalahan saat membuat akun!");
        }
    }

    public Parent getView() {
        return root;
    }
}