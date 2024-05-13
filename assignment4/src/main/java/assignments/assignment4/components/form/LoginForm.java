package assignments.assignment4.components.form;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
// import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;

// import java.util.function.Consumer;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
    }

    private Scene createLoginForm() {
        // TODO: Implementasi method untuk menampilkan komponen form login
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(10);

        Text welcomeText = new Text("Welcome to DepeFood");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 30)); // Alternative approach
        welcomeText.setTextAlignment(TextAlignment.CENTER);

        // forum nama
        Label nameLabel = new Label("Name:");
        nameInput = new TextField();
        // forum telepon
        Label phoneLabel = new Label("Phone Number:");
        phoneInput = new TextField();
        // login button
        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> handleLogin());

        grid.add(welcomeText, 0, 0, 2, 1);// column, row, column span, row span, span across 2 columns (0 and 1)
        grid.add(nameLabel, 0, 1);
        grid.add(nameInput, 1, 1);
        grid.add(phoneLabel, 0, 2);
        grid.add(phoneInput, 1, 2);
        grid.add(loginButton, 1, 3);
        GridPane.setHalignment(loginButton, HPos.LEFT);
        return new Scene(grid, 400, 600);
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
            mainApp.setScene(menuUI);
            stage.show();
            nameInput.clear();
            phoneInput.clear();
        } else{
            System.out.println(menuUI);
            System.out.println("kenapa ya");
        }
    }

    public Scene getScene() {
        return this.createLoginForm();
    }

}
