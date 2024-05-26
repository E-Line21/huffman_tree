package ru.khafizov.huffmantree;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanTree {
    static class Node implements Comparable<Node> {
        char data;
        int freq;
        Node left;
        Node right;

        public Node(char character, int frequency) {
            this.data = character;
            this.freq = frequency;
            this.left = null;
            this.right = null;
        }

        public int compareTo(Node other) {
            return freq - other.freq;
        }
    }

    PriorityQueue<Node> pq;
    String str;
    Node root;

    public int iter;


    public HuffmanTree(String str) {
        this.iter = 0;
        this.str = str;
        this.root = generateTree(str);
    }

    private Node generateTree(String str) {
        PriorityQueue<Node> pq = generatePriorityQueue(str);

        while (pq.size() > 1) {
            this.iter++;
            Node left = pq.poll();
            Node right = pq.poll();

            assert right != null;
            Node node = new Node('⚑', left.freq + right.freq);
            node.left = left;
            node.right = right;

            pq.add(node);
        }
        return pq.poll();
    }

    private PriorityQueue<Node> generatePriorityQueue(String str) {
        Map<Character, Integer> data = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (int i = 0; i < str.length(); i++) {
            data.put(str.charAt(i), data.getOrDefault(str.charAt(i), 0) + 1);
            this.iter++;
        }
        data.forEach((ch, freq) -> {
            pq.add(new Node(ch, freq));
        });

        return pq;
    }

    public Map<Character, String> getCodes() {
        Map<Character, String> codes = new HashMap<>();
        findCodesRecursive(this.root, new StringBuilder(), 0, codes);
        return codes;
    }

    private void findCodesRecursive(Node root, StringBuilder sb, int top, Map<Character, String> codes) {
        this.iter++;
        if (root.left != null) {
            sb.append('0');
            findCodesRecursive(root.left, sb, top + 1, codes);
            sb.deleteCharAt(sb.length() - 1);
        }

        if (root.right != null) {
            sb.append('1');
            findCodesRecursive(root.right, sb, top + 1, codes);
            sb.deleteCharAt(sb.length() - 1);
        }

        if (root.left == null && root.right == null) {
            codes.put(root.data, sb.toString());
        }
    }

    public String getEncoded() {
        Map<Character, String> codes = getCodes();
        StringBuilder sb = new StringBuilder();
        for (char c : this.str.toCharArray()) {
            this.iter++;
            sb.append(codes.get(c));
        }
        return sb.toString();
    }
}
