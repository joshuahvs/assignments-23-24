package assignments.assignment3.systemCLI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import assignments.assignment3.MainMenu;
import assignments.assignment3.daritp2.*;;

//TODO: Extends Abstract yang diberikan
public class AdminSystemCLI extends UserSystemCLI{
    private static final Scanner input = new Scanner(System.in);
    static ArrayList<Restaurant> restoList = MainMenu.getRestoList(); 

    public void run(User user) {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }

    //Menambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    protected boolean handleMenu(int command){
        switch(command){
            case 1 -> handleTambahRestoran();
            case 2 -> handleHapusRestoran();
            case 3 -> {return false;}
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //Menambahkan modifier dan membuat metode ini mengoverride dari Abstract class
    @Override
    protected void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    //Mengimplementasi method untuk handle ketika admin ingin tambah restoran
    protected void handleTambahRestoran(){
        System.out.println("--------------Tambah Restoran---------------");
        Restaurant restaurant = null;
        while (restaurant == null) {
            String namaRestaurant = getValidRestaurantName();
            restaurant = new Restaurant(namaRestaurant);
            restaurant = handleTambahMenuRestaurant(restaurant);
        }
        restoList.add(restaurant);
        System.out.print("Restaurant "+restaurant.getNama()+" Berhasil terdaftar." );
    }

    // Mengimplementasi method untuk handle ketika admin ingin menghapus restoran
    protected void handleHapusRestoran(){
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
                if (restaurant.getNama().equalsIgnoreCase(namaRestoran)) {
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

    //Method untuk menambahkan restaurant (mengikuti TP2)
    protected Restaurant handleTambahMenuRestaurant(Restaurant restoran){
        System.out.print("Jumlah Makanan: ");
        int  jumlahMenu = Integer.parseInt(input.nextLine().trim());
        boolean isMenuValid = true;
        for(int i = 0; i < jumlahMenu; i++){
            String inputValue = input.nextLine().trim();
            String[] splitter = inputValue.split(" ");
            String hargaStr = splitter[splitter.length-1];
            boolean isDigit = checkIsDigit(hargaStr);
            String namaMenu = String.join(" ", Arrays.copyOfRange(splitter, 0, splitter.length - 1));
            if(isDigit){
                int hargaMenu = Integer.parseInt(hargaStr);
                restoran.addMenu(new Menu(namaMenu, hargaMenu));
            }
            else{
                isMenuValid = false;
            }
        }
        if(!isMenuValid){
            System.out.println("Harga menu harus bilangan bulat!");
            System.out.println();
        }

        return isMenuValid? restoran : null; 
    }

    //Method untuk mendapatkan restoran dengan nama (Dari TP2)
    protected String getValidRestaurantName() {
        String name = "";
        boolean isRestaurantNameValid = false;
    
        while (!isRestaurantNameValid) {
            System.out.print("Nama: ");
            String inputName = input.nextLine().trim();
            boolean isRestaurantExist = restoList.stream()
                    .anyMatch(restoran -> restoran.getNama().toLowerCase().equals(inputName.toLowerCase()));
            boolean isRestaurantNameLengthValid = inputName.length() >= 4;
    
            if (isRestaurantExist) {
                System.out.printf("Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!%n", inputName);
                System.out.println();
            } else if (!isRestaurantNameLengthValid) {
                System.out.println("Nama Restoran tidak valid! Minimal 4 karakter diperlukan.");
                System.out.println();
            } else {
                name = inputName;
                isRestaurantNameValid = true;
            }
        }
        return name;
    }

    // Method untuk mengecheck apakah character di string sebuah digit
    protected boolean checkIsDigit(String digits){
        return  digits.chars().allMatch(Character::isDigit);
    }
}
