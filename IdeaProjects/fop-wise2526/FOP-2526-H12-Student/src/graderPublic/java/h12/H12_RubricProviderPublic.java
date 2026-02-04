package h12;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;
import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.graderPrivateOnly;

public class H12_RubricProviderPublic implements RubricProvider {

    private static final Criterion H12_1_1 = Criterion.builder()
        .shortDescription("H12.1.1 | Video per Titel entfernen")
        .addChildCriteria(
            criterion("Die Methode remove(String) für einen Channel entfernt das Video mit dem angegeben Titel " +
                    "korrekt und gibt beim Erfolg true zurück.",
                1,
                JUnitTestRef.ofMethod(() -> ChannelTests.class.getDeclaredMethod("testRemove", JsonParameterSet.class)))
        )
        .build();
    private static final Criterion H12_1_2 = Criterion.builder()
        .shortDescription("H12.1.2 | Abonnenten im Zeitintervall")
        .addChildCriteria(
            criterion("Die Methode getSubscribersBetween(LocalDateTime, LocalDateTime) gibt für einen Channel " +
                    "alle Benutzer korrekt zurück, die zwischen dem angegebenen Zeitintervall den Channel abonniert haben.",
                1,
                JUnitTestRef.ofMethod(() -> ChannelTests.class.getDeclaredMethod("testGetSubscribersBetween", JsonParameterSet.class)),
                JUnitTestRef.ofMethod(() -> ChannelTests.class.getDeclaredMethod("testGetSubscribersBetween_VA")))
        )
        .build();
    private static final Criterion H12_1_3 = Criterion.builder()
        .shortDescription("H12.1.3 | Neueste Abonnenten eines Monats")
        .addChildCriteria(
            criterion("Die Methode getLatestSubscribers(Month, int) gibt für einen Channel die letzten n Benutzer " +
                    "korrekt zurück, die in dem angegebenen Monat den Channel abonniert haben.",
                2,
                JUnitTestRef.ofMethod(() -> ChannelTests.class.getDeclaredMethod("testGetLatestSubscribers", JsonParameterSet.class)),
                JUnitTestRef.ofMethod(() -> ChannelTests.class.getDeclaredMethod("testGetLatestSubscribers_VA")))
        )
        .build();

    private static final Criterion H12_2_1 = Criterion.builder()
        .shortDescription("H12.2.1 | Gesamte Wiedergabezeit")
        .addChildCriteria(
            criterion("Die Methode getTotalWatchTime() gibt für eine Playlist die korrekte Wiedergabezeit aller Videos zurück.",
                1,
                JUnitTestRef.ofMethod(() -> PlaylistTests.class.getDeclaredMethod("testGetTotalWatchTime", JsonParameterSet.class)),
                JUnitTestRef.ofMethod(() -> PlaylistTests.class.getDeclaredMethod("testGetTotalWatchTime_VA")))
        )
        .build();
    private static final Criterion H12_2_2 = Criterion.builder()
        .shortDescription("H12.2.2 | Anzahl öffentlicher Videos")
        .addChildCriteria(
            criterion("Die Methode getNumberOfPublicVideos() gibt für eine Playlist die korrekte Anzahl an öffentlichen Videos zurück.",
                1,
                JUnitTestRef.ofMethod(() -> PlaylistTests.class.getDeclaredMethod("testGetNumberOfPublicVideos", JsonParameterSet.class)),
                JUnitTestRef.ofMethod(() -> PlaylistTests.class.getDeclaredMethod("testGetNumberOfPublicVideos_VA")))
        )
        .build();
    private static final Criterion H12_2_3 = Criterion.builder()
        .shortDescription("H12.2.3 | Gruppierung nach Uploader")
        .addChildCriteria(
            criterion("Die Methode groupVideosByUser() gibt für eine Playlist die Videos gruppiert nach dem " +
                    "Benutzer korrekt zurück, der sie hochgeladen hat.",
                1,
                JUnitTestRef.ofMethod(() -> PlaylistTests.class.getDeclaredMethod("testGroupVideosByUser", JsonParameterSet.class)),
                JUnitTestRef.ofMethod(() -> PlaylistTests.class.getDeclaredMethod("testGroupVideosByUser_VA")))
        )
        .build();
    private static final Criterion H12_2_4 = Criterion.builder()
        .shortDescription("H12.2.4 | Zufälliger Video-Picker")
        .addChildCriteria(
            criterion("Die Methode generateRandomVideoPicker() erzeugt einen potenziell unendlichen Stream, " +
                    "der bei jedem Zugriff ein zufälliges Video aus der Playlist liefert.",
                1,
                JUnitTestRef.ofMethod(() -> PlaylistTests.class.getDeclaredMethod("testGenerateRandomVideoPicker", JsonParameterSet.class)),
                JUnitTestRef.ofMethod(() -> PlaylistTests.class.getDeclaredMethod("testGenerateRandomVideoPicker_VA")))
        )
        .build();

