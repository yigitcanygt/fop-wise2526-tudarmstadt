package h12;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a channel of a user on a video streaming service.
 *
 * @param name        the name of the channel
 * @param description the description of the channel
 * @param createdAt   the time the channel was created
 * @param owner       the owner of the channel
 * @param videos      the videos uploaded to the channel
 * @param subscribers the subscribers of the channel with the time they subscribed
 * @author Nhan Huynh
 */
@DoNotTouch
public record Channel(
    String name,
    Optional<String> description,
    LocalDateTime createdAt,
    User owner,
    List<Video> videos,
    Map<User, LocalDateTime> subscribers
) {

    /**
     * The default description of a channel.
     */
    @DoNotTouch
    private static final String DEFAULT_DESCRIPTION = "";

    /**
     * Creates a new channel with the given information.
     *
     * @param name        the name of the channel
     * @param description the description of the channel
     * @param createdAt   the time the channel was created
     * @param owner       the owner of the channel
     * @param videos      the videos uploaded to the channel
     * @param subscribers the subscribers of the channel with the time they subscribed
     */
    @DoNotTouch
    public Channel(
        String name,
        String description,
        LocalDateTime createdAt,
        User owner,
        List<Video> videos,
        Map<User, LocalDateTime> subscribers
    ) {
        this(name, Optional.of(description), createdAt, owner, videos, subscribers);
    }

    /**
     * Creates a new channel with the given information.
     *
     * @param name        the name of the channel
     * @param description the description of the channel
     * @param createdAt   the time the channel was created
     * @param owner       the owner of the channel
     * @param videos      the videos uploaded to the channel
     */
    @DoNotTouch
    public Channel(
        String name,
        String description,
        LocalDateTime createdAt,
        User owner,
        List<Video> videos
    ) {
        this(name, description, createdAt, owner, videos, new HashMap<>());
    }

    /**
     * Creates a new channel with the given information.
     *
     * @param name        the name of the channel
     * @param description the description of the channel
     * @param createdAt   the time the channel was created
     * @param owner       the owner of the channel
     */
    @DoNotTouch
    public Channel(
        String name,
        String description,
        LocalDateTime createdAt,
        User owner
    ) {
        this(name, description, createdAt, owner, new ArrayList<>(), new HashMap<>());
    }

    /**
     * Creates a new channel with the given information.
     *
     * @param name        the name of the channel
     * @param createdAt   the time the channel was created
     * @param owner       the owner of the channel
     * @param videos      the videos uploaded to the channel
     * @param subscribers the subscribers of the channel with the time they subscribed
     */
    @DoNotTouch
    public Channel(
        String name,
        LocalDateTime createdAt,
        User owner,
        List<Video> videos,
        Map<User, LocalDateTime> subscribers
    ) {
        this(name, DEFAULT_DESCRIPTION, createdAt, owner, videos, subscribers);
    }

    /**
     * Creates a new channel with the given information.
     *
     * @param name      the name of the channel
     * @param createdAt the time the channel was created
     * @param owner     the owner of the channel
     * @param videos    the videos uploaded to the channel
     */
    @DoNotTouch
    public Channel(
        String name,
        LocalDateTime createdAt,
        User owner,
        List<Video> videos
    ) {
        this(name, DEFAULT_DESCRIPTION, createdAt, owner, videos);
    }

    /**
     * Creates a new channel with the given information.
     *
     * @param name      the name of the channel
     * @param createdAt the time the channel was created
     * @param owner     the owner of the channel
     */
    @DoNotTouch
    public Channel(
        String name,
        LocalDateTime createdAt,
        User owner
    ) {
        this(name, DEFAULT_DESCRIPTION, createdAt, owner);
    }

    /**
     * Adds a video to the channel. If the video is already present, it is not added again.
     *
     * @param video the video to add
     * @return {@code true} if the video was added, {@code false} if it was already present
     */
    @DoNotTouch
    public boolean add(Video video) {
        if (videos.contains(video)) {
            return false;
        }
        return videos.add(video);
    }

    /**
     * Removes a video from the channel.
     *
     * @param video the video to remove
     * @return {@code true} if the video was removed, {@code false} if it was not present
     */
    @DoNotTouch
    public boolean remove(Video video) {
        return videos.remove(video);
    }

    /**
     * Removes a video with the given title from the channel.
     *
     * @param title the title of the video to remove
     * @return {@code true} if the video was removed, {@code false} if it was not present
     */
    @StudentImplementationRequired("H12.1.1")
    public boolean remove(String title) {
        return videos.stream().filter(video -> video.title().equals(title)).findFirst().map(this::remove).orElse(false);
    }

    /**
     * Subscribes a user to the channel. If the user is already subscribed, nothing happens.
     *
     * @param user the user to subscribe
     */
    @DoNotTouch
    public void subscribe(User user) {
        if (subscribers.containsKey(user)) {
            return;
        }
        subscribers.put(user, LocalDateTime.now());
    }

    /**
     * Retrieves the subscribers who subscribed between the given start and end times.
     *
     * @param start the start time (exclusive)
     * @param end   the end time (exclusive)
     * @return the set of subscribers who subscribed between the given times
     */
    @StudentImplementationRequired("H12.1.2")
    public Set<User> getSubscribersBetween(LocalDateTime start, LocalDateTime end) {
        return subscribers.entrySet().stream()
            .filter(record -> record.getValue().isAfter(start))
            .filter(kayit -> kayit.getValue().isBefore(end))
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }

    /**
     * Retrieves the latest subscribers who subscribed in the given month, limited to the given count.
     *
     * @param month the month to filter by
     * @param count the maximum number of subscribers to return
     * @return the set of latest subscribers in the given month, limited to the given count
     */
    @StudentImplementationRequired("H12.1.3")
    public Set<User> getLatestSubscribers(Month month, int count) {
        return subscribers.entrySet().stream()
            .filter(input -> input.getValue().getMonth().equals(month))
            .sorted(Map.Entry.<User, LocalDateTime>comparingByValue().reversed())
            .limit(count)
            .map(Map.Entry::getKey)
            .collect(Collectors.toSet());
    }
}
