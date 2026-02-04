package h12;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class ChannelTests {

    @Tag("H12.1.1")
    @ParameterizedTest
    @JsonParameterSetTest("/H12.1/H12.1.1.json")
    public void testRemove(JsonParameterSet params) {
        List<Video> videos = params.<List<String>>get("input")
            .stream()
            .map(title -> {
                Video video = Mockito.mock();
                Mockito.when(video.title()).thenReturn(title);
                return video;
            })
            .collect(Collectors.toList());
        Channel channel = new Channel("TestChannel", LocalDateTime.now(), Mockito.mock(), videos);
        String title = params.get("title");

        Context context = contextBuilder()
            .add("channel", channel)
            .add("remove(String) argument 1", title)
            .build();
        assertCallEquals(params.get("output"), () -> channel.remove(title), context,
            r -> "remove(String) did not return the correct value");
    }

    @Tag("H12.1.2")
    @ParameterizedTest
    @JsonParameterSetTest("/H12.1/H12.1.2.json")
    public void testGetSubscribersBetween(JsonParameterSet params) {
        Map<User, LocalDateTime> subscribers = new HashMap<>();
        for (Map.Entry<String, Integer> entry : params.<Map<String, Integer>>get("input").entrySet()) {
            User user = Mockito.mock();
            Mockito.when(user.displayName()).thenReturn(entry.getKey());
            subscribers.put(user, LocalDateTime.ofEpochSecond(entry.getValue(), 0, ZoneOffset.UTC));
        }
        Set<User> users = Set.copyOf(subscribers.keySet());
        Channel channel = new Channel("TestChannel", LocalDateTime.now(), Mockito.mock(), Collections.emptyList(), subscribers);
        LocalDateTime start = LocalDateTime.ofEpochSecond(params.get("start", Integer.class), 0, ZoneOffset.UTC);
        LocalDateTime end = LocalDateTime.ofEpochSecond(params.get("end", Integer.class), 0, ZoneOffset.UTC);

        Context context = contextBuilder()
            .add("channel", channel)
            .add("getSubscribersBetween(LocalDateTime, LocalDateTime) argument 1", start)
            .add("getSubscribersBetween(LocalDateTime, LocalDateTime) argument 2", end)
            .build();
        Set<User> actual = callObject(() -> channel.getSubscribersBetween(start, end), context, r ->
            "An exception occurred while invoking getSubscribersBetween(LocalDateTime, LocalDateTime)");
        Set<User> expected = users.stream()
            .filter(user -> params.get("output", List.class).contains(user.displayName()))
            .collect(Collectors.toUnmodifiableSet());
        assertEquals(expected, actual, context, r ->
            "getSubscribersBetween(LocalDateTime, LocalDateTime) did not return the expected users");
    }

    @Tag("H12.1.2")
    @Test
    public void testGetSubscribersBetween_VA() {
        TestUtils.checkVA(Channel.class, "getSubscribersBetween", LocalDateTime.class, LocalDateTime.class);
    }

    @Tag("H12.1.3")
    @ParameterizedTest
    @JsonParameterSetTest("/H12.1/H12.1.3.json")
    public void testGetLatestSubscribers(JsonParameterSet params) {
        Map<User, LocalDateTime> subscribers = new HashMap<>();
        for (Map.Entry<String, String> entry : params.<Map<String, String>>get("input").entrySet()) {
            User user = Mockito.mock();
            Mockito.when(user.displayName()).thenReturn(entry.getKey());
            subscribers.put(user, LocalDateTime.parse(entry.getValue()));
        }
        Set<User> users = Set.copyOf(subscribers.keySet());
        Channel channel = new Channel("TestChannel", LocalDateTime.now(), Mockito.mock(), Collections.emptyList(), subscribers);
        Month month = Month.valueOf(params.get("month"));
        int count = params.get("count");

        Context context = contextBuilder()
            .add("channel", channel)
            .add("getLatestSubscribers(Month, int) argument 1", month)
            .add("getLatestSubscribers(Month, int) argument 2", count)
            .build();
        Set<User> actual = callObject(() -> channel.getLatestSubscribers(month, count), context, r ->
            "An exception occurred while invoking getLatestSubscribers(Month, int)");
        Set<User> expected = users.stream()
            .filter(user -> params.get("output", List.class).contains(user.displayName()))
            .collect(Collectors.toUnmodifiableSet());
        assertEquals(expected, actual, context, r ->
            "getLatestSubscribers(Month, int) did not return the expected users");
    }

    @Tag("H12.1.3")
    @Test
    public void testGetLatestSubscribers_VA() {
        TestUtils.checkVA(Channel.class, "getLatestSubscribers", Month.class, int.class);
    }
}
