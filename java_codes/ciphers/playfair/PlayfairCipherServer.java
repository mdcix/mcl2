import java.io.*;
import java.net.*;

public class PlayfairCipherServer {

    private static final int MATRIX_SIZE = 5;
    private static final String ALPHABET = "ABCDEFGHIKLMNOPQRSTUVWXYZ"; // Note: J is omitted

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8081)) {
            System.out.println("Server is waiting for a connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Read data from the client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String keyword = in.readLine();
            String ciphertext = in.readLine();
            System.out.println("Received keyword: " + keyword);
            System.out.println("Received ciphertext: " + ciphertext);

            // Recreate the Playfair matrix and decrypt the ciphertext
            String[][] playfairMatrix = generatePlayfairMatrix(keyword);
            String decryptedText = decrypt(ciphertext, playfairMatrix);
            System.out.println("Decrypted text: " + decryptedText);

            // Send the decrypted text back to the client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(decryptedText);

            // Close the connection
            socket.close();
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

    private static String decrypt(String ciphertext, String[][] matrix) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            char a = ciphertext.charAt(i);
            char b = ciphertext.charAt(i + 1);
            int[] pos1 = findPosition(a, matrix);
            int[] pos2 = findPosition(b, matrix);

            if (pos1[0] == pos2[0]) { // Same row
                plaintext.append(matrix[pos1[0]][(pos1[1] - 1 + MATRIX_SIZE) % MATRIX_SIZE]);
                plaintext.append(matrix[pos2[0]][(pos2[1] - 1 + MATRIX_SIZE) % MATRIX_SIZE]);
            } else if (pos1[1] == pos2[1]) { // Same column
                plaintext.append(matrix[(pos1[0] - 1 + MATRIX_SIZE) % MATRIX_SIZE][pos1[1]]);
                plaintext.append(matrix[(pos2[0] - 1 + MATRIX_SIZE) % MATRIX_SIZE][pos2[1]]);
            } else { // Rectangle
                plaintext.append(matrix[pos1[0]][pos2[1]]);
                plaintext.append(matrix[pos2[0]][pos1[1]]);
            }
        }

        return plaintext.toString();
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
