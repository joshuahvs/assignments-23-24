package assignments.assignment1;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

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
        // TODO:Lengkapi method ini sehingga dapat mengenerate Order ID sesuai ketentuan
        return "TP";
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
        return "Bill";
    }

    public static void main(String[] args) {
        // TODO: Implementasikan program sesuai ketentuan yang diberikan
        int option;
        String namaRestoran;
        String tanggalOrder;
        String noTelepon;
        do {
            showMenu();
            System.out.println("---------------------------------");
            System.out.print("Pilihan menu: ");
            option = input.nextInt();
            if (option == 1) {
                boolean isValid = false;
                while (isValid == false) {
                    System.out.print("Nama Restoran: ");
                    input.nextLine();
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
                                System.out.println(generateOrderID(namaRestoran, tanggalOrder, noTelepon));
                            }
                        }
                    }

                }
            } else if (option == 2){

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
        } //jaja
    }

    public static boolean isTelpValid(String noTelpon) {
        try{
            Integer.parseInt(noTelpon);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

}
