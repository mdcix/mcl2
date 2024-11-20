import java.io.*;
import java.net.*;

public class VigenereCipherServer {

    // Decrypt the ciphertext, skipping spaces
    static String decrypt(String cipherText, String key) {
        StringBuilder origText = new StringBuilder();
        int keyIndex = 0; // To track the position in the key

        for (char ch : cipherText.toCharArray()) {
            if (Character.isLetter(ch)) { // Decrypt only letters
                char base = Character.isUpperCase(ch) ? 'A' : 'a';
                char keyBase = Character.isUpperCase(key.charAt(keyIndex)) ? 'A' : 'a';

                int shift = (ch - base - (key.charAt(keyIndex) - keyBase) + 26) % 26;

                origText.append((char) (base + shift));
                keyIndex = (keyIndex + 1) % key.length(); // Move to the next key character
            } else {
                origText.append(ch); // Append spaces and other characters unchanged
            }
        }
        return origText.toString();
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8082)) {
            System.out.println("Server is waiting for a connection...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Receive data from client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String keyword = in.readLine();
            String ciphertext = in.readLine();
            System.out.println("Received keyword: " + keyword);
            System.out.println("Received ciphertext: " + ciphertext);

            // Decrypt the received ciphertext
            String decryptedText = decrypt(ciphertext, keyword);
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
}
