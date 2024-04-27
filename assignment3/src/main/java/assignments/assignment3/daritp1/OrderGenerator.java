package assignments.assignment3.daritp1;

import java.util.Scanner;
import java.time.*;
import java.time.format.*;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);
    final static String code39CharacterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; //menyimpan kode konversi 
    /*
     * Method ini untuk menampilkan menu
     */
    public static void showMenu() {
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    /*
     * Method ini digunakan untuk membuat ID
     * dari nama restoran, tanggal order, dan nomor telepon
     */
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String output = ""; 
        String orderID = "";

        String namaRestornaNoSpaces = namaRestoran.replace(" ", ""); // Menghapus spasi di nama restoran
        String namaRestoranUpperSpace = namaRestornaNoSpaces.toUpperCase(); // Membuat nama restoran huruf besar
        String fourLetter = namaRestoranUpperSpace.substring(0, 4); // slicing 4 huruf pertama
        output += fourLetter;
        orderID += fourLetter;

        String tanggalOrderNoSlash = tanggalOrder.replace("/", ""); // Menghapus tanda "/"
        // Menambahkan tanggal ke variabel
        output += tanggalOrderNoSlash;
        orderID += tanggalOrderNoSlash;
        // Menambah seluruh no dari nomer telepon
        int sumOfNumber = 0;
        for (int i = 0; i < noTelepon.length(); i++) {
            sumOfNumber += Integer.parseInt(noTelepon.substring(i, i + 1));
        }
        // Kalau misal hasil mod kurang dari 1, menambahkan 0 ke depannya
        String resultNumber;
        int modOfSum = sumOfNumber % 100;
        if (modOfSum < 10) {
            resultNumber = "0" + modOfSum;
        } else {
            resultNumber = Integer.toString(modOfSum);
        }
        orderID += resultNumber;
        output += resultNumber;

        // checksum berdasarkan ganjil genapnya
        int checksumOdd = 0;
        int checksumEven = 0;
        // mengiterasi keseluruhan 14 karakter dan mengubahnya berdasarkan 39 code value dan menambahkan ke variabel berdasarkan genap ganjilnya
        for (int i = 0; i < 14; i++) {
            if (i % 2 == 0) { //jika genap

                char letter = orderID.charAt(i);
                checksumEven += code39CharacterSet.indexOf(letter);
            } else { //jika ganjil
                char letter = orderID.charAt(i);
                checksumOdd += code39CharacterSet.indexOf(letter);
            }
        }
        // memod kannya dengan 36
        checksumOdd = checksumOdd % 36;
        checksumEven = checksumEven % 36;
        // mengubah kembali menjadi huruf
        char charOdd = code39CharacterSet.charAt(checksumOdd);
        char charEven = code39CharacterSet.charAt(checksumEven);
        // menambahkan karakter yang sudah diubah ke output
        output += charEven;
        output += charOdd;
        return output;
    }

    /*
     * Method ini digunakan untuk membuat bill
     * dari order id dan lokasi
     */
    public static String generateBill(String OrderID, String lokasi) {
        // TODO:Lengkapi method ini sehingga dapat mengenerate Bill sesuai ketentuan
        String output = "Bill:" + "\n";
        output += "Order ID: " + OrderID + "\n";
        // slicing untuk  ambil bagian tanggal saja, lalu meformatnya menjadi dd/mm/yyyy
        String tanggalOrder = OrderID.substring(4, 12);
        String day = tanggalOrder.substring(0,2);
        String month  = tanggalOrder.substring(2,4);
        String year = tanggalOrder.substring(4, 8);
        String date = day +"/"+month+"/"+year;
        //menambakan tanggal memesanan dan lokasi pengiriman ke output
        output += "Tanggal Pemesanan: " + date + "\n";
        output += "Lokasi Pengiriman: "+ lokasi.toUpperCase() +"\n";
        String biayaOngkir = "";
        //memilih biaya ongkir berdasarkan lokasi
        switch (lokasi.toUpperCase()){
            case "P":
                biayaOngkir = "Rp 10.000";
                break;
            case "U":
                biayaOngkir = "Rp 20.000";
                break;
            case "T":
                biayaOngkir = "Rp 35.000";
                break;
            case "S":
                biayaOngkir = "Rp 40.000";
                break;
            case "B":
                biayaOngkir = "Rp 60.000";
                break;
        }
        // menambahkan biaya ongkir kirim
        output += "Biaya Ongkos Kirim: " + biayaOngkir+"\n";
        return output;
    }
    public static void main(String[] args) {
        int option;
        String namaRestoran;
        String tanggalOrder;
        String noTelepon;
        String orderID;
        showMenu();
        do {
            //menampilkan menu utama, dan meminta input user
            System.out.println("---------------------------------");
            System.out.print("Pilihan menu: ");
            option = input.nextInt();
            input.nextLine();
            System.out.println();
            //jika user memilih opsi 1 (generate order id)
            if (option == 1) {
                // menggunakan while loop agar meminta input kembali jika input tidak valid
                boolean isValid = false;
                while (isValid == false) {
                    System.out.print("Nama Restoran: ");
                    namaRestoran = input.nextLine();
                    // jika panjang restoran kurang dari empat
                    if (namaRestoran.length() < 4) {
                        System.out.println("Nama Restoran tidak valid!");
                        System.out.println();
                    //jika panjang restoran valid maka akan meminta tanggal pesanan
                    } else {
                        System.out.print("Tanggal Pemesanan: ");
                        tanggalOrder = input.nextLine();
                        boolean isValidDateFormat = isValidDateFormat(tanggalOrder);
                        //jika format data tidak valid
                        if (isValidDateFormat == false) {
                            System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!");
                            System.out.println();
                        //jika format data valid maka akan meminta nomer telepon
                        } else {
                            System.out.print("No. Telpon: ");
                            noTelepon = input.nextLine();
                            boolean isTelpValid = isTelpValid(noTelepon);
                            // jika nomer telepon tidak valid
                            if (isTelpValid == false) {
                                System.out.println("Harap masukan nomor telepon dalam bentuk bilangan bulat positif.");
                                System.out.println();
                            // jika nomer telepon valid
                            } else {
                                // mengeprint order id dan mengulangi loop
                                String orderId = generateOrderID(namaRestoran, tanggalOrder, noTelepon);
                                isValid = true;
                                System.out.println("Order ID " + orderId + " diterima!");
                                System.out.println("---------------------------------");
                                System.out.println("1. Generate Order ID");
                                System.out.println("2. Generate Bill");
                                System.out.println("3. Keluar");
                            }
                        }
                    }

                }
                // jika user memilih opsi 2
            } else if (option == 2) {
                boolean isValid = false;
                String lokasi;
                //menggunakan while loop agar mengulang pertanyaan jika salah
                while (isValid == false){
                    System.out.print("Order ID: ");
                    orderID = input.nextLine();
                    //jika panjang kurang order id dari 16
                    if (orderID.length() < 16) {
                        System.out.println("Order ID minimal 16 karakter");
                        System.out.println();
                    }else {
                        // jika order id tidak valid
                        if (isOrderIdValid(orderID) == false) {
                            System.out.println("Silahkan masukkan Order ID yang valid!");
                            System.out.println();
                        }else{
                            //meminta lokasi pengiriman
                            System.out.print("Lokasi Pengiriman: ");
                            lokasi = input.nextLine();
                            String lokasii = lokasi.toUpperCase();
                            //jika lokasi pengiriman sesuai sesuai  
                            if (lokasii.equals("P") | lokasii.equals("U") | lokasii.equals("T") | lokasii.equals("S") | lokasii.equals("B")) {
                                String bill = generateBill(orderID, lokasii);
                                System.out.println(bill);
                                System.out.println("---------------------------------");
                                System.out.println("1. Generate Order ID");
                                System.out.println("2. Generate Bill");
                                System.out.println("3. Keluar");
                                isValid = true;
                            //jika lokasi pengiriman tidak sesuai
                            }else{
                                System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                                System.out.println();
                            }
                        }
                    }
                }
            }
            // jika user memilih 3 maka do while akan berhenti
        } while (option != 3);
        System.out.println("Terima kasih telah menggunakan DepeFood!");
    }
    // method untuk memvalidasi format dari tanggal
    public static boolean isValidDateFormat(String tanggalOrder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //format yang sesuai
        try {
            LocalDate.parse(tanggalOrder, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        } 
    }
    // method untuk memvalidasi nomer telepon
    public static boolean isTelpValid(String noTelpon) {
        try {
            Long.parseLong(noTelpon);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    // method untuk memvalidasi order id
    public static boolean isOrderIdValid(String OrderID) {
        String codeToCheck = OrderID.substring(0, 14);
        String resultToCompare = OrderID.substring(14);
        String checkSum = "";
        int checksumOdd = 0;
        int checksumEven = 0;
        for (int i = 0; i < 14; i++) {
            if (i % 2 == 0) {
                char letter = codeToCheck.charAt(i);
                checksumEven += code39CharacterSet.indexOf(letter);
            } else {
                char letter = codeToCheck.charAt(i);
                checksumOdd += code39CharacterSet.indexOf(letter);
            }
        }
        checksumOdd = checksumOdd % 36;
        checksumEven = checksumEven % 36;
        char charOdd = code39CharacterSet.charAt(checksumOdd);
        char charEven = code39CharacterSet.charAt(checksumEven);
        checkSum += charEven;
        checkSum += charOdd;
        if (checkSum.equals(resultToCompare)){
            return true;
        } else{
            return false;
        }
    }


    public static int calculateDeliveryCost(String location) {
        switch (location) {
            case "P":
                return 10000;
            case "U":
                return 20000;
            case "T":
                return 35000;
            case "S":
                return 40000;
            case "B":
                return 60000;
            default:
                return 0;
        }
    }


}