package h12;

import org.jetbrains.annotations.NotNull;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a user on a video streaming service.
 *
 * @param displayName      the display name of the user
 * @param playlists        the playlists of the user
 * @param streamingService the streaming service the user belongs to
 * @author Nhan Huynh
 */
@DoNotTouch
public record User(
    String displayName,
    Set<Playlist> playlists,
    VideoStreamingService streamingService
) {

    /**
     * Returns the total watch time of all videos in all playlists of the user.
     *
     * @return the total watch time of all videos in all playlists of the user
     */
    @StudentImplementationRequired("H12.4.1")
    public Duration getTotalWatchTime() {
        long toplamMilisaniye = playlists.stream()
            .mapToLong(liste -> liste.getTotalWatchTime().toMillis())
            .sum();
        return Duration.ofMillis(toplamMilisaniye);
    }

    /**
     * Returns the public playlists of the user.
     *
     * @return the public playlists of the user
     */
    @StudentImplementationRequired("H12.4.2")
    public Set<Playlist> getPublicPlaylists() {
        return playlists.stream()
            .filter(playlistRecord -> playlistRecord.isPublic())
            .collect(Collectors.toSet());
    }

    /**
     * Returns the private playlists of the user.
     *
     * @return the private playlists of the user
     */
    @StudentImplementationRequired("H12.4.2")
    public Set<Playlist> getPrivatePlaylists() {
        return playlists.stream()
            .filter(playlistRecord -> !playlistRecord.isPublic())
            .collect(Collectors.toSet());
    }

    @Override
    @DoNotTouch
    public @NotNull String toString() {
        return "User{displayName='%s', playlists=%s, streamingService=%s}".formatted(
            displayName,
            playlists.stream().map(Playlist::name).collect(Collectors.joining("', '", "['", "']")),
            streamingService
        );
    }
}
