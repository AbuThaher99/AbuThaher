package com.example.projecttwo;


import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BufferedBitWriter {
    private OutputStream out;
    private int buffer;
    private int bufferSize;

    public BufferedBitWriter(String fileName,boolean apand) throws IOException {
        this.out = new BufferedOutputStream(new FileOutputStream(fileName,apand));
        this.buffer = 0;
        this.bufferSize = 0;
    }

    public void write(String s) throws IOException {
        for (char c : s.toCharArray()) {
            writeBoolean(c == '1');
        }
    }

    public void writeBoolean(boolean b) throws IOException {
        buffer = (buffer << 1) | (b ? 1 : 0);
        bufferSize++;
        if (bufferSize == 8) {
            flush();
        }
    }

    public void flush() throws IOException {
        if (bufferSize > 0) {
            out.write(buffer);
            buffer = 0;
            bufferSize = 0;
        }
        out.flush();
    }

    public void close() throws IOException {
        flush();
        out.close();
    }
}