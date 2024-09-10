#set($symbol_pound='#')
#set($symbol_dollar='$')
#set($symbol_escape='\')
package ${package}.configs;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CustomHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final String body;

    public CustomHttpServletRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        // Read and preserve the incoming body
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader bufferedReader = request.getReader()) {
            char[] charBuffer = new char[128];
            int bytesRead;
            while ((bytesRead = bufferedReader.read(charBuffer)) != -1) {
                stringBuilder.append(charBuffer, 0, bytesRead);
            }
        }
        body = stringBuilder.toString();
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() {
        // Creates a new InputStream pre-saving the incoming request and allowing its next requests
        final byte[] bytes = body.getBytes();
        return new ServletInputStream() {
            int lastIndexRetrieved = -1;
            int index = 0;

            @Override
            public int read() {
                if (index == bytes.length) {
                    return -1;
                }
                lastIndexRetrieved = index;
                return bytes[index++] & 0xFF;
            }

            @Override
            public boolean isFinished() {
                return index == bytes.length;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
                // Not implemented
            }
        };
    }

    public String getBody() {
        return body;
    }
}
