package h12;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents the metadata of a comment.
 *
 * @param likes     the users who liked the comment
 * @param dislikes  the users who disliked the comment
 * @param createdAt the time the comment was created
 * @author Nhan Huynh
 */
@DoNotTouch
public record CommentMetaData(
    Set<User> likes,
    Set<User> dislikes,
    LocalDateTime createdAt
) {

    /**
     * Creates an empty CommentMetaData with the current time as creation time.
     */
    @DoNotTouch
    public CommentMetaData() {
        this(new HashSet<>(), new HashSet<>(), LocalDateTime.now());
    }

    /**
     * Returns the number of likes.
     *
     * @return the number of likes
     */
    @DoNotTouch
    public int likeCount() {
        return likes.size();
    }

    /**
     * Returns the number of dislikes.
     *
     * @return the number of dislikes
     */
    @DoNotTouch
    public int dislikeCount() {
        return dislikes.size();
    }
}
