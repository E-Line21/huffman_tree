package ru.khafizov;

import ru.khafizov.huffmantree.HuffmanTree;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            generateFiles();
            testFiles(i);
        }
    }

    public static void generateFiles() {
        Random random = new Random();
        for (int i = 100; i <= 10000; i+=100) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("test" + i + ".txt"))) {
                byte[] bytes = new byte[i];
                random.nextBytes(bytes);
                String generatedString = new String(bytes, StandardCharsets.UTF_8);
                writer.write(generatedString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void testFiles(int i) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data" + i + ".csv"))) {
            for (int size = 100; size <= 10000; size+=100) {
                String file = Files.readString(Path.of("test" + size + ".txt"), StandardCharsets.UTF_8);
                long start = System.nanoTime();
                HuffmanTree huffmanTree = new HuffmanTree(file);
                long time = System.nanoTime() - start;
                writer.write(size + "; " + huffmanTree.iter + "; " + time + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
