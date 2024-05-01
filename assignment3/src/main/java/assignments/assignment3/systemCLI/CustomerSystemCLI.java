package assignments.assignment3.systemCLI;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import assignments.assignment3.MainMenu;
import assignments.assignment3.daritp1.OrderGenerator;
import assignments.assignment3.daritp2.*;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;;

//TODO: Extends abstract class yang diberikan
public class CustomerSystemCLI extends UserSystemCLI {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<User> userList = MainMenu.getUserList(); 
    private static ArrayList<Restaurant> restoList = MainMenu.getRestoList(); 
    public static User userLoggedIn;

    public void run(User user) {
        boolean isLoggedIn = true;
        userLoggedIn = user;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }


    //Menambahkan modifier dan membuat metode ini mengoverride dari Abstract class
    @Override
    protected boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //Menambahkan modifier dan membuat metode ini mengoverride dari Abstract class
    @Override
    protected void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

     // Mengimplementasi method untuk handle ketika customer membuat pesanan (Dari TP2)
    protected void handleBuatPesanan(){
        System.out.println("--------------Buat Pesanan----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalPemesanan = input.nextLine().trim();
            if(!OrderGenerator.isValidDateFormat(tanggalPemesanan)){
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)");
                System.out.println();
                continue;
            }
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = Integer.parseInt(input.nextLine().trim());
            System.out.println("Order: ");
            List<String> listMenuPesananRequest = new ArrayList<>();
            for(int i=0; i < jumlahPesanan; i++){
                listMenuPesananRequest.add(input.nextLine().trim());
            }
            if(! validateRequestPesanan(restaurant, listMenuPesananRequest)){
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            };
            Order order = new Order(
                    OrderGenerator.generateOrderID(restaurantName, tanggalPemesanan, userLoggedIn.getNomorTelepon()),
                    tanggalPemesanan, 
                    OrderGenerator.calculateDeliveryCost(userLoggedIn.getLokasi()), 
                    restaurant, 
                    getMenuRequest(restaurant, listMenuPesananRequest));
            System.out.printf("Pesanan dengan ID %s diterima!", order.getOrderId());
            userLoggedIn.addOrderHistory(order);
            return;
        }
    }

    // Mengimplementasikan method untuk handle ketika customer ingin cetak bill (Dari TP2)
    protected void handleCetakBill(){
        System.out.println("--------------Cetak Bill----------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId);
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.println("");
            System.out.print(outputBillPesanan(order));
            return;
        }
    }

    // Mengimplementasikan method untuk handle ketika customer ingin melihat menu (Dari TP2)
    protected void handleLihatMenu(){
        System.out.println("--------------Lihat Menu----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print(restaurant.printMenu());
            return;
        }
    }

     // Mengimplementasikan method untuk handle ketika customer ingin membayar bill
    protected void handleBayarBill(){
        System.out.println("--------------Bayar Bill----------------");
        while (true) {
            // Meminta order ID dan memvalidasi orderIdnya
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId);
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            if (order.getOrderFinished() == true){
                System.out.println("Pesanan dengan ID ini sudah lunas!");
                continue;
            }
            double totalHarga = order.getTotalHarga();
            System.out.println("");
            System.out.print(outputBillPesanan(order));
            System.out.println();
            
            System.out.println("\nOpsi Pembayaran");
            System.out.println("1. Credit Card");
            System.out.println("2. Debit");
            System.out.print("Pilihan Metode Pembayaran: ");
            int pilihan = input.nextInt();

            //validasi berdasarkan metode pembayaran yang dipilih oleh user
            if (pilihan == 1){
                //Jika user memilih credit card dan metode pembayaran yang dimiliki user sesuai
                if (userLoggedIn.getPaymentMethod() instanceof CreditCardPayment){
                    userLoggedIn.getPaymentMethod().processPayment(userLoggedIn,order.getRestaurant(), order,(long) totalHarga);
                //Jika user memilih credit card tapi tidak sesuai dengan metode pembayaran yang dimiliki user
                } else{
                    System.out.println("User belum memiliki metode pembayaran ini!\n");
                }
            }else if (pilihan==2){
                //Jika user memilih debit card dan metode pembayaran yang dimiliki user sesuai
                if (userLoggedIn.getPaymentMethod() instanceof DebitPayment){
                    userLoggedIn.getPaymentMethod().processPayment(userLoggedIn,order.getRestaurant(), order,(long) totalHarga);
                //Jika user memilih debit card tapi tidak sesuai dengan metode pembayaran yang dimiliki user
                } else{
                    System.out.println("User belum memiliki metode pembayaran ini!\n");
                }
            //Jika pilihan tidak valid
            }else{
                System.out.println("Pilihan tidak valid");
            }
            return;
        }
    }

    //Method untuk mengeprint saldo dari user
    protected void handleCekSaldo(){
        System.out.println("Sisa saldo sebesar Rp" + userLoggedIn.getSaldo());
    }

    //Method untuk mendapatkan restoran dari nama (Dari TP2)
    protected Restaurant getRestaurantByName(String name){
        Optional<Restaurant> restaurantMatched = restoList.stream().filter(restoran -> restoran.getNama().toLowerCase().equals(name.toLowerCase())).findFirst();
        if(restaurantMatched.isPresent()){
            return restaurantMatched.get();
        }
        return null;
    }

    //Method untuk memvalidasi request pesanan (Dari TP2)
    protected boolean validateRequestPesanan(Restaurant restaurant, List<String> listMenuPesananRequest){
        return listMenuPesananRequest.stream().allMatch(pesanan -> 
            restaurant.getMenu().stream().anyMatch(menu -> menu.getNamaMakanan().equals(pesanan))
        );
    }

    //Method untuk mendapatkan 
    protected Menu[] getMenuRequest(Restaurant restaurant, List<String> listMenuPesananRequest){
        Menu[] menu = new Menu[listMenuPesananRequest.size()];
        for(int i=0;i<menu.length;i++){
            for(Menu existMenu : restaurant.getMenu()){
                if(existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))){
                    menu[i] = existMenu;
                }
            }
        }
        return menu;
    }
    
    //Mendapatkan order yang sesuai(Dari TP2)
    protected Order getOrderOrNull(String orderId) {
        for (User user : userList) {
            for (Order order : user.getOrderHistory()) {
                if (order.getOrderId().equals(orderId)) {
                    return order;
                }
            }
        }
        return null;
    }

    //Method untuk mengeprint bill pesanan (Dari TP2)
    protected String outputBillPesanan(Order o) {
        String[] tempOrderedMenuItems = new String[o.getItems().length];
        String output = "bill:\n";
        int i = 0;
        int totalBiaya = 0;
        //mengiterasi untuk makanan yang diorder dan harganya
        for (Menu m : o.getItems()) {
            totalBiaya += m.getHarga();
            String menu = "- " + m.getNamaMakanan() + " " + String.format("%.0f", m.getHarga());
            tempOrderedMenuItems[i] = menu;
            i++;
        }
        // menambahkan untuk pesan outputnya dan mengambil data dari class Order 
        totalBiaya += o.getOngkir();
        output += "Order Id: " + o.getOrderId() + "\n";
        output += "Tanggal Pemesanan: " + o.getTanggal() + "\n";
        output += "Restaurant: " + o.getRestaurant().getNama() + "\n";
        output += "Lokasi Pengiriman: " + userLoggedIn.getLokasi() + "\n";
        output += "Status Pengiriman: " + o.getStatusFull() + "\n";
        output += "Pesanan:\n";
        //mengiterasi untuk menampilakn menu di output
        for (String menu : tempOrderedMenuItems) {
            output += menu + "\n";
        }
        output += "Biaya Ongkos Kirim: Rp " + o.getOngkir() + "\n";
        output += "Total Biaya: Rp " + totalBiaya;
        return output;
    }

    // Method untuk mendapatkan pesanan yang akan di print (Dari TP2)
    protected String getMenuPesananOutput(Order order){
        StringBuilder pesananBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder.append("- ").append(menu.getNamaMakanan()).append(" ").append(decimalFormat.format(menu.getHarga())).append("\n");
        }
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString();
    }

}
