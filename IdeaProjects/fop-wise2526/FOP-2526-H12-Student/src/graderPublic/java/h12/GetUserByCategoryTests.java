package h12;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@Tag("H12.7.5")
@TestForSubmission
public class GetUserByCategoryTests {

    @Tag("H12.7.5")
    @Test
    public void testNormal() {
        Set<UserAccount> vssAccounts = new HashSet<>();
        VideoStreamingService vss = new VideoStreamingService(vssAccounts);

        List<String> accountNames = List.of("A", "B", "C");
        Map<Category, Set<String>> categories = IntStream.range(0, accountNames.size())
            .mapToObj(i -> Map.entry(new Category("C%d".formatted(i + 1)), Set.copyOf(accountNames.subList(i, accountNames.size()))))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        List<Video> videos = IntStream.range(0, accountNames.size())
            .mapToObj(i -> {
                VideoMetaData metaData = new VideoMetaData(new Category("C%d".formatted(i + 1)),
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

        for (Category category : categories.keySet()) {
            Context context = contextBuilder()
                .add("VideoStreamingService instance", vss)
                .add("user accounts", userAccounts)
                .add("categoryName", category.name())
                .build();
            Set<String> expected = categories.get(category);
            Set<User> actual = callObject(() -> vss.getUserByCategory(category.name()), context, r ->
                "An exception occurred while invoking VideoStreamingService.getUserByCategory(String)");
            assertNotNull(actual, context, r ->
                "The value returned by VideoStreamingService.getUserByCategory(String) is null");
            assertEquals(expected, actual.stream().map(User::displayName).collect(Collectors.toUnmodifiableSet()), context, r ->
                "The set returned by VideoStreamingService.getUserByCategory(String) does not contain all expected users");
        }
    }

    @Tag("H12.7.5")
    @Test
    public void testNoUsers() {
        Set<UserAccount> vssAccounts = new HashSet<>();
        VideoStreamingService vss = new VideoStreamingService(vssAccounts);

        Context context = contextBuilder()
            .add("VideoStreamingService instance", vss)
            .add("user accounts", Collections.emptyList())
            .build();
        Set<User> actual = callObject(() -> vss.getUserByCategory("AnyCategory"), context, r ->
            "An exception occurred while invoking VideoStreamingService.getUserByCategory(String)");
        assertNotNull(actual, context, r ->
            "The value returned by VideoStreamingService.getUserByCategory(String) is null");
        assertEquals(0, actual.size(), context, r ->
            "The set returned by VideoStreamingService.getUserByCategory(String) is not empty");
    }

    @Tag("H12.7.5")
    @Test
    public void testVA() {
        TestUtils.checkVA(VideoStreamingService.class, "getUserByCategory", String.class);
    }
}
