package h12;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class PlaylistTests {

    @Tag("H12.2.1")
    @ParameterizedTest
    @JsonParameterSetTest("/H12.2/H12.2.1.json")
    public void testGetTotalWatchTime(JsonParameterSet params) {
        Playlist playlist = makePlaylist(params);

        Context context = contextBuilder()
            .add("playlist", playlist)
            .build();
        Duration actual = callObject(playlist::getTotalWatchTime, context, r ->
            "An exception occurred while invoking Playlist.getTotalWatchTime()");
        assertEquals(params.get("output"), (int) actual.getSeconds(), context, r ->
            "Playlist.getTotalWatchTime() did not return the correct value");
    }

    @Tag("H12.2.1")
    @Test
    public void testGetTotalWatchTime_VA() {
        TestUtils.checkVA(Playlist.class, "getTotalWatchTime");
    }

    @Tag("H12.2.2")
    @ParameterizedTest
    @JsonParameterSetTest("/H12.2/H12.2.2.json")
    public void testGetNumberOfPublicVideos(JsonParameterSet params) {
        Playlist playlist = makePlaylist(params);

        Context context = contextBuilder()
            .add("playlist", playlist)
            .build();
        int actual = callObject(playlist::getNumberOfPublicVideos, context, r ->
            "An exception occurred while invoking Playlist.getNumberOfPublicVideos()");
        assertEquals(params.get("output"), actual, context, r ->
            "Playlist.getNumberOfPublicVideos() did not return the correct value");
    }

    @Tag("H12.2.2")
    @Test
    public void testGetNumberOfPublicVideos_VA() {
        TestUtils.checkVA(Playlist.class, "getNumberOfPublicVideos");
    }

    @Tag("H12.2.3")
    @ParameterizedTest
    @JsonParameterSetTest("/H12.2/H12.2.3.json")
    public void testGroupVideosByUser(JsonParameterSet params) {
        Playlist playlist = makePlaylist(params);
        Duration duration = Duration.of(params.get("duration", Integer.class), ChronoUnit.SECONDS);

        Context context = contextBuilder()
            .add("playlist", playlist)
            .build();
        Map<User, Set<Video>> expected = params.<List<String>>get("output")
            .stream()
            .flatMap(title -> playlist.videos().stream().filter(video -> video.title().equals(title)))
            .collect(Collectors.groupingBy(Video::uploader, Collectors.toSet()));
        Map<User, Set<Video>> actual = callObject(() -> playlist.groupVideosByUser(duration), context, r ->
            "An exception occurred while invoking Playlist.groupVideosByUser(Duration)");
        assertEquals(expected, actual, context, r ->
            "Playlist.groupVideosByUser(Duration) did not return the correct value");
    }

    @Tag("H12.2.3")
    @Test
    public void testGroupVideosByUser_VA() {
        TestUtils.checkVA(Playlist.class, "groupVideosByUser", Duration.class);
    }

    @Tag("H12.2.4")
    @ParameterizedTest
    @JsonParameterSetTest("/H12.2/H12.2.4.json")
    public void testGenerateRandomVideoPicker(JsonParameterSet params) {
        Playlist playlist = makePlaylist(params);
        Playlist.randomizer = new Random(params.get("seed", Integer.class));

        Context context = contextBuilder()
            .add("playlist", playlist)
            .build();
        List<String> expected = params.get("output");
        List<String> actual = callObject(playlist::generateRandomVideoPicker, context, r ->
                "An exception occurred while invoking Playlist.generateRandomVideoPicker()")
            .limit(expected.isEmpty() ? 1 : expected.size())
            .map(Video::title)
            .toList();
        assertEquals(expected, actual, context, r ->
            "Playlist.generateRandomVideoPicker() did not return the correct value");
    }

    @Tag("H12.2.4")
    @Test
    public void testGenerateRandomVideoPicker_VA() {
        TestUtils.checkVA(Playlist.class, "generateRandomVideoPicker");
    }

    private static Playlist makePlaylist(JsonParameterSet params) {
        VideoStreamingService vss = new VideoStreamingService();
        Map<String, User> users = params.<List<Map<String, ?>>>get("input")
            .stream()
            .map(map -> new User((String) map.get("uploader"), Collections.emptySet(), vss))
            .collect(Collectors.toMap(User::displayName, user -> user));
        List<Video> videos = params.<List<Map<String, ?>>>get("input")
            .stream()
            .map(map -> new Video((String) map.get("title"),
                "",
                Duration.of((Integer) map.get("duration"), ChronoUnit.SECONDS),
                Mockito.mock(),
                users.get((String) map.get("uploader")),
                LocalDateTime.now(),
                Collections.emptyList(),
                Optional.empty(),
                (Boolean) map.get("isPublic")))
            .toList();
        return new Playlist("TestPlaylist",
            Optional.of(""),
            Mockito.mock(),
            videos,
            LocalDateTime.now(),
            Collections.emptySet(),
            true,
            vss);
    }
}
