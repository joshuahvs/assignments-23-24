// package main.java.assignments.assignment2;
package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    // TODO: tambahkan attributes yang diperlukan untuk class ini
   private String nama;
   private ArrayList<Menu> menuList;

   public Restaurant(String nama){
       // TODO: buat constructor untuk class ini
       this.nama = nama;
       this.menuList = new ArrayList<>();
   }
   
   // TODO: tambahkan methods yang diperlukan untuk class ini
   //GETTER
   public String getName(){
    return nama;
   }
   public ArrayList <Menu> getMenuList() {
    return menuList;
   }

   public void addMenu(Menu menu){
    menuList.add(menu);
   }
}
