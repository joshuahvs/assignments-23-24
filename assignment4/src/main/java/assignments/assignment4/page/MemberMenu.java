package assignments.assignment4.page;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public abstract class MemberMenu {
    private Scene scene;

    abstract protected Scene createBaseMenu();

    //method untuk menampilkan alert
    protected void showAlert(String title, String header, String content, Alert.AlertType c){
        Alert alert = new Alert(c);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    //method untuk menambahkan effect fade untuk button
    protected void addFadeTransition(Button button) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.4), button);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
     //method untuk menambahkan effect fade untuk text
    protected void addFadeTransition(Text text) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.4), text);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
     //method untuk menambahkan effect fade untuk label
    protected void addFadeTransition(Label label) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.4), label);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
     //method untuk menambahkan effect fade untuk teksfield
    protected void addFadeTransition(TextField textField) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.4), textField);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }
     //method untuk menambahkan effect fade untuk combobox
    protected void addFadeTransition(ComboBox<String> box) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.4), box);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.setCycleCount(1);
        fadeTransition.play();
    }

    //method untuk menambahkan shadow dan animasi ke teks
    protected void addShadow(Text text){
        // menambahkan dropshadow ke teks
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.ANTIQUEWHITE);
        dropShadow.setRadius(20);
        text.setEffect(dropShadow);

        // menambahkan animasi ke teks
        ScaleTransition shadowTransition = new ScaleTransition(Duration.seconds(2), text);
        shadowTransition.setFromX(1.0);
        shadowTransition.setToX(1.05);
        shadowTransition.setFromY(1.0);
        shadowTransition.setToY(1.05);
        shadowTransition.setAutoReverse(true);
        shadowTransition.setCycleCount(Timeline.INDEFINITE);
        shadowTransition.play();
    }

    //method untuk menambahkan hover effect
    protected void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> {
            button.setScaleX(1.15);
            button.setScaleY(1.15);
            DropShadow buttonShadow = new DropShadow();
            buttonShadow.setColor(Color.ANTIQUEWHITE);
            buttonShadow.setRadius(20);
            button.setEffect(buttonShadow);
        });

        button.setOnMouseExited(e -> {
            button.setScaleX(1);
            button.setScaleY(1);
            button.setEffect(null);
        });
    }

    public Scene getScene(){
        return this.scene;
    }

    //refresh input dari menulist
    protected void refresh(ListView<String> menuList){
        menuList.getItems().clear();
    }
    //refresh input dari textfield
    protected void refresh(TextField textField){
        textField.clear();
    }

}