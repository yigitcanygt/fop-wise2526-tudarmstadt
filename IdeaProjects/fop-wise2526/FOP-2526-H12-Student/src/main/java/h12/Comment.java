package h12;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Optional;

/**
 * Represents a comment made by a user on a video.
 *
 * @param author   the author of the comment
 * @param text     the text of the comment
 * @param metaData the metadata of the comment
 * @author Nhan Huynh
 */
@DoNotTouch
public record Comment(User author, String text, Optional<CommentMetaData> metaData) {
    /**
     * Creates a new comment with the given information.
     *
     * @param author   the author of the comment
     * @param text     the text of the comment
     * @param metaData the metadata of the comment
     */
    @DoNotTouch
    public Comment(User author, String text, CommentMetaData metaData) {
        this(author, text, Optional.of(metaData));
    }

    /**
     * Creates a new comment with the given information.
     *
     * @param author the author of the comment
     * @param text   the text of the comment
     */
    @DoNotTouch
    public Comment(User author, String text) {
        this(author, text, Optional.empty());
    }

    /**
     * Checks if the comment is made by the given user.
     *
     * @param user the user to check
     * @return {@code true} if the comment is made by the given user, {@code false} otherwise
     */
    @DoNotTouch
    public boolean isFrom(User user) {
        return author.equals(user);
    }

    /**
     * Returns the number of characters in the comment.
     *
     * @return the number of characters in the comment
     */
    @DoNotTouch
    public int count() {
        return text.length();
    }

    /**
     * Returns the number of likes of the comment.
     *
     * @return the number of likes of the comment
     */
    @StudentImplementationRequired("H12.3")
    public int likeCount() {
        return metaData.stream()
            .mapToInt(CommentMetaData::likeCount)
            .sum();
    }

    /**
     * Returns the number of dislikes of the comment.
     *
     * @return the number of dislikes of the comment
     */
    @StudentImplementationRequired("H12.3")
    public int dislikeCount() {
        return metaData.stream()
            .mapToInt(CommentMetaData::dislikeCount)
            .sum();
    }
}
