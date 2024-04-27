package assignments.assignment3.payment;
import assignments.assignment3.daritp2.*;

public class CreditCardPayment implements DepeFoodPaymentSystem {
     //TODO: implementasikan class yang implement interface di sini
    // Anda dibebaskan untuk membuat method yang diperlukan
    private double TRANSACTION_FEE_PERCENTAGE = 0.02 ;
    @Override
    public void processPayment(User userLoggedIn, Restaurant restaurant,Order order,long amount) {
        long fee = countTransactionFee(amount);
        long totalPayment = amount + fee;
        // Simulate Credit Card Processing...

        System.out.println("Processing Credit Card Payment of Rp " + totalPayment);
        System.out.println("Transaction fee: Rp " + fee);
    }

    public long countTransactionFee(long amount){
        return (long) (amount*TRANSACTION_FEE_PERCENTAGE);
    }
}
