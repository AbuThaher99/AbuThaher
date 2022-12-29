package com.example.projecttwo;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BufferedBitReader {
    private InputStream in;
    private int buffer;
    private int bufferSize;

    public BufferedBitReader(String fileName) throws IOException {
        this.in = new BufferedInputStream(new FileInputStream(fileName));
        this.buffer = 0;
        this.bufferSize = 0;
    }

    public int readInt() throws IOException {
        int value = 0;
        // Read each bit of the value from the buffer
        for (int i = 0; i < 32; i++) {
            value |= readBoolean() ? (1 << i) : 0;
        }
        return value;
    }

    public byte readByte() throws IOException {
        byte value = 0;
        // Read each bit of the value from the buffer
        for (int i = 0; i < 8; i++) {
            value |= readBoolean() ? (1 << i) : 0;
        }
        return value;
    }
    public boolean readBoolean() throws IOException {
        // If the buffer is empty, refill it
        if (bufferSize == 0) {
            buffer = in.read();
            bufferSize = 8;
        }
        // Read the next bit from the buffer
        boolean bit = (buffer & (1 << (bufferSize - 1))) != 0;
        bufferSize--;
        return bit;
    }

    public boolean hasNext() throws IOException {
        // Check if there are any bits left in the buffer
        if (bufferSize > 0) {
            return true;
        }
        // Check if there are any more bytes in the input stream
        return in.available() > 0;
    }

    public void close() throws IOException {
        in.close();
    }
}