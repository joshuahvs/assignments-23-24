package assignments.assignment3.payment;

import assignments.assignment3.daritp2.*;

public interface DepeFoodPaymentSystem {
    public static long saldo = 0;
    void processPayment(User user, Restaurant restaurant, Order order, long amount);
}