    private static final Criterion H12_4_1 = Criterion.builder()
        .shortDescription("H12.4.1 | Gruppierung nach Uploader")
        .addChildCriteria(
            privateOnlyCriterion("Die Methode getTotalWatchTime() gibt für einen User die korrekte Wiedergabezeit aller " +
                    "Playlists zurück. Dabei muss mapToLong verwendet werden; reduce ist nicht erlaubt.",
                1)
        )
        .build();
    private static final Criterion H12_4_2 = Criterion.builder()
        .shortDescription("H12.4.2 | Öffentliche und private Playlists")
        .addChildCriteria(
            privateOnlyCriterion("Die Methode getPublicPlaylists() gibt für einen User alle öffentlichen Playlists korrekt zurück.",
                1),
            privateOnlyCriterion("Die Methode getPrivatePlaylists() gibt für einen User alle privaten Playlists korrekt zurück.",
                1)
        )
        .build();

    private static final Criterion H12_5_1 = Criterion.builder()
        .shortDescription("H12.5.1 | Eigene und abonnierte Videos")
        .addChildCriteria(
            privateOnlyCriterion("Die Methode videos() gibt für einen UserAccount alle vom Benutzer hochgeladenen Videos korrekt zurück.",
                1),
            privateOnlyCriterion("Die Methode subscribedVideos() geben für einen UserAccount alle vom Benutzer abonnierten Channelvideos korrekt zurück.",
                1)
        )
        .build();
    private static final Criterion H12_5_2 = Criterion.builder()
        .shortDescription("H12.5.2 | Filter nach Uploader")
        .addChildCriteria(
            privateOnlyCriterion("Die Methode getSubscribedVideosByUploader(String) gibt für einen UserAccount die Videos " +
                    "korrekt zurück, deren Uploader dem angegebenen Benutzernamen entspricht.",
                1)
        )
        .build();

    private static final Criterion H12_6_1 = Criterion.builder()
        .shortDescription("H12.6.1 | Tags, Kategorien und Kanäle")
        .addChildCriteria(
            privateOnlyCriterion("Die Methode getTags() gibt für eine UserHistory alle Tags der geschauten Videos korrekt zurück.",
                1),
            privateOnlyCriterion("Die Methode getCategories() gibt für eine UserHistory alle Kategorien der geschauten Videos korrekt zurück.",
                1),
            privateOnlyCriterion("Die Methode getChannels() gibt für eine UserHistory alle Channels der geschauten Videos korrekt zurück.",
                1)
        )
        .build();
    private static final Criterion H12_6_2 = Criterion.builder()
        .shortDescription("H12.6.2 | Videos nach Kriterium filtern")
        .addChildCriteria(
            privateOnlyCriterion("Die Methode getVideosFromUser(User) gibt für eine UserHistory alle geschauten Videos korrekt " +
                    "zurück, die vom angegebenen Benutzer stammen.",
                1),
            privateOnlyCriterion("Die Methode getVideosFromChannel(Channel) gibt für eine UserHistory alle geschauten Videos " +
                    "korrekt zurück, die zu dem angegebenen Channel gehören.",
                1),
            privateOnlyCriterion("Die Methode getVideosFromTag(String) gibt für eine UserHistory alle geschauten Videos mit " +
                    "dem angegebenen Tag korrekt zurück.",
                1)
        )
        .build();

