public class AdapterPatternTest {
    public static void main(String[] args) {
        // Create instances of third-party gateways
        PayPalGateway payPalGateway = new PayPalGateway();
        StripeGateway stripeGateway = new StripeGateway();

        // Create adapters for the payment processors
        PaymentProcessor payPalProcessor = new PayPalAdapter(payPalGateway);
        PaymentProcessor stripeProcessor = new StripeAdapter(stripeGateway);

        // Process payments using the adapters
        payPalProcessor.processPayment(100.00);
        stripeProcessor.processPayment(200.00);
    }
}