public class CommandPatternTest {
    public static void main(String[] args) {
        // Create the receiver
        Light light = new Light();
        
        // Create command objects
        Command lightOn = new LightOnCommand(light);
        Command lightOff = new LightOffCommand(light);
        
        // Create the invoker
        RemoteControl remote = new RemoteControl();
        
        // Issue commands
        remote.setCommand(lightOn);
        remote.pressButton();
        
        remote.setCommand(lightOff);
        remote.pressButton();
    }
}