public class ProxyPatternTest {
    public static void main(String[] args) {
        // Create a proxy for the image
        Image image1 = new ProxyImage("image1.jpg");

        // Display the image (this will trigger the loading of the image from the remote server)
        image1.display();

        // Display the image again (this will use the cached image)
        image1.display();
    }
}