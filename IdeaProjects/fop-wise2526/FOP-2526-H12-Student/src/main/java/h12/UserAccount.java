package h12;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a user account on a video streaming service.
 *
 * @param user               the user of the account
 * @param firstName          the first name of the user
 * @param lastName           the last name of the user
 * @param email              the email of the user
 * @param createdAt          the time the account was created
 * @param ownedChannels      the channels owned by the user
 * @param subscribedChannels the channels the user is subscribed to
 * @param history            the watch history of the user
 * @author Nhan Huynh
 */
@DoNotTouch
public record UserAccount(
    User user,
    String firstName,
    String lastName,
    String email,
    LocalDateTime createdAt,
    List<Channel> ownedChannels,
    List<Channel> subscribedChannels,
    UserHistory history
) {

    /**
     * Creates a new user account with the given information.
     *
     * @param user               the user of the account
     * @param firstName          the first name of the user
     * @param lastName           the last name of the user
     * @param email              the email of the user
     * @param createdAt          the time the account was created
     * @param ownedChannels      the channels owned by the user
     * @param subscribedChannels the channels the user is subscribed to
     */
    @DoNotTouch
    public UserAccount(
        User user,
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt,
        List<Channel> ownedChannels,
        List<Channel> subscribedChannels
    ) {
        this(user, firstName, lastName, email, createdAt, ownedChannels, subscribedChannels, new UserHistory());
    }

    /**
     * Creates a new user account with the given information.
     *
     * @param user          the user of the account
     * @param firstName     the first name of the user
     * @param lastName      the last name of the user
     * @param email         the email of the user
     * @param createdAt     the time the account was created
     * @param ownedChannels the channels owned by the user
     */
    @DoNotTouch
    public UserAccount(
        User user,
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt,
        List<Channel> ownedChannels
    ) {
        this(user, firstName, lastName, email, createdAt, ownedChannels, new ArrayList<>());
    }

    /**
     * Creates a new user account with the given information.
     *
     * @param user      the user of the account
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param email     the email of the user
     * @param createdAt the time the account was created
     */
    @DoNotTouch
    public UserAccount(
        User user,
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt
    ) {
        this(user, firstName, lastName, email, createdAt, new ArrayList<>());
    }

    /**
     * Creates a new user account with the given information.
     *
     * @param user      the user of the account
     * @param firstName the first name of the user
     * @param lastName  the last name of the user
     * @param email     the email of the user
     */
    @DoNotTouch
    public UserAccount(
        User user,
        String firstName,
        String lastName,
        String email
    ) {
        this(user, firstName, lastName, email, LocalDateTime.now());
    }

    /**
     * Returns all videos from the channels owned by the user.
     *
     * @return the videos from the channels owned by the user
     */
    @StudentImplementationRequired("H12.5.1")
    public Set<Video> videos() {
        return ownedChannels.stream()
            .flatMap(channel -> channel.videos().stream())
            .collect(Collectors.toSet());
    }

    /**
     * Returns all videos from the channels the user is subscribed to.
     *
     * @return the videos from the channels the user is subscribed to
     */
    @StudentImplementationRequired("H12.5.1")
    public Set<Video> subscribedVideos() {
        return subscribedChannels.stream()
            .flatMap(channelSub -> channelSub.videos().stream())
            .collect(Collectors.toSet());
    }

    /**
     * Returns all videos from the channels the user is subscribed to by the given uploader.
     *
     * @param uploaderName the name of the uploader
     * @return the videos from the channels the user is subscribed to by the given uploader
     */
    @StudentImplementationRequired("H12.5.2")
    public Set<Video> getSubscribedVideosByUploader(String uploaderName) {
        return subscribedChannels.stream()
            .flatMap(channelSubscription -> channelSubscription.videos().stream())
            .filter(videoRecord -> videoRecord.uploader().displayName().equals(uploaderName))
            .collect(Collectors.toSet());
    }
}
