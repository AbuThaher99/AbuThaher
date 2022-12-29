package com.example.projecttwo;

import javafx.scene.Node;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;

public class test {
    public static void main(String[] args) throws IOException {
    //    compress("C:\\Users\\moham\\Desktop\\ProjectTwoTest\\moha.txt","C:\\Users\\moham\\Desktop\\ProjectTwoTest\\moha.huff");

        decompress("C:\\Users\\moham\\Desktop\\ProjectTwoTest\\moha.huff","C:\\Users\\moham\\Desktop\\ProjectTwoTest\\moha2.txt");
    }
    public static HuffmanNode countFrequencies(String fileName) throws IOException {
        LinkedList<Byte> Character = new LinkedList<>();
        LinkedList<Integer> Frequencies = new LinkedList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                if (!Character.contains((byte) line.charAt(i))) {

                    Character.add((byte) line.charAt(i));
                    Frequencies.add(1);
                } else {
                    int index = Character.indexOf((byte) line.charAt(i));
                    Frequencies.set(index, Frequencies.get(index) + 1);

                }
            }
        }
        reader.close();

        PriorityQueue<HuffmanNode> pq = new PriorityQueue<>();
        for (int i = 0; i < Character.size(); i++) {
            pq.add(new HuffmanNode(Character.get(i), Frequencies.get(i)));
        }

        while (pq.size() > 1) {
            HuffmanNode left = pq.poll();
            HuffmanNode right = pq.poll();
            HuffmanNode parent = new HuffmanNode(left.getFrequency() + right.getFrequency(), left, right);
            pq.add(parent);
        }


        return pq.poll();
    }


    public static LinkedList<Encoding> createTble(HuffmanNode root) {
        LinkedList<Encoding> encodings = new LinkedList<>();
        preOrder(root, "", encodings);
        return encodings;
    }




    private static void preOrder(HuffmanNode node, String code, LinkedList<Encoding> encodings) {
        if (node.isLeaf()) {
            Encoding encoding = new Encoding(new byte[]{node.getData()},code, node.getFrequency(), code.length());
            encodings.add(encoding);
        } else {
            preOrder(node.getLeft(), code + "0", encodings);
            preOrder(node.getRight(), code + "1", encodings);
        }
    }



    public static void compress(String inputFileName, String outputFileName) throws IOException {
        // Count the frequencies of the characters in the input file
        HuffmanNode root = countFrequencies(inputFileName);

        // Create the encoding table from the Huffman tree
        LinkedList<Encoding> encodings = createTble(root);

        // Open the input file and the output file
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        BufferedBitWriter writer = new BufferedBitWriter(outputFileName, false);

        // Write the file extension to the output file
        String extension = getFileExtension(inputFileName);
        writer.write(extension);

        // Write the Huffman tree to the output file
        writeTree(root, writer);

        // Read the input file one character at a time, look up the encoding, and write it to the output file
        String line;
        while ((line = reader.readLine()) != null) {
            for (int i = 0; i < line.length(); i++) {
                byte data = (byte) line.charAt(i);
                String code = getEncoding(encodings, data).getCode();
                writer.write(code);
            }
        }

        // Close the input file and the output file
        reader.close();
        writer.close();
    }

    private static String getFileExtension(String inputFileName) {
        int index = inputFileName.lastIndexOf('.');
        if (index == -1) {
            return "";
        } else {
            return inputFileName.substring(index + 1);
        }
    }

    private static void writeTree(HuffmanNode node, BufferedBitWriter writer) throws IOException {
        if (node.isLeaf()) {
            writer.writeBoolean(true);
            writer.write(String.format("%8s", Integer.toBinaryString(node.getData() & 0xFF)).replace(' ', '0'));
        } else {
            writer.writeBoolean(false);
            writeTree(node.getLeft(), writer);
            writeTree(node.getRight(), writer);
        }
    }

    private static Encoding getEncoding(LinkedList<Encoding> encodings, byte data) {
        for (Encoding encoding : encodings) {
            if (encoding.getData()[0] == data) {
                return encoding;
            }
        }
        return null;
    }


    public static void decompress(String inputFileName, String outputFileName) throws IOException {
        // Open the input file and the output file
        BufferedBitReader reader = new BufferedBitReader(inputFileName);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));

        // Read the file extension from the input file
        String extension = String.valueOf(reader.readByte() + reader.readByte() + reader.readByte());

        // Read the Huffman tree from the input file
        HuffmanNode root = readTree(reader);

        // Traverse the Huffman tree and decode the input file
        HuffmanNode node = root;
        while (reader.hasNext()) {
            boolean bit = reader.readBoolean();
            if (bit) {
                node = node.getRight();
            } else {
                node = node.getLeft();
            }
            if (node.isLeaf()) {
                writer.write(node.getData());
                node = root;
            }
        }

        // Close the input file and the output file
        reader.close();
        writer.close();
    }


    private static HuffmanNode readTree(BufferedBitReader reader) throws IOException {
        boolean isLeaf = reader.readBoolean();
        if (isLeaf) {
            byte data = reader.readByte();
            return new HuffmanNode(data, 0);
        } else {
            HuffmanNode left = readTree(reader);
            HuffmanNode right = readTree(reader);
            return new HuffmanNode(0, left, right);
        }
    }



}
