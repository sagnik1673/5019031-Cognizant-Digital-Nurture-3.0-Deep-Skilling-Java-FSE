public class DecoratorPatternTest {
    public static void main(String[] args) {
        // Create a base notifier (Email)
        Notifier emailNotifier = new EmailNotifier();
        
        // Decorate with SMS functionality
        Notifier smsNotifier = new SMSNotifierDecorator(emailNotifier);
        
        // Decorate with Slack functionality
        Notifier slackNotifier = new SlackNotifierDecorator(smsNotifier);
        
        // Send notifications using the decorated notifier
        slackNotifier.send("Hello World!");
    }
}