package h13.csv;

import h13.csv.departure.JoinedDeparture;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

/**
 * Class to export given row-objects in CSV format.
 * Data is written to a {@link BufferedWriter} which is set once and cannot be changed.
 */
public class CSVWriter implements AutoCloseable {

    private BufferedWriter writer;

    /**
     * Creates a new {@link CSVWriter} that will write CSV data as text into the given writer.
     *
     * @param writer the {@link BufferedWriter} to write text data to
     */
    @DoNotTouch
    public CSVWriter(BufferedWriter writer) {
        this.writer = writer;
    }

    /**
     * Exports the table as CSV data into the writer.
     *
     * @param rows the table as row objects
     * @throws IOException if an I/O error occurs
     */
    @StudentImplementationRequired("H13.2.5")
    public void writeCSV(List<JoinedDeparture> rows) throws IOException {
        for (int lineIndex = 0; lineIndex < rows.size(); lineIndex++) {
            JoinedDeparture line = rows.get(lineIndex);
            writeRow(line);
            writer.newLine();
        }
        writer.flush();
    }

    /**
     * Writes a single row containing serialized data from one row object.
     *
     * @param row the row object to serialize and write
     * @throws IOException if an I/O error occurs
     */
    @StudentImplementationRequired("H13.2.5")
    public void writeRow(JoinedDeparture row) throws IOException {
        List<String> column = row.serialize();
        for (int columnIndex = 0; columnIndex < column.size(); columnIndex++) {
            String columns = column.get(columnIndex);
            writer.write(columns);
            if (columnIndex < column.size() - 1) {
                writer.write(',');
                writer.write(' ');
            }
        }
    }

    @DoNotTouch
    @Override
    public void close() throws IOException {
        writer.close();
    }
}
