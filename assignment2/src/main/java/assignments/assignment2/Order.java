package main.java.assignments.assignment2;

public class Order {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String orderId;
    private String tanggal;
    private int ongkir;
    private Restaurant resto;
    private Menu[] items;

    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, Menu[] items){
        // TODO: buat constructor untuk class ini
        this.orderId = orderId;
        this.tanggal = tanggal;
        this.ongkir = ongkir;
        this.resto = resto;
        this.items = items;
    }
    
    // TODO: tambahkan methods yang diperlukan untuk class ini
}
