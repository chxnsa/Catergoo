package catergoo.view;

import catergoo.manager.MenuManager;
import catergoo.manager.SessionManager;
import catergoo.service.NotificationService;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {
    private static Stage primaryStage;
    private static SceneManager instance;

    // Views akan dibuat fresh setiap kali dibutuhkan
    private WelcomeView welcomeView;
    private LoginView loginView;
    private RegisterView registerView;
    private HomeView homeView;
    private CartView cartView;
    private HistoryView historyView;

    private SceneManager() {
        // Views will be created fresh each time to avoid reuse issues
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public void showWelcomeView() {
        welcomeView = new WelcomeView(this);
        Scene scene = new Scene(welcomeView.getView(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catergoo - Welcome");
        primaryStage.show();
    }

    public void showLoginView() {
        loginView = new LoginView(this);
        Scene scene = new Scene(loginView.getView(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catergoo - Masuk");
        primaryStage.show();
    }

    public void showRegisterView() {
        registerView = new RegisterView(this);
        Scene scene = new Scene(registerView.getView(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catergoo - Daftar");
        primaryStage.show();
    }

    public void showHomeView() {
        // Initialize menu if not done
        MenuManager.initializeDefaultMenu();

        // Start notification service
        NotificationService.getInstance().startService();

        homeView = new HomeView(this);
        Scene scene = new Scene(homeView.getView(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catergoo - Home");
        primaryStage.show();

        // Refresh home view
        homeView.refreshView();
    }

    public void showCartView() {
        cartView = new CartView(this);
        Scene scene = new Scene(cartView.getView(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catergoo - Keranjang");
        primaryStage.show();

        // Refresh cart view
        cartView.refreshView();
    }

    public void showHistoryView() {
        historyView = new HistoryView(this);
        Scene scene = new Scene(historyView.getView(), 1200, 800);
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Catergoo - Riwayat");
        primaryStage.show();

        // Refresh history view
        historyView.refreshView();
    }

    public void logout() {
        // Stop notification service
        NotificationService.getInstance().stopService();

        // Logout user
        SessionManager.logoutUser();

        // Show welcome view
        showWelcomeView();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }
}