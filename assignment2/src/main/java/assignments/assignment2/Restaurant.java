<<<<<<< HEAD
// package main.java.assignments.assignment2;
package assignments.assignment2;

import java.util.ArrayList;
=======
package assignments.assignment2;
>>>>>>> ac1926fd4a6a1a4028adae5ea1351b0f244271f9

public class Restaurant {
    // menambahkan attributes yang diperlukan untuk class ini
   private String nama;
   private ArrayList<Menu> menuList;

    // membuat constructor untuk class ini
   public Restaurant(String nama){
       this.nama = nama;
       this.menuList = new ArrayList<>();
   }
   
   //GETTER
   public String getName(){
    return nama;
   }
   public ArrayList <Menu> getMenuList() {
    return menuList;
   }

   //Method untuk menanmbahkan menu kedalam menuList
   public void addMenu(Menu menu){
    menuList.add(menu);
   }
}
