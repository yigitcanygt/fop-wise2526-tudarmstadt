package h13.csv.departure;

import h13.csv.CSVFormatException;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.time.LocalTime;
import java.util.List;

/**
 * Actual departure information from live data.
 *
 * @param tripId     a unique ID identifying this departure
 * @param actualTime the train's actual departure time
 */
@DoNotTouch
public record LiveDeparture(int tripId, LocalTime actualTime) {

    public LiveDeparture {
        if (actualTime == null) {
            throw new IllegalArgumentException("actualTime must not be null");
        }
    }

    /**
     * Deserializes a row of strings into a {@link LiveDeparture} object.
     *
     * @param row a list of length 2 which contains ID and departure time as strings
     * @return a deserialized {@link LiveDeparture} object
     */
    public static LiveDeparture deserialize(List<String> row) {
        if (row.size() != 2) {
            throw new CSVFormatException("Live departure row must have 2 columns");
        }

        int tripId;
        try {
            tripId = Integer.parseInt(row.getFirst());
        } catch (NumberFormatException e) {
            throw new CSVFormatException("Trip ID column must contain a number");
        }

        LocalTime departureTime = DepartureTimes.parseTime(row.get(1));

        return new LiveDeparture(tripId, departureTime);
    }

}
