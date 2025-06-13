package catergoo;

import catergoo.view.SceneManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class CatergooApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {

            primaryStage.setTitle("Catergoo - Catering Application");
            primaryStage.setMinWidth(1200);
            primaryStage.setMinHeight(800);
            primaryStage.setResizable(true);
            primaryStage.centerOnScreen();

            SceneManager.setPrimaryStage(primaryStage);
            SceneManager sceneManager = SceneManager.getInstance();

            sceneManager.showWelcomeView();

            primaryStage.setOnCloseRequest(e -> {
                catergoo.service.NotificationService.getInstance().stopService();
                System.exit(0);
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}