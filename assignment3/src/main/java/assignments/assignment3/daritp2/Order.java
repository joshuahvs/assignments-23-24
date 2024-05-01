package assignments.assignment3.daritp2;

public class Order {
    //Atribut yang diperlukan dari class order
    private String OrderId;
    private String tanggal;
    private int ongkir;
    private Restaurant restaurant;
    private boolean orderFinished;
    private Menu[] items;

    //constructor
    public Order(String orderId, String tanggal, int ongkir, Restaurant resto, Menu[] items){
        this.OrderId = orderId;
        this.tanggal = tanggal;
        this.ongkir = ongkir;
        this.restaurant = resto;
        this.orderFinished = false;
        this.items = items;
    }
    //GETTER dan SETTER yang diperlukan
    public Restaurant getRestaurant() {
        return restaurant;
    }
    public boolean getOrderFinished(){
        return this.orderFinished;
    }
    public void setOrderFinished(boolean orderFinished) {
        this.orderFinished = orderFinished;
    }
    public String getOrderId() {
        return OrderId;
    }
    public String getTanggal() {
        return tanggal;
    }
    public int getOngkir() {
        return ongkir;
    }
    public Menu[] getItems() {
        return items;
    }
    public String getStatusFull(){
        if (orderFinished == true){
            return "Finished";
        }else{
            return "Not Finished";
        }
    }
    // Method untuk menyorting menu
    public Menu[] getSortedMenu(){
        Menu[] menuArr = new Menu[getItems().length];
        for(int i=0; i < getItems().length;i++){
            menuArr[i] = getItems()[i];
        }
        int n = menuArr.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (menuArr[j].getHarga() > menuArr[j+1].getHarga()) {
                    
                    Menu temp = menuArr[j];
                    menuArr[j] = menuArr[j+1];
                    menuArr[j+1] = temp;
                }
            }
        }
        return menuArr;
    }

    // Method untuk mendapatkan harga total
    public double getTotalHarga(){
        double sum = 0;
        for(Menu menu: getItems()){
            sum += menu.getHarga();
        }
        return sum += getOngkir();
    }
}