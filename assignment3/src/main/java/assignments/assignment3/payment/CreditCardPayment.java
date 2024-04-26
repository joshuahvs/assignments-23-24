package assignments.assignment3.payment;

public class CreditCardPayment implements DepeFoodPaymentSystem {
     //TODO: implementasikan class yang implement interface di sini
    // Anda dibebaskan untuk membuat method yang diperlukan
    private double TRANSACTION_FEE_PERCENTAGE = 0.02 ;
    @Override
    public long processPayment(long amount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'processPayment'");
    }

    public long countTransactionFee(long amount){
        return (long) (amount*TRANSACTION_FEE_PERCENTAGE);
    }
}
