package catergoo.view;

import catergoo.manager.MenuManager;
import catergoo.manager.SessionManager;
import catergoo.service.NotificationService;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

public class SceneManager {
    private static Stage primaryStage;
    private static SceneManager instance;

    private static final double DEFAULT_WIDTH = 1200;
    private static final double DEFAULT_HEIGHT = 800;
    private static final double WELCOME_WIDTH = 1400;
    private static final double WELCOME_HEIGHT = 900;

    private WelcomeView welcomeView;
    private LoginView loginView;
    private RegisterView registerView;
    private HomeView homeView;
    private CartView cartView;
    private HistoryView historyView;

    private SceneManager() {

    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;

        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(true);
    }

    public void showWelcomeView() {
        welcomeView = new WelcomeView(this);
        Scene scene = new Scene(welcomeView.getView(), WELCOME_WIDTH, WELCOME_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catergoo - Welcome");

        primaryStage.setResizable(false);
        primaryStage.setWidth(WELCOME_WIDTH);
        primaryStage.setHeight(WELCOME_HEIGHT);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void showLoginView() {
        loginView = new LoginView(this);
        Scene scene = new Scene(loginView.getView(), WELCOME_WIDTH, WELCOME_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());

        Platform.runLater(() -> {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Catergoo - Masuk");
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
        });
    }

    public void showRegisterView() {
        registerView = new RegisterView(this);
        Scene scene = new Scene(registerView.getView(), WELCOME_WIDTH, WELCOME_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());

        Platform.runLater(() -> {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Catergoo - Daftar");
            primaryStage.setResizable(false);
            primaryStage.centerOnScreen();
        });
    }

    public void showHomeView() {

        MenuManager.initializeDefaultMenu();

        NotificationService.getInstance().startService();

        homeView = new HomeView(this);
        Scene scene = new Scene(homeView.getView(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());

        Platform.runLater(() -> {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Catergoo - Home");

            primaryStage.setResizable(true);
            primaryStage.setWidth(DEFAULT_WIDTH);
            primaryStage.setHeight(DEFAULT_HEIGHT);
            primaryStage.centerOnScreen();
        });

        homeView.refreshView();
    }

    public void showCartView() {
        cartView = new CartView(this);
        Scene scene = new Scene(cartView.getView(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());

        Platform.runLater(() -> {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Catergoo - Keranjang");
            primaryStage.setResizable(true);
        });

        cartView.refreshView();
    }

    public void showHistoryView() {
        historyView = new HistoryView(this);
        Scene scene = new Scene(historyView.getView(), DEFAULT_WIDTH, DEFAULT_HEIGHT);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());

        Platform.runLater(() -> {
            primaryStage.setScene(scene);
            primaryStage.setTitle("Catergoo - Riwayat");
            primaryStage.setResizable(true);
        });

        historyView.refreshView();
    }

    public void logout() {

        NotificationService.getInstance().stopService();

        SessionManager.logoutUser();

        showWelcomeView();
    }

    public void exitApplication() {

        if (NotificationService.getInstance().isServiceRunning()) {
            NotificationService.getInstance().stopService();
        }

        if (SessionManager.getCurrentUser() != null) {
            SessionManager.logoutUser();
        }

        Platform.exit();
        System.exit(0);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}