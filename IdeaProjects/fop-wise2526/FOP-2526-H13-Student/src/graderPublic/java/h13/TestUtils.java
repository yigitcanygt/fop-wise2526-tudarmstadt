package h13;

import java.util.stream.IntStream;

public final class TestUtils {

    public static Integer[] toUnsignedBytes(byte[] data) {
        return IntStream.range(0, data.length)
            .map(i -> Byte.toUnsignedInt(data[i]))
            .boxed()
            .toArray(Integer[]::new);
    }
}
