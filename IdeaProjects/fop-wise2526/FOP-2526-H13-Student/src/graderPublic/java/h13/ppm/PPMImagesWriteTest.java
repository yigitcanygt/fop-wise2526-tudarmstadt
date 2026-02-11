package h13.ppm;

import h13.VirtualFile;
import h13.ppm.image.PPMHeader;
import h13.ppm.image.PPMImage;
import h13.ppm.image.PixelColor;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.BasicTypeLink;
import spoon.reflect.declaration.CtMethod;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;
import static org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers.identical;

@TestForSubmission
public class PPMImagesWriteTest {

    @Tag("H13.1.3")
    @Test
    public void emitHeaderTest() {
        try {
            VirtualFile vf = new VirtualFile();
            OutputStream out = vf.getOutputStream();

            int width = 10;
            int height = 7;

            PPMHeader header = new PPMHeader(width, height);

            PPMExporter writer = new PPMExporter(out);

            writer.emitHeader(header);

            String result = new String(vf.getContent(), StandardCharsets.US_ASCII);

            assertEquals(
                "P6 " + width + " " + height + " " + PPMImage.MAX_COLOR + " ",
                result,
                emptyContext(),
                r -> "The header was not written in full to the output."
            );
        } catch (Exception e) {
            fail(emptyContext(), r -> "An unexpected exception was thrown, even though none should have been thrown.");
        }
    }

    @Tag("H13.1.3")
    @Test
    public void emitPixelDataTest() {
        try {
            VirtualFile vf = new VirtualFile();
            OutputStream out = vf.getOutputStream();

            PPMExporter writer = new PPMExporter(out);

            PixelColor A = new PixelColor((byte) 10, (byte) 20, (byte) 30);
            PixelColor B = new PixelColor((byte) 40, (byte) 50, (byte) 60);
            PixelColor C = new PixelColor((byte) 70, (byte) 80, (byte) 90);

            PixelColor[][] pixels = new PixelColor[][]{
                {A, B},
                {C}
            };

            writer.emitPixelData(pixels);

            byte[] written = vf.getContent();

            byte[] expected = new byte[]{
                10, 20, 30,   // A
                40, 50, 60,   // B
                70, 80, 90    // C
            };

            assertEquals(
                expected.length,
                written.length,
                emptyContext(),
                r -> "Written byte count must match expected pixel data length"
            );

            for (int i = 0; i < expected.length; i++) {
                int index = i;
                assertEquals(
                    expected[i],
                    written[i],
                    emptyContext(),
                    r -> "Byte at index " + index + " must match"
                );
            }
        } catch (Exception e){
            fail(emptyContext(), r -> "An unexpected exception was thrown, even though none should have been thrown.");
        }
    }

    @Tag("H13.1.3")
    @Test
    public void exportCorrectCallTest() {
        CtMethod ctMethod =
            ((BasicMethodLink) BasicTypeLink.of(PPMExporter.class).getMethod(identical("export"))).getCtElement();

        String body = ctMethod.getBody().toStringDebug();

        long emitHeader = body.lines()
            .filter(line -> line.contains("emitHeader("))
            .count();

        long emitPixelData = body.lines()
            .filter(line -> line.contains("emitPixelData("))
            .count();

        assertTrue(emitHeader > 0, emptyContext(), r -> "export does not call emitHeader");
        assertTrue(emitPixelData > 0, emptyContext(), r -> "export does not call emitHeader emitPixelData");
    }

    @Tag("H13.1.3")
    @Test
    public void exportFlushTest() {
        CtMethod ctMethod =
            ((BasicMethodLink) BasicTypeLink.of(PPMExporter.class).getMethod(identical("export"))).getCtElement();

        String body = ctMethod.getBody().toStringDebug();

        long flushed = body.lines()
            .filter(line -> line.contains("output.flush()"))
            .count();

        assertTrue(flushed > 0, emptyContext(), r -> "export does not call flush");


    }

}
