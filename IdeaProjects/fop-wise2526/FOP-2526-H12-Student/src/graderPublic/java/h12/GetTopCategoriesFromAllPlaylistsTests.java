package h12;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.*;
import java.util.stream.IntStream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@Tag("H12.7.6")
@TestForSubmission
public class GetTopCategoriesFromAllPlaylistsTests {

    @Tag("H12.7.6")
    @Test
    public void testNormal() {
        Set<UserAccount> vssAccounts = new HashSet<>();
        VideoStreamingService vss = new VideoStreamingService(vssAccounts);

        List<String> accountNames = List.of("A", "B", "C");
        List<Category> categories = IntStream.range(0, accountNames.size())
            .mapToObj(i -> new Category("C%d".formatted(i + 1)))
            .toList();
        List<Video> videos = IntStream.range(0, accountNames.size())
            .mapToObj(i -> {
                VideoMetaData metaData = new VideoMetaData(categories.get(i),
                    Collections.emptySet(),
                    Collections.emptySet(),
                    Collections.emptySet(),
                    Collections.emptySet());
                Video video = Mockito.mock();
                Mockito.when(video.title()).thenReturn("V%d".formatted(i + 1));
                Mockito.when(video.metaData()).thenReturn(Optional.of(metaData));
                return video;
            })
            .toList();
        List<Playlist> playlists = IntStream.range(0, accountNames.size())
            .mapToObj(i -> {
                Playlist playlist = Mockito.mock();
                Mockito.when(playlist.videos()).thenReturn(videos.subList(0, i + 1));
                return playlist;
            })
            .toList();
        Map<String, UserAccount> userAccounts = new HashMap<>();
        for (int i = 0; i < accountNames.size(); i++) {
            String accountName = accountNames.get(i);

            User user = Mockito.mock();
            Mockito.when(user.displayName()).thenReturn(accountName);
            Mockito.when(user.streamingService()).thenReturn(vss);
            Mockito.when(user.playlists()).thenReturn(Set.copyOf(playlists.subList(0, i + 1)));

            UserAccount userAccount = Mockito.mock();
            Mockito.when(userAccount.user()).thenReturn(user);

            userAccounts.put(accountName, userAccount);
            vssAccounts.add(userAccount);
        }

        Context context = contextBuilder()
            .add("VideoStreamingService instance", vss)
            .add("user accounts", userAccounts)
            .build();
        List<Category> actual = callObject(() -> vss.getTopCategoriesFromAllPlaylists(100), context, r ->
            "An exception occurred while invoking VideoStreamingService.getTopCategoriesFromAllPlaylists(int)");
        assertNotNull(actual, context, r ->
            "The value returned by VideoStreamingService.getTopCategoriesFromAllPlaylists(int) is null");
        assertEquals(categories, actual, context, r ->
            "The set returned by VideoStreamingService.getTopCategoriesFromAllPlaylists(int) does not contain all expected categories");
    }

    @Tag("H12.7.6")
    @Test
    public void testNoVideos() {
        Set<UserAccount> vssAccounts = new HashSet<>();
        VideoStreamingService vss = new VideoStreamingService(vssAccounts);

        List<String> accountNames = List.of("A", "B", "C");
        List<Category> categories = IntStream.range(0, accountNames.size())
            .mapToObj(i -> new Category("C%d".formatted(i + 1)))
            .toList();
        List<Playlist> playlists = IntStream.range(0, accountNames.size())
            .mapToObj(i -> {
                Playlist playlist = Mockito.mock();
                Mockito.when(playlist.videos()).thenReturn(Collections.emptyList());
                return playlist;
            })
            .toList();
        Map<String, UserAccount> userAccounts = new HashMap<>();
        for (int i = 0; i < accountNames.size(); i++) {
            String accountName = accountNames.get(i);

            User user = Mockito.mock();
            Mockito.when(user.displayName()).thenReturn(accountName);
            Mockito.when(user.streamingService()).thenReturn(vss);
            Mockito.when(user.playlists()).thenReturn(Set.copyOf(playlists.subList(0, i + 1)));

            UserAccount userAccount = Mockito.mock();
            Mockito.when(userAccount.user()).thenReturn(user);

            userAccounts.put(accountName, userAccount);
            vssAccounts.add(userAccount);
        }

        Context context = contextBuilder()
            .add("VideoStreamingService instance", vss)
            .add("user accounts", Collections.emptyList())
            .build();
        List<Category> actual = callObject(() -> vss.getTopCategoriesFromAllPlaylists(100), context, r ->
            "An exception occurred while invoking VideoStreamingService.getTopCategoriesFromAllPlaylists(int)");
        assertNotNull(actual, context, r ->
            "The value returned by VideoStreamingService.getTopCategoriesFromAllPlaylists(int) is null");
        assertEquals(0, actual.size(), context, r ->
            "The list returned by VideoStreamingService.getTopCategoriesFromAllPlaylists(int) is not empty");
    }

    @Tag("H12.7.6")
    @Test
    public void testNoUsers() {
        Set<UserAccount> vssAccounts = new HashSet<>();
        VideoStreamingService vss = new VideoStreamingService(vssAccounts);

        Context context = contextBuilder()
            .add("VideoStreamingService instance", vss)
            .add("user accounts", Collections.emptyList())
            .build();
        List<Category> actual = callObject(() -> vss.getTopCategoriesFromAllPlaylists(100), context, r ->
            "An exception occurred while invoking VideoStreamingService.getTopCategoriesFromAllPlaylists(int)");
        assertNotNull(actual, context, r ->
            "The value returned by VideoStreamingService.getTopCategoriesFromAllPlaylists(int) is null");
        assertEquals(0, actual.size(), context, r ->
            "The list returned by VideoStreamingService.getTopCategoriesFromAllPlaylists(int) is not empty");
    }

    @Tag("H12.7.6")
    @Test
    public void testVA() {
        TestUtils.checkVA(VideoStreamingService.class, "getTopCategoriesFromAllPlaylists", int.class);
    }
}
