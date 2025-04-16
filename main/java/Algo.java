
package main.java;
import java.io.*;
import java.nio.file.Files;
import java.util.Arrays;

public class Algo {

    private static final int BLOCK_SIZE = 16;

    public static boolean Encrypt(String keyStr, File inputFile, File outputFile) {
        try {
            if (keyStr.length() != 16) {
                System.out.println("Key must be 16 characters.");
                return false;
            }

            byte[] key = Arrays.copyOf(keyStr.getBytes(), BLOCK_SIZE);
            byte[] data = pad(Files.readAllBytes(inputFile.toPath()));

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (int i = 0; i < data.length; i += BLOCK_SIZE) {
                byte[] block = Arrays.copyOfRange(data, i, i + BLOCK_SIZE);
                byte[] encrypted = RawAES.encrypt(block, key);
                outputStream.write(encrypted);
            }

            Files.write(outputFile.toPath(), outputStream.toByteArray());
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean Decrypt(String keyStr, File inputFile, File outputFile) {
        try {
            if (keyStr.length() != 16) {
                System.out.println("Key must be 16 characters.");
                return false;
            }

            byte[] key = Arrays.copyOf(keyStr.getBytes(), BLOCK_SIZE);
            byte[] data = Files.readAllBytes(inputFile.toPath());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (int i = 0; i < data.length; i += BLOCK_SIZE) {
                byte[] block = Arrays.copyOfRange(data, i, i + BLOCK_SIZE);
                byte[] decrypted = RawAES.decrypt(block, key);
                outputStream.write(decrypted);
            }

            Files.write(outputFile.toPath(), unpad(outputStream.toByteArray()));
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Padding logic
    private static byte[] pad(byte[] data) {
        int padding = BLOCK_SIZE - (data.length % BLOCK_SIZE);
        byte[] padded = Arrays.copyOf(data, data.length + padding);
        for (int i = data.length; i < padded.length; i++) {
            padded[i] = (byte) padding;
        }
        return padded;
    }

    private static byte[] unpad(byte[] data) {
        int padding = data[data.length - 1];
        return Arrays.copyOf(data, data.length - padding);
}
}