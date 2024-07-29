public class Logger {
    // Private static instance of the Logger class
    private static Logger instance;
    
    // Private constructor to prevent instantiation
    private Logger() {
        // Initialize logger settings here if needed
    }
    
    // Public static method to get the single instance of Logger
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    // Example method for logging
    public void log(String message) {
        System.out.println("Log: " + message);
    }
}