// package main.java.assignments.assignment2;
package assignments.assignment2;

import java.util.ArrayList;
import java.util.Scanner;
// import assignments.assignment1.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    public static void main(String[] args) {
        initUser();
        printHeader();
        restoList = new ArrayList<>();// biar tidak null
        boolean programRunning = true;
        while (programRunning) {
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if (command == 1) {
                System.out.println("\nSilakan Login:");
                System.out.print("Nama: ");
                String nama = input.nextLine();
                System.out.print("Nomor Telepon: ");
                String noTelp = input.nextLine();

                // TODO: Validasi input login
                User userLoggedIn = getUser(nama, noTelp); // TODO: lengkapi
                if (userLoggedIn == null) {
                    System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                    continue;
                }
                boolean isLoggedIn = true;

                if (userLoggedIn.getRole() == "Customer") {
                    System.out.println("Selamat Datang " + userLoggedIn.getNama() + "!");
                    while (isLoggedIn) {
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch (commandCust) {
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill(userLoggedIn);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan(userLoggedIn);
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                } else {
                    System.out.println("Selamat Datang Admin!");
                    while (isLoggedIn) {
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch (commandAdmin) {
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            } else if (command == 2) {
                programRunning = false;
            } else {
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    public static User getUser(String nama, String nomorTelepon) {
        // TODO: Implementasi method untuk mendapat user dari userList
        for (User user : userList) {
            if (user.getNama().equals(nama)) {
                if (user.getNomorTelepon().equals(nomorTelepon)) {
                    return user;
                }
            }
        }
        return null;
    }

    public static void handleBuatPesanan(User user) {
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("--------------Buat Pesanan---------------");
        boolean allValid = false;
        while (allValid == false) {
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();
            boolean restaurantFound = false;
            for (Restaurant restaurant : restoList) {
                if (restaurant.getName().equalsIgnoreCase(namaRestoran)) {
                    restaurantFound = true;
                    System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
                    String tanggalPemesanan = input.nextLine();
                    if (Order.isValidDateFormat(tanggalPemesanan)) {
                        System.out.print("Jumlah Pesanan: ");
                        int jumlahPesanan = Integer.parseInt(input.nextLine());
                        // TODO minta input order sebanyak jumlah pesanan, lalu validasi dengan
                        // mencocokan terhadap menu restoran
                        String[] tempOrderedMenuItems = new String[jumlahPesanan];
                        for (int i = 0; i < jumlahPesanan; i++) {
                            System.out.print("Pesanan ke-" + (i + 1) + ": ");
                            String pesanan = input.nextLine();
                            tempOrderedMenuItems[i] = pesanan;
                        }
                        int j = 0;
                        boolean menuFound = false;
                        Menu[] orderedMenuItems = new Menu[jumlahPesanan];
                        int count = orderedMenuItems.length;
                        for (String menuItem : tempOrderedMenuItems) {
                            for (Menu menu : restaurant.getMenuList()) {
                                if (menu.getNamaMakanan().trim().equalsIgnoreCase(menuItem)) {
                                    System.out.println("sucesss");
                                    orderedMenuItems[j] = menu; // Store the ordered item
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
                        if (menuFound == true) {
                            String orderID = Order.generateOrderID(namaRestoran, tanggalPemesanan,
                                    user.getNomorTelepon());
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
                            Order newOrder = new Order(orderID, tanggalPemesanan, biayaOngkir, restaurant,
                                    orderedMenuItems);
                            // TODO add order ke order history user
                            user.addOrder(newOrder);
                            System.out.println("Pesanan dengan ID " + orderID + " diterima!");
                            allValid = true;
                        } else {
                            System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                        }
                    } else {
                        System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY) !");
                    }
                }
            }

            if (restaurantFound == false) {
                System.out.println("Restoran tidak terdaftar pada sistem.");
            }
        }
    }

    public static void handleCetakBill(User user) {
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
        boolean orderIdFound = false;
        System.out.println("Masukkan OrderID: ");
        String orderId = input.nextLine();
        for (Order o : user.getOrderHistory()) {
            if (o.getOrderId().equals(orderId)) {
                orderIdFound = true;
                String[] tempOrderedMenuItems = new String[o.getItems().length];
                String output = "bill:\n";
                int i = 0;
                int totalHarga = 0;
                for (Menu m : o.getItems()) {
                    totalHarga += m.getHarga();
                    String menu = "- " + m.getNamaMakanan() + " " + m.getHarga();
                    tempOrderedMenuItems[i] = menu;
                    i++;
                }
                output += "Order Id: " + orderId + "\n";
                output += "Tanggal Pemesanan: " + o.getTanggal() + "\n";
                output += "Restaurant: " + o.getRestoName() + "\n";
                output += "Lokasi Pengiriman: " + user.getLokasi() + "\n";
                output += "Status Pengiriman: " + o.getStatusFull() + "\n";
                output += "Pesanan:\n";
                for (String menu : tempOrderedMenuItems) {
                    output += menu + "\n";
                }
                output += "Biaya Ongkos Kirim: Rp " + o.getOngkir() + "\n";
                output += "Total Biaya: Rp " + totalHarga + o.getOngkir();
                System.out.println(output);
            }
        }
        if (orderIdFound == false) {
            System.out.println("Order ID tidak dapat ditemukan.");
        }

    }

    public static void handleLihatMenu() {
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("--------------Lihat Menu---------------");
        System.out.print("Nama Restoran: ");
        String namaRestoran = input.nextLine();
        boolean restaurantFound = false;
        for (Restaurant restaurant : restoList) {
            if (restaurant.getName().equalsIgnoreCase(namaRestoran)) {
                restaurantFound = true;
                System.out.println("Menu:");
                ArrayList<Menu> menuList = restaurant.getMenuList();
                boolean swapped;
                do {
                    swapped = false;
                    for (int i = 0; i < menuList.size() - 1; i++) {
                        if (menuList.get(i).getHarga() > menuList.get(i + 1).getHarga()) {
                            // Swap items if out of order (price)
                            Menu temp = menuList.get(i);
                            menuList.set(i, menuList.get(i + 1));
                            menuList.set(i + 1, temp);
                            swapped = true;
                        } else if (menuList.get(i).getHarga() == menuList.get(i + 1).getHarga()) {
                            // Secondary sort - alphabetical by name
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
                for (Menu menu : restaurant.getMenuList()) {
                    System.out.println(count + ". " + menu.getNamaMakanan() + String.format("%.0f", menu.getHarga()));
                    count++;
                }
                break;
            }
        }
        if (restaurantFound == false) {
            System.out.println("Restoran tidak terdaftar pada sistem.");
        }
    }

    public static void handleUpdateStatusPesanan(User user) {
        // TODO: Implementasi method untuk handle ketika customer ingin update status
        // pesanan
        boolean orderIdFound = false;
        System.out.println("--------------Update Status Pesanan---------------");
        System.out.print("Order ID: ");
        String orderId = input.nextLine();
        for (Order o : user.getOrderHistory()) {
            if (o.getOrderId().equals(orderId)) {
                orderIdFound = true;
                System.out.print("Status: ");
                String status = input.nextLine();
                if (o.getStatus().equalsIgnoreCase(status)) {
                    System.out.println("Status pesanan dengan ID " + orderId + " tidak berhasil diupdate!");
                } else {
                    o.setOrderFinished(true);
                    System.out.println("Status pesanan dengan ID " + orderId + " berhasil diupdate!");
                }
            }
        }
        if (orderIdFound == false) {
            System.out.println("Order ID tidak dapat ditemukan.");
        }
    }

    public static void handleTambahRestoran() {
        System.out.println("--------------Tambah Restoran---------------");
        boolean allValid = false;
        while (allValid == false) {
            System.out.print("Nama: ");
            String namaRestoran = input.nextLine();
            if (namaRestoran.length() < 4) {
                System.out.println("Nama restoran tidak valid");
                System.out.println();
            } else {
                boolean restaurantFound = false;
                for (Restaurant restaurant : restoList) {
                    if (restaurant.getName().equalsIgnoreCase(namaRestoran)) {
                        restaurantFound = true;
                        break;
                    }
                }
                if (restaurantFound == false) {
                    Restaurant newRestaurant = new Restaurant(namaRestoran);
                    System.out.print("Jumlah Makanan: ");
                    int jumlahMakanan = input.nextInt();
                    input.nextLine(); // consume newline character
                    boolean hargaValid = true;
                    for (int i = 0; i < jumlahMakanan; i++) { // Use < instead of <=
                        String menu = input.nextLine();
                        String namaMakanan = "";
                        String hargaStr = "";
                        double harga;
                        for (int j = 0; j < menu.length(); j++) {
                            char c = menu.charAt(j);
                            if (Character.isDigit(c)) {
                                hargaStr = menu.substring(j);
                                break;
                            } else {
                                namaMakanan += c;
                            }
                        }
                        try {
                            harga = Double.parseDouble(hargaStr);
                            Menu menuBaru = new Menu(namaMakanan, harga);
                            newRestaurant.addMenu(menuBaru);
                        } catch (NumberFormatException e) {
                            hargaValid = false;
                        }
                    }
                    if (hargaValid == false) {
                        System.out.println("Harga menu harus bilangan bulat!");
                        System.out.println();
                    } else {
                        restoList.add(newRestaurant);
                        System.out.println("Restaurant " + namaRestoran + " Berhasil terdaftar.");
                        allValid = true;
                    }
                } else {
                    System.out.println("Restoran dengan nama " + namaRestoran
                            + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda!");
                    System.out.println();
                }
            }
        }
    }

    public static void handleHapusRestoran() {
        // TODO: Implementasi method untuk handle ketika admin ingin hapus restoran
        System.out.println("--------------Hapus Restoran---------------");
        boolean allValid = false;
        while (allValid == false) {
            System.out.print("Nama Restoran: ");
            String namaRestoran = input.nextLine();
            boolean restaurantFound = false;

            for (int i = 0; i < restoList.size(); i++) {
                Restaurant restaurant = restoList.get(i);
                if (restaurant.getName().equalsIgnoreCase(namaRestoran)) {
                    restoList.remove(i);
                    restaurantFound = true;
                    break;
                }
            }
            if (restaurantFound == true) {
                System.out.println("Restoran berhasil dihapus.");
                allValid = true;
            } else {
                System.out.println("Restoran tidak terdaftar dalam sistem.");
            }
        }
    }

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

    public static void printHeader() {
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu() {
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

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