public class ObserverPatternTest {
    public static void main(String[] args) {
        // Create a StockMarket instance
        StockMarket stockMarket = new StockMarket("ABC Corp", 100.00);
        
        // Create observers
        Observer mobileApp = new MobileApp();
        Observer webApp = new WebApp();
        
        // Register observers
        stockMarket.registerObserver(mobileApp);
        stockMarket.registerObserver(webApp);
        
        // Change stock price and notify observers
        stockMarket.setStockPrice(105.50);
        stockMarket.setStockPrice(110.00);
        
        // Deregister an observer
        stockMarket.deregisterObserver(mobileApp);
        
        // Change stock price again and notify remaining observers
        stockMarket.setStockPrice(115.00);
    }
}