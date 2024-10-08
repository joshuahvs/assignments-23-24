package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu {
    //Atribut
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private static List<Restaurant> restoList = new ArrayList<>();
    private MainApp mainApp; 
    private ListView<String> menuItemsListView = new ListView<>();

    //constructor
    public AdminMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

    //Base menu yang akan ditampilkan pertama kali
    @Override
    public Scene createBaseMenu() {
        VBox menuLayout = new VBox(30);
        menuLayout.setStyle("-fx-background-color: black;");

        //text welcome
        Text welcomeText = new Text("Welcome, " + user.getNama() + "!");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 30));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        addShadow(welcomeText);

        //button untuk tambah restoran
        Button tambahRestoranbtn = new Button("Tambah Restoran");
        tambahRestoranbtn.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        tambahRestoranbtn.setOnAction(e -> stage.setScene(createAddRestaurantForm()));

        //button untuk tambah menu restoran
        Button tambahMenuRestoranbtn = new Button("Tambah Menu Restoran");
        tambahMenuRestoranbtn.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        tambahMenuRestoranbtn.setOnAction(e -> stage.setScene(createAddMenuForm()));

        //button untuk lihat daftar menu restoran
        Button lihatDaftarRestoranbtn = new Button("Lihat Daftar Restoran");
        tambahMenuRestoranbtn.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        lihatDaftarRestoranbtn.setOnAction(e -> stage.setScene(createViewRestaurantsForm()));

        //button untuk log out
        Button logOutbtn = new Button("Log Out");
        logOutbtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        logOutbtn.setOnAction(e -> handleLogOut());

        // menambahkan transisi effect pada button
        addFadeTransition(tambahRestoranbtn);
        addFadeTransition(tambahMenuRestoranbtn);
        addFadeTransition(lihatDaftarRestoranbtn);
        addFadeTransition(logOutbtn);

        //menambahkan hovereffect pada button
        addHoverEffect(tambahRestoranbtn);
        addHoverEffect(tambahMenuRestoranbtn);
        addHoverEffect(lihatDaftarRestoranbtn);
        addHoverEffect(logOutbtn);


        //menambahkan button dan teks ke menu layout
        menuLayout.getChildren().addAll(welcomeText, tambahRestoranbtn, tambahMenuRestoranbtn, lihatDaftarRestoranbtn,
                logOutbtn);
        menuLayout.setAlignment(Pos.CENTER);

        //menyimpan scene
        mainApp.addScene("adminMenu", scene);
        return new Scene(menuLayout, 500, 600);
    }

    //Method untuk menampilkan page tambah restoran
    private Scene createAddRestaurantForm() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: black;");

        //Text welcome, label dan input restoran, dan button sumbit serta kembali
        Text welcomeText = new Text("Tambahkan Restoran!");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 20));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        addShadow(welcomeText);

        Label restaurantLabel = new Label("Restaurant Name:");
        restaurantLabel.setTextFill(Color.WHITE);

        TextField restaurantNameInput = new TextField();
        restaurantNameInput.setMaxWidth(300);

        Button submit = new Button("Submit");
        submit.setOnAction(e -> {handleTambahRestoran(restaurantNameInput.getText()); refresh(restaurantNameInput);});

        Button exit = new Button("Keluar");
        exit.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        exit.setOnAction(e -> stage.setScene(scene));

        //Menambahkan effect fade, dan hover untuk button
        addFadeTransition(restaurantLabel);
        addFadeTransition(restaurantNameInput);
        addFadeTransition(submit);
        addFadeTransition(exit);
        addHoverEffect(submit);
        addHoverEffect(exit);

        layout.getChildren().addAll(welcomeText, restaurantLabel, restaurantNameInput, submit, exit);
        layout.setAlignment(Pos.CENTER);
        addRestaurantScene = new Scene(layout, 500, 600);
        return addRestaurantScene;
    }

     // Method untuk menampilkan page tambah menu restoran
    private Scene createAddMenuForm() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: black;");

        //Welcome teks, label dan input restoran, label dan input menu, label dan input harga
        Text welcomeText = new Text("Tambahkan Menu!");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 20));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        addShadow(welcomeText);

        Label restaurantLabel = new Label("Restaurant Name:");
        restaurantLabel.setTextFill(Color.WHITE);
        TextField restaurantNameInput = new TextField();
        restaurantNameInput.setMaxWidth(300);

        Label menuLabel = new Label("Menu Item Name:");
        menuLabel.setTextFill(Color.WHITE);
        TextField itemNameInput = new TextField();
        itemNameInput.setMaxWidth(300);

        Label priceLabel = new Label("Price:");
        priceLabel.setTextFill(Color.WHITE);
        TextField priceInput = new TextField();
        priceInput.setMaxWidth(300);

        //Button tambah menu dan exit
        Button addMenuBtn = new Button("Add Menu Item");
        addMenuBtn.setOnAction(e -> {handleTambahMenuRestoran(restaurantNameInput.getText(), itemNameInput.getText(),
                priceInput.getText()); refresh(priceInput); refresh(itemNameInput);});

        Button exit = new Button("Keluar");
        exit.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        exit.setOnAction(e -> stage.setScene(scene));

        //menambahkan transisi, dan 
        addFadeTransition(restaurantLabel);
        addFadeTransition(restaurantNameInput);
        addFadeTransition(menuLabel);
        addFadeTransition(itemNameInput);
        addFadeTransition(priceLabel);
        addFadeTransition(priceInput);
        addFadeTransition(addMenuBtn);
        addFadeTransition(exit);
        addHoverEffect(addMenuBtn);
        addHoverEffect(exit);

        //Menambahkan semuanya
        layout.getChildren().addAll(welcomeText, restaurantLabel, restaurantNameInput, menuLabel, itemNameInput,
                priceLabel, priceInput, addMenuBtn, exit);
        layout.setAlignment(Pos.CENTER);

        addMenuScene = new Scene(layout, 500, 600);
        return addMenuScene;
    }

    //Method untuk menampilkan page daftar restoran
    private Scene createViewRestaurantsForm() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: black;");
        layout.setAlignment(Pos.CENTER);

        //Welcome teks, label dan input restoran dan search button
        Text welcomeText = new Text("Melihat Restoran Terdaftar");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 20));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        addShadow(welcomeText);

        Label restaurantLabel = new Label("Restaurant Name:");
        restaurantLabel.setTextFill(Color.WHITE);
        TextField restaurantNameInput = new TextField();
        restaurantNameInput.setMaxWidth(300);

        Label menuLabel = new Label("Restaurant Name:");
        menuLabel.setTextFill(Color.WHITE);

        restaurantNameInput.setPromptText("Enter restaurant name to view menu");
        Button searchBtn = new Button("Search");
        //Mengiterasi restoran yang sesuai, dan menambahkan menunya ke dalam list results
        searchBtn.setOnAction(e -> {
            String restoName = restaurantNameInput.getText();
            Restaurant currentResto = null;
            for (Restaurant resto : restoList) {
                if (resto.getNama().equalsIgnoreCase(restoName)) {
                    currentResto = resto;
                    break;
                }
            }
            //results yang akan ditampilkan
            List<String> results = new ArrayList<>();
            if (currentResto != null) {
                for (Menu menu : currentResto.getMenu()) {
                    String menuToShow = menu.getNamaMakanan() + " - " + menu.getHarga();
                    results.add(menuToShow);
                }

                // Sorting list results
                Collections.sort(results, new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        String[] parts1 = o1.split(" - ");
                        String[] parts2 = o2.split(" - ");
                        double price1 = Double.parseDouble(parts1[1]);
                        double price2 = Double.parseDouble(parts2[1]);

                        // compare dari harga
                        int priceComparison = Double.compare(price1, price2);
                        if (priceComparison != 0) {
                            return priceComparison;
                        }

                       //compare secara alphabetical
                        return parts1[0].compareTo(parts2[0]);
                    }
                });
                //set items
                menuItemsListView.setItems(FXCollections.observableArrayList(results));
            } else {
                showAlert("Error", "Error", "Restaurant not found!", AlertType.ERROR);
                refresh(restaurantNameInput);
            }
        });
        menuItemsListView.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;");
        Button kembaliBtn = new Button("Kembali");
        kembaliBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        kembaliBtn.setOnAction(e -> {stage.setScene(scene); refresh(menuItemsListView);});

        //menambahkan effect
        addFadeTransition(restaurantLabel);
        addFadeTransition(restaurantNameInput);
        addFadeTransition(searchBtn);
        addFadeTransition(kembaliBtn);
        addHoverEffect(searchBtn);
        addHoverEffect(kembaliBtn);

        //menambahkan semuanya ke layout
        layout.getChildren().addAll(welcomeText, restaurantLabel, restaurantNameInput, searchBtn, menuLabel,
                menuItemsListView, kembaliBtn);
        viewRestaurantsScene = new Scene(layout, 500, 620);

        return viewRestaurantsScene;
    }

    //method untuk validasi nama restoran
    private void handleTambahRestoran(String nama) {
        // Menambahkan restoran jika valid dan menampilkan pesan yang sesuai
        String validName = getValidRestaurantName(nama);
        if (validName != null) {
            Restaurant resto = new Restaurant(nama);
            restoList.add(resto);
            showAlert("Message", "Message", "Restaurant successfully registered!", AlertType.INFORMATION);
        }
    }

    //Method untuk menambahkan menu ke restoran
    private void handleTambahMenuRestoran(String restaurant, String itemName, String price) {
        double harga;
        //cek apakah harganya angka 
        try {
            harga = Double.parseDouble(price);
        } catch (Exception e) {
            showAlert("Error", null, "Price must be a number", AlertType.ERROR);
            return;
        }
        //cek apakah restorannya tersedia
        Restaurant currentResto = null;
        for (Restaurant resto : restoList) {
            if (resto.getNama().equalsIgnoreCase(restaurant)) {
                currentResto = resto;
            }
        }
        //jika restoran tersedia
        if (currentResto != null) {
            currentResto.addMenu(new Menu(itemName, harga));
            showAlert("Message", "Message", "Menu item added successfully", AlertType.INFORMATION);
        //jika restoran tidak tersedia
        } else {
            showAlert("Error", "Error", "Restaurant not found!", AlertType.ERROR);
        }
    }

    //method untuk kembali ke login page
    private void handleLogOut() {
        Scene loginScene = mainApp.getScene("Login");
        stage.setScene(loginScene);
    }

    //method untuk mengechek apakah nama restoran valid
    public String getValidRestaurantName(String inputName) {
        String name = "";
        boolean isRestaurantNameValid = false;

        while (!isRestaurantNameValid) {
            System.out.print("Nama: ");
            boolean isRestaurantExist = restoList.stream()
                    .anyMatch(restoran -> restoran.getNama().toLowerCase().equals(inputName.toLowerCase()));
            boolean isRestaurantNameLengthValid = inputName.length() >= 4;
            if (isRestaurantExist) {
                showAlert("Error", null, "Restaurant already registered!", AlertType.ERROR);
                return null;
            } else if (!isRestaurantNameLengthValid) {
                showAlert("Error", null, "Restaurant name must be more than 4 letter", AlertType.ERROR);
                return null;
            } else {
                name = inputName;
                isRestaurantNameValid = true;
            }
        }
        return name;
    }

    //GETTER
    public static List<Restaurant> getRestaurants() {
        return restoList;
    }
    @Override
    public Scene getScene() {
        return this.scene;
    }
}
