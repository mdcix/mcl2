import java.io.*;
import java.net.*;

public class MultiplicativeCipherServer {
    public static void main(String[] args) {
        try {
            // Create server socket
            ServerSocket serverSocket = new ServerSocket(8084);
            System.out.println("Server started. Waiting for client...");

            // Accept connection from client
            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            // Read encrypted text and key from client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String encryptedText = in.readLine();
            int key = Integer.parseInt(in.readLine());

            // Decrypt the received ciphertext
            String decrypted = decryption(encryptedText, key);
            System.out.println("Decrypted value is: " + decrypted);

            // Send the decrypted text back to the client
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(decrypted);

            // Close connections
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Decryption method
    public static String decryption(String encrypted, int key) {
        StringBuilder decrypted = new StringBuilder();
        int inverseKey = findInverseKey(key);
        if (inverseKey == -1) {
            return "Cannot be decrypted";
        }
        for (int i = 0; i < encrypted.length(); i++) {
            char ascii = encrypted.charAt(i);
            if (Character.isUpperCase(ascii)) {
                char x = (char) (((ascii - 'A') * inverseKey % 26 + 26) % 26 + 'A');
                decrypted.append(x);
            } else {
                char x = (char) (((ascii - 'a') * inverseKey % 26 + 26) % 26 + 'a');
                decrypted.append(x);
            }
        }
        return decrypted.toString();
    }

    // Method to find modular inverse of the key
    public static int findInverseKey(int key) {
        for (int i = 1; i < 26; i++) {
            if ((key * i) % 26 == 1) {
                return i;
            }
        }
        return -1;
    }
}
