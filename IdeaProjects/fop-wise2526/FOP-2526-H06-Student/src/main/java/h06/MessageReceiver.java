package h06;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.*;

/**
 * Provides a queue of messages for a {@link Smartphone} to receive.
 * This is circular, i.e. when the last message has been received,
 * {@link MessageReceiver#nextMessage} will return the first message again.
 */
@DoNotTouch
public class MessageReceiver {
    private final List<String> messages;
    private final Deque<String> queue = new ArrayDeque<>();

    /**
     * Creates a new MessageReceiver
     *
     * @param messages the messages to receive
     */
    public MessageReceiver(List<String> messages) {
        this.messages = new ArrayList<>(messages);
        queue.addAll(messages);
    }

    /**
     * Creates a new MessageReceiver
     *
     * @param messages the messages to receive
     */
    public MessageReceiver(String[] messages) {
        this(Arrays.asList(messages));
    }

    /**
     * Creates a new MessageReceiver
     */
    public MessageReceiver() {
        this(new ArrayList<>());
    }

    /**
     * receives a message
     *
     * @return the received message
     */
    public String nextMessage() {
        if (messages.isEmpty()) {
            throw new IllegalStateException("No Messages have been sent to this Receiver");
        }
        if (queue.isEmpty()) {
            queue.addAll(messages);
        }

        if (queue.isEmpty()) {
            throw new IllegalStateException("There are no messages to receive");
        }

        return queue.removeFirst();
    }

    /**
     * sends a message to the receiver.
     * the message must contain a token.
     * the token must start with '&lt;' (left angle bracket) and end with '&gt;' (right angle bracket)
     *
     * @param message the message to be sent
     */
    public void addMessage(String message) {
        if (!message.contains("<") || !message.contains(">")) {
            throw new IllegalArgumentException("Message must contain a Token enclosed by '<' and '>'");
        }
        if (message.indexOf("<") > message.indexOf(">")) {
            throw new IllegalArgumentException("'<' must come before '>'");
        }
        messages.add(message);
        queue.add(message);
    }

}
