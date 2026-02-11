package h13.csv;

/**
 * An exception that is thrown when read text data does not conform the expected CSV format
 */
public class CSVFormatException extends RuntimeException {

    /**
     * Creates a new {@link CSVFormatException} with a specified message
     *
     * @param message the exception's message
     */
    public CSVFormatException(String message) {
        super(message);
    }

}
