package h12;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Represents a video on a video streaming service.
 *
 * @param title       the title of the video
 * @param description the description of the video
 * @param duration    the duration of the video
 * @param channel     the channel the video belongs to
 * @param uploader    the user who uploaded the video
 * @param uploadTime  the time the video was uploaded
 * @param comments    the comments on the video
 * @param metaData    the metadata of the video
 * @param isPublic    whether the video is public or private
 * @author Nhan Huynh
 */
@DoNotTouch
public record Video(
    String title,
    String description,
    Duration duration,
    Channel channel,
    User uploader,
    LocalDateTime uploadTime,
    List<Comment> comments,
    Optional<VideoMetaData> metaData,
    Boolean isPublic
) {

    /**
     * Creates a new video with the given information.
     *
     * @param title       the title of the video
     * @param description the description of the video
     * @param duration    the duration of the video
     * @param channel     the channel the video belongs to
     * @param uploader    the user who uploaded the video
     * @param uploadTime  the time the video was uploaded
     * @param comments    the comments on the video
     * @param metaData    the metadata of the video
     * @param isPublic    whether the video is public or private
     * @throws IllegalStateException if the streaming service of the uploader does not match the streaming service of
     *                               any comment author
     */
    @DoNotTouch
    public Video {
        VideoStreamingService streamingService = uploader.streamingService();
        if (comments.stream()
            .map(Comment::author)
            .map(User::streamingService)
            .anyMatch(service -> service == streamingService)) {
            throw new IllegalStateException("Streaming service does not match");
        }
    }

    /**
     * Creates a new video with the given information.
     *
     * @param title       the title of the video
     * @param description the description of the video
     * @param duration    the duration of the video
     * @param channel     the channel the video belongs to
     * @param uploader    the user who uploaded the video
     * @param uploadTime  the time the video was uploaded
     * @param comments    the comments on the video
     * @param metaData    the metadata of the video
     * @param isPublic    whether the video is public or private
     */
    @DoNotTouch
    public Video(
        String title,
        String description,
        Duration duration,
        Channel channel,
        User uploader,
        LocalDateTime uploadTime,
        List<Comment> comments,
        VideoMetaData metaData,
        Boolean isPublic
    ) {
        this(title, description, duration, channel, uploader, uploadTime, comments, Optional.of(metaData), isPublic);
    }

    /**
     * Creates a new video with the given information.
     *
     * @param title       the title of the video
     * @param description the description of the video
     * @param duration    the duration of the video
     * @param channel     the channel the video belongs to
     * @param uploader    the user who uploaded the video
     * @param uploadTime  the time the video was uploaded
     * @param comments    the comments on the video
     */
    @DoNotTouch
    public Video(
        String title,
        String description,
        Duration duration,
        Channel channel,
        User uploader,
        LocalDateTime uploadTime,
        List<Comment> comments
    ) {
        this(title, description, duration, channel, uploader, uploadTime, comments, Optional.empty(), Boolean.TRUE);
    }

    /**
     * Adds a comment to the video.
     *
     * @param comment the comment to add
     * @throws IllegalStateException if the streaming service of the comment author does not match the
     */
    @DoNotTouch
    public void add(Comment comment) {
        if (comment.author().streamingService() != uploader.streamingService()) {
            throw new IllegalStateException("Streaming service does not match");
        }
        comments.add(comment);
    }

    /**
     * Removes a comment from the video.
     *
     * @param comme the comment to remove
     */
    @DoNotTouch
    public void remove(Comment comme) {
        comments.remove(comme);
    }
}
