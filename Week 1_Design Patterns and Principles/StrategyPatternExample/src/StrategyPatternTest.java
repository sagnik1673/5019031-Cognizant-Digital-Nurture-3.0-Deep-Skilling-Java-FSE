public class StrategyPatternTest {
    public static void main(String[] args) {
        // Create payment context
        PaymentContext paymentContext = new PaymentContext();
        
        // Set strategy to Credit Card and execute payment
        paymentContext.setPaymentStrategy(new CreditCardPayment("1234-5678-9876-5432", "John Doe"));
        paymentContext.executePayment(250.00);
        
        // Change strategy to PayPal and execute payment
        paymentContext.setPaymentStrategy(new PayPalPayment("john.doe@example.com"));
        paymentContext.executePayment(150.00);
    }
}