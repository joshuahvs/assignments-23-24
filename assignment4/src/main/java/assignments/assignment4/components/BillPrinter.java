package assignments.assignment4.components;

import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;

public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;
    private String orderId;
    private Order orderToPrint = null;

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    private Scene createBillPrinterForm() {
    // Implementasi untuk menampilkan komponen hasil cetak bill
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: black;");
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        printBill(orderId);
        if (orderToPrint != null) {
            Text billText = new Text("Bill");
            billText.setFont(Font.font(billText.getFont().getName(), FontWeight.BOLD, 30)); 
            billText.setFill(Color.WHITE);
            billText.setTextAlignment(TextAlignment.CENTER);

            // Adding drop shadow effect to the welcome text
            DropShadow dropShadow = new DropShadow();
            dropShadow.setColor(Color.ANTIQUEWHITE);
            dropShadow.setRadius(20);
            billText.setEffect(dropShadow);

            // Animation for the drop shadow effect
            ScaleTransition shadowTransition = new ScaleTransition(Duration.seconds(2), billText);
            shadowTransition.setFromX(1.0);
            shadowTransition.setToX(1.05);
            shadowTransition.setFromY(1.0);
            shadowTransition.setToY(1.05);
            shadowTransition.setAutoReverse(true);
            shadowTransition.setCycleCount(Timeline.INDEFINITE);
            shadowTransition.play();

            Text orderText = new Text("Order ID: " + orderId);
            styleText(orderText);
            applyTypingTransition(orderText);

            Text restaurantText = new Text("Restaurant: " + orderToPrint.getRestaurant().getNama());
            styleText(restaurantText);
            applyTypingTransition(restaurantText);

            Text lokasiText = new Text("Lokasi Pengiriman: " + user.getLokasi());
            styleText(lokasiText);
            applyTypingTransition(lokasiText);

            Text statusText = new Text("Status Pengiriman: " + orderToPrint.getStatusFull());
            styleText(statusText);
            applyTypingTransition(statusText);

            Label pesananLabel = new Label("Pesanan:");
            styleLabel(pesananLabel);

            VBox menuList = new VBox(5);
            menuList.setAlignment(Pos.CENTER);
            for (Menu menu : orderToPrint.getSortedMenu()) {
                Text menuText = new Text("- " + menu.getNamaMakanan() + " Rp " + menu.getHarga());
                styleText(menuText);
                applyTypingTransition(menuText);
                menuList.getChildren().add(menuText);
            }

            Text biayaText = new Text("Biaya Ongkos Kirim: Rp " + orderToPrint.getOngkir());
            styleText(biayaText);
            applyTypingTransition(biayaText);

            Text totalBiayaText = new Text("Total Biaya: Rp " + orderToPrint.getTotalHarga());
            styleText(totalBiayaText);
            applyTypingTransition(totalBiayaText);

            Button kembaliBtn = new Button("Kembali");
            kembaliBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            kembaliBtn.setOnAction(e -> stage.setScene(mainApp.getScene("customerMenu")));
            addHoverEffect(kembaliBtn);

            layout.getChildren().addAll(billText, orderText, restaurantText, lokasiText, statusText, pesananLabel, menuList, biayaText, totalBiayaText, kembaliBtn);
        } else {
            Text warning = new Text("Please Enter Valid Order ID");
            warning.setFill(Color.RED);
            warning.setFont(Font.font(warning.getFont().getName(), FontWeight.NORMAL, 40));
            addShadow(warning);

            Button kembaliBtn = new Button("Kembali");
            kembaliBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            kembaliBtn.setOnAction(e -> stage.setScene(mainApp.getScene("customerMenu")));
            addHoverEffect(kembaliBtn);

            layout.getChildren().addAll(warning, kembaliBtn);
        }

        return new Scene(layout, 500, 600);
    }

    private void styleLabel(Label label) {
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(label.getFont().getName(), FontWeight.NORMAL, 14));
    }

    private void styleText(Text text) {
        text.setFill(Color.WHITE);
        text.setFont(Font.font(text.getFont().getName(), FontWeight.NORMAL, 14));
    }

    private void printBill(String orderId) {
        //TODO: Implementasi validasi orderID
        boolean orderIdValid = false;
        for (Order order: user.getOrderHistory()){
            if (orderId.equals(order.getOrderId())){
                orderIdValid = true;
                orderToPrint = order;
                break;
            }
        }
        if (!orderIdValid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Order cannot be found!");
            alert.showAndWait();
            return;
        }
    }

    public Scene getScene(String orderID) {
        this.orderId = orderID;
        return this.createBillPrinterForm();
    }

    private void applyTypingTransition(Text text) {
        String content = text.getText();
        text.setText("");
        Timeline timeline = new Timeline();
        for (int i = 0; i < content.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(50 * i), e -> text.setText(text.getText() + content.charAt(index)));
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    protected void addShadow(Text text){
        // Adding drop shadow effect to the welcome text
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.ANTIQUEWHITE);
        dropShadow.setRadius(20);
        text.setEffect(dropShadow);

        // Animation for the drop shadow effect
        ScaleTransition shadowTransition = new ScaleTransition(Duration.seconds(2), text);
        shadowTransition.setFromX(1.0);
        shadowTransition.setToX(1.05);
        shadowTransition.setFromY(1.0);
        shadowTransition.setToY(1.05);
        shadowTransition.setAutoReverse(true);
        shadowTransition.setCycleCount(Timeline.INDEFINITE);
        shadowTransition.play();
    }

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
}
