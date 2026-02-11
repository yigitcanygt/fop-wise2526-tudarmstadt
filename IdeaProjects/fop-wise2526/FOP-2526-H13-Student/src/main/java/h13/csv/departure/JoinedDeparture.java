package h13.csv.departure;
import java.util.ArrayList;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

/**
 * Departure information that contains both, departure times from the schedule and actual times from live data.
 *
 * @param tripId        a unique ID identifying this departure
 * @param trainLine     the train's line/display name on the departure display
 * @param destination   the train's destination
 * @param scheduledTime the train's scheduled (planned) departure time
 * @param actualTime    the train's actual departure time according to live data
 * @param delay         the train's delay (actualTime - scheduledTime)
 */
public record JoinedDeparture(int tripId, String trainLine, String destination, LocalTime scheduledTime, LocalTime actualTime, Duration delay) {

    @DoNotTouch
    public JoinedDeparture {
        if (trainLine == null) {
            throw new IllegalArgumentException("trainLine must not be null");
        }
        if (destination == null) {
            throw new IllegalArgumentException("destination must not be null");
        }
        if (scheduledTime == null) {
            throw new IllegalArgumentException("scheduledTime must not be null");
        }
        if (actualTime == null) {
            throw new IllegalArgumentException("actualTime must not be null");
        }
        if (delay == null) {
            throw new IllegalArgumentException("delay must not be null");
        }
    }

    /**
     * Serializes this departure into a list of strings with the following order:
     * ID, train line, destination, scheduled departure time, actual departure time, delay
     *
     * @return a list containing the above-mentioned data as strings
     */
    @StudentImplementationRequired("H13.2.4")
    public List<String> serialize() {
        List<String> lineDatas = new ArrayList<>();
        lineDatas.add(String.valueOf(tripId));
        lineDatas.add(trainLine);
        lineDatas.add(destination);
        lineDatas.add(DepartureTimes.timeToString(scheduledTime));
        lineDatas.add(DepartureTimes.timeToString(actualTime));
        lineDatas.add(DepartureTimes.delayToString(delay));
        return lineDatas;
    }
}
