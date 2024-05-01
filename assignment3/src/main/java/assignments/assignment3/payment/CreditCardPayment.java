// Mengimport yang diperlukan
package assignments.assignment3.payment;
import assignments.assignment3.daritp2.*;

public class CreditCardPayment implements DepeFoodPaymentSystem {
    //Mengimplementasikan class yang implement interface di sini
    // Anda dibebaskan untuk membuat method yang diperlukan
    private final double TRANSACTION_FEE_PERCENTAGE = 0.02 ;
    @Override
    public void processPayment(User userLoggedIn, Restaurant restaurant,Order order,long amount) {
        long fee = countTransactionFee(amount);
        long totalPayment = amount + fee;
        // Melakukan proses pembayaran menggunakan kartu kredit
        userLoggedIn.setSaldo(userLoggedIn.getSaldo()-totalPayment);
        restaurant.setSaldo(restaurant.getSaldo()+amount);
        order.setOrderFinished(true);
        System.out.println("Berhasil Membayar Bill sebesar Rp " + amount + " dengan biaya transaksi sebesar Rp " + fee);
        
    }

    // Method untuk menghitung biaya transaksi
    public long countTransactionFee(long amount){
        return (long) (amount*TRANSACTION_FEE_PERCENTAGE);
    }
}
