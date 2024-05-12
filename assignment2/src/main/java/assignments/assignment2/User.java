package assignments.assignment2;
import java.util.ArrayList;

public class User {
    // menambahkan attributes yang diperlukan untuk class ini
   private String nama;
   private String nomorTelepon;
   private String email;
   private String lokasi;
   private String role;
   private ArrayList<Order> orderHistory;

    // membuat constructor untuk class ini
   public User(String nama, String nomorTelepon, String email, String lokasi, String role){
       this.nama = nama;
       this.nomorTelepon = nomorTelepon;
       this.email = email;
       this.lokasi = lokasi;
       this.role = role;
       this.orderHistory = new ArrayList<>();
   }

   // menambahkan methods yang diperlukan untuk class ini
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
   
   // Method untuk menambahkan order ke user order history
   public void addOrder(Order order){
    this.orderHistory.add(order);
   }

}