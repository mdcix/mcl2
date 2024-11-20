import java.io.*;
import java.net.*;
import java.util.*;

public class RC4Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(1234);
            System.out.println("Server is listening on port 1234...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            // Receive length and key
            int plainTextLength = dis.readInt();
            int[] plainText = new int[plainTextLength];

            for (int i = 0; i < plainTextLength; i++) {
                plainText[i] = dis.readInt();
            }

            int keyLength = dis.readInt();
            int[] key = new int[keyLength];

            for (int i = 0; i < keyLength; i++) {
                key[i] = dis.readInt();
            }

            System.out.println("Received plain text: " + Arrays.toString(plainText));
            System.out.println("Received key: " + Arrays.toString(key));

            // Encrypt using RC4
            int[] encryptedText = rc4Encrypt(plainText, key);
            System.out.println("Encrypted text: " + Arrays.toString(encryptedText));

            // Send the encrypted text back to the client
            dos.writeInt(encryptedText.length);
            for (int value : encryptedText) {
                dos.writeInt(value);
            }

            // Decrypt the text
            int[] decryptedText = rc4Encrypt(encryptedText, key);
            System.out.println("Decrypted text: " + Arrays.toString(decryptedText));

            // Close resources
            dis.close();
            dos.close();
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // RC4 Encryption/Decryption
    public static int[] rc4Encrypt(int[] plainText, int[] key) {
        int[] s = initS(key);
        int[] k = new int[plainText.length];
        int i = 0, j = 0;

        for (int cnt = 0; cnt < plainText.length; cnt++) {
            i = (i + 1) % s.length;
            j = (j + s[i]) % s.length;

            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;

            int ct = (s[i] + s[j]) % s.length;
            k[cnt] = s[ct];
        }

        int[] encryptedText = new int[plainText.length];
        for (int x = 0; x < plainText.length; x++) {
            encryptedText[x] = plainText[x] ^ k[x];
        }

        return encryptedText;
    }

    public static int[] initS(int[] key) {
        int[] s = new int[256];
        int[] gen = new int[256];

        for (int i = 0; i < 256; i++) {
            s[i] = i;
            gen[i] = key[i % key.length];
        }

        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + s[i] + gen[i]) % 256;
            int temp = s[i];
            s[i] = s[j];
            s[j] = temp;
        }

        return s;
    }
}
