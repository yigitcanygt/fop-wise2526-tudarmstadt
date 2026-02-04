package h12;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents the watch history of a user on a video streaming service.
 *
 * @param videos the videos in the watch history with the time they were watched
 * @author Nhan Huynh
 */
@DoNotTouch
public record UserHistory(List<Map.Entry<Video, LocalDateTime>> videos) {

    /**
     * The number of days after which videos are removed from the history.
     */
    @DoNotTouch
    private static final int CLEANUP_DAYS = 30;

    /**
     * Creates a new empty watch history.
     */
    @DoNotTouch
    public UserHistory() {
        this(new ArrayList<>());
    }

    /**
     * Adds a video to the watch history with the current time.
     *
     * @param video the video to add
     */
    @DoNotTouch
    public void add(Video video) {
        videos.add(Map.entry(video, LocalDateTime.now()));
    }

    /**
     * Returns the number of videos in the watch history.
     *
     * @return the number of videos in the watch history
     */
    @DoNotTouch
    public int size() {
        return videos.size();
    }

    /**
     * Removes videos from the watch history that were watched more than {@value CLEANUP_DAYS} ago.
     */
    @DoNotTouch
    public void cleanup() {
        videos.removeIf(entry -> entry.getValue().isBefore(LocalDateTime.now().minusDays(CLEANUP_DAYS)));
    }

    /**
     * Clears the watch history.
     */
    @DoNotTouch
    public void clear() {
        videos.clear();
    }

    /**
     * Returns all unique tags of the videos in the watch history.
     *
     * @return all unique tags of the videos in the watch history
     */
    @StudentImplementationRequired("H12.6.1")
    public Set<String> getTags() {
        return videos.stream()
            .map(Map.Entry::getKey)
            .flatMap(videoRecord -> videoRecord.metaData().stream())
            .flatMap(metaData -> metaData.tags().stream())
            .collect(Collectors.toSet());
    }

    /**
     * Returns all unique categories of the videos in the watch history.
     *
     * @return all unique categories of the videos in the watch history
     */
    @StudentImplementationRequired("H12.6.1")
    public Set<Category> getCategories() {
        return videos.stream()
            .map(Map.Entry::getKey)
            .flatMap(videoRecord -> videoRecord.metaData().stream())
            .map(VideoMetaData::category)
            .collect(Collectors.toSet());
    }

    /**
     * Returns all unique channels of the videos in the watch history, sorted by the time they were watched (oldest first).
     *
     * @return all unique channels of the videos in the watch history, sorted by the time they were watched (oldest first)
     */
    @StudentImplementationRequired("H12.6.1")
    public List<Channel> getChannels() {
        return videos.stream()
            .sorted(Map.Entry.comparingByValue())
            .map(input -> input.getKey().channel())
            .distinct()
            .collect(Collectors.toList());
    }

    /**
     * Returns all unique videos uploaded by the given user in the watch history.
     *
     * @param user the user to get the videos of
     * @return all unique videos from the given user in the watch history
     */
    @StudentImplementationRequired("H12.6.2")
    public Set<Video> getVideosFromUploader(User user) {
        return videos.stream()
            .map(Map.Entry::getKey)
            .filter(videoRecord -> videoRecord.uploader().equals(user))
            .collect(Collectors.toSet());
    }

    /**
     * Returns all unique videos from the given channel in the watch history.
     *
     * @param channel the channel to get the videos from
     * @return all unique videos from the given channel in the watch history
     */
    @StudentImplementationRequired("H12.6.2")
    public Set<Video> getVideosFromChannel(Channel channel) {
        return videos.stream()
            .map(Map.Entry::getKey)
            .filter(videoRecord -> videoRecord.channel().equals(channel))
            .collect(Collectors.toSet());
    }

    /**
     * Returns all unique videos with the given tag in the watch history.
     *
     * @param tag the tag to get the videos from
     * @return all unique videos with the given tag in the watch history
     */
    @StudentImplementationRequired("H12.6.2")
    public Set<Video> getVideosFromTag(String tag) {
        return videos.stream()
            .map(Map.Entry::getKey)
            .filter(videoRecord -> videoRecord.metaData().stream()
                .anyMatch(metaInfo -> metaInfo.tags().contains(tag)))
            .collect(Collectors.toSet());
    }
}
