import java.io.*;
import java.net.*;
import java.util.Scanner;

public class VigenereCipherClient {

    // Generate the key by extending it to match the length of the plaintext without spaces
    static String generateKey(String str, String key) {
        StringBuilder extendedKey = new StringBuilder(key);
        int strLength = str.replace(" ", "").length(); // Count only characters excluding spaces

        while (extendedKey.length() < strLength) {
            extendedKey.append(key);
        }

        return extendedKey.substring(0, strLength);
    }

    // Encrypt the plaintext, skipping spaces
    static String encrypt(String str, String key) {
        StringBuilder ciphertext = new StringBuilder();
        int keyIndex = 0; // To track the position in the key

        for (char ch : str.toCharArray()) {
            if (Character.isLetter(ch)) { // Encrypt only letters
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char keyBase = Character.isUpperCase(key.charAt(keyIndex)) ? 'A' : 'a';

                int shift = (ch - base + (key.charAt(keyIndex) - keyBase)) % 26;
                if (shift < 0) shift += 26;

                ciphertext.append((char) (base + shift));
                keyIndex = (keyIndex + 1) % key.length(); // Move to the next key character
            } else {
                ciphertext.append(ch); // Append spaces and other characters unchanged
            }
        }
        return ciphertext.toString();
    }

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8082)) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter plaintext: ");
            String plaintext = scanner.nextLine();

            System.out.print("Enter keyword: ");
            String keyword = scanner.nextLine();

            // Generate key based on the plaintext (excluding spaces)
            String key = generateKey(plaintext, keyword);

            // Encrypt the plaintext
            String cipherText = encrypt(plaintext, key);
            System.out.println("Ciphertext: " + cipherText);

            // Send the encrypted text and keyword to the server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(key);
            out.println(cipherText);

            // Receive the decrypted text from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String decryptedText = in.readLine();
            System.out.println("Decrypted text from server: " + decryptedText);

            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
