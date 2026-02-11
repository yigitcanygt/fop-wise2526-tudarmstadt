package h13;

import h13.csv.departure.DepartureJoinerTests;
import h13.csv.departure.ScheduledDepartureTests;
import h13.ppm.PPMImagesWriteTest;
import h13.ppm.PPMParserTests;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.sourcegrade.jagr.api.rubric.Rubric;
import org.sourcegrade.jagr.api.rubric.RubricProvider;

import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.criterion;
import static org.tudalgo.algoutils.tutor.general.jagr.RubricUtils.graderPrivateOnly;

public class H13_RubricProviderPublic implements RubricProvider {

    private static final Criterion H13_1_1 = Criterion.builder()
        .shortDescription("H13.1.1 | PPM-Bilder einlesen")
        .addChildCriteria(
            criterion("parseHeader gibt korrektes PPMHeader-Objekt zurück",
                1,
                JUnitTestRef.ofMethod(() -> PPMParserTests.class.getDeclaredMethod("parseHeaderReturnTest"))),
            criterion("parseHeader wirft eine PPMFormatException, wenn die ersten drei Bytes falsch oder der max. Farbwert nicht 255 ist",
                1,
                JUnitTestRef.ofMethod(() -> PPMParserTests.class.getDeclaredMethod("parseHeaderWrongMagicTest")),
                JUnitTestRef.ofMethod(() -> PPMParserTests.class.getDeclaredMethod("parseHeaderWrongMaxColorTest"))),
            criterion("parsePixelData gibt ein 2D-Array mit korrekten Größen zurück",
                1,
                JUnitTestRef.ofMethod(() -> PPMParserTests.class.getDeclaredMethod("parsePixelDataReturnTest"))),
            criterion("parsePixelData liest nicht-symmetrisches Bild korrekt",
                1,
                JUnitTestRef.ofMethod(() -> PPMParserTests.class.getDeclaredMethod("parsePixelDataAntSymTest"))),
            criterion("parsePixelData wirft eine PPMFormatException, wenn der InputStream zu wenig/zu viele Bytes enthält",
                1,
                JUnitTestRef.ofMethod(() -> PPMParserTests.class.getDeclaredMethod("parsePixelLessBytesTest")),
                JUnitTestRef.ofMethod(() -> PPMParserTests.class.getDeclaredMethod("parsePixelManyBytesTest"))),
            criterion("parse gibt ein PPMImage-Objekt mit korrektem Header/Pixel-Daten zurück",
                1,
                JUnitTestRef.ofMethod(() -> PPMParserTests.class.getDeclaredMethod("parseReturnsValidPPMImageTest")))
        ).build();

    private static final Criterion H13_1_2 = Criterion.builder()
        .shortDescription("H13.1.2 | Bilder skalieren mit Nearest Neighbor Interpolation")
        .addChildCriteria(
            privateOnlyCriterion("Die Größen des zurückgegebenen Arrays sind korrekt (abgerundet)", 1),
            privateOnlyCriterion("Symmetrisches Bild wird mit Skalierungsfaktor 1 korrekt kopiert", 1),
            privateOnlyCriterion("Nicht-symmetrisches Bild wird mit Skalierungsfaktor < 1 korrekt skaliert", 1),
            privateOnlyCriterion("Nicht-symmetrisches Bild wird mit Skalierungsfaktor > 1 korrekt skaliert", 1)
        ).build();

    private static final Criterion H13_1_3 = Criterion.builder()
        .shortDescription("H13.1.3 | PPM-Bilder in Datei schreiben")
        .addChildCriteria(
            criterion("emitHeader schreibt korrekt den gesamten Header und das terminierende Whitespace-Symbol",
                1,
                JUnitTestRef.ofMethod(() -> PPMImagesWriteTest.class.getDeclaredMethod("emitHeaderTest"))),
            criterion("emitPixelData schreibt korrekt alle übergebenen Pixel",
                1,
                JUnitTestRef.ofMethod(() -> PPMImagesWriteTest.class.getDeclaredMethod("emitPixelDataTest"))),
            criterion("export ruft emitHeader und emitPixelData mit den übergebenen Daten auf",
                1,
                JUnitTestRef.ofMethod(() -> PPMImagesWriteTest.class.getDeclaredMethod("exportCorrectCallTest"))),
            criterion("export ruft nicht flush() auf",
                -1,
                JUnitTestRef.ofMethod(() -> PPMImagesWriteTest.class.getDeclaredMethod("exportFlushTest")))
        )
        .minPoints(0)
        .maxPoints(3)
        .build();

    private static final Criterion H13_1 = Criterion.builder()
        .shortDescription("H13.1 | PPM Bilder")
        .addChildCriteria(H13_1_1, H13_1_2, H13_1_3)
        .build();

    private static final Criterion H13_2_1 = Criterion.builder()
        .shortDescription("H13.2.1 | CSV einlesen")
        .addChildCriteria(
            privateOnlyCriterion("Rückgabe ist bei einer Zeile ohne Leerzeichen und Zeilenumbruch korrekt", 1),
            privateOnlyCriterion("Rückgabe ist bei mehreren Zeile ohne Leerzeichen und zusätzlichen Zeilenumbrüchen korrekt", 1),
            privateOnlyCriterion("Leerzeichen um einzelne Spalten-Werte herum werden entfernt", 1),
            privateOnlyCriterion("Zeilen, die nur Whitespaces beinhalten, werden nicht zurückgeben bzw. führen zu keinem Fehler", 1),
            privateOnlyCriterion("BufferedReader wird hier bereits geschlossen", -1)
        )
        .minPoints(0)
        .build();