    private static final Criterion H12_7_1 = Criterion.builder()
        .shortDescription("H12.7.1 | Account hinzufügen")
        .addChildCriteria(
            privateOnlyCriterion("Die Methode addUserAccount(UserAccount) fügt einen Benutzeraccount korrekt hinzu und gibt " +
                    "bei Erfolg true zurück.",
                1)
        )
        .build();
    private static final Criterion H12_7_2 = Criterion.builder()
        .shortDescription("H12.7.2 | Account nach Name")
        .addChildCriteria(
            privateOnlyCriterion("Die Methode getUserAccountByName(String) gibt den zum angegebenen Namen passenden UserAccount korrekt zurück.",
                1)
        )
        .build();
    private static final Criterion H12_7_3 = Criterion.builder()
        .shortDescription("H12.7.3 | Aktivster Nutzer")
        .addChildCriteria(
            privateOnlyCriterion("Die Methode getMostActiveUsers() gibt den Benutzer mit der größten Anzahl gesehener Videos korrekt zurück.",
                1)
        )
        .build();
    private static final Criterion H12_7_4 = Criterion.builder()
        .shortDescription("H12.7.4 | Top-n gesehene Videos")
        .addChildCriteria(
            criterion("Die Methode getTopWatchedVideos(int) gibt die n meistgesehenen Videos korrekt zurück, " +
                    "sortiert nach Anzahl der Aufrufe in absteigender Reihenfolge.",
                1,
                JUnitTestRef.ofMethod(() -> GetTopWatchedVideosTests.class.getDeclaredMethod("testNormal")),
                JUnitTestRef.ofMethod(() -> GetTopWatchedVideosTests.class.getDeclaredMethod("testVA"))),
            criterion("Die Methode getTopWatchedVideos(int) gibt eine leere Liste zurück wenn keiner der Benutzer ein Video angesehen hat.",
                1,
                JUnitTestRef.ofMethod(() -> GetTopWatchedVideosTests.class.getDeclaredMethod("testNoVideos")),
                JUnitTestRef.ofMethod(() -> GetTopWatchedVideosTests.class.getDeclaredMethod("testVA"))),
            criterion("Die Methode getTopWatchedVideos(int) gibt eine leere Liste zurück wenn der Streaming Service keine Benutzer hat.",
                1,
                JUnitTestRef.ofMethod(() -> GetTopWatchedVideosTests.class.getDeclaredMethod("testNoUsers")),
                JUnitTestRef.ofMethod(() -> GetTopWatchedVideosTests.class.getDeclaredMethod("testVA")))
        )
        .build();
    private static final Criterion H12_7_5 = Criterion.builder()
        .shortDescription("H12.7.5 | Nutzer nach Kategorie")
        .addChildCriteria(
            criterion("Die Methode getUserByCategory(String) gibt alle Benutzer korrekt zurück, die Videos mit " +
                    "der angegebenen Kategorie geschaut haben.",
                1,
                JUnitTestRef.ofMethod(() -> GetUserByCategoryTests.class.getDeclaredMethod("testNormal")),
                JUnitTestRef.ofMethod(() -> GetUserByCategoryTests.class.getDeclaredMethod("testVA"))),
            criterion("Die Methode getUserByCategory(String) gibt eine leere Liste zurück wenn der Streaming Service keine Benutzer hat.",
                1,
                JUnitTestRef.ofMethod(() -> GetUserByCategoryTests.class.getDeclaredMethod("testNoUsers")),
                JUnitTestRef.ofMethod(() -> GetUserByCategoryTests.class.getDeclaredMethod("testVA")))
        )
        .build();
    private static final Criterion H12_7_6 = Criterion.builder()
        .shortDescription("H12.7.6 | Top-n Kategorien aus allen Playlists")
        .addChildCriteria(
            criterion("Die Methode getTopCategoriesFromAllPlaylists(int) gibt die n meistgesehenen Kategorien " +
                    "aus allen Playlists korrekt zurück, sortiert nach Häufigkeit.",
                1,
                JUnitTestRef.ofMethod(() -> GetTopCategoriesFromAllPlaylistsTests.class.getDeclaredMethod("testNormal")),
                JUnitTestRef.ofMethod(() -> GetTopCategoriesFromAllPlaylistsTests.class.getDeclaredMethod("testVA"))),
            criterion("Die Methode getTopCategoriesFromAllPlaylists(int) gibt eine leere Liste zurück wenn alle Playlists leer sind.",
                1,
                JUnitTestRef.ofMethod(() -> GetTopCategoriesFromAllPlaylistsTests.class.getDeclaredMethod("testNoVideos")),
                JUnitTestRef.ofMethod(() -> GetTopCategoriesFromAllPlaylistsTests.class.getDeclaredMethod("testVA"))),
            criterion("Die Methode getTopCategoriesFromAllPlaylists(int) gibt eine leere Liste zurück wenn der Streaming Service keine Benutzer hat.",
                1,
                JUnitTestRef.ofMethod(() -> GetTopCategoriesFromAllPlaylistsTests.class.getDeclaredMethod("testNoUsers")),
                JUnitTestRef.ofMethod(() -> GetTopCategoriesFromAllPlaylistsTests.class.getDeclaredMethod("testVA")))
        )
        .build();

