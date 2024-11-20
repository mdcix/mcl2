import java.io.*;
import java.net.*;

public class RSAServer {
    // Function to calculate gcd
    public static int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // Function to compute modular inverse using Extended Euclidean Algorithm
    public static int modInverse(int a, int m) {
        int m0 = m;
        int y = 0, x = 1;

        if (m == 1) return 0;

        while (a > 1) {
            int q = a / m;
            int t = m;

            m = a % m;
            a = t;
            t = y;

            y = x - q * y;
            x = t;
        }

        if (x < 0) x += m0;

        return x;
    }

    // Function for modular exponentiation
    public static int modPow(int base, int exp, int mod) {
        int result = 1;
        base = base % mod;

        while (exp > 0) {
            if ((exp % 2) == 1) {
                result = (result * base) % mod;
            }
            exp = exp >> 1; // exp /= 2
            base = (base * base) % mod;
        }
        return result;
    }

    public static void main(String[] args) {
        try {
            int p = 7;
            int q = 11;
            int n = p * q; // Modulus
            int phi = (p - 1) * (q - 1); // Euler's totient function
            int e = 13; // Public exponent
            int d = modInverse(e, phi); // Private exponent

            System.out.println("Server Public key (n, e): (" + n + ", " + e + ")");
            System.out.println("Server Private key (d): " + d);

            ServerSocket serverSocket = new ServerSocket(2345);
            System.out.println("Server is waiting for connections...");

            while (true) {
                Socket socket = serverSocket.accept();
                DataInputStream in = new DataInputStream(socket.getInputStream());
                int c = in.readInt();
                System.out.println("Received encrypted data: " + c);

                // Decryption
                int pt = modPow(c, d, n);
                System.out.println("Decrypted (Original Message): " + pt);

                in.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
