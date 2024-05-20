package assignments.assignment4.components.form;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;

public class LoginForm {
    //Atribut
    private Stage stage;
    private MainApp mainApp; 
    private TextField nameInput;
    private TextField phoneInput;

    //constructor
    public LoginForm(Stage stage, MainApp mainApp) { 
        this.stage = stage;
        this.mainApp = mainApp;
    }

    //Method yang akan menampilkan scene untuk login
    public Scene createLoginForm() {
        //Mengatur style dari grid
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: black;");

        //Pesan welcome
        Text welcomeText = new Text("Welcome to DepeFood");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 30)); 
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);

        // menambahkan shadow ke teks welcome
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.ANTIQUEWHITE);
        dropShadow.setRadius(20);
        welcomeText.setEffect(dropShadow);

        // menambahkan animasi ke teks welcome
        ScaleTransition shadowTransition = new ScaleTransition(Duration.seconds(2), welcomeText);
        shadowTransition.setFromX(1.0);
        shadowTransition.setToX(1.05);
        shadowTransition.setFromY(1.0);
        shadowTransition.setToY(1.05);
        shadowTransition.setAutoReverse(true);
        shadowTransition.setCycleCount(Timeline.INDEFINITE);
        shadowTransition.play();

        // label nama dan textfield untuk input nama
        Label nameLabel = new Label("Name:");
        nameLabel.setTextFill(Color.WHITE);
        nameInput = new TextField();
        nameInput.setPromptText("Enter your name");

        // label phone dan textfield untuk input telpon
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setTextFill(Color.WHITE);
        phoneInput = new TextField();
        phoneInput.setPromptText("Enter your phone number");

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        loginButton.setOnAction(e -> handleLogin());

        //Memassukan teks, label, textfield, dan button
        grid.add(welcomeText, 0, 0, 2, 1); // column, row, column span, row span
        grid.add(nameLabel, 0, 1);
        grid.add(nameInput, 1, 1);
        grid.add(phoneLabel, 0, 2);
        grid.add(phoneInput, 1, 2);
        grid.add(loginButton, 1, 3);
        GridPane.setHalignment(loginButton, HPos.LEFT);

        // Slide transition untuk welcome text
        TranslateTransition slideInWelcomeText = new TranslateTransition(Duration.millis(2500), welcomeText);
        slideInWelcomeText.setFromX(-400);
        slideInWelcomeText.setToX(0);
        slideInWelcomeText.play();

        // Slide transition untuk name input
        TranslateTransition slideInNameInput = new TranslateTransition(Duration.millis(3000), nameInput);
        slideInNameInput.setFromX(-400);
        slideInNameInput.setToX(0);
        slideInNameInput.play();

        // Slide transition untuk phone input
        TranslateTransition slideInPhoneInput = new TranslateTransition(Duration.millis(3000), phoneInput);
        slideInPhoneInput.setFromX(-400);
        slideInPhoneInput.setToX(0);
        slideInPhoneInput.play();

        // Slide transition untuk login button
        TranslateTransition slideInLoginButton = new TranslateTransition(Duration.millis(3000), loginButton);
        slideInLoginButton.setFromX(-400);
        slideInLoginButton.setToX(0);
        slideInLoginButton.play();

        // Hover effect untuk login button
        loginButton.setOnMouseEntered(e -> {
            loginButton.setScaleX(1.2);
            loginButton.setScaleY(1.2);
            DropShadow buttonShadow = new DropShadow();
            buttonShadow.setColor(Color.WHITE);
            buttonShadow.setRadius(20);
            loginButton.setEffect(buttonShadow);
        });

        loginButton.setOnMouseExited(e -> {
            loginButton.setScaleX(1);
            loginButton.setScaleY(1);
            loginButton.setEffect(null);
        });
        return new Scene(grid, 500, 600);
    }

    //Method yang akan memvalidasi login user
    private void handleLogin() {
        //mengambil input dari text field
        String username = nameInput.getText();
        String userphone = phoneInput.getText();
        boolean loginSuccess = false;

        //Mendapatkan user yang sesuai, jika tidak ada maka akan menampilkan alert
        User user = DepeFood.getUser(username, userphone);
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("User not found!");
            alert.showAndWait();
        } else {
            loginSuccess = true;
            System.out.println("Test");
        }

        //Jika user ada, maka akan mendapatkan scene yang sesuai dengan role dari user
        Scene menuUI = null;
        if (loginSuccess) {
            String userRole = user.getRole();
            if (userRole.equals("Admin")) {
                AdminMenu adminMenu = new AdminMenu(stage, mainApp, user);
                menuUI = adminMenu.getScene();
            } else {
                CustomerMenu customerMenu = new CustomerMenu(stage, mainApp, user);
                menuUI = customerMenu.getScene();
            }
        }
        // Menampilkan menu yang sesuai
        if (menuUI != null) {
            mainApp.setScene(menuUI);
            nameInput.clear();
            phoneInput.clear();
        } else {
            System.out.println(menuUI);
            System.out.println("kenapa ya");
        }
    }
    
    public Scene getScene() {
        return this.createLoginForm();
    }

}