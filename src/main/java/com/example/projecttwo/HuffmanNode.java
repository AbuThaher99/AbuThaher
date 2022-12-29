package com.example.projecttwo;



public class HuffmanNode implements Comparable<HuffmanNode> {
    private byte data;
    private int frequency;
    private HuffmanNode left;
    private HuffmanNode right;

    public HuffmanNode( int frequency, HuffmanNode left, HuffmanNode right) {

        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public HuffmanNode(byte data, int frequency) {
        this.data = data;
        this.frequency = frequency;
    }


    public byte getData() {
        return data;
    }

    public void setData(byte data) {
        this.data = data;
    }
    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    public HuffmanNode getLeft() {
        return left;
    }

    public void setLeft(HuffmanNode left) {
        this.left = left;
    }

    public HuffmanNode getRight() {
        return right;
    }

    public void setRight(HuffmanNode right) {
        this.right = right;
    }
    //to String method

    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringHelper(sb, "", this);
        return sb.toString();
    }
    public boolean isLeaf() {
        return left == null && right == null;
    }

    private void toStringHelper(StringBuilder sb, String indent, HuffmanNode node) {
        if (node == null) {
            sb.append(indent + "null\n");
            return;
        }
        sb.append(indent + node.getData() + " (" + node.frequency + ")\n");
        toStringHelper(sb, indent + "  ", node.left);
        toStringHelper(sb, indent + "  ", node.right);
    }

    @Override
    public int compareTo(HuffmanNode o) {
        return this.frequency - o.frequency;

    }
}
