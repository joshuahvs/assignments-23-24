// package main.java.assignments.assignment2;
package assignments.assignment2;

public class Menu {
    // menambahkan attributes yang diperlukan untuk class ini
   private String namaMakanan;
   private double harga;
   public Menu(String namaMakanan, double harga){
       // membuat constructor untuk class ini
       this.namaMakanan = namaMakanan;
       this.harga = harga;
   }
   // menambahkan methods yang diperlukan untuk class ini
   //GETTER
   public String getNamaMakanan(){
    return namaMakanan;
   }
   public double getHarga(){
    return harga;
   }

}