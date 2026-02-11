package h13.csv;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

/**
 * Provides a class method to parse CSV data
 */
public final class CSVParser {

    @DoNotTouch
    private CSVParser() {
    }

    /**
     * Reads and parses CSV data from the given {@link BufferedReader}.
     * Blank lines are skipped and whitespaces around single data cells are trimmed.
     * Each row is split at ',' and then converted to an object representing that row
     * using the provided rowDeserializer.
     *
     * @param reader          reader to read text data from
     * @param rowDeserializer a function that converts ONE ','-split row into an object representing that row
     * @param <R>             the type of object to represent one row
     * @return the parsed table as a list of row-objects
     * @throws IOException if an I/O error occurs while reading from reader
     */
    @StudentImplementationRequired("H13.2.1")
    public static <R> List<R> parse(BufferedReader reader, Function<List<String>, R> rowDeserializer) throws IOException {
        List<R> lineList = new ArrayList<>();
        String lineText;
        while ((lineText = reader.readLine()) != null) {
            if (lineText.isBlank()) {
                continue;
            }
            String[] columns = lineText.split(",");
            List<String> cleanColumns = new ArrayList<>();
            for (String column : columns) {
                cleanColumns.add(column.trim());
            }
            R lineObj = rowDeserializer.apply(cleanColumns);
            lineList.add(lineObj);
        }
        return lineList;
    }
}
