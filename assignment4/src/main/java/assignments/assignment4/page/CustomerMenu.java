package assignments.assignment4.page;

import javafx.collections.FXCollections;
// import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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

import java.util.List;

public class CustomerMenu extends MemberMenu {
    //Atribut
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private MainApp mainApp;
    private List<Restaurant> restoList = AdminMenu.getRestaurants();
    private User user;

    //Constructor
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

    // Method untuk menampilkan menu untuk Customer
    @Override
    public Scene createBaseMenu() {
        initComboBox(); //inisiasi combobox
        VBox menuLayout = new VBox(30);
        menuLayout.setStyle("-fx-background-color: black;");

        //welcome text, button untuk buat pesanan, cetak bill, bayar bill, cek saldo, dan logout
        Text welcomeText = new Text("Welcome, " + user.getNama() + "!");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 30));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        addShadow(welcomeText);

        Button buatPesananbtn = new Button("Buat Pesanan");
        buatPesananbtn.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        buatPesananbtn.setOnAction(e -> stage.setScene(createTambahPesananForm()));

        Button cetakBillbtn = new Button("Cetak Bill");
        cetakBillbtn.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        cetakBillbtn.setOnAction(e -> stage.setScene(createBillPrinter()));

        Button bayarBillbtn = new Button("Bayar Bill");
        bayarBillbtn.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        bayarBillbtn.setOnAction(e -> stage.setScene(createBayarBillForm()));

        Button cekSaldobtn = new Button("Cek Saldo");
        cekSaldobtn.setStyle("-fx-background-color: white; -fx-text-fill: black;");
        cekSaldobtn.setOnAction(e -> stage.setScene(createCekSaldoScene()));

        Button logOutbtn = new Button("Log Out");
        logOutbtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        logOutbtn.setOnAction(e -> handleLogOut());

        //menambahkan effect pada button
        addFadeTransition(buatPesananbtn);
        addFadeTransition(cetakBillbtn);
        addFadeTransition(bayarBillbtn);
        addFadeTransition(cekSaldobtn);
        addFadeTransition(logOutbtn);

        addHoverEffect(buatPesananbtn);
        addHoverEffect(cetakBillbtn);
        addHoverEffect(bayarBillbtn);
        addHoverEffect(cekSaldobtn);
        addHoverEffect(logOutbtn);

        //menambahkan semuanya ke menu layout
        menuLayout.getChildren().addAll(welcomeText, buatPesananbtn, cetakBillbtn, bayarBillbtn, cekSaldobtn, logOutbtn);
        menuLayout.setAlignment(Pos.CENTER);
        //menyimpan scene
        Scene customerScene = new Scene(menuLayout, 500, 600);
        mainApp.addScene("customerMenu", customerScene);
        return customerScene;
    }

    //inisiasi restaurant combobox
    private void initComboBox() {
        restaurantComboBox.setPromptText("Select a restaurant");
        for (Restaurant resto : restoList) {
            restaurantComboBox.getItems().add(resto.getNama());
        }
    }

    // method untuk menampilkan page tambah pesanan
    private Scene createTambahPesananForm() {
        VBox menuLayout = new VBox(10);
        menuLayout.setStyle("-fx-background-color: black;");
        menuLayout.setAlignment(Pos.CENTER);
        //welcome teks, label restoran, label dan input tanggal, dan button untuk menampilkan menu
        Text welcomeText = new Text("Buat Pesanan");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 20));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        addShadow(welcomeText);

        Label restaurantLabel = new Label("Restaurant:");
        restaurantLabel.setTextFill(Color.WHITE);

        Label dateLabel = new Label("Date (DD/MM/YYYY):");
        dateLabel.setTextFill(Color.WHITE);

        TextField date = new TextField();
        date.setMaxWidth(300);
        date.setPromptText("Enter date (DD/MM/YYYY)");

        Button menuBtn = new Button("Menu");
        Label menuLabel = new Label("Menu:");
        menuLabel.setTextFill(Color.WHITE);

        //Menampilakn menulist jika button menu ditekan
        ListView<String> menuList = new ListView<>();
        menuList.setStyle("-fx-control-inner-background: black; -fx-text-fill: white;");
        menuBtn.setOnAction(e -> {
            refresh(menuList);
            String selectedRestaurantName = restaurantComboBox.getSelectionModel().getSelectedItem();
            Restaurant currentResto = null;
            //cek apakah restoran sudah dipilih
            if (selectedRestaurantName != null) {
                for (Restaurant resto : restoList) {
                    if (resto.getNama().equalsIgnoreCase(selectedRestaurantName)) {
                        currentResto = resto;
                        break;
                    }
                }
                //menambahkan menu ke menulist untuk ditampilkan
                for (Menu menu : currentResto.getMenu()) {
                    menuList.getItems().add(menu.getNamaMakanan());
                }
            } else {
                showAlert("Error", "Error", "Please select a restaurant!", AlertType.ERROR);
            }
        });

        //button buat pesanan yang akan handle buat pesanan
        Button buatPesananbtn = new Button("Buat Pesanan");
        menuList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        List<String> selectedMenuItems = menuList.getSelectionModel().getSelectedItems();
        buatPesananbtn.setOnAction(e -> {handleBuatPesanan(restaurantComboBox.getSelectionModel().getSelectedItem(), date.getText(), selectedMenuItems);refresh(date);refresh(menuList);});

        Button kembaliBtn = new Button("Kembali");
        kembaliBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        kembaliBtn.setOnAction(e -> stage.setScene(scene));

        //jika restoran yang dipilih berganti, menulist akan terrefresh otomatis
        restaurantComboBox.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> refresh(menuList));

        //menambahkan effect
        addFadeTransition(restaurantLabel);
        addFadeTransition(restaurantComboBox);
        addFadeTransition(dateLabel);
        addFadeTransition(date);
        addFadeTransition(menuLabel);
        addFadeTransition(menuBtn);
        addFadeTransition(buatPesananbtn);
        addFadeTransition(kembaliBtn);

        addHoverEffect(menuBtn);
        addHoverEffect(buatPesananbtn);
        addHoverEffect(kembaliBtn);

        menuLayout.getChildren().addAll(welcomeText,restaurantLabel, restaurantComboBox, dateLabel, date, menuBtn, menuLabel,
                menuList,buatPesananbtn, kembaliBtn);

        addOrderScene = new Scene(menuLayout, 500, 600);
        return addOrderScene;
    }

    // Method untuk menampilkan page cetak bill
    private Scene createBillPrinter() {
        VBox menuLayout = new VBox(10);
        menuLayout.setStyle("-fx-background-color: black;");

        //Teks welcome, label dan input orderId dan button untuk cetak bill
        Text welcomeText = new Text("Cetak Bill");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 20));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        addShadow(welcomeText);

        Label orderIDLabel = new Label("Restaurant:");
        orderIDLabel.setTextFill(Color.WHITE);

        TextField orderIdInput = new TextField();
        orderIdInput.setMaxWidth(300);

        //akan memanggil method di billprinter untuk menampilkan bill jika button ditekan
        Button printBillbtn = new Button("Print Bill");
        printBillbtn.setOnAction(e->{
            String orderId = orderIdInput.getText();
            stage.setScene(billPrinter.getScene(orderId));
            refresh(orderIdInput);
        });
        Button kembaliBtn = new Button("Kembali");
        kembaliBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        kembaliBtn.setOnAction(e -> stage.setScene(scene));

        //menambahkan effect
        addFadeTransition(orderIDLabel);
        addFadeTransition(orderIdInput);
        addFadeTransition(printBillbtn);
        addFadeTransition(kembaliBtn);

        addHoverEffect(printBillbtn);
        addHoverEffect(kembaliBtn);
        
        menuLayout.getChildren().addAll(welcomeText,orderIDLabel,orderIdInput, printBillbtn, kembaliBtn);
        menuLayout.setAlignment(Pos.CENTER);
        printBillScene = new Scene(menuLayout, 500,600);
        return printBillScene;
    }

    //Method untuk menampilkan page bayar bill
    private Scene createBayarBillForm() {
        VBox menuLayout = new VBox(10);
        menuLayout.setStyle("-fx-background-color: black;");

        //welcome text, input untuk orderid
        Text welcomeText = new Text("Bayar Bill");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 20));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        addShadow(welcomeText);

        TextField orderIDInput = new TextField();
        orderIDInput.setPromptText("Masukkan Order ID");
        orderIDInput.setMaxWidth(300);

        //combobox piihan payment
        ComboBox<String> paymentBox = new ComboBox<>(FXCollections.observableArrayList("Credit Card", "Debit"));
        paymentBox.setPromptText("Pilih Opsi Pembayaran");

        //memproses pembayaran jika button bayar ditekan
        Button bayarBtn = new Button("Bayar");
        bayarBtn.setOnAction(e -> {handleBayarBill(orderIDInput.getText(), paymentBox.getSelectionModel().getSelectedItem()); refresh(orderIDInput);});

        Button kembaliBtn = new Button("Kembali");
        kembaliBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        kembaliBtn.setOnAction(e -> stage.setScene(scene));

        addFadeTransition(orderIDInput);
        addFadeTransition(paymentBox);
        addFadeTransition(bayarBtn);
        addFadeTransition(kembaliBtn);
        addHoverEffect(bayarBtn);
        addHoverEffect(kembaliBtn);

        menuLayout.getChildren().addAll(welcomeText,orderIDInput,paymentBox,bayarBtn,kembaliBtn);
        menuLayout.setAlignment(Pos.CENTER);
        payBillScene = new Scene(menuLayout, 500, 600);
        return payBillScene;
    }

     // Method untuk menampilkan page cetak saldo
    private Scene createCekSaldoScene() {
        VBox menuLayout = new VBox(10);
        menuLayout.setStyle("-fx-background-color: black;");
        //welcometext, teks nama user, dan saldo user, serta button untuk kembali
        Text welcomeText = new Text("Saldo User");
        welcomeText.setFont(Font.font(welcomeText.getFont().getName(), FontWeight.BOLD, 20));
        welcomeText.setFill(Color.WHITE);
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        addShadow(welcomeText);

        Text namaUser = new Text(user.getNama());
        namaUser.setFill(Color.WHITE);
        Text saldoUser = new Text("Saldo: Rp " + user.getSaldo());
        saldoUser.setFill(Color.WHITE);

        Button kembaliBtn = new Button("Kembali");
        kembaliBtn.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        kembaliBtn.setOnAction(e -> stage.setScene(scene));

        menuLayout.getChildren().addAll(welcomeText, namaUser, saldoUser, kembaliBtn);
        menuLayout.setAlignment(Pos.CENTER);

        //menambahkan effect
        addFadeTransition(namaUser);
        addFadeTransition(saldoUser);
        addFadeTransition(kembaliBtn);
        addHoverEffect(kembaliBtn);
        cekSaldoScene = new Scene(menuLayout, 500, 600);
        return cekSaldoScene;
    }

     //method untuk validasi isian pesanan
    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        //jika tanggal tidak valid
        if (!OrderGenerator.validateDate(tanggalPemesanan)) {
            showAlert("Error", "Error", "Date is invalid", AlertType.ERROR);
            return;
        //jika user tidak memilih menu
        } else if (menuItems.isEmpty()){
            showAlert("Error", "Error", "Please choose a menu", AlertType.ERROR);
            return;
        }
        //mencari resto yang sesuai
        Restaurant currentResto = null;
        for (Restaurant resto : restoList) {
            if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                currentResto = resto;
                break;
            }
        }
        try {
            //membuat orderId, dan membuat ordernya lalu memasukkannya kedalam user orderhistory
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
        //jika terjadi error
        } catch (Exception e) {
            showAlert("Error", "Error", "Can not make an order!", AlertType.ERROR);
        }
    }

    //method untuk validasi pembayaran
    private void handleBayarBill(String orderID, String pilihanPembayaran) {
        //jika user belum memilih pembayaran
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
        //jika order tidak ditemukan
        if (founOrder==null){
            showAlert("Error", "Error", "Order cannot be found!", AlertType.ERROR);
            return;
        }
        //jika order sudah dibayar
        if (founOrder.getOrderFinished()==true){
            showAlert("Error", "Error", "Order has been paid!", AlertType.ERROR);
            return;
        }
        try {
            if (pilihanPembayaran=="Credit Card"){
                //mengecheck apakah user dapat membayar dengan metode ini, jika iya maka process pembayarannya dan set order selesai
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
                //memproses pembayaran dan set order selesai jika metode pembayaran dimiliki user
                if (user.getPaymentSystem() instanceof DebitPayment){
                    user.getPaymentSystem().processPayment(user.getSaldo(),(long)founOrder.getTotalHarga());
                    user.setSaldo((long)(user.getSaldo()-founOrder.getTotalHarga()));
                    founOrder.setOrderFinished(true);
                    showAlert("Success", null, "Berhasil Membayar Bill sebesar Rp " + founOrder.getTotalHarga(), AlertType.INFORMATION);
                //Jika user memilih debit card tapi tidak sesuai dengan metode pembayaran yang dimiliki user
                } else{
                    showAlert("Error", "Invalid Payment Option", "User cannot pay with this method", AlertType.ERROR);
                }
            }
        } catch (Exception e) {
            showAlert("Error", null, "Payment Failed", AlertType.ERROR);
        }
    }

    //Method untuk mendapatkan menu yang sesuai (dari SOLUSI)
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

    //method untuk kembali ke login page
    private void handleLogOut() {
        Scene loginScene = mainApp.getScene("Login");
        stage.setScene(loginScene);
    }

    @Override
    public Scene getScene() {
        return this.scene;
    }
}