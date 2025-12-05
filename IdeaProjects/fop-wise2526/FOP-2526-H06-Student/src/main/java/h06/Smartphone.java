package h06;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * A smartphone with an electric battery
 */
public class Smartphone implements ElectricallyPowered {

    @DoNotTouch
    private final TokenDictionary tokenDictionary;
    @DoNotTouch
    private final MessageReceiver messageReceiver;

    /**
     * Creates a new Smartphone
     *
     * @param tokenDictionary a dictionary to translate tokens
     * @param messageReceiver a receiver to receive messages from
     */
    @DoNotTouch
    public Smartphone(TokenDictionary tokenDictionary, MessageReceiver messageReceiver) {
        this.tokenDictionary = tokenDictionary;
        this.messageReceiver = messageReceiver;
    }
    @Override
    public PlugType getSupportedPlugType() {
        return PlugType.USB;
    }

    @Override
    public void use(int duration) {
        int counter = 0;
        while (counter < duration) {
            String message = messageReceiver.nextMessage();
            String complete = replaceToken(message);
            System.out.println(complete);
            counter = counter + 1;
        }
    }

    /**
     * replaces a token within a message
     * with a string specified in the {@link Smartphone#tokenDictionary}
     *
     * @param template a string which contains a token
     * @return the message generated from the template
     */
    @StudentImplementationRequired("H6.4.2")
    public String replaceToken(String template) {
        int beginning = template.indexOf('<');
        int end = template.indexOf('>');

        String tokenWord = template.substring(beginning, end + 1);
        String value = tokenDictionary.lookup(tokenWord);

        String result = template.replace(tokenWord, value);

        return result;
    }
}
