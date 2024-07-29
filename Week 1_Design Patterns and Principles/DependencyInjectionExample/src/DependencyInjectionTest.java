public class DependencyInjectionTest {
    public static void main(String[] args) {
        // Create the concrete repository
        CustomerRepository customerRepository = new CustomerRepositoryImpl();
        
        // Inject the repository into the service
        CustomerService customerService = new CustomerService(customerRepository);
        
        // Use the service to get customer details
        String customerName = customerService.getCustomerName("1234");
        System.out.println("Customer Name: " + customerName);
    }
}