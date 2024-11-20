import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Arrays; 


public class RC4Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 1234);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            Scanner sc = new Scanner(System.in);

            // Get plain text from the user
            System.out.println("Enter the length of the plain text:");
            int plainTextLength = sc.nextInt();
            int[] plainText = new int[plainTextLength];

            System.out.println("Enter plain text elements:");
            for (int i = 0; i < plainTextLength; i++) {
                plainText[i] = sc.nextInt();
            }

            // Get key from the user
            System.out.println("Enter the length of the key:");
            int keyLength = sc.nextInt();
            int[] key = new int[keyLength];

            System.out.println("Enter key elements:");
            for (int i = 0; i < keyLength; i++) {
                key[i] = sc.nextInt();
            }

            // Send plain text and key to the server
            dos.writeInt(plainTextLength);
            for (int value : plainText) {
                dos.writeInt(value);
            }

            dos.writeInt(keyLength);
            for (int value : key) {
                dos.writeInt(value);
            }

            // Receive encrypted text from the server
            int encryptedLength = dis.readInt();
            int[] encryptedText = new int[encryptedLength];

            for (int i = 0; i < encryptedLength; i++) {
                encryptedText[i] = dis.readInt();
            }

            System.out.println("Encrypted text: " + Arrays.toString(encryptedText));

            // Close resources
            dis.close();
            dos.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
