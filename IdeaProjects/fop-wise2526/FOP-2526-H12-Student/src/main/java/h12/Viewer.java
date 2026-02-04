package h12;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.time.LocalDateTime;

/**
 * Represents a viewer of a video.
 *
 * @param viewer    the user who viewed the video
 * @param timestamp the time the video was viewed
 * @author Nhan Huynh
 */
@DoNotTouch
public record Viewer(
    User viewer,
    LocalDateTime timestamp
) {
}
