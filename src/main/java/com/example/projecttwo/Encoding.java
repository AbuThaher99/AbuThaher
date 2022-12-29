package com.example.projecttwo;

public class Encoding {
    private byte[] data;
    private String code;
    private int frequency;
    private int length;

    public Encoding(byte[] data,String code, int frequency, int length) {
        this.data = data;
        this.code = code;
        this.frequency = frequency;
        this.length = length;
    }

    public Encoding(byte[] data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }



    public String toString() {
        return ""+" "+code+" "+frequency+" "+length+"\n";
    }

}
