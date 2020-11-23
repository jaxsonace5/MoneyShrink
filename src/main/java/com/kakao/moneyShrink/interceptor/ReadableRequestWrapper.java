package com.kakao.moneyShrink.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

public class ReadableRequestWrapper extends HttpServletRequestWrapper {

    private final Charset encoding;
    private byte[] rawData;

    public ReadableRequestWrapper(HttpServletRequest request) throws IOException {

        super(request);

        String charEncoding = request.getCharacterEncoding();
        this.encoding = StringUtils.isBlank(charEncoding) ? StandardCharsets.UTF_8 : Charset.forName(charEncoding);

        try {

            InputStream is = request.getInputStream();
            this.rawData = IOUtils.toByteArray(is);

            request.setAttribute("requestBody", this.getReader().lines().collect(Collectors.joining(System.lineSeparator())));

        } catch (IOException e) {
            throw e;
        }
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.rawData);

        ServletInputStream sis = new ServletInputStream() {
            @Override public boolean isFinished() {
                return false;
            }

            @Override public boolean isReady() {
                return false;
            }

            @Override public void setReadListener(ReadListener readListener) {

            }

            @Override public int read() throws IOException {
                return byteArrayInputStream.read();
            }
        };
        return sis;
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream(), this.encoding));
    }
}
