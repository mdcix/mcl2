import java.util.Scanner;

public class HillCipherDec {

    // Function to print a 2x2 matrix
    public static void printMatrix(int[][] matrix) {
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Function to find the modular inverse of 'a' under modulo 'm'
    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }

    // Function to find the inverse of a 2x2 matrix under modulo 26
    public static void inverseMatrix(int[][] matrix) {
        int a = matrix[0][0];
        int b = matrix[0][1];
        int c = matrix[1][0];
        int d = matrix[1][1];
        int det = (a * d) - (b * c);
        int newd = modInverse(det, 26);
        
        matrix[0][0] = (((d + 26) % 26) * newd) % 26;
        matrix[0][1] = (((-b + 26) % 26) * newd) % 26;
        matrix[1][0] = (((-c + 26) % 26) * newd) % 26;
        matrix[1][1] = (((a + 26) % 26) * newd) % 26;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String key, plainText = "", encryptedText;

        System.out.println("\nNOTE: KINDLY USE UPPERCASE THROUGHOUT THE PROGRAM !!!\n");
        int shift = 65;

        // Input 4-letter keyword
        System.out.print("Enter 4 letter keyword: ");
        key = scanner.nextLine();
        while (key.length() != 4) {
            System.out.print("Enter 4 letter keyword: ");
            key = scanner.nextLine();
        }

        // Input encrypted text
        System.out.print("Enter Encrypted Text: ");
        encryptedText = scanner.nextLine();

        // Convert key characters to integers
        char[] keyChars = key.toCharArray();
        for (int i = 0; i < keyChars.length; i++) {
            keyChars[i] -= shift;
        }

        // Convert encrypted text characters to integers
        char[] encryptedChars = encryptedText.toCharArray();
        for (int i = 0; i < encryptedChars.length; i++) {
            encryptedChars[i] -= shift;
        }

        // Initialize key matrix
        int[][] keyMatrix = new int[2][2];
        int ptr = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                keyMatrix[j][i] = keyChars[ptr++];
            }
        }

        // Inverse the key matrix
        inverseMatrix(keyMatrix);

        // If encryptedText length is odd, pad with 'X'
        if (encryptedText.length() % 2 == 1) {
            encryptedText += "X";
            encryptedChars = encryptedText.toCharArray(); // Update encryptedChars array
            encryptedChars[encryptedChars.length - 1] -= shift;
        }

        // Decryption process
        for (int i = 0; i < encryptedChars.length; i += 2) {
            int[] temp = {encryptedChars[i], encryptedChars[i + 1]};
            for (int row = 0; row < 2; row++) {
                int sum = 0;
                for (int col = 0; col < 2; col++) {
                    sum += keyMatrix[row][col] * temp[col];
                }
                sum = sum % 26;
                sum += shift;
                plainText += (char) sum;
            }
        }

        // Output the decrypted text
        System.out.println("\nPlain Text: " + plainText);
        scanner.close();
    }
}
