// package main.java.assignments.assignment2;
package assignments.assignment2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
// import java.util.ArrayList;

public class Order {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String orderId;
    private String tanggal;
    private int ongkir;
    private Restaurant resto;
    private Menu[] items;
    private boolean orderFinished;

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, Menu[] items) {
        // TODO: buat constructor untuk class ini
        this.orderId = orderId;
        this.tanggal = tanggal;
        this.ongkir = ongkir;
        this.resto = resto;
        this.items = items;
        this.orderFinished = false;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    // GETTER
    public String getOrderId() {
        return orderId;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getOngkir() {
        return ongkir;
    }

    public Restaurant getResto() {
        return resto;
    }

    public Menu[] getItems() {
        return items;
    }
    public String getRestoName(){
        return resto.getName();
    }

    public String getStatusFull(){
        if (orderFinished == true){
            return "Finished";
        }else{
            return "Not Finished";
        }
    }

    public String getStatus(){
        if (orderFinished == true){
            return "Selesai";
        }else{
            return "Belum selesai";
        }
    }
    // SETTER
    public void setOrderFinished(boolean status) {
        this.orderFinished = status;
    }

    // method untuk memvalidasi order id
    public static boolean isOrderIdValid(String OrderID) {
        final String code39CharacterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
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
        if (checkSum.equals(resultToCompare)) {
            return true;
        } else {
            return false;
        }
    }

    // method untuk memvalidasi format dari tanggal
    public static boolean isValidDateFormat(String tanggalOrder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // format yang sesuai
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

    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        final String code39CharacterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // menyimpan kode konversi
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
        // mengiterasi keseluruhan 14 karakter dan mengubahnya berdasarkan 39 code value
        // dan menambahkan ke variabel berdasarkan genap ganjilnya
        for (int i = 0; i < 14; i++) {
            if (i % 2 == 0) { // jika genap

                char letter = orderID.charAt(i);
                checksumEven += code39CharacterSet.indexOf(letter);
            } else { // jika ganjil
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
}
