package h13;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class VirtualFile {

    private final List<Byte> content;

    public VirtualFile() {
        content = new ArrayList<>();
    }

    public VirtualFile(byte[] data) {
        content = new ArrayList<>(data.length);
        for (byte datum : data) {
            content.add(datum);
        }
    }

    public byte[] getContent() {
        byte[] result = new byte[content.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = content.get(i);
        }
        return result;
    }

    public VirtualFileInputStream getInputStream() {
        return new VirtualFileInputStream();
    }

    public VirtualFileOutputStream getOutputStream() {
        return new VirtualFileOutputStream();
    }

    public class VirtualFileInputStream extends InputStream {

        private boolean closed = false;
        private int pos = 0;

        @Override
        public int read() {
            if (!closed && pos < content.size()) {
                return Byte.toUnsignedInt(content.get(pos++));
            } else {
                return -1;
            }
        }

        @Override
        public void close() {
            closed = true;
        }

        public boolean isClosed() {
            return closed;
        }
    }

    public class VirtualFileOutputStream extends OutputStream {

        private boolean closed = false;

        @Override
        public void write(int b) {
            if (!closed) {
                content.add((byte) b);
            }
        }

        @Override
        public void close() {
            closed = true;
        }

        public boolean isClosed() {
            return closed;
        }
    }
}
