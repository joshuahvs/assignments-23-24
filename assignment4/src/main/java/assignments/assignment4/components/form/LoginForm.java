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
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
    }

    public Scene createLoginForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: black;");

        Text welcomeText = new Text("Welcome to DepeFood");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 30)); 
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);

        // Adding drop shadow effect to the welcome text
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.ANTIQUEWHITE);
        dropShadow.setRadius(20);
        welcomeText.setEffect(dropShadow);

        // Animation for the drop shadow effect
        ScaleTransition shadowTransition = new ScaleTransition(Duration.seconds(2), welcomeText);
        shadowTransition.setFromX(1.0);
        shadowTransition.setToX(1.05);
        shadowTransition.setFromY(1.0);
        shadowTransition.setToY(1.05);
        shadowTransition.setAutoReverse(true);
        shadowTransition.setCycleCount(Timeline.INDEFINITE);
        shadowTransition.play();

        // Forum name
        Label nameLabel = new Label("Name:");
        nameLabel.setTextFill(Color.WHITE);
        nameInput = new TextField();
        nameInput.setPromptText("Enter your name");

        // Forum phone
        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setTextFill(Color.WHITE);
        phoneInput = new TextField();
        phoneInput.setPromptText("Enter your phone number");

        // Login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        loginButton.setOnAction(e -> handleLogin());

        grid.add(welcomeText, 0, 0, 2, 1); // column, row, column span, row span
        grid.add(nameLabel, 0, 1);
        grid.add(nameInput, 1, 1);
        grid.add(phoneLabel, 0, 2);
        grid.add(phoneInput, 1, 2);
        grid.add(loginButton, 1, 3);
        GridPane.setHalignment(loginButton, HPos.LEFT);

        // Slide transition for the welcome text
        TranslateTransition slideInWelcomeText = new TranslateTransition(Duration.millis(2500), welcomeText);
        slideInWelcomeText.setFromX(-400);
        slideInWelcomeText.setToX(0);
        slideInWelcomeText.play();

        // Slide transition for the name input
        TranslateTransition slideInNameInput = new TranslateTransition(Duration.millis(3000), nameInput);
        slideInNameInput.setFromX(-400);
        slideInNameInput.setToX(0);
        slideInNameInput.play();

        // Slide transition for the phone input
        TranslateTransition slideInPhoneInput = new TranslateTransition(Duration.millis(3000), phoneInput);
        slideInPhoneInput.setFromX(-400);
        slideInPhoneInput.setToX(0);
        slideInPhoneInput.play();

        // Slide transition for the login button
        TranslateTransition slideInLoginButton = new TranslateTransition(Duration.millis(3000), loginButton);
        slideInLoginButton.setFromX(-400);
        slideInLoginButton.setToX(0);
        slideInLoginButton.play();

        // Hover effect for the login button
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

    private void handleLogin() {
        // TODO: Implementasi validasi isian form login
        String username = nameInput.getText();
        String userphone = phoneInput.getText();
        boolean loginSuccess = false;

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

        Scene menuUI = null;
        if (loginSuccess) {
            String userRole = user.getRole();
            if (userRole.equals("Admin")) {
                System.out.println("test admin");
                AdminMenu adminMenu = new AdminMenu(stage, mainApp, user);
                menuUI = adminMenu.getScene();
                System.out.println("admin menu: " + adminMenu.getScene());
            } else {
                System.out.println("test customer");
                CustomerMenu customerMenu = new CustomerMenu(stage, mainApp, user);
                menuUI = customerMenu.getScene();
                System.out.println("customer menu: " + customerMenu.getScene());
            }
        }
        if (menuUI != null) {
            System.out.println("login success");
            // stage.setScene(menuUI);
            mainApp.setScene(menuUI);
            // stage.show();
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