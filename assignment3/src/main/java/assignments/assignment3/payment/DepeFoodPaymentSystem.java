//Mengimport yang diperlukan
package assignments.assignment3.payment;
import assignments.assignment3.daritp2.*;

//Interface untuk Debit card dan credit card
public interface DepeFoodPaymentSystem {
    public void processPayment(User user, Restaurant restaurant, Order order, long amount);
}
