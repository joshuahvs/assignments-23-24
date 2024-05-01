// Mengimport yang diperlukan
package assignments.assignment3.payment;
import assignments.assignment3.daritp2.*;

public class DebitPayment implements DepeFoodPaymentSystem{
    // Anda dibebaskan untuk membuat method yang diperlukan
    private final double MINIMUM_TOTAL_PRICE = 50000;
    @Override
    // Method untuk memproses pembayaran
    public void processPayment(User userLoggedIn, Restaurant restaurant,Order order, long amount) {
        if (amount<MINIMUM_TOTAL_PRICE){
            System.out.println("Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain");
        }else if (userLoggedIn.getSaldo()<amount){
            System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
        }
        //Jika semua persyaratan terpenuhi
        else{
            userLoggedIn.setSaldo(userLoggedIn.getSaldo()-amount);
            restaurant.setSaldo(restaurant.getSaldo()+amount);
            order.setOrderFinished(true);
            System.out.println("Berhasil Membayar Bill sebesar Rp " + amount);
        }
    }

}
