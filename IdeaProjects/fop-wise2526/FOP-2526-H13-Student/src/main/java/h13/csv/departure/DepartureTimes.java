package h13.csv.departure;

import h13.csv.CSVFormatException;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for parsing/stringify-ing dates and durations.
 */
@DoNotTouch
public final class DepartureTimes {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("H:mm");

    private DepartureTimes() {
    }

    /**
     * Parses a time in H:mm format and returns it
     *
     * @param s the string containing the time in H:mm format
     * @return the parsed time
     */
    public static LocalTime parseTime(String s) {
        try {
            return LocalTime.parse(s, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new CSVFormatException("Invalid departure time format: " + s);
        }
    }

    /**
     * Converts a time to a string in H:mm format
     *
     * @param time time to be converted
     * @return a string containing the time in H:mm format
     */
    public static String timeToString(LocalTime time) {
        return time.format(FORMATTER);
    }

    /**
     * Converts a duration to a string in hours:minutes:seconds format.
     *
     * @param delay the duration to be converted
     * @return a string containing the duration in hours:minutes:seconds format
     */
    public static String delayToString(Duration delay) {
        long seconds = delay.getSeconds(); // may be negative
        long absSeconds = Math.abs(seconds);

        String positiveString = String.format("%d:%02d:%02d", absSeconds / 3600, (absSeconds % 3600) / 60, absSeconds % 60);
        return seconds < 0 ? "-" + positiveString : positiveString;
    }

}
