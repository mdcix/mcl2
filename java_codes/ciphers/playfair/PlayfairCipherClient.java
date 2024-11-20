import java.io.*;
import java.net.*;
import java.util.Scanner;

public class PlayfairCipherClient {

    private static final int MATRIX_SIZE = 5;
    private static final String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // Note: J is omitted

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8081)) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the keyword (without J): ");
            String keyword = scanner.nextLine().toUpperCase().replace("J", "I");

            // Generate Playfair matrix
            String[][] playfairMatrix = generatePlayfairMatrix(keyword);

            System.out.print("Enter text to encrypt (no spaces, only uppercase letters): ");
            String plaintext = scanner.nextLine().toUpperCase().replace("J", "I");

            // Encrypt the plaintext
            String encryptedText = encrypt(plaintext, playfairMatrix);
            System.out.println("Encrypted text: " + encryptedText);

            // Send the encrypted text and keyword to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(keyword);
            out.println(encryptedText);

            // Receive the decrypted text from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String decryptedText = in.readLine();
            System.out.println("Decrypted text from server: " + decryptedText);

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[][] generatePlayfairMatrix(String keyword) {
        boolean[] used = new boolean[26];
        StringBuilder matrixString = new StringBuilder();
        String[][] matrix = new String[MATRIX_SIZE][MATRIX_SIZE];

        // Add keyword to matrix
        for (char ch : keyword.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z' && !used[ch - 'A']) {
                used[ch - 'A'] = true;
                matrixString.append(ch);
            }
        }

        // Add remaining letters of the alphabet
        for (char ch : ALPHABET.toCharArray()) {
            if (!used[ch - 'A']) {
                matrixString.append(ch);
            }
        }

        // Fill the matrix
        int index = 0;
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matrix[i][j] = String.valueOf(matrixString.charAt(index++));
            }
        }

        return matrix;
    }

    private static String encrypt(String plaintext, String[][] matrix) {
        plaintext = prepareText(plaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += 2) {
            char a = plaintext.charAt(i);
            char b = plaintext.charAt(i + 1);
            int[] pos1 = findPosition(a, matrix);
            int[] pos2 = findPosition(b, matrix);

            if (pos1[0] == pos2[0]) { // Same row
                ciphertext.append(matrix[pos1[0]][(pos1[1] + 1) % MATRIX_SIZE]);
                ciphertext.append(matrix[pos2[0]][(pos2[1] + 1) % MATRIX_SIZE]);
            } else if (pos1[1] == pos2[1]) { // Same column
                ciphertext.append(matrix[(pos1[0] + 1) % MATRIX_SIZE][pos1[1]]);
                ciphertext.append(matrix[(pos2[0] + 1) % MATRIX_SIZE][pos2[1]]);
            } else { // Rectangle
                ciphertext.append(matrix[pos1[0]][pos2[1]]);
                ciphertext.append(matrix[pos2[0]][pos1[1]]);
            }
        }

        return ciphertext.toString();
    }

    private static String prepareText(String text) {
        StringBuilder preparedText = new StringBuilder();
        char prevChar = '\0';

        for (char ch : text.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                if (ch == 'J') ch = 'I'; // Replace J with I

                if (ch == prevChar) {
                    preparedText.append('X'); // Insert filler 'X' if same letters are adjacent
                }

                preparedText.append(ch);
                prevChar = ch;
            }
        }

        // Ensure the length is even
        if (preparedText.length() % 2 != 0) {
            preparedText.append('X');
        }

        return preparedText.toString();
    }

    private static int[] findPosition(char ch, String[][] matrix) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (matrix[i][j].charAt(0) == ch) {
                    return new int[]{i, j};
                }
            }
        }
        throw new IllegalArgumentException("Character not found in matrix");
    }
}
