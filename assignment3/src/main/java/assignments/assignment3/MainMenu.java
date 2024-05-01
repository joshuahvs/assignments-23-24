//Import yang diperlukan
package assignments.assignment3;
import java.util.ArrayList;
import java.util.Scanner;
import assignments.assignment3.daritp2.*;
import assignments.assignment3.systemCLI.*;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;

public class MainMenu {
    //atribut yang diperlukan
    private final Scanner input;
    private final LoginManager loginManager;
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;

    //Untuk inisiasi main menu
    public MainMenu(Scanner in, LoginManager loginManager) {
        this.input = in;
        this.loginManager = loginManager;
    }

    //inisiasi userList, restoList, dan menjalankan main menu.
    public static void main(String[] args) {
        initUser();
        restoList = new ArrayList<>();// biar tidak null
        MainMenu mainMenu = new MainMenu(new Scanner(System.in),
                new LoginManager(new AdminSystemCLI(), new CustomerSystemCLI()));
        mainMenu.run();
        System.out.println("Terima kasih telah menggunakan DepeFood!");
    }

    //Method untuk menjalankan keseluruhan program (Template)
    public void run() {
        printHeader();
        boolean exit = false;
        while (!exit) {
            startMenu();
            int choice = input.nextInt();
            input.nextLine();
            switch (choice) {
                case 1 -> login();
                case 2 -> exit = true;
                default -> System.out.println("Pilihan tidak valid, silakan coba lagi.");
            }
        }

        input.close();
    }

    //Metode untuk login sebagai admin atau customer
    private void login() {
        System.out.println("\nSilakan Login:");
        System.out.print("Nama: ");
        String nama = input.nextLine();
        System.out.print("Nomor Telepon: ");
        String noTelp = input.nextLine();

        // Memvalidasi input login dengan mengambil user yang sesuai
        User userLoggedIn = null; 
        for (User user : userList) {
            if (user.getNama().equalsIgnoreCase(nama)) {
                if (user.getNomorTelepon().equals(noTelp)) {
                    userLoggedIn = user;
                    break;
                }
            }
        }
        //Jika user ada
        if (userLoggedIn != null) {
            //Menjalankan system berdasarkan peran user
            System.out.println("Selamat Datang " + userLoggedIn.getNama() + "!");
            UserSystemCLI userSystem = loginManager.getSystem(userLoggedIn.role);
            userSystem.run(userLoggedIn);
        //Jika user tidak ditemukan
        }else{
            System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
            System.out.println();
        }
    }

    //Method untuk mendapatkan RestoList
    public static ArrayList<Restaurant> getRestoList() {
        return restoList; 
    }

    //Method untuk mendapatkan userList
    public static ArrayList<User> getUserList(){
        return userList;
    }

    //Method untuk print header (Template)
    private static void printHeader() {
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    //Method untuk menampilkan menu utama (Template)
    private static void startMenu() {
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    //Method untuk inisiasi user (Template)
    public static void initUser() {
        userList = new ArrayList<User>();

        // Adjust constructor dan atribut pada class User di Assignment 3
        userList.add(
                new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer", new DebitPayment(), 500000));
        userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer",
                new CreditCardPayment(), 2000000));
        userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer",
                new DebitPayment(), 750000));
        userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer",
                new CreditCardPayment(), 1800000));
        userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer", new DebitPayment(),
                650000));

        userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
        userList.add(
                new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin", new CreditCardPayment(), 0));
    }
}
