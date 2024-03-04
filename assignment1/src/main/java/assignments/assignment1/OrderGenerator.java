package assignments.assignment1;

import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        System.out.println("output 1"+ output);

        int sumOfNumber = 0;
        for (int i = 0; i < noTelepon.length(); i++) {
            sumOfNumber += Integer.parseInt(noTelepon.substring(i, i + 1));
        }
        System.out.println("sum of number " + sumOfNumber);
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
        System.out.println("output 2: "+ output);
        // System.out.println(checksumOdd);
        // System.out.println(checksumEven);
        checksumOdd = checksumOdd%36;
        checksumEven = checksumEven%36;

        // System.out.println(checksumOdd);
        // System.out.println(checksumEven);

        char charOdd = code39CharacterSet.charAt(checksumOdd);
        char charEven = code39CharacterSet.charAt(checksumEven);
        System.out.println("output 3: "+ output);

        System.out.println(charOdd);
        System.out.println(charEven);
    ;
        // System.out.println(checkSum);       
        output += charEven;
        output += charOdd;

        return output;
        // return "haha";
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

        return output;
    }

    public static void main(String[] args) {
        // TODO: Implementasikan program sesuai ketentuan yang diberikan
        int option;
        String namaRestoran;
        String tanggalOrder;
        String noTelepon;
        String orderID;
        do {
            showMenu();
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
                                System.out.println(generateOrderID(namaRestoran, tanggalOrder, noTelepon));
                                isValid = true;
                            }
                        }
                    }

                }
            } else if (option == 2) {
                System.out.print("Order ID: ");
                orderID = input.nextLine();
                boolean isValid = false;
                while (isValid == false) {
                    if (orderID.length() < 16) {
                        System.out.println("Order ID minimal 16 karakter");
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

}
