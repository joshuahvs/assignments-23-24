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
    //atribut
    private Stage stage;
    private MainApp mainApp;
    private User user;
    private String orderId;
    private Order orderToPrint = null;

    //constructor
    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }
    // method untuk menampilkan komponen hasil cetak bill
    private Scene createBillPrinterForm() {
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: black;");
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        //Memvalidasi bill
        printBill(orderId);
        //Menampilkan bill berdasarkan hasil validasi
        if (orderToPrint != null) {
            //Menambahkan teks
            Text billText = new Text("Bill");
            billText.setFont(Font.font(billText.getFont().getName(), FontWeight.BOLD, 30)); 
            billText.setFill(Color.WHITE);
            billText.setTextAlignment(TextAlignment.CENTER);
            addShadow(billText);

            //Teks orderId
            Text orderText = new Text("Order ID: " + orderId);
            styleText(orderText);
            applyTypingTransition(orderText);

            //Teks nama restoran
            Text restaurantText = new Text("Restaurant: " + orderToPrint.getRestaurant().getNama());
            styleText(restaurantText);
            applyTypingTransition(restaurantText);

            //text lokasi pengiriman
            Text lokasiText = new Text("Lokasi Pengiriman: " + user.getLokasi());
            styleText(lokasiText);
            applyTypingTransition(lokasiText);

            //teks status pengiriman
            Text statusText = new Text("Status Pengiriman: " + orderToPrint.getStatusFull());
            styleText(statusText);
            applyTypingTransition(statusText);

            //label pesanan
            Label pesananLabel = new Label("Pesanan:");
            styleLabel(pesananLabel);

            //Mengiterasi menu yang di order untuk ditampilkan
            VBox menuList = new VBox(5);
            menuList.setAlignment(Pos.CENTER);
            for (Menu menu : orderToPrint.getSortedMenu()) {
                Text menuText = new Text("- " + menu.getNamaMakanan() + " Rp " + menu.getHarga());
                styleText(menuText);
                applyTypingTransition(menuText);
                menuList.getChildren().add(menuText);
            }

            //teks biaya ongkir
            Text biayaText = new Text("Biaya Ongkos Kirim: Rp " + orderToPrint.getOngkir());
            styleText(biayaText);
            applyTypingTransition(biayaText);

            //teks total biaya
            Text totalBiayaText = new Text("Total Biaya: Rp " + orderToPrint.getTotalHarga());
            styleText(totalBiayaText);
            applyTypingTransition(totalBiayaText);

            //button untuk kembali
            Button kembaliBtn = new Button("Kembali");
            kembaliBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
            kembaliBtn.setOnAction(e -> stage.setScene(mainApp.getScene("customerMenu")));
            addHoverEffect(kembaliBtn);

            //Menambahkan semuanya
            layout.getChildren().addAll(billText, orderText, restaurantText, lokasiText, statusText, pesananLabel, menuList, biayaText, totalBiayaText, kembaliBtn);
        //JIka orderId tidak valid maka akan menampilkan teks warning
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

    //Method untuk styling label
    private void styleLabel(Label label) {
        label.setTextFill(Color.WHITE);
        label.setFont(Font.font(label.getFont().getName(), FontWeight.NORMAL, 14));
    }

    //Method untuk styling teks
    private void styleText(Text text) {
        text.setFill(Color.WHITE);
        text.setFont(Font.font(text.getFont().getName(), FontWeight.NORMAL, 14));
    }

    //Method untuk validasi orderID
    private void printBill(String orderId) {
        //Mengecheck apakah orderId ada di user order history
        boolean orderIdValid = false;
        for (Order order: user.getOrderHistory()){
            if (orderId.equals(order.getOrderId())){
                orderIdValid = true;
                orderToPrint = order;
                break;
            }
        }
        //jika tidak ada
        if (!orderIdValid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Order cannot be found!");
            alert.showAndWait();
            return;
        }
    }

    //Mendapatkan scene, dan mengatur orderId yang akan di check
    public Scene getScene(String orderID) {
        this.orderId = orderID;
        return this.createBillPrinterForm();
    }

    //Method untuk transisi teks
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

    //Method untuk menambahkan shadow dan animasi
    protected void addShadow(Text text){
        //menambahkan shadow pada teks
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.ANTIQUEWHITE);
        dropShadow.setRadius(20);
        text.setEffect(dropShadow);

        // menambahkan animasi pada teks
        ScaleTransition shadowTransition = new ScaleTransition(Duration.seconds(2), text);
        shadowTransition.setFromX(1.0);
        shadowTransition.setToX(1.05);
        shadowTransition.setFromY(1.0);
        shadowTransition.setToY(1.05);
        shadowTransition.setAutoReverse(true);
        shadowTransition.setCycleCount(Timeline.INDEFINITE);
        shadowTransition.play();
    }

    //Method untuk hover effect
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
