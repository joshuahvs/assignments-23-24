package assignments.assignment1;

import java.util.Scanner;
import java.time.*;
import java.time.format.*;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);
    final static String code39CharacterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    /*
     * Anda boleh membuat method baru sesuai kebutuhan Anda
     * Namun, Anda tidak boleh menghapus ataupun memodifikasi return type method
     * yang sudah ada.
     */

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
     * 
     * @return String Order ID dengan format sesuai pada dokumen soal
     */
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        String output = "";
        String orderID = "";
        // TODO:Lengkapi method ini sehingga dapat mengenerate Order ID sesuai ketentuan
        String namaRestornaNoSpaces = namaRestoran.replace(" ", "");
        String namaRestoranUpperSpace = namaRestornaNoSpaces.toUpperCase();
        String fourLetter = namaRestoranUpperSpace.substring(0, 4);
        output += fourLetter;
        orderID += fourLetter;

        String tanggalOrderNoSlash = tanggalOrder.replace("/", "");
        output += tanggalOrderNoSlash;
        orderID += tanggalOrderNoSlash;

        int sumOfNumber = 0;
        for (int i = 0; i < noTelepon.length(); i++) {
            sumOfNumber += Integer.parseInt(noTelepon.substring(i, i + 1));
        }
        String resultNumber;
        int modOfSum = sumOfNumber % 100;
        if (modOfSum < 10) {
            resultNumber = "0" + modOfSum;
        } else {
            resultNumber = Integer.toString(modOfSum);
        }
        orderID += resultNumber;
        output += resultNumber;

        System.out.println("OrderID sementara: " + orderID);
        int checksumOdd = 0;
        int checksumEven = 0;
        for (int i = 0; i < 14; i++) {
            if (i % 2 == 0) {
                char letter = orderID.charAt(i);
                checksumEven += code39CharacterSet.indexOf(letter);
            } else {
                char letter = orderID.charAt(i);
                checksumOdd += code39CharacterSet.indexOf(letter);
            }
        }
        checksumOdd = checksumOdd % 36;
        checksumEven = checksumEven % 36;

        char charOdd = code39CharacterSet.charAt(checksumOdd);
        char charEven = code39CharacterSet.charAt(checksumEven);

        output += charEven;
        output += charOdd;

        return output;
    }

    /*
     * Method ini digunakan untuk membuat bill
     * dari order id dan lokasi
     * 
     * @return String Bill dengan format sesuai di bawah:
     * Bill:
     * Order ID: [Order ID]
     * Tanggal Pemesanan: [Tanggal Pemesanan]
     * Lokasi Pengiriman: [Kode Lokasi]
     * Biaya Ongkos Kirim: [Total Ongkos Kirim]
     */
    public static String generateBill(String OrderID, String lokasi) {
        // TODO:Lengkapi method ini sehingga dapat mengenerate Bill sesuai ketentuan
        String output = "Bill:" + "\n";
        output += "Order ID: " + OrderID + "\n";
        String tanggalOrder = OrderID.substring(4, 12);
        output += "Tanggal Pemesanan: " + tanggalOrder + "\n";
        String biayaOngkir = "";

        switch (lokasi.toUpperCase()){
            case "P":
                biayaOngkir = "Rp. 10.000";
                break;
            case "U":
                biayaOngkir = "Rp. 20.000";
                break;
            case "T":
                biayaOngkir = "Rp. 35.000";
                break;
            case "S":
                biayaOngkir = "Rp. 40.000";
            case "B":
                biayaOngkir = "Rp. 60.000";
        }
        output += "Biaya Ongkos Kirim: " + biayaOngkir;
        return output;
    }

    public static void main(String[] args) {
        // TODO: Implementasikan program sesuai ketentuan yang diberikan
        int option;
        String namaRestoran;
        String tanggalOrder;
        String noTelepon;
        String orderID;
        showMenu();
        do {
            System.out.println("---------------------------------");
            System.out.print("Pilihan menu: ");
            option = input.nextInt();
            input.nextLine();
            System.out.println();
            if (option == 1) {
                boolean isValid = false;
                while (isValid == false) {
                    System.out.print("Nama Restoran: ");
                    namaRestoran = input.nextLine();
                    if (namaRestoran.length() < 4) {
                        System.out.println("Nama Restoran tidak valid!");
                        System.out.println();
                    } else {
                        System.out.print("Tanggal Pemesanan: ");
                        tanggalOrder = input.nextLine();
                        boolean isValidDateFormat = isValidDateFormat(tanggalOrder);
                        if (isValidDateFormat == false) {
                            System.out.println("Tanggal Pemesanan dalam format DD/MM/YYYY!");
                            System.out.println();
                        } else {
                            System.out.print("No. Telpon: ");
                            noTelepon = input.nextLine();
                            boolean isTelpValid = isTelpValid(noTelepon);
                            if (isTelpValid == false) {
                                System.out.println("Harap masukan nomor telepon dalam bentuk bilangan bulat positif.");
                            } else {
                                String orderId = generateOrderID(namaRestoran, tanggalOrder, noTelepon);
                                isValid = true;
                                System.out.println("Order ID " + orderId + " diterima!");
                                System.out.println("---------------------------------");
                                System.out.println("1. Generate Order ID");
                                System.out.println("2. Generate Bill");
                                System.out.println("3. Keluar");
                                System.out.println("---------------------------------");
                                System.out.println("Pilih menu:");
                            }
                        }
                    }

                }
            } else if (option == 2) {
                System.out.print("Order ID: ");
                orderID = input.nextLine();
                boolean isValid = false;
                boolean isOrderIdValid = isOrderIdValid(orderID);
                String lokasi;
                while (isValid == false) {
                    if (orderID.length() < 16) {
                        System.out.println("Order ID minimal 16 karakter");
                    } else if (isOrderIdValid == false) {
                        System.out.println("Silahkan masukkan Order ID yang valid!");
                    } else {
                        System.out.print("Lokasi Pengiriman: ");
                        lokasi = input.nextLine();
                        String lokasii = lokasi.toUpperCase();
                        System.out.println();    
                        if (lokasii.equals("P") | lokasii.equals("U") | lokasii.equals("T") | lokasii.equals("S") | lokasii.equals("B")) {
                            String bill = generateBill(orderID, lokasii);
                            System.out.println(bill);
                            System.out.println("---------------------------------");
                            System.out.println("1. Generate Order ID");
                            System.out.println("2. Generate Bill");
                            System.out.println("3. Keluar");
                            System.out.println("---------------------------------");
                            System.out.println("Pilih menu:");
                            isValid = true;
                        } else {
                            System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan!");
                        }
                    }
                }
            }
        } while (option != 3);
        System.out.println("Terima kasih telah menggunakan DepeFood!");
    }

    public static boolean isValidDateFormat(String tanggalOrder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate.parse(tanggalOrder, formatter);
            return true; // Valid
        } catch (DateTimeParseException e) {
            return false; // Invalid
        } // jaja
    }

    public static boolean isTelpValid(String noTelpon) {
        try {
            Long.parseLong(noTelpon);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isOrderIdValid(String OrderID) {
        return true;
    }

}
