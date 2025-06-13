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
import javafx.animation.FadeTransition;
import javafx.util.Duration;

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

        StackPane background = UIUtil.createWelcomeBackground("/images/background/food-bg.jpg");

        VBox registerModal = UIUtil.createModal(450, 550);

        Label titleLabel = new Label("Daftar");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: black;");

        VBox usernameContainer = new VBox(8);
        Label usernameLabel = new Label("Username");
        usernameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        usernameField = UIUtil.createTextField("");
        usernameField.setPrefHeight(45);
        usernameField.setStyle("-fx-font-size: 14px; -fx-background-radius: 8;");

        Label usernameHint = new Label("Minimal 5 karakter");
        usernameHint.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        usernameContainer.getChildren().addAll(usernameLabel, usernameField, usernameHint);

        VBox passwordContainer = new VBox(8);
        Label passwordLabel = new Label("Kata Sandi");
        passwordLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        passwordField = UIUtil.createPasswordField("");
        passwordField.setPrefHeight(45);
        passwordField.setStyle("-fx-font-size: 14px; -fx-background-radius: 8;");

        Label passwordHint = new Label("Minimal 8 karakter dan terdapat minimal 3 angka");
        passwordHint.setStyle("-fx-font-size: 12px; -fx-text-fill: gray;");

        passwordContainer.getChildren().addAll(passwordLabel, passwordField, passwordHint);

        Button registerButton = new Button("Daftar");
        registerButton.setPrefSize(380, 50);
        registerButton.setStyle("-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                "; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;" +
                "; -fx-background-radius: 25; -fx-cursor: hand;");
        registerButton.setOnAction(e -> handleRegister());

        HBox loginContainer = new HBox(5);
        loginContainer.setAlignment(Pos.CENTER);

        Label loginPrompt = new Label("Sudah punya akun?");
        loginPrompt.setStyle("-fx-font-size: 14px;");

        Label loginLink = new Label("Masuk");
        loginLink.setStyle("-fx-font-size: 14px; -fx-text-fill: " + UIUtil.BLUE_LINK +
                "; -fx-cursor: hand; -fx-underline: true;");
        loginLink.setOnMouseClicked(e -> {
            fadeTransition(() -> sceneManager.showLoginView());
        });

        loginContainer.getChildren().addAll(loginPrompt, loginLink);

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

        registerModal.getChildren().addAll(
                titleLabel,
                usernameContainer,
                passwordContainer,
                registerButton,
                loginContainer,
                navButtons);

        registerModal.setAlignment(Pos.CENTER);

        root = new StackPane();
        root.getChildren().addAll(background, registerModal);
        StackPane.setAlignment(registerModal, Pos.CENTER);

        passwordField.setOnAction(e -> handleRegister());

        registerModal.setOpacity(0);
        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), registerModal);
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

    private void handleRegister() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

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

        if (SessionManager.isUserRegistered(username)) {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.WARNING,
                    "Username Sudah Digunakan", "Silakan pilih username yang lain!");
            return;
        }

        User newUser = new User(username, password);
        boolean registerSuccess = SessionManager.registerUser(newUser);

        if (registerSuccess) {

            usernameField.clear();
            passwordField.clear();

            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.INFORMATION,
                    "Registrasi Berhasil", "Akun berhasil dibuat! Silakan login.");

            fadeTransition(() -> sceneManager.showLoginView());
        } else {
            UIUtil.showAlert(javafx.scene.control.Alert.AlertType.ERROR,
                    "Registrasi Gagal", "Terjadi kesalahan saat membuat akun!");
        }
    }

    public Parent getView() {
        return root;
    }
}