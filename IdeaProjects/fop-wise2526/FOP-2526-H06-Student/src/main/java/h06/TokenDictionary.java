package h06;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.HashMap;
import java.util.Map;

/**
 * specifies which string a token is to be replaced with
 */
@DoNotTouch
public class TokenDictionary {
    private final Map<String, String> map;

    /**
     * Creates a new TokenDictionary
     *
     * @param map the underlying map
     */
    public TokenDictionary(Map<String, String> map) {
        this.map = map;
    }

    /**
     * Creates an empty TokenDictionary
     */
    public TokenDictionary() {
        this(new HashMap<>());
    }

    /**
     * returns the string that corresponds to a token.
     * the token must begin with a '&lt;' (left angle bracket) and end with a '&gt;' (right angle bracket).
     *
     * @param token the token to be looked up
     * @return the string that corresponds to the token
     */
    public String lookup(String token) {
        if (!(token.charAt(0) == '<' && token.charAt(token.length() - 1) == '>')) {
            throw new IllegalArgumentException("Token must start with '<' and end with '>'");
        }
        return map.get(token.substring(1, token.length() - 1));
    }

    /**
     * registers a token within the dictionary.
     * the token must not contain any '&lt;' (left angle bracket) or '&gt;' (right angle bracket).
     *
     * @param token the token to register
     * @param text  the corresponding string
     */
    public void putToken(String token, String text) {
        if (token.contains("<") || token.contains(">")) {
            throw new IllegalArgumentException("Token must not contain any '<' or '>'");
        }
        map.put(token, text);
    }

}
