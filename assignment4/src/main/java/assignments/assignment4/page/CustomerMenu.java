package assignments.assignment4.page;

import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import assignments.assignment1.OrderGenerator;
// import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;

// import java.util.ArrayList;
// import java.util.Arrays;
import java.util.List;
// import java.util.stream.Collectors;

public class CustomerMenu extends MemberMenu {
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private static Label label = new Label();
    private MainApp mainApp;
    private List<Restaurant> restoList = AdminMenu.getRestaurants();
    private User user;

    public CustomerMenu(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp, this.user); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
        this.billPrinter = new BillPrinter(stage, mainApp, user);
    }

    @Override
    public Scene createBaseMenu() {
        // TODO: Implementasikan method ini untuk menampilkan menu untuk Customer
        initComboBox();
        VBox menuLayout = new VBox(10);

        Button buatPesananbtn = new Button("Buat Pesanan");
        buatPesananbtn.setOnAction(e -> stage.setScene(createTambahPesananForm()));

        Button cetakBillbtn = new Button("Cetak Bill");
        cetakBillbtn.setOnAction(e -> stage.setScene(createBillPrinter()));

        Button bayarBillbtn = new Button("Bayar Bill");
        bayarBillbtn.setOnAction(e -> stage.setScene(createBayarBillForm()));

        Button cekSaldobtn = new Button("Cek Saldo");
        cekSaldobtn.setOnAction(e -> stage.setScene(createCekSaldoScene()));

        Button logOutbtn = new Button("Log Out");
        logOutbtn.setOnAction(e -> handleLogOut());

        menuLayout.getChildren().addAll(buatPesananbtn, cetakBillbtn, bayarBillbtn, cekSaldobtn, logOutbtn);
        menuLayout.setAlignment(Pos.CENTER);
        
        Scene customerScene = new Scene(menuLayout, 400, 600);
        mainApp.addScene("customerMenu", customerScene);
        return customerScene;
    }

    private void initComboBox() {
        restaurantComboBox.setPromptText("Select a restaurant");
        for (Restaurant resto : restoList) {
            restaurantComboBox.getItems().add(resto.getNama());
        }
    }

    private Scene createTambahPesananForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah pesanan
        VBox menuLayout = new VBox(10);
        menuLayout.setAlignment(Pos.CENTER);
        Label restaurantLabel = new Label("Restaurant:");
        Label dateLabel = new Label("Date (DD/MM/YYYY):");
        TextField date = new TextField();
        date.setPromptText("Enter date (DD/MM/YYYY)");
        Button menuBtn = new Button("Menu");
        Label menuLabel = new Label("Menu:");

        ListView<String> menuList = new ListView<>();
        menuBtn.setOnAction(e -> {
            String selectedRestaurantName = restaurantComboBox.getSelectionModel().getSelectedItem();
            Restaurant currentResto = null;
            if (selectedRestaurantName != null) {
                for (Restaurant resto : restoList) {
                    if (resto.getNama().equalsIgnoreCase(selectedRestaurantName)) {
                        currentResto = resto;
                        break;
                    }
                }
                // Clear previous menu items
                menuList.getItems().clear();
                for (Menu menu : currentResto.getMenu()) {
                    menuList.getItems().add(menu.getNamaMakanan());
                }
            } else {
                showAlert("Error", "Error", "Please select a restaurant!", AlertType.ERROR);
            }
        });

        Button buatPesananbtn = new Button("Buat Pesanan");
        menuList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<String> selectedMenuItems = menuList.getSelectionModel().getSelectedItems();
        buatPesananbtn.setOnAction(e -> handleBuatPesanan(restaurantComboBox.getSelectionModel().getSelectedItem(), date.getText(), selectedMenuItems));
        Button kembaliBtn = new Button("Kembali");
        kembaliBtn.setOnAction(e -> stage.setScene(scene));

        menuLayout.getChildren().addAll(restaurantLabel, restaurantComboBox, dateLabel, date, menuBtn, menuLabel,
                menuList,buatPesananbtn, kembaliBtn);

        addOrderScene = new Scene(menuLayout, 400, 600);
        return addOrderScene;
    }

    private Scene createBillPrinter() {
        // TODO: Implementasikan method ini untuk menampilkan page cetak bill
        VBox menuLayout = new VBox(10);
        TextField orderIdInput = new TextField();
        Button printBillbtn = new Button("Print Bill");
        printBillbtn.setOnAction(e->{
            String orderId = orderIdInput.getText();
            stage.setScene(billPrinter.getScene(orderId));
        });
        Button kembaliBtn = new Button("Kembali");
        kembaliBtn.setOnAction(e -> stage.setScene(scene));
        
        menuLayout.getChildren().addAll(orderIdInput, printBillbtn, kembaliBtn);
        menuLayout.setAlignment(Pos.CENTER);
        printBillScene = new Scene(menuLayout, 400,600);
        return printBillScene;
    }

    private Scene createBayarBillForm() {
        // TODO: Implementasikan method ini untuk menampilkan page bayar bill
        VBox menuLayout = new VBox(10);
        TextField orderIDInput = new TextField();
        orderIDInput.setPromptText("Masukkan Order ID");
        ComboBox<String> paymentBox = new ComboBox<>(FXCollections.observableArrayList("Credit Card", "Debit"));
        paymentBox.setPromptText("Pilih Opsi Pembayaran");

        Button bayarBtn = new Button("Bayar");
        bayarBtn.setOnAction(e -> handleBayarBill(orderIDInput.getText(), paymentBox.getSelectionModel().getSelectedItem()));

        Button kembaliBtn = new Button("Kembali");
        kembaliBtn.setOnAction(e -> stage.setScene(scene));

        menuLayout.getChildren().addAll(orderIDInput,paymentBox,bayarBtn,kembaliBtn);
        menuLayout.setAlignment(Pos.CENTER);
        payBillScene = new Scene(menuLayout, 400, 600);
        return payBillScene;
    }

    private Scene createCekSaldoScene() {
        // TODO: Implementasikan method ini untuk menampilkan page cetak saldo
        VBox menuLayout = new VBox(10);

        Text namaUser = new Text(user.getNama());
        Text saldoUser = new Text("Saldo: Rp " + user.getSaldo());

        Button kembaliBtn = new Button("Kembali");
        kembaliBtn.setOnAction(e -> stage.setScene(scene));

        menuLayout.getChildren().addAll(namaUser, saldoUser, kembaliBtn);
        menuLayout.setAlignment(Pos.CENTER);

        cekSaldoScene = new Scene(menuLayout, 400, 600);
        return cekSaldoScene;
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        // TODO: Implementasi validasi isian pesanan
        if (!OrderGenerator.validateDate(tanggalPemesanan)) {
            showAlert("Error", "Error", "Date is invalid", AlertType.ERROR);
            return;
        } else if (menuItems.isEmpty()){
            showAlert("Error", "Error", "Please choose a menu", AlertType.ERROR);
            return;
        }
        Restaurant currentResto = null;
        for (Restaurant resto : restoList) {
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                currentResto = resto;
                break;
            }
        }
        try {
            String orderId = OrderGenerator.generateOrderID(namaRestoran, tanggalPemesanan, user.getNomorTelepon());
            System.out.println(orderId);
            Order order = new Order(
                    orderId,
                    tanggalPemesanan,
                    OrderGenerator.calculateDeliveryCost(user.getLokasi()),
                    currentResto,
                    getMenuRequest(currentResto, menuItems));
            user.addOrderHistory(order);
            showAlert("Success", null, "Order dengan ID " + orderId + " berhasil ditambahkan" , AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Error", "Can not make an order!", AlertType.ERROR);
        }
    }

    private void handleBayarBill(String orderID, String pilihanPembayaran) {
        // TODO: Implementasi validasi pembayaran
        if (pilihanPembayaran == null) {
            showAlert("Error", "Invalid Payment Option", "Please select a payment option.", AlertType.ERROR);
            return;
        }
        Order founOrder = null;
        for (Order order: user.getOrderHistory()){
            if (orderID.equals(order.getOrderId())){
                founOrder = order;
                break;
            }
        }
        if (founOrder==null){
            showAlert("Error", "Error", "Order cannot be found!", AlertType.ERROR);
            return;
        }
        if (founOrder.getOrderFinished()==true){
            showAlert("Error", "Error", "Order has been paid!", AlertType.ERROR);
            return;
        }
        try {
            if (pilihanPembayaran=="Credit Card"){
                if (user.getPaymentSystem() instanceof CreditCardPayment){
                    user.getPaymentSystem().processPayment(user.getSaldo(), (long) founOrder.getTotalHarga());
                    long transactionFee = (long)(founOrder.getTotalHarga()*0.02);
                    long totalPayment = (long) (founOrder.getTotalHarga() + transactionFee);
                    user.setSaldo(user.getSaldo()-totalPayment);
                    founOrder.setOrderFinished(true);
                    showAlert("Success", null, "Berhasil Membayar Bill sebesar Rp " + founOrder.getTotalHarga() + "dengan biaya transaksi sebesar Rp " + transactionFee, AlertType.INFORMATION);
                //Jika user memilih credit card tapi tidak sesuai dengan metode pembayaran yang dimiliki user
                } else{
                    showAlert("Error", "Invalid Payment Option", "User cannot pay with this method", AlertType.ERROR);
                }
            } else{
                if (user.getPaymentSystem() instanceof DebitPayment){
                    user.getPaymentSystem().processPayment(user.getSaldo(),(long)founOrder.getTotalHarga());
                    user.setSaldo((long)(user.getSaldo()-founOrder.getTotalHarga()));
                    founOrder.setOrderFinished(true);
                    showAlert("Success", null, "Berhasil Membayar Bill sebesar Rp " + founOrder.getTotalHarga(), AlertType.INFORMATION);
                //Jika user memilih credit card tapi tidak sesuai dengan metode pembayaran yang dimiliki user
                } else{
                    showAlert("Error", "Invalid Payment Option", "User cannot pay with this method", AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            showAlert("Error", null, "Payment Failed", AlertType.ERROR);
        }
    }

    protected Menu[] getMenuRequest(Restaurant restaurant, List<String> listMenuPesananRequest) {
        Menu[] menu = new Menu[listMenuPesananRequest.size()];
        for (int i = 0; i < menu.length; i++) {
            for (Menu existMenu : restaurant.getMenu()) {
                if (existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))) {
                    menu[i] = existMenu;
                }
            }
        }
        return menu;
    }

    private void handleLogOut() {
        Scene loginScene = mainApp.getScene("Login");
        stage.setScene(loginScene);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}