public class Computer {
    // Attributes of the Computer
    private final String CPU;
    private final String RAM;
    private final String storage;
    private final boolean graphicsCard;
    private final boolean wifi;

    // Private constructor to enforce use of Builder
    private Computer(Builder builder) {
        this.CPU = builder.CPU;
        this.RAM = builder.RAM;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.wifi = builder.wifi;
    }

    // Static nested Builder class
    public static class Builder {
        private final String CPU;
        private final String RAM;
        private String storage = "256GB SSD"; // Default value
        private boolean graphicsCard = false;
        private boolean wifi = true;

        // Constructor for mandatory attributes
        public Builder(String CPU, String RAM) {
            this.CPU = CPU;
            this.RAM = RAM;
        }

        // Setter methods for optional attributes
        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder setGraphicsCard(boolean graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        public Builder setWifi(boolean wifi) {
            this.wifi = wifi;
            return this;
        }

        // Build method to create a Computer instance
        public Computer build() {
            return new Computer(this);
        }
    }

    // Getters for attributes
    public String getCPU() {
        return CPU;
    }

    public String getRAM() {
        return RAM;
    }

    public String getStorage() {
        return storage;
    }

    public boolean hasGraphicsCard() {
        return graphicsCard;
    }

    public boolean hasWifi() {
        return wifi;
    }

    @Override
    public String toString() {
        return "Computer{" +
                "CPU='" + CPU + '\'' +
                ", RAM='" + RAM + '\'' +
                ", storage='" + storage + '\'' +
                ", graphicsCard=" + graphicsCard +
                ", wifi=" + wifi +
                '}';
    }
}