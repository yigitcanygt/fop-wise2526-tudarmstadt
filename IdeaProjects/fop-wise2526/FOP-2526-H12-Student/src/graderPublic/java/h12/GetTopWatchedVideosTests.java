package h12;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@Tag("H12.7.4")
@TestForSubmission
public class GetTopWatchedVideosTests {

    @Tag("H12.7.4")
    @Test
    public void testNormal() {
        Set<UserAccount> vssAccounts = new HashSet<>();
        VideoStreamingService vss = new VideoStreamingService(vssAccounts);

        List<String> accountNames = List.of("A", "B", "C");
        List<Video> videos = IntStream.range(0, accountNames.size())
            .mapToObj(i -> {
                Video video = Mockito.mock();
                Mockito.when(video.title()).thenReturn("V%d".formatted(i + 1));
                return video;
            })
            .toList();
        Map<String, UserAccount> userAccounts = new HashMap<>();
        for (int i = 0; i < accountNames.size(); i++) {
            String accountName = accountNames.get(i);

            User user = Mockito.mock();
            Mockito.when(user.displayName()).thenReturn(accountName);
            Mockito.when(user.streamingService()).thenReturn(vss);

            List<Map.Entry<Video, LocalDateTime>> history = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                history.add(Map.entry(videos.get(j), LocalDateTime.now()));
            }
            UserHistory userHistory = new UserHistory(history);

            UserAccount userAccount = Mockito.mock();
            Mockito.when(userAccount.user()).thenReturn(user);
            Mockito.when(userAccount.history()).thenReturn(userHistory);

            userAccounts.put(accountName, userAccount);
            vssAccounts.add(userAccount);
        }

        Context context = contextBuilder()
            .add("VideoStreamingService instance", vss)
            .add("user accounts", userAccounts)
            .build();
        List<Video> actual = callObject(() -> vss.getTopWatchedVideos(100), context, r ->
            "An exception occurred while invoking VideoStreamingService.getTopWatchedVideos(int)");
        assertNotNull(actual, context, r ->
            "The value returned by VideoStreamingService.getTopWatchedVideos(int) is null");
        assertEquals(videos, actual, context, r ->
            "The list returned by VideoStreamingService.getTopWatchedVideos(int) does not equal the expected one");
    }

    @Tag("H12.7.4")
    @Test
    public void testNoVideos() {
        Set<UserAccount> vssAccounts = new HashSet<>();
        VideoStreamingService vss = new VideoStreamingService(vssAccounts);

        List<String> accountNames = List.of("A", "B", "C");
        Map<String, UserAccount> userAccounts = new HashMap<>();
        for (String accountName : accountNames) {
            User user = Mockito.mock();
            Mockito.when(user.displayName()).thenReturn(accountName);
            Mockito.when(user.streamingService()).thenReturn(vss);

            List<Map.Entry<Video, LocalDateTime>> history = new ArrayList<>();
            UserHistory userHistory = new UserHistory(history);

            UserAccount userAccount = Mockito.mock();
            Mockito.when(userAccount.user()).thenReturn(user);
            Mockito.when(userAccount.history()).thenReturn(userHistory);

            userAccounts.put(accountName, userAccount);
            vssAccounts.add(userAccount);
        }

        Context context = contextBuilder()
            .add("VideoStreamingService instance", vss)
            .add("user accounts", userAccounts)
            .build();
        List<Video> actual = callObject(() -> vss.getTopWatchedVideos(100), context, r ->
            "An exception occurred while invoking VideoStreamingService.getTopWatchedVideos(int)");
        assertNotNull(actual, context, r ->
            "The value returned by VideoStreamingService.getTopWatchedVideos(int) is null");
        assertEquals(Collections.emptyList(), actual, context, r ->
            "The list returned by VideoStreamingService.getTopWatchedVideos(int) does not equal the expected one");
    }

    @Tag("H12.7.4")
    @Test
    public void testNoUsers() {
        Set<UserAccount> vssAccounts = new HashSet<>();
        VideoStreamingService vss = new VideoStreamingService(vssAccounts);

        Context context = contextBuilder()
            .add("VideoStreamingService instance", vss)
            .add("user accounts", Collections.emptyList())
            .build();
        List<Video> actual = callObject(() -> vss.getTopWatchedVideos(100), context, r ->
            "An exception occurred while invoking VideoStreamingService.getTopWatchedVideos(int)");
        assertNotNull(actual, context, r ->
            "The value returned by VideoStreamingService.getTopWatchedVideos(int) is null");
        assertEquals(0, actual.size(), context, r ->
            "The list returned by VideoStreamingService.getTopWatchedVideos(int) is not empty");
    }

    @Tag("H12.7.4")
    @Test
    public void testVA() {
        TestUtils.checkVA(VideoStreamingService.class, "getTopWatchedVideos", int.class);
    }
}
