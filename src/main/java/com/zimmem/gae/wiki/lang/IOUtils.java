package com.zimmem.gae.wiki.lang;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtils {

    public static void in2out(InputStream is, OutputStream out) {
        if (is == null || out == null) {
            throw new NullPointerException();
        }
        byte[] buffer = new byte[4096];
        int read = 0;
        try {
            while ((read = is.read(buffer)) > 0) {
                out.write(buffer, 0, read);
                out.flush();
            }
            out.flush();
        } catch (Exception e) {
            close(is);
            close(out);
        }
    }

    private static void close(Closeable close) {
        try {
            close.close();
        } catch (IOException e) {
        }
    }
}
