package assignments.assignment4.components;

// import javafx.beans.property.SimpleStringProperty;
// import javafx.beans.property.StringProperty;
// import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
// import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
// import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;

public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;
    private String orderId;
    private Order orderToPrint;

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
    }

    private Scene createBillPrinterForm(){
        //TODO: Implementasi untuk menampilkan komponen hasil cetak bill
        VBox layout = new VBox(10);

        printBill(orderId);
        if (orderToPrint!= null){
            Label billLabel = new Label("Bill");
            Text orderText = new Text("Order ID: " + orderId);
            Text restaurantText = new Text("Restaurant: " + orderToPrint.getRestaurant().getNama());
            Text lokasiText = new Text("Lokasi Pengiriman: " + user.getLokasi());
            Text statusText = new Text("Status Pengiriman: " + orderToPrint.getStatusFull());
            Label pesananLabel = new Label("Pesanan:");

            VBox menuList = new VBox(5);
            menuList.setAlignment(Pos.CENTER);
            for (Menu menu : orderToPrint.getSortedMenu()) {
                Text menuText = new Text("- " + menu.getNamaMakanan() + " Rp " + menu.getHarga());
                menuList.getChildren().add(menuText);
            }
            Text biayaText = new Text("Biaya Ongkos Kirim: Rp " + orderToPrint.getOngkir());
            Text totalBiayaText = new Text("Total Biaya: Rp " + orderToPrint.getTotalHarga());
            Button kembaliBtn = new Button("Kembali");
            kembaliBtn.setOnAction(e -> stage.setScene(mainApp.getScene("customerMenu")));
            layout.getChildren().addAll(billLabel, orderText, restaurantText,lokasiText, statusText, pesananLabel,menuList,biayaText,totalBiayaText,kembaliBtn);
        }else{
            Text warning = new Text("Please enter valid Order ID");
            Button kembaliBtn = new Button("Kembali");
            kembaliBtn.setOnAction(e -> stage.setScene(mainApp.getScene("customerMenu")));
            layout.getChildren().addAll(warning, kembaliBtn);
        }
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);
        
        return new Scene(layout, 500, 300);
    }

    private void printBill(String orderId) {
        //TODO: Implementasi validasi orderID
        boolean orderIdValid = false;
        Order founOrder = null;
        for (Order order: user.getOrderHistory()){
            if (orderId.equals(order.getOrderId())){
                orderIdValid = true;
                founOrder = order;
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
        } else {
            orderToPrint = founOrder;
        }
    }

    public Scene getScene(String orderID) {
        this.orderId = orderID;
        return this.createBillPrinterForm();
    }

    // // Class ini opsional
    // public class MenuItem {
    //     private final StringProperty itemName;
    //     private final StringProperty price;

    //     public MenuItem(String itemName, String price) {
    //         this.itemName = new SimpleStringProperty(itemName);
    //         this.price = new SimpleStringProperty(price);
    //     }

    //     public StringProperty itemNameProperty() {
    //         return itemName;
    //     }

    //     public StringProperty priceProperty() {
    //         return price;
    //     }

    //     public String getItemName() {
    //         return itemName.get();
    //     }

    //     public String getPrice() {
    //         return price.get();
    //     }
    // }
}
