package h09.utils;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Utility class for controlling verbose output in the application.
 * Provides a way to enable or disable detailed logging.
 * <p>
 * More on streams later in the lecture!
 */
@DoNotTouch
public class Verbose {

    @DoNotTouch
    public static PrintStream out = new PrintStream(new EmptyOutputStream());

    @DoNotTouch
    public static void turnVerbose() {
        Verbose.out = System.out;
    }

    @DoNotTouch
    private static class EmptyOutputStream extends OutputStream {
        @Override
        public void write(int b) {
        }
    }

}
