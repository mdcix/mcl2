import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MultiplicativeCipherClient {
    public static void main(String[] args) {
        try {
            // Create client socket
            Socket socket = new Socket("localhost", 8084);

            // Get input from user
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter the string to encrypt:");
            String input = scanner.nextLine();
            System.out.println("Enter the key value:");
            int key = scanner.nextInt();

            // Encrypt the string
            String encrypted = encryption(input, key);
            System.out.println("Encrypted value is: " + encrypted);

            // Send data to the server (ciphertext and key)
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(encrypted);
            out.println(key);

            // Receive decrypted text from the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String decrypted = in.readLine();
            System.out.println("Decrypted value from server is: " + decrypted);

            // Close connections
            socket.close();
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Encryption method
    public static String encryption(String input, int key) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char ascii = input.charAt(i);
            if (Character.isUpperCase(ascii)) {
                char x = (char) (((ascii - 'A') * key % 26 + 26) % 26 + 'A');
                encrypted.append(x);
            } else {
                char x = (char) (((ascii - 'a') * key % 26 + 26) % 26 + 'a');
                encrypted.append(x);
            }
        }
        return encrypted.toString();
    }
}
