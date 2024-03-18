// package main.java.assignments.assignment2;
package assignments.assignment2;

import java.util.ArrayList;
import java.util.Scanner;
// import assignments.assignment1.*;

public class MainMenu {
    //attributes yang diperlukan untuk class ini
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public static void main(String[] args) {
        //menginisiasi user, print header
        initUser();
        printHeader();
        restoList = new ArrayList<>();// biar tidak null
        boolean programRunning = true;
        //menjalankan program dengan looping
        while (programRunning) {
            //menampilkan menu dan meminta input user
            startMenu();
            int command = input.nextInt();
            input.nextLine();
            //jika input 1, artinya user ingin login
            if (command == 1) {
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                // memvalidasi input login
                User userLoggedIn = getUser(nama, noTelp); 
                if (userLoggedIn == null) { //jika user tidak ada
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    continue;
                }
                boolean isLoggedIn = true;

                //jika user adalah customer
                if (userLoggedIn.getRole() == "Customer") {
                    System.out.println("Selamat Datang " + userLoggedIn.getNama() + "!");
                    while (isLoggedIn) {
                        //menampilkan menu dan meminta input
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();
                        //menjalankan method sesuai dengan input user
                        switch (commandCust) {
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill(userLoggedIn);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan(userLoggedIn);
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                    //jika user adalah admin
                } else {
                    System.out.println("Selamat Datang Admin!");
                    while (isLoggedIn) {
                        //menampilkan menu dan meminta input
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();
                        // menjalankan method sesuai dengan input user
                        switch (commandAdmin) {
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            //jika input 2 maka user akan berhenti dari program
            } else if (command == 2) {
                programRunning = false;
            //jika input tidak diketahui
            } else {
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

     // method untuk mendapatkan user dari userList dan mereturn null jika tidak ada
    public static User getUser(String nama, String nomorTelepon) {
        for (User user : userList) {
            if (user.getNama().equals(nama)) {
                if (user.getNomorTelepon().equals(nomorTelepon)) {
                    return user;
                }
            }
        }
        return null;
    }
    //method untuk handle ketika customer membuat pesanan
    public static void handleBuatPesanan(User user) {
        System.out.println("--------------Buat Pesanan---------------");
        boolean allValid = false;
        //menggunakan loop agar menanyakan terus jika salah
        while (allValid == false) {
            //meminta nama restoran dan validasi
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();
            boolean restaurantFound = false;
            for (Restaurant restaurant : restoList) {
                if (restaurant.getName().equalsIgnoreCase(namaRestoran)) {
                    restaurantFound = true;
                    //meminta input tanggal dan validasi dan input jumlah pesanan
                    System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
                    String tanggalPemesanan = input.nextLine();
                    if (Order.isValidDateFormat(tanggalPemesanan)) {
                        System.out.print("Jumlah Pesanan: ");
                        int jumlahPesanan = Integer.parseInt(input.nextLine());
                        // meminta input order sebanyak jumlah pesanan dan memasukkannya ke array
                        String[] tempOrderedMenuItems = new String[jumlahPesanan];
                        for (int i = 0; i < jumlahPesanan; i++) {
                            String pesanan = input.nextLine();
                            tempOrderedMenuItems[i] = pesanan;
                        }
                        int j = 0;
                        boolean menuFound = false;
                        //memvalidasi menu dengan mencocokan terhadap menu restoran
                        Menu[] orderedMenuItems = new Menu[jumlahPesanan];
                        int count = orderedMenuItems.length;
                        for (String menuItem : tempOrderedMenuItems) {
                            for (Menu menu : restaurant.getMenuList()) {
                                if (menu.getNamaMakanan().trim().equalsIgnoreCase(menuItem)) {
                                    orderedMenuItems[j] = menu; //menyimpan menu yang sesuai
                                    j++;
                                    count--;
                                    menuFound = true;
                                    if (count == 0) {
                                        break;
                                    }
                                } else {
                                    menuFound = false;
                                }
                            }
                        }
                        //jika menu sudah sesuai semua
                        if (menuFound == true) {
                            //generate order id dan menentukan biaya ongkir
                            String orderID = Order.generateOrderID(namaRestoran, tanggalPemesanan,user.getNomorTelepon());
                            int biayaOngkir = 0;
                            switch (user.getLokasi()) {
                                case "P":
                                    biayaOngkir = 10000;
                                    break;
                                case "U":
                                    biayaOngkir = 20000;
                                    break;
                                case "T":
                                    biayaOngkir = 35000;
                                    break;
                                case "S":
                                    biayaOngkir = 40000;
                                    break;
                                case "B":
                                    biayaOngkir = 60000;
                                    break;
                                default:
                                    biayaOngkir = 0;
                            }
                            //membuat object order baru
                            Order newOrder = new Order(orderID, tanggalPemesanan, biayaOngkir, restaurant,
                                    orderedMenuItems);
                            // menyimpan object order ke order history user
                            user.addOrder(newOrder);
                            System.out.println("Pesanan dengan ID " + orderID + " diterima!");
                            allValid = true;
                        } else {
                            System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                            System.out.println();
                        }
                    } else {
                        System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY) !");
                        System.out.println();
                    }
                }
            }
            //jika restoran tidak ditemukan
            if (restaurantFound == false) {
                System.out.println("Restoran tidak terdaftar pada sistem.");
                System.out.println();
            }
        }
    }

    // method untuk handle ketika customer ingin cetak bill
    public static void handleCetakBill(User user) {
        boolean orderIdFound = false;
        System.out.println("--------------Cetak Bill---------------");
        while(orderIdFound == false){
            System.out.print("Masukkan OrderID: ");
            String orderId = input.nextLine();
            //mengecheck apakah orderID yang diminta tersedia di orderhistory sang user
            for (Order o : user.getOrderHistory()) {
                //jika tersedia
                if (o.getOrderId().equals(orderId)) {
                    orderIdFound = true;
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
                    output += "Order Id: " + orderId + "\n";
                    output += "Tanggal Pemesanan: " + o.getTanggal() + "\n";
                    output += "Restaurant: " + o.getRestoName() + "\n";
                    output += "Lokasi Pengiriman: " + user.getLokasi() + "\n";
                    output += "Status Pengiriman: " + o.getStatusFull() + "\n";
                    output += "Pesanan:\n";
                    //mengiterasi untuk menampilakn menu di output
                    for (String menu : tempOrderedMenuItems) {
                        output += menu + "\n";
                    }
                    output += "Biaya Ongkos Kirim: Rp " + o.getOngkir() + "\n";
                    output += "Total Biaya: Rp " + totalBiaya;
                    System.out.println(output); //mencetak output
                }
            }
            //jika tidak tersedia
            if (orderIdFound == false) {
                System.out.println("Order ID tidak dapat ditemukan.");
                System.out.println();
            }
        }
    }

    // method untuk handle ketika customer ingin melihat menu
    public static void handleLihatMenu() {
        boolean restaurantFound = false;
        System.out.println("--------------Lihat Menu---------------");
        while(restaurantFound == false){
             //meminta nama restoran dan mengiterasi untuk mencari restoran
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();
            for (Restaurant restaurant : restoList) {
                if (restaurant.getName().equalsIgnoreCase(namaRestoran)) {
                    restaurantFound = true;
                    System.out.println("Menu:");
                    //list menu restoran
                    ArrayList<Menu> menuList = restaurant.getMenuList();
                    boolean swapped;
                    do {
                        swapped = false;
                        for (int i = 0; i < menuList.size() - 1; i++) {
                            if (menuList.get(i).getHarga() > menuList.get(i + 1).getHarga()) {
                                // menukar item jika harga item lebih besar dari harga item selanjutnya
                                Menu temp = menuList.get(i);
                                menuList.set(i, menuList.get(i + 1));
                                menuList.set(i + 1, temp);
                                swapped = true;
                            } else if (menuList.get(i).getHarga() == menuList.get(i + 1).getHarga()) {
                                // jika harganya sama, maka akan mengurutkan berdasarkan alphabet
                                if (menuList.get(i).getNamaMakanan()
                                        .compareToIgnoreCase(menuList.get(i + 1).getNamaMakanan()) > 0) {
                                    Menu temp = menuList.get(i);
                                    menuList.set(i, menuList.get(i + 1));
                                    menuList.set(i + 1, temp);
                                    swapped = true;
                                }
                            }
                        }
                    } while (swapped);
                    int count = 1;
                    // mengiterasi menu dan mencetak setiap menunya
                    for (Menu menu : restaurant.getMenuList()) {
                        System.out.println(count + ". " + menu.getNamaMakanan() + String.format("%.0f", menu.getHarga()));
                        count++;
                    }
                    break;
                }
            }
            //jika restoran tidak tersedia
            if (restaurantFound == false) {
                System.out.println("Restoran tidak terdaftar pada sistem.");
            }
        }
    }
     // method untuk handle ketika customer ingin update status pesanan
    public static void handleUpdateStatusPesanan(User user) {
        boolean orderIdFound = false;
        System.out.println("--------------Update Status Pesanan---------------");
        while(orderIdFound == false){
            System.out.print("Order ID: ");
            String orderId = input.nextLine();
            //mencari order id yang sesuai di order history user
            for (Order o : user.getOrderHistory()) {
                if (o.getOrderId().equals(orderId)) {
                    orderIdFound = true;
                    System.out.print("Status: ");
                    String status = input.nextLine();
                    //jika status sama maka tidak berhasil diupdate
                    if (o.getStatus().equalsIgnoreCase(status)) {
                        System.out.println("Status pesanan dengan ID " + orderId + " tidak berhasil diupdate!");
                    // jika status beda, maka menyetel order finished menjadi true
                    } else {
                        o.setOrderFinished(true);
                        System.out.println("Status pesanan dengan ID " + orderId + " berhasil diupdate!");
                    }
                }
            }
            //jika order id tidak ditemukan
            if (orderIdFound == false) {
                System.out.println("Order ID tidak dapat ditemukan.");
            }
        }
    }

    //method untuk admin yang berfungsi menambahkan resto
    public static void handleTambahRestoran() {
        System.out.println("--------------Tambah Restoran---------------");
        boolean allValid = false;
        while (allValid == false) {
            System.out.print("Nama: ");
            String namaRestoran = input.nextLine();
            //jika nama restoran kurang dari 4 huruf
            if (namaRestoran.length() < 4) {
                System.out.println("Nama restoran tidak valid");
                System.out.println();
            } else {
                boolean restaurantFound = false;
                //mencari apakah restoran sudah ada
                for (Restaurant restaurant : restoList) {
                    if (restaurant.getName().equalsIgnoreCase(namaRestoran)) {
                        restaurantFound = true;
                        break;
                    }
                }
                //jika restoran belum ada di resto list
                if (restaurantFound == false) {
                    Restaurant newRestaurant = new Restaurant(namaRestoran);
                    System.out.print("Jumlah Makanan: ");
                    int jumlahMakanan = input.nextInt();
                    input.nextLine(); // ambil newline character
                    boolean hargaValid = true;
                    //mengiterasi untuk menanyakan menu sesuai jumlah makanan
                    for (int i = 0; i < jumlahMakanan; i++) {
                        String menu = input.nextLine();
                        String namaMakanan = "";
                        String hargaStr = "";
                        double harga;
                        //mengiterasi menu untuk memisahkan nama makanan dan harganya
                        for (int j = 0; j < menu.length(); j++) {
                            char c = menu.charAt(j);
                            if (Character.isDigit(c)) {
                                hargaStr = menu.substring(j);
                                break;
                            } else {
                                namaMakanan += c;
                            }
                        }
                        //mencoba mengubah harga menjadi double
                        try {
                            harga = Double.parseDouble(hargaStr);
                            Menu menuBaru = new Menu(namaMakanan, harga);
                            newRestaurant.addMenu(menuBaru);
                        } catch (NumberFormatException e) {
                            hargaValid = false;
                        }
                    }
                    //jika harga bukan double atau mengandung string
                    if (hargaValid == false) {
                        System.out.println("Harga menu harus bilangan bulat!");
                        System.out.println();
                    //jika semua valid
                    } else {
                        restoList.add(newRestaurant);
                        System.out.println("Restaurant " + namaRestoran + " Berhasil terdaftar.");
                        allValid = true;
                    }
                    //jika restoran sudah ada di resto list
                } else {
                    System.out.println("Restoran dengan nama " + namaRestoran
                            + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda!");
                    System.out.println();
                }
            }
        }
    }

     // method untuk handle ketika admin ingin hapus restoran
    public static void handleHapusRestoran() {
        System.out.println("--------------Hapus Restoran---------------");
        boolean allValid = false;
        while (allValid == false) {
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();
            boolean restaurantFound = false;
            //mengiterasi restolist 
            for (int i = 0; i < restoList.size(); i++) {
                Restaurant restaurant = restoList.get(i);
                //jika resto di restolist sesuai dengan nama restoran yang diinginkan
                if (restaurant.getName().equalsIgnoreCase(namaRestoran)) {
                    //menghapus restoran dari restolist
                    restoList.remove(i);
                    restaurantFound = true;
                    break;
                }
            }
            //jika restoran ditemukan
            if (restaurantFound == true) {
                System.out.println("Restoran berhasil dihapus.");
                allValid = true;
            //jika restoran tidak ditemukan
            } else {
                System.out.println("Restoran tidak terdaftar dalam sistem.");
            }
        }
    }
    //method static untuk inisiasi user
    public static void initUser() {
        userList = new ArrayList<User>();
        userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
        userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }
    //method untuk print header
    public static void printHeader() {
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }
    //method pilihan menu awal
    public static void startMenu() {
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
    //method menampilkan menu admin
    public static void menuAdmin() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
    //method menampilkan menu customer
    public static void menuCustomer() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }
}