    private static final Criterion H12_1 = Criterion.builder()
        .shortDescription("H12.1 | Channel-Streams")
        .addChildCriteria(H12_1_1, H12_1_2, H12_1_3)
        .build();
    private static final Criterion H12_2 = Criterion.builder()
        .shortDescription("H12.2 | Playlist-Streams")
        .addChildCriteria(H12_2_1, H12_2_2, H12_2_3, H12_2_4)
        .build();
    private static final Criterion H12_3 = Criterion.builder()
        .shortDescription("H12.3 | Likes und Dislikes")
        .addChildCriteria(
            privateOnlyCriterion("Die Methode likeCount() und dislikeCount() gibt die korrekte Anzahl an \"Gefällt mir\" " +
                    "bzw. \"Gefällt mir nicht\" zurück.",
                1)
        )
        .build();
    private static final Criterion H12_4 = Criterion.builder()
        .shortDescription("H12.4 | User-Aggregationen")
        .addChildCriteria(H12_4_1, H12_4_2)
        .build();
    private static final Criterion H12_5 = Criterion.builder()
        .shortDescription("H12.5 | Accounts und abonnierte Videos")
        .addChildCriteria(H12_5_1, H12_5_2)
        .build();
    private static final Criterion H12_6 = Criterion.builder()
        .shortDescription("H12.6 | Watch-History und Metadaten")
        .addChildCriteria(H12_6_1, H12_6_2)
        .build();
    private static final Criterion H12_7 = Criterion.builder()
        .shortDescription("H12.7 | Video-Streaming-Service")
        .addChildCriteria(H12_7_1, H12_7_2, H12_7_3, H12_7_4, H12_7_5, H12_7_6)
        .build();

    private static final Rubric RUBRIC = Rubric.builder()
        .title("H12 | Video-Streaming")
        .addChildCriteria(H12_1, H12_2, H12_3, H12_4, H12_5, H12_6, H12_7)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

    private static Criterion privateOnlyCriterion(String description, int points) {
        return Criterion.builder()
            .shortDescription(description)
            .minPoints(Math.min(points, 0))
            .maxPoints(Math.max(points, 0))
            .grader(graderPrivateOnly(points))
            .build();
    }
}
