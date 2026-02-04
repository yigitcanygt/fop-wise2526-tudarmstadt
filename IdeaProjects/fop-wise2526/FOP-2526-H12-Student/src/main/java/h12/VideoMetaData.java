package h12;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Set;

/**
 * Represents the metadata of a video.
 *
 * @param category the category of the video
 * @param tags     the tags associated with the video
 * @param viewers  the viewers of the video
 * @param likes    the users who liked the video
 * @param dislikes the users who disliked the video
 */
@DoNotTouch
public record VideoMetaData(
    Category category,
    Set<String> tags,
    Set<Viewer> viewers,
    Set<User> likes,
    Set<User> dislikes
) {
}
