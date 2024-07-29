public class BuilderPatternTest {
    public static void main(String[] args) {
        // Create a basic Computer with default settings
        Computer basicComputer = new Computer.Builder("Intel i5", "8GB RAM").build();
        System.out.println("Basic Computer: " + basicComputer);

        // Create a Computer with additional features
        Computer advancedComputer = new Computer.Builder("AMD Ryzen 7", "16GB RAM")
                .setStorage("1TB SSD")
                .setGraphicsCard(true)
                .setWifi(true)
                .build();
        System.out.println("Advanced Computer: " + advancedComputer);
    }
}