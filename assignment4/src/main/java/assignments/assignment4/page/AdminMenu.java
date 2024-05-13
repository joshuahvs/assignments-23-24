package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.List;
// import java.util.stream.Collectors;

// import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import javafx.collections.FXCollections;
// import javafx.geometry.HPos;
// import javafx.geometry.Insets;
import javafx.geometry.Pos;
// import javafx.print.PrintSides;
import javafx.scene.Scene;
// import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
// import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;
// import javafx.scene.text.Text;
// import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu {
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private static List<Restaurant> restoList = new ArrayList<>();
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();

    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

    @Override
    public Scene createBaseMenu() {
        VBox menuLayout = new VBox(20);
        Button tambahRestoranbtn = new Button("Tambah Restoran");
        tambahRestoranbtn.setOnAction(e -> stage.setScene(createAddRestaurantForm()));

        Button tambahMenuRestoranbtn = new Button("Tambah Menu Restoran");
        tambahMenuRestoranbtn.setOnAction(e-> stage.setScene(createAddMenuForm()));

        Button lihatDaftarRestoranbtn = new Button("Lihat Daftar Restoran");
        lihatDaftarRestoranbtn.setOnAction(e-> stage.setScene(createViewRestaurantsForm()));

        Button logOutbtn = new Button("Log Out");
        logOutbtn.setOnAction(e -> handleLogOut());

        menuLayout.getChildren().addAll(tambahRestoranbtn, tambahMenuRestoranbtn, lihatDaftarRestoranbtn, logOutbtn);
        menuLayout.setAlignment(Pos.CENTER);

        mainApp.addScene("adminMenu", scene);
        return new Scene(menuLayout, 400, 600);
    }

    private Scene createAddRestaurantForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah restoran
        VBox layout = new VBox(10);
        Label label = new Label("Restaurant Name:");
        TextField restaurantNameInput = new TextField();

        Button submit = new Button("Submit");
        submit.setOnAction(e-> handleTambahRestoran(restaurantNameInput.getText()));
        Button exit = new Button("Keluar");
        exit.setOnAction(e ->stage.setScene(scene));

        layout.getChildren().addAll(label, restaurantNameInput, submit, exit);
        layout.setAlignment(Pos.CENTER);
        addRestaurantScene = new Scene(layout, 400, 600);
        return addRestaurantScene;
    }

    private Scene createAddMenuForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah menu restoran
        VBox layout = new VBox(10);

        Label restaurantLabel = new Label("Restaurant Name:");
        TextField restaurantNameInput  = new TextField();

        Label menuLabel = new Label("Menu Item Name:");
        TextField itemNameInput = new TextField();

        Label priceLabel = new Label("Price:");
        TextField priceInput = new TextField();

        Button addMenuBtn = new Button("Add Menu Item");
        addMenuBtn.setOnAction(e -> handleTambahMenuRestoran(restaurantNameInput.getText(), itemNameInput.getText(), priceInput.getText()));

        Button exit = new Button("Keluar");
        exit.setOnAction(e ->stage.setScene(scene));

        layout.getChildren().addAll(restaurantLabel,restaurantNameInput,menuLabel,itemNameInput,priceLabel,priceInput,addMenuBtn,exit);
        layout.setAlignment(Pos.CENTER);

        addMenuScene =  new Scene(layout, 400, 600);
        return addMenuScene;
    }

    private Scene createViewRestaurantsForm() {
        // TODO: Implementasikan method ini untuk menampilkan page daftar restoran
        VBox layout = new VBox(10);

        layout.setAlignment(Pos.CENTER);
        Label restaurantLabel = new Label("Restaurant Name:");
        TextField restaurantNameInput = new TextField();
        restaurantNameInput.setPromptText("Enter restaurant name to view menu");
        Button searchBtn = new Button("Search");
        ListView<String> menuList = new ListView<>();
        searchBtn.setOnAction(e -> {
            String restoName = restaurantNameInput.getText();
            Restaurant currentResto = null;
            for (Restaurant resto : restoList){
                if (resto.getNama().equalsIgnoreCase(restoName)){
                    currentResto = resto;
                    break;
                }
            }
            List<String> results = new ArrayList<>();
            if (currentResto!=null){
                for (Menu menu: currentResto.getMenu()){
                    String menuToShow = menu.getNamaMakanan() + " - " + menu.getHarga();
                    results.add(menuToShow);
                }
            }else{
                showAlert("Error", "Error", "Restaurant not found!", AlertType.ERROR);
            }
            menuList.setItems(FXCollections.observableArrayList(results));
        });

        Button kembaliBtn = new Button("Kembali");
        kembaliBtn.setOnAction(e -> stage.setScene(scene));
        layout.getChildren().addAll(restaurantLabel, restaurantNameInput, searchBtn, new Label("Menu:"), menuList, kembaliBtn);
        viewRestaurantsScene = new Scene(layout, 400, 600);
        return viewRestaurantsScene;
    }

    private void handleTambahRestoran(String nama) {
        // TODO: Implementasi validasi isian nama Restoran
        String validName = getValidRestaurantName(nama);
        if (validName != null) {
            Restaurant resto = new Restaurant(nama);
            restoList.add(resto);
            showAlert("Message", "Message", "Restaurant successfully registered!", AlertType.INFORMATION);
        } else {
            showAlert("Error", null, "Restaurant cannot be registered!", AlertType.ERROR);
        }
    }

    private void handleTambahMenuRestoran(String restaurant, String itemName, String price) {
        //pricenya harusnya double !!!
        // TODO: Implementasi validasi isian menu Restoran
        double harga;
        try{
            harga = Double.parseDouble(price);
        } catch (Exception e){
            showAlert("Error", null, "Price must be a number", AlertType.ERROR);
            return;
        }
        Restaurant currentResto = null;
        for (Restaurant resto : restoList){
            if (resto.getNama().equalsIgnoreCase(restaurant)){
                currentResto = resto;
            }
        }
        if (currentResto!= null) {
            currentResto.addMenu(new Menu(itemName, harga));
            showAlert("Message", "Message", "Menu item added successfully", AlertType.INFORMATION);
        } else {
            showAlert("Error", "Error", "Restaurant not found!", AlertType.ERROR);
        }
    }

    private void handleLogOut(){
        Scene loginScene = mainApp.getScene("Login");
        stage.setScene(loginScene);
    }

    public static String getValidRestaurantName(String inputName) {
        String name = "";
        boolean isRestaurantNameValid = false;
    
        while (!isRestaurantNameValid) {
            System.out.print("Nama: ");
            boolean isRestaurantExist = restoList.stream().anyMatch(restoran -> restoran.getNama().toLowerCase().equals(inputName.toLowerCase()));
            boolean isRestaurantNameLengthValid = inputName.length() >= 4;
            if (isRestaurantExist) {
                return null;
            } else if (!isRestaurantNameLengthValid) {
                return null;
            } else {
                name = inputName;
                isRestaurantNameValid = true;
            }
        }
        return name;
    }

    public static List<Restaurant> getRestaurants(){
        return restoList;
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}