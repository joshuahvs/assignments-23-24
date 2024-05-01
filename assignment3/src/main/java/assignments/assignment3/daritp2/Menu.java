package assignments.assignment3.daritp2;

public class Menu {
    //Atribut untuk menu
    private String namaMakanan;
    private double harga; 
    //constructor
    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }
    //GETTER
    public double getHarga() {
        return harga;
    }
    public String getNamaMakanan() {
        return namaMakanan;
    }
}