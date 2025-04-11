package unicorns.backend.filter;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.*;

public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private final ServletOutputStream outputStream;
    private final PrintWriter writer;

    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
        outputStream = new ServletOutputStream() {
            public void write(int b) {
                buffer.write(b);
            }

            public boolean isReady() {
                return true;
            }

            public void setWriteListener(WriteListener listener) {
            }
        };
        writer = new PrintWriter(new OutputStreamWriter(buffer));
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return outputStream;
    }

    @Override
    public PrintWriter getWriter() {
        return writer;
    }

    public byte[] getBody() throws IOException {
        writer.flush(); // Đảm bảo dữ liệu được ghi vào buffer
        return buffer.toByteArray();
    }

    @Override
    public void flushBuffer() throws IOException {
        writer.flush();
        super.flushBuffer();
    }
}
