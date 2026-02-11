package h13.ppm;

import h13.TestUtils;
import h13.VirtualFile;
import h13.ppm.image.PPMHeader;
import h13.ppm.image.PPMImage;
import h13.ppm.image.PixelColor;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.io.IOException;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class PPMParserTests {

    private PPMParser createParser(byte[] data) {
        return new PPMParser(new VirtualFile(data).getInputStream());
    }

    private record PPMImageData(int width, int height, byte[] header, byte[] pixels) {

        private PPMImageData(int width, int height, int maxColorValue, byte[] pixels) {
            this(width, height, "P6 %d %d %d ".formatted(width, height, maxColorValue).getBytes(), pixels);
        }

        private byte[] data() {
            byte[] data = new byte[header.length + pixels.length];
            System.arraycopy(header, 0, data, 0, header.length);
            System.arraycopy(pixels, 0, data, header.length, pixels.length);
            return data;
        }

        private Context getContext() {
            return contextBuilder()
                .add("Image width", width)
                .add("Image height", height)
                .add("Image header", new String(header))
                .add("Image pixel values (in bytes)", TestUtils.toUnsignedBytes(pixels))
                .add("Full image data (in bytes)", TestUtils.toUnsignedBytes(data()))
                .build();
        }
    }

    @Tag("H13.1.1")
    @Test
    public void parseHeaderReturnTest() {
        PPMImageData image = new PPMImageData(3, 2, 255, new byte[0]);
        Context context = image.getContext();
        PPMParser parser = createParser(image.header());

        PPMHeader header = callObject(parser::parseHeader, context, r ->
            "An exception occurred while invoking PPMParser.parseHeader()");
        assertEquals(3, header.width(), context, r ->
            "PPMParser.parseHeader() did not parse the image width correctly");
        assertEquals(2, header.height(), context, r ->
            "PPMParser.parseHeader() did not parse the image height correctly");
    }

    @Tag("H13.1.1")
    @Test
    public void parseHeaderWrongMagicTest() {
        PPMImageData image = new PPMImageData(3, 2, "P3 3 2 255 ".getBytes(), new byte[0]);
        Context context = image.getContext();
        PPMParser parser = createParser(image.header());

        assertThrows(PPMFormatException.class, parser::parseHeader, context, r ->
            "A PPMFormatException should have been thrown because the magic number is incorrect.");
    }

    @Tag("H13.1.1")
    @Test
    public void parseHeaderWrongMaxColorTest() {
        PPMImageData image = new PPMImageData(3, 2, "P3 3 2 200 ".getBytes(), new byte[0]);
        Context context = image.getContext();
        PPMParser parser = createParser(image.header());

        assertThrows(PPMFormatException.class, parser::parseHeader, context, r ->
            "An PPMFormatException should have been thrown,because the max color wasn't correct.");
    }

    @Tag("H13.1.1")
    @Test
    public void parsePixelDataReturnTest() {
        PPMImageData image = new PPMImageData(2, 2, 255, new byte[] {
            1,2,3, 4,5,6,
            7,8,9, 10,11,12
        });
        Context context = image.getContext();
        PPMParser parser = createParser(image.pixels());

        PixelColor[][] img = callObject(() -> parser.parsePixelData(image.width(), image.height()), context, r ->
            "An exception occurred while invoking PPMParser.parsePixelData(int, int)");
        assertEquals(image.height(), img.length, context, r -> "The outer array does not have the correct size.");
        assertEquals(image.width(), img[0].length, context, r -> "The inner array does not have the correct size.");
    }

    @Tag("H13.1.1")
    @Test
    public void parsePixelDataAntSymTest() {
        PPMImageData image = new PPMImageData(3, 1, 255, new byte[] {
            1,2,3, 4,5,6, 7,8,9
        });
        Context context = image.getContext();
        PPMParser parser = createParser(image.pixels());

        PixelColor[][] img = callObject(() -> parser.parsePixelData(image.width(), image.height()), context, r ->
            "An exception occurred while invoking PPMParser.parsePixelData(int, int)");
        assertEquals(image.height(), img.length, context, r -> "The outer array does not have the correct size.");
        assertEquals(image.width(), img[0].length, context, r -> "The inner array does not have the correct size.");
    }

    @Tag("H13.1.1")
    @Test
    public void parsePixelLessBytesTest() {
        PPMImageData image = new PPMImageData(2, 2, 255, new byte[] {
            1,2,3, 4,5,6, 7,8,9
        });
        Context context = image.getContext();
        PPMParser parser = createParser(image.pixels());

        assertThrows(
            PPMFormatException.class,
            () -> parser.parsePixelData(image.width(), image.height()),
            context,
            r -> "No PPMFormatException was thrown, even though the InputStream contains too few bytes."
        );
    }

    @Tag("H13.1.1")
    @Test
    public void parsePixelManyBytesTest() {
        PPMImageData image = new PPMImageData(1, 1, 255, new byte[] {
            1,2,3, 4
        });
        Context context = image.getContext();
        PPMParser parser = createParser(image.pixels());

        assertThrows(
            PPMFormatException.class,
            () -> parser.parsePixelData(image.width(), image.height()),
            context,
            r -> "No PPMFormatException was thrown, even though the InputStream contains too many bytes."
        );
    }

    @Tag("H13.1.1")
    @Test
    public void parseReturnsValidPPMImageTest() throws IOException {
        PPMImageData image = new PPMImageData(2, 1, 255, new byte[] {
            1,2,3,
            4,5,6
        });
        Context context = image.getContext();
        PPMParser parser = Mockito.spy(createParser(image.data()));
        Mockito.doReturn(new PPMHeader(image.width(), image.height()))
            .when(parser)
            .parseHeader();
        Mockito.doReturn(new PixelColor[][] {{
                new PixelColor((byte) 1, (byte) 2, (byte) 3), new PixelColor((byte) 4, (byte) 5, (byte) 6)
            }})
            .when(parser)
            .parsePixelData(Mockito.eq(image.width()), Mockito.eq(image.height()));

        PPMImage img = callObject(parser::parse, context, r ->
            "An exception occurred while invoking PPMParser.parse()");

        // Header checks
        assertEquals(image.width(), img.header().width(), context, r ->
            "Expected image width = 2, but was: " + img.header().width());
        assertEquals(image.height(), img.header().height(), context, r ->
            "Expected image height = 1, but was: " + img.header().height());

        // Pixel checks
        PixelColor[][] px = img.pixelData();

        assertEquals(image.height(), px.length, context, r ->
            "Expected pixelData row count = 1, but was: " + px.length);
        assertEquals(image.width(), px[0].length, context, r ->
            "Expected pixelData column count = 2, but was: " + px[0].length);

        assertEquals(new PixelColor((byte) 1,(byte) 2,(byte) 3), px[0][0], context, r ->
            "Expected first pixel = (1,2,3), but was: " + px[0][0]);
        assertEquals(new PixelColor((byte) 4,(byte) 5,(byte) 6), px[0][1], context, r ->
            "Expected second pixel = (4,5,6), but was: " + px[0][1]);
    }
}
