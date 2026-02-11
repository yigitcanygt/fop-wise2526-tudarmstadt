package h13.csv.departure;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class DepartureJoinerTests {

    private final Map<Integer, ScheduledDeparture> scheduledDepartures = Map.of(
        1, new ScheduledDeparture(1, "RE42", "Darmstadt", LocalTime.of(12, 34)),
        123, new ScheduledDeparture(123, "RE60", "Frankfurt (Main)", LocalTime.of(13, 23)),
        88932, new ScheduledDeparture(88932, "RB67/68", "Frankfurt (Main)", LocalTime.of(14, 0)),
        1013, new ScheduledDeparture(1013, "S6", "Groß Karben", LocalTime.of(13, 35)),
        2934, new ScheduledDeparture(2934, "ICE2934", "Frankfurt (Main)", LocalTime.of(13, 27))
    );
    private final Map<Integer, LiveDeparture> liveDepartures = Map.of(
        1, new LiveDeparture(1, LocalTime.of(12, 34)),
        123, new LiveDeparture(123, LocalTime.of(13, 30)),
        1013, new LiveDeparture(1013, LocalTime.of(13, 30)),
        2934, new LiveDeparture(2934, LocalTime.of(13, 30)),
        101010, new LiveDeparture(101010, LocalTime.of(13, 50))
    );

    @Tag("H13.2.3")
    @Test
    public void testJoinTables_departureTime() {
        Map<Integer, LocalTime> expectedTimes = scheduledDepartures.values()
            .stream()
            .map(scheduledDeparture -> liveDepartures.getOrDefault(scheduledDeparture.tripId(), null))
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(LiveDeparture::tripId, LiveDeparture::actualTime));
        JoinTablesResult result = testJoinTables();
        Set<JoinedDeparture> joinedDepartures = result.joinedDepartures()
            .stream()
            .filter(joinedDeparture -> expectedTimes.containsKey(joinedDeparture.tripId()))
            .collect(Collectors.toUnmodifiableSet());

        assertEquals(expectedTimes.size(), joinedDepartures.size(), result.context(), r ->
            "The size of the returned (and filtered) list is incorrect");
        for (JoinedDeparture joinedDeparture : joinedDepartures) {
            Context context = contextBuilder()
                .add(result.context())
                .add("JoinedDeparture object under test", joinedDeparture)
                .build();
            assertEquals(expectedTimes.get(joinedDeparture.tripId()), joinedDeparture.actualTime(), context, r ->
                "The actual departure time of the tested JoinedDeparture object is incorrect");
        }
    }

    @Tag("H13.2.3")
    @Test
    public void testJoinTables_delay() {
        JoinTablesResult result = testJoinTables();
        List<JoinedDeparture> joinedDepartures = result.joinedDepartures();

        assertEquals(scheduledDepartures.size(), joinedDepartures.size(), result.context(), r ->
            "The size of the returned list is incorrect");
        for (JoinedDeparture joinedDeparture : joinedDepartures) {
            Context context = contextBuilder()
                .add(result.context())
                .add("JoinedDeparture object under test", joinedDeparture)
                .build();
            int tripId = joinedDeparture.tripId();
            assertTrue(scheduledDepartures.containsKey(tripId), context, r ->
                "The JoinedDeparture object's tripId does not exist in the scheduled departures");
            if (!liveDepartures.containsKey(tripId)) continue;
            ScheduledDeparture scheduledDeparture = scheduledDepartures.get(tripId);
            LiveDeparture liveDeparture = liveDepartures.get(tripId);
            assertEquals(Duration.between(scheduledDeparture.scheduledTime(), liveDeparture.actualTime()), joinedDeparture.delay(), context, r ->
                "The delay of the tested JoinedDeparture object is incorrect");
        }
    }

    @Tag("H13.2.3")
    @Test
    public void testJoinTables_missing() {
        Set<Integer> filteredIds = scheduledDepartures.keySet()
            .stream()
            .filter(Predicate.not(liveDepartures::containsKey))
            .collect(Collectors.toSet());
        JoinTablesResult result = testJoinTables();
        Set<JoinedDeparture> joinedDepartures = result.joinedDepartures()
            .stream()
            .filter(joinedDeparture -> filteredIds.contains(joinedDeparture.tripId()))
            .collect(Collectors.toUnmodifiableSet());

        assertEquals(filteredIds.size(), joinedDepartures.size(), result.context(), r ->
            "The size of the returned (and filtered) list is incorrect");
        for (JoinedDeparture joinedDeparture : joinedDepartures) {
            Context context = contextBuilder()
                .add(result.context())
                .add("JoinedDeparture object under test", joinedDeparture)
                .build();
            assertEquals(joinedDeparture.scheduledTime(), joinedDeparture.actualTime(), context, r ->
                "The actual departure time is not equal to the scheduled departure time");
            assertEquals(Duration.ZERO, joinedDeparture.delay(), context, r ->
                "The delay of the tested JoinedDeparture object is incorrect");
        }
    }

    @Tag("H13.2.3")
    @Test
    public void testJoinTables_early() {
        Map<Integer, Duration> expectedDelay = scheduledDepartures.values()
            .stream()
            .map(scheduledDeparture -> {
                int tripId = scheduledDeparture.tripId();
                if (!liveDepartures.containsKey(tripId)) {
                    return null;
                } else {
                    LiveDeparture liveDeparture = liveDepartures.get(tripId);
                    return liveDeparture.actualTime().isBefore(scheduledDeparture.scheduledTime()) ?
                        Map.entry(tripId, Duration.between(scheduledDeparture.scheduledTime(), liveDeparture.actualTime())) :
                        null;
                }
            })
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        JoinTablesResult result = testJoinTables();
        Set<JoinedDeparture> joinedDepartures = result.joinedDepartures()
            .stream()
            .filter(joinedDeparture -> expectedDelay.containsKey(joinedDeparture.tripId()))
            .collect(Collectors.toUnmodifiableSet());

        assertEquals(expectedDelay.size(), joinedDepartures.size(), result.context(), r ->
            "The size of the returned (and filtered) list is incorrect");
        for (JoinedDeparture joinedDeparture : joinedDepartures) {
            Context context = contextBuilder()
                .add(result.context())
                .add("JoinedDeparture object under test", joinedDeparture)
                .build();
            assertEquals(expectedDelay.get(joinedDeparture.tripId()), joinedDeparture.delay(), context, r ->
                "The delay of the tested JoinedDeparture object is incorrect");
        }
    }

    @Tag("H13.2.3")
    @Test
    public void testJoinTables_entries() {
        JoinTablesResult result = testJoinTables();
        List<JoinedDeparture> joinedDepartures = result.joinedDepartures();

        for (JoinedDeparture joinedDeparture : joinedDepartures) {
            Context context = contextBuilder()
                .add(result.context())
                .add("JoinedDeparture object under test", joinedDeparture)
                .build();
            assertTrue(scheduledDepartures.containsKey(joinedDeparture.tripId()), context, r ->
                "The returned list contains an entry (tripId = %d) that doesn't exist in the scheduled departures".formatted(joinedDeparture.tripId()));
        }
    }

    private record JoinTablesResult(List<JoinedDeparture> joinedDepartures, Context context) {}

    private JoinTablesResult testJoinTables() {
        List<ScheduledDeparture> scheduledDeparturesArg = List.copyOf(scheduledDepartures.values());
        List<LiveDeparture> liveDeparturesArg = List.copyOf(liveDepartures.values());
        Context context = contextBuilder()
            .add("scheduledDepartures", scheduledDeparturesArg)
            .add("liveDepartures", liveDeparturesArg)
            .build();

        List<JoinedDeparture> joinedDepartures = callObject(() -> DepartureJoiner.joinTables(scheduledDeparturesArg, liveDeparturesArg), context, r ->
            "An exception occurred while invoking DepartureJoiner.joinTables(List, List)");
        assertNotNull(joinedDepartures, context, r -> "The value returned by DepartureJoiner.joinTables(List, List) is null");
        return new JoinTablesResult(joinedDepartures, context);
    }
}
