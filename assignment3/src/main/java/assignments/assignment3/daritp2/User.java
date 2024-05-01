//Mengimport yang diperlukan
package assignments.assignment3.daritp2;
import java.util.ArrayList;
import assignments.assignment3.payment.DepeFoodPaymentSystem;

public class User {
    // Atribut untuk class user
    private String nama;
    private String nomorTelepon;
    private String email;
    private ArrayList<Order> orderHistory;
    public String role;
    private String lokasi;
    private DepeFoodPaymentSystem payment;
    private long saldo;

    // constructor
    public User(String nama, String nomorTelepon, String email, String lokasi, String role, DepeFoodPaymentSystem payment, int saldo){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        orderHistory = new ArrayList<>();
        this.payment = payment;
        this.saldo = saldo;
    }
    //GETTER DAN SETTER yang diperlukan
    public String getEmail() {
        return email;
    }
    public String getNama() {
        return nama;
    }
    public String getLokasi() {
        return lokasi;
    }
    public String getNomorTelepon() {
        return nomorTelepon;
    }
    public void addOrderHistory(Order order){
        orderHistory.add(order);
    }
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }
    public boolean isOrderBelongsToUser(String orderId) {
        for (Order order : orderHistory) {
            if (order.getOrderId().equals(orderId)) {
                return true;
            }
        }
        return false;
    }
    public DepeFoodPaymentSystem getPaymentMethod(){
        return payment;
    }
    public long getSaldo(){
        return saldo;
    }
    public void  setSaldo(long saldo){
        this.saldo = saldo;
    }

    //method toString
    @Override
    public String toString() {
        return String.format("User dengan nama %s dan nomor telepon %s", nama, nomorTelepon);
    }

}
