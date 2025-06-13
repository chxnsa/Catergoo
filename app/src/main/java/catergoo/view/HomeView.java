package catergoo.view;

import catergoo.view.component.MenuItemCard;
import catergoo.view.component.NavigationBar;
import catergoo.manager.MenuManager;
import catergoo.model.MenuItem;
import catergoo.util.UIUtil;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;
import java.util.ArrayList;
import java.util.List;

public class HomeView {
    private SceneManager sceneManager;
    private BorderPane root;
    private FlowPane menuContainer;
    private List<Button> filterButtons;
    private String currentFilter = "Semua";

    private ItemDetailView itemDetailView;

    public HomeView(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
        this.itemDetailView = new ItemDetailView(sceneManager);
        initializeView();
    }

    private void initializeView() {
        root = new BorderPane();
        root.setStyle("-fx-background-color: " + UIUtil.LIGHT_GRAY + ";");

        NavigationBar navbar = new NavigationBar("Home");
        navbar.setOnHomeClick(() -> sceneManager.showHomeView());
        navbar.setOnKeranjangClick(() -> sceneManager.showCartView());
        navbar.setOnRiwayatClick(() -> sceneManager.showHistoryView());
        navbar.setOnLogoutClick(() -> sceneManager.logout());

        root.setTop(navbar.getNavBar());

        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));

        HBox filterContainer = createFilterButtons();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background: " + UIUtil.LIGHT_GRAY + "; -fx-background-color: transparent;");

        menuContainer = new FlowPane();
        menuContainer.setHgap(20);
        menuContainer.setVgap(20);
        menuContainer.setPadding(new Insets(20));
        menuContainer.setAlignment(Pos.TOP_LEFT);

        scrollPane.setContent(menuContainer);

        mainContent.getChildren().addAll(filterContainer, scrollPane);
        root.setCenter(mainContent);

        loadMenuItems();
    }

    private HBox createFilterButtons() {
        HBox filterContainer = new HBox(15);
        filterContainer.setAlignment(Pos.CENTER_LEFT);
        filterContainer.setPadding(new Insets(0, 20, 0, 20));

        filterButtons = new ArrayList<>();

        String[] filters = { "Semua", "Nasi Kotak", "Kotak Snack" };

        for (String filter : filters) {
            Button filterBtn = UIUtil.createFilterButton(filter, filter.equals(currentFilter));
            filterBtn.setOnAction(e -> {
                currentFilter = filter;
                updateFilterButtons();
                loadMenuItems();
            });
            filterButtons.add(filterBtn);
            filterContainer.getChildren().add(filterBtn);
        }

        return filterContainer;
    }

    private void updateFilterButtons() {
        for (Button btn : filterButtons) {
            boolean isActive = btn.getText().equals(currentFilter);
            btn.setStyle(isActive ? "-fx-background-color: " + UIUtil.PRIMARY_COLOR +
                    "; -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 14px;"
                    : "-fx-background-color: " + UIUtil.LIGHT_GRAY +
                            "; -fx-text-fill: black; -fx-background-radius: 20; -fx-font-size: 14px;");
        }
    }

    private void loadMenuItems() {
        menuContainer.getChildren().clear();

        List<MenuItem> menuItems = MenuManager.getMenuItemsByCategory(currentFilter);

        for (MenuItem item : menuItems) {
            MenuItemCard card = new MenuItemCard(item, 250);
            card.setOnCardClick(() -> showItemDetail(item));
            menuContainer.getChildren().add(card.getCard());
        }
    }

    private void showItemDetail(MenuItem menuItem) {
        itemDetailView.showItemDetail(menuItem);
    }

    public void refreshView() {
        loadMenuItems();
    }

    public Parent getView() {
        return root;
    }
}