package h13.csv.departure;

import h13.csv.CSVFormatException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ScheduledDepartureTests {

    @Tag("H13.2.2")
    @Test
    public void testDeserialize_valid() {
        List<String> row = List.of("123", "RE42", "Darmstadt", "12:34");
        Context context = contextBuilder()
            .add("row", row)
            .build();
        ScheduledDeparture expected = new ScheduledDeparture(123, "RE42", "Darmstadt", LocalTime.of(12, 34));
        ScheduledDeparture actual = callObject(() -> ScheduledDeparture.deserialize(row), context, r ->
            "An exception occurred while invoking ScheduledDeparture.deserialize(List)");
        assertEquals(expected, actual, context, r ->
            "The object returned by ScheduledDeparture.deserialize(List) does not equal the expected one");
    }

    @Tag("H13.2.2")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 5, 6, 7, 8})
    public void testDeserialize_incorrectSize(int size) {
        List<String> row = IntStream.rangeClosed(0, size)
            .mapToObj(i -> "" + i)
            .toList();
        Context context = contextBuilder()
            .add("row", row)
            .build();
        assertThrows(CSVFormatException.class, () -> ScheduledDeparture.deserialize(row), context, r ->
            "ScheduledDeparture.deserialize(List) did not throw a CSVFormatException for an incorrect input list size");
    }

    @Tag("H13.2.2")
    @Test
    public void testDeserialize_invalidFormat() {
        List<String> row = List.of("BEEP BOOP", "RE42", "Darmstadt", "12:34");
        Context context = contextBuilder()
            .add("row", row)
            .build();
        assertThrows(CSVFormatException.class, () -> ScheduledDeparture.deserialize(row), context, r ->
            "ScheduledDeparture.deserialize(List) did not throw a CSVFormatException for an incorrectly formatted input list");
    }
}
