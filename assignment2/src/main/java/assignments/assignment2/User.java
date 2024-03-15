// package main.java.assignments.assignment2;
package assignments.assignment2;
import java.util.ArrayList;

public class User {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
   private String nama;
   private String nomorTelepon;
   private String email;
   private String lokasi;
   private String role;
   private ArrayList<Order> orderHistory;

   public User(String nama, String nomorTelepon, String email, String lokasi, String role){
       // TODO: buat constructor untuk class ini
       this.nama = nama;
       this.nomorTelepon = nomorTelepon;
       this.email = email;
       this.lokasi = lokasi;
       this.role = role;
       this.orderHistory = new ArrayList<>();
   }
   // TODO: tambahkan methods yang diperlukan untuk class ini
   //GETTER
   public String getNama(){
    return nama;
   }
   public String getNomorTelepon(){
    return nomorTelepon;
   }
   public String getEmail(){
    return email;
   }
   public String getLokasi(){
    return lokasi;
   }
   public String getRole(){
    return role;
   }
   public ArrayList<Order> getOrderHistory(){
    return orderHistory;
   }
   public void addOrder(Order order){
    this.orderHistory.add(order);
   }

}
