package h13;

import h13.csv.departure.DepartureJoiner;
import h13.ppm.NearestNeighborInterpolation;
import h13.ppm.PPMExporter;
import h13.ppm.PPMParser;
import h13.ppm.image.PPMImage;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;
import java.io.InputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.OutputStream;
/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    @StudentImplementationRequired("H13.3")
    public static void main(String[] args) throws IOException {
        System.out.println("=== PPM Image Processing Test ===");
        Path fopbotInputPath = Path.of("test-data", "fopbot.ppm");
        PPMImage originalImage;
        try (InputStream inputFlow = Files.newInputStream(fopbotInputPath);
             PPMParser parser = new PPMParser(inputFlow)) {
            originalImage = parser.parse();
            System.out.println("Original image read: " +
                originalImage.header().width() + "x" +
                originalImage.header().height() + " pixel");
        }
        float[] scalingCoefficients = {2.0f, 0.5f, 1.5f};
        for (float exponent : scalingCoefficients) {
            PPMImage scaledImage = NearestNeighborInterpolation.scaleImage(originalImage, exponent);
            String outputFileName = "fopbot-scale-" + exponent + ".ppm";
            Path outputPath = Path.of("test-data", outputFileName);
            try (OutputStream outputFlow = Files.newOutputStream(outputPath);
                 PPMExporter exporter = new PPMExporter(outputFlow)) {
                exporter.export(scaledImage);
                System.out.println("Scaling factor " + exponent + ": " +
                    scaledImage.header().width() + "x" +
                    scaledImage.header().height() + " pixel -> " + outputFileName);
            }
        }
        System.out.println("\n=== CSV Table Processing Test ===");
        Path scheduledDeparturePath = Path.of("test-data", "scheduled-departures.csv");
        Path liveDeparturePath = Path.of("test-data", "live-departures.csv");
        Path joinedOutputPath = Path.of("test-data", "joined-departures.csv");
        DepartureJoiner.processFiles(scheduledDeparturePath, liveDeparturePath, joinedOutputPath);
        System.out.println("Departure data was combined and written: " + joinedOutputPath);
        System.out.println("\n=== All tests completed ===");
    }
}
