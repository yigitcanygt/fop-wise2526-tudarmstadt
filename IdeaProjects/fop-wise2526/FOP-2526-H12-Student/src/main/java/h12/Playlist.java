package h12;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a playlist of videos on a video streaming service.
 *
 * @param name             the name of the playlist
 * @param description      the description of the playlist
 * @param owner            the owner of the playlist
 * @param videos           the videos in the playlist
 * @param createdAt        the time the playlist was created
 * @param tags             the tags of the playlist
 * @param isPublic         whether the playlist is public or private
 * @param streamingService the streaming service the playlist belongs to
 * @author Nhan Huynh
 */
@DoNotTouch
public record Playlist(
    String name,
    Optional<String> description,
    User owner,
    List<Video> videos,
    LocalDateTime createdAt,
    Set<String> tags,
    Boolean isPublic,
    VideoStreamingService streamingService
) {

    /**
     * The random number generator used for generating random videos.
     */
    static Random randomizer = new Random();

    /**
     * Creates a new playlist with the given information.
     *
     * @param name             the name of the playlist
     * @param description      the description of the playlist
     * @param owner            the owner of the playlist
     * @param videos           the videos in the playlist
     * @param createdAt        the time the playlist was created
     * @param tags             the tags of the playlist
     * @param isPublic         whether the playlist is public or private
     * @param streamingService the streaming service the playlist belongs to
     * @throws IllegalStateException if the streaming service of any video uploader does not match the streaming
     *                               service of the playlist
     */
    @DoNotTouch
    public Playlist {
        if (videos.stream()
            .map(Video::uploader)
            .map(User::streamingService)
            .anyMatch(service -> service != streamingService)) {
            throw new IllegalStateException("Streaming service does not match");
        }
    }

    /**
     * Creates a new playlist with the given information.
     *
     * @param video the videos in the playlist
     * @throws IllegalStateException if the streaming service of the video uploader does not match the streaming
     *                               service of the playlist
     */
    @DoNotTouch
    public void add(Video video) {
        if (video.uploader().streamingService() != streamingService) {
            throw new IllegalStateException("Streaming service does not match");
        }
        videos.add(video);
    }

    /**
     * Removes the given video from the playlist.
     *
     * @param video the video to remove
     */
    @DoNotTouch
    public void remove(Video video) {
        videos.remove(video);
    }

    /**
     * Removes the video with the given title from the playlist.
     *
     * @param title the title of the video to remove
     */
    @DoNotTouch
    public void remove(String title) {
        Iterator<Video> it = videos.iterator();
        while (it.hasNext()) {
            if (it.next().title().equals(title)) {
                it.remove();
                break;
            }
        }
    }

    /**
     * Returns the total watch time of all videos in the playlist.
     *
     * @return the total watch time of all videos in the playlist
     */
    @StudentImplementationRequired("H12.2.1")
    public Duration getTotalWatchTime() {
        return videos.stream()
            .map(Video::duration)
            .reduce(Duration.ZERO, Duration::plus);
    }

    /**
     * Generates an infinite stream of random videos from the playlist.
     *
     * @return an infinite stream of random videos from the playlist
     */
    @StudentImplementationRequired("H12.2.4")
    public Stream<Video> generateRandomVideoPicker() {
        return videos.isEmpty() ? Stream.empty() : Stream.generate(() -> {
            int randomIndex = randomizer.nextInt(videos.size());
            return videos.get(randomIndex);
        });
    }

    /**
     * Returns the number of public videos in the playlist.
     *
     * @return the number of public videos in the playlist
     */
    @StudentImplementationRequired("H12.2.2")
    public int getNumberOfPublicVideos() {
        return (int) videos.stream()
            .filter(Video::isPublic)
            .count();
    }

    /**
     * Returns the number of private videos in the playlist.
     *
     * @return the number of private videos in the playlist
     */
    @DoNotTouch
    public int getNumberOfPrivateVideos() {
        return videos.size() - getNumberOfPublicVideos();
    }

    /**
     * Groups the videos in the playlist by their uploader, but only includes videos with a duration greater than or
     * equal to the given duration.
     *
     * @param duration the minimum duration of the videos to include
     * @return a map of uploaders to their videos with a duration greater than or equal to the given duration
     */
    @StudentImplementationRequired("H12.2.3")
    public Map<User, Set<Video>> groupVideosByUser(Duration duration) {
        return videos.stream()
            .filter(videoRecord -> !videoRecord.duration().minus(duration).isNegative())
            .collect(Collectors.groupingBy(Video::uploader, Collectors.toSet()));
    }
}
