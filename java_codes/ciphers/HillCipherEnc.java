import java.util.Scanner;

public class HillCipherEnc {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String key, plainText, encryptedText = "";

        System.out.println("\nNOTE: KINDLY USE UPPERCASE THROUGHOUT THE PROGRAM !!!");
        int shift = 65;

        // Get 4-letter keyword
        System.out.print("\nEnter 4 letter keyword : ");
        key = scanner.next();
        while (key.length() != 4) {
            System.out.print("Enter 4 letter keyword : ");
            key = scanner.next();
        }

        // Get plain text
        System.out.print("Enter secret message : ");
        plainText = scanner.next();

        // Create key matrix from user input
        int[][] keyMatrix = new int[2][2];
        int ptr = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                keyMatrix[j][i] = key.charAt(ptr++) - shift;
            }
        }

        // Pad plaintext with 'X' if odd length
        if (plainText.length() % 2 == 1) {
            plainText += "X";
        }

        // Encryption process
        for (int i = 0; i < plainText.length(); i += 2) {
            int[] temp = {plainText.charAt(i) - shift, plainText.charAt(i+1) - shift};
            for (int k = 0; k < 2; k++) {
                int sum = 0;
                for (int j = 0; j < 2; j++) {
                    sum += keyMatrix[k][j] * temp[j];
                }
                sum = sum % 26;
                sum += shift;
                encryptedText += (char) sum;
            }
        }

        System.out.println("\nEncrypted Text : " + encryptedText);

        scanner.close();
    }
}