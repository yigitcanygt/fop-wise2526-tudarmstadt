package h05.base.game;

import fopbot.Direction;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles keyboard input for the game, mapping key events to game actions in the MineBot environment.
 *
 * @author Nhan Huynh, Nico Schnieders
 */
@DoNotTouch
public class GameInputHandler {

    /**
     * Maps key codes to their corresponding {@link Direction} values.
     */
    @DoNotTouch
    private static final Map<Integer, Direction> KEY_TO_DIRECTION = Map.ofEntries(
        Map.entry(KeyEvent.VK_UP, Direction.UP),
        Map.entry(KeyEvent.VK_W, Direction.UP),
        Map.entry(KeyEvent.VK_RIGHT, Direction.RIGHT),
        Map.entry(KeyEvent.VK_D, Direction.RIGHT),
        Map.entry(KeyEvent.VK_DOWN, Direction.DOWN),
        Map.entry(KeyEvent.VK_S, Direction.DOWN),
        Map.entry(KeyEvent.VK_LEFT, Direction.LEFT),
        Map.entry(KeyEvent.VK_A, Direction.LEFT)
    );

    /**
     * Maps key codes to their corresponding selection numbers.
     * The keys 1-9 correspond to selections 1-9.
     */
    @DoNotTouch
    private static final Map<Integer, Integer> KEY_TO_SELECTION = Map.ofEntries(
        Map.entry(KeyEvent.VK_1, 1),
        Map.entry(KeyEvent.VK_2, 2),
        Map.entry(KeyEvent.VK_3, 3),
        Map.entry(KeyEvent.VK_4, 4),
        Map.entry(KeyEvent.VK_5, 5),
        Map.entry(KeyEvent.VK_6, 6),
        Map.entry(KeyEvent.VK_7, 7),
        Map.entry(KeyEvent.VK_8, 8),
        Map.entry(KeyEvent.VK_9, 9)
    );

    /**
     * A map to keep track of the state of keys (pressed or released).
     * The key is the key code, and the value is true if the key is pressed, false otherwise.
     */
    @DoNotTouch
    private final Map<Integer, Boolean> keyState = new HashMap<>();

    /**
     * The current direction based on the pressed keys. Defaults to {@code null} if no direction key is pressed.
     */
    @DoNotTouch
    private Direction direction = null;

    /**
     * The current selection based on the pressed number keys. Defaults to -1 if no selection key is pressed.
     */
    @DoNotTouch
    private int selection = -1;

    /**
     * Flag indicating whether the gear selection key (E) is pressed.
     */
    @DoNotTouch
    private boolean isPickingGear = false;

    /**
     * Flag indicating whether the mining action key (Space) is pressed.
     */
    @DoNotTouch
    private boolean isMining = false;

    /**
     * Flag indicating whether the info key (I) is pressed.
     */
    @DoNotTouch
    private boolean isInfo = false;


    @DoNotTouch
    public GameInputHandler() {
    }

    /**
     * Installs the key game input handler.
     */
    @DoNotTouch
    public void install() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> {
            int code = e.getKeyCode();
            switch (e.getID()) {
                case KeyEvent.KEY_PRESSED -> keyState.put(code, true);
                case KeyEvent.KEY_RELEASED -> keyState.put(code, false);
                default -> {
                }
            }
            refreshKeyState();
            return false;
        });
    }

    /**
     * Refreshes the key state based on the current keyState map.
     */
    @DoNotTouch
    private void refreshKeyState() {
        isInfo = Boolean.TRUE.equals(keyState.get(KeyEvent.VK_I));
        isMining = Boolean.TRUE.equals(keyState.get(KeyEvent.VK_SPACE));
        isPickingGear = Boolean.TRUE.equals(keyState.get(KeyEvent.VK_E));

        final Set<Direction> pressedDirections = keyState.entrySet().stream()
            .filter(Map.Entry::getValue)
            .map(e -> KEY_TO_DIRECTION.get(e.getKey()))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        direction = pressedDirections.size() == 1 ? pressedDirections.iterator().next() : null;

        final Set<Integer> pressedSelections = keyState.entrySet().stream()
            .filter(Map.Entry::getValue)
            .map(e -> KEY_TO_SELECTION.get(e.getKey()))
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
        selection = pressedSelections.size() == 1 ? pressedSelections.iterator().next() : -1;
    }

    /**
     * Returns the current direction based on the pressed keys.
     *
     * @return the current direction, or {@code null} if no direction key is pressed
     */
    @DoNotTouch
    public Direction getDirection() {
        return direction;
    }

    /**
     * Returns the current selection based on the pressed number keys.
     *
     * @return the current selection, or -1 if no selection key is pressed
     */
    @DoNotTouch
    public int getSelection() {
        return selection;
    }

    /**
     * Checks if the gear selection key (E) is pressed.
     *
     * @return {@code true} if the gear selection key is pressed, {@code false} otherwise
     */
    @DoNotTouch
    public boolean isPickingGear() {
        return isPickingGear;
    }

    /**
     * Checks if the mining action key (Space) is pressed.
     *
     * @return {@code true} if the mining action key is pressed, {@code false} otherwise
     */
    @DoNotTouch
    public boolean isMining() {
        return isMining;
    }

    /**
     * Checks if the info key (I) is pressed.
     *
     * @return {@code true} if the info key is pressed, {@code false} otherwise
     */
    @DoNotTouch
    public boolean isInfo() {
        return isInfo;
    }
}