    private static final Criterion H13_2_2 = Criterion.builder()
        .shortDescription("H13.2.2 | Domänen-Objekte deserialisieren")
        .addChildCriteria(
            criterion("Bei korrekter Eingabe ist die Rückgabe korrekt", 1,
                JUnitTestRef.ofMethod(() -> ScheduledDepartureTests.class.getDeclaredMethod("testDeserialize_valid"))),
            criterion("Es wird eine CSVFormatException geworfen, wenn die Liste nicht exakt 4 Elemente enthält oder das erste keine ganze Zahl ist", 1,
                JUnitTestRef.ofMethod(() -> ScheduledDepartureTests.class.getDeclaredMethod("testDeserialize_incorrectSize", int.class)),
                JUnitTestRef.ofMethod(() -> ScheduledDepartureTests.class.getDeclaredMethod("testDeserialize_invalidFormat")))
        )
        .build();

    private static final Criterion H13_2_3 = Criterion.builder()
        .shortDescription("H13.2.3 | Tabellen verarbeiten")
        .addChildCriteria(
            criterion("Die tatsächliche Abfahrtszeit einer JoinedDeparture-Zeile entspricht der korrespondierenden Zeit aus der Live-Tabelle", 1,
                JUnitTestRef.ofMethod(() -> DepartureJoinerTests.class.getDeclaredMethod("testJoinTables_departureTime"))),
            criterion("Die Verspätung einer JoinedDeparture-Zeile entspricht der Differenz aus Abfahrtszeiten", 1,
                JUnitTestRef.ofMethod(() -> DepartureJoinerTests.class.getDeclaredMethod("testJoinTables_delay"))),
            criterion("Bei fehlender Zeile in Live-Tabelle wird die geplante Abfahrtszeit übernommen und mit 0 Minuten Verspätung gerechnet", 1,
                JUnitTestRef.ofMethod(() -> DepartureJoinerTests.class.getDeclaredMethod("testJoinTables_missing"))),
            criterion("Wenn ein Zug zu früh ist, ist die Verspätung negativ", 1,
                JUnitTestRef.ofMethod(() -> DepartureJoinerTests.class.getDeclaredMethod("testJoinTables_early"))),
            criterion("Die Rückgabe enthält nur Zeilen, deren IDs auch in der ScheduledDeparture-Tabelle enthalten waren", 1,
                JUnitTestRef.ofMethod(() -> DepartureJoinerTests.class.getDeclaredMethod("testJoinTables_entries")))
        )
        .build();

    private static final Criterion H13_2_4 = Criterion.builder()
        .shortDescription("H13.2.4 | Domänen-Objekte serialisieren")
        .addChildCriteria(
            privateOnlyCriterion("Die Rückgabe ist korrekt", 1)
        )
        .build();

    private static final Criterion H13_2_5 = Criterion.builder()
        .shortDescription("H13.2.5 | CSV-Dateien schreiben")
        .addChildCriteria(
            privateOnlyCriterion("writeRow schreibt korrekt eine Zeile mit ',' getrennt", 1),
            privateOnlyCriterion("writeCSV schreibt korrekt eine Tabelle mit exakt einer Zeile", 1),
            privateOnlyCriterion("writeCSV schreibt korrekt einen Zeilenumbruch nach jeder Zeile (letzte optional)", 1),
            privateOnlyCriterion("writeCSV ruft flush() nicht auf", -1)
        )
        .minPoints(0)
        .build();

    private static final Criterion H13_2_6 = Criterion.builder()
        .shortDescription("H13.2.6 | Alles kommt zusammen")
        .addChildCriteria(
            privateOnlyCriterion("Alle von den übergebenen Pfaden identifizierten Dateien werden geöffnet", 1),
            privateOnlyCriterion("Wenn das Parsen der Eingabedateien fehlschlägt, werden die beiden geöffneten BufferedReader trotzdem geschlossen", 1),
            privateOnlyCriterion("Die vom Ergebnis-Pfad identifizierte Datei enthält am Ende der Methode Text", 1),
            privateOnlyCriterion("Wenn das Schreiben des Ergebnis fehlschlägt, wird der geöffnete BufferedWriter geschlossen", 1)
        )
        .build();

    private static final Criterion H13_2 = Criterion.builder()
        .shortDescription("H13.2 | Tabellenstrukturierte Daten")
        .addChildCriteria(H13_2_1, H13_2_2, H13_2_3, H13_2_4, H13_2_5, H13_2_6)
        .build();

    private static final Rubric RUBRIC = Rubric.builder()
        .title("H13 | Bilder und Tabellen")
        .addChildCriteria(H13_1, H13_2)
        .build();

    @Override
    public Rubric getRubric() {
        return RUBRIC;
    }

    private static Criterion privateOnlyCriterion(String description, int points) {
        return Criterion.builder()
            .shortDescription(description)
            .minPoints(Math.min(points, 0))
            .maxPoints(Math.max(points, 0))
            .grader(graderPrivateOnly(points))
            .build();
    }
}
