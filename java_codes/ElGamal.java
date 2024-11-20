import java.util.Scanner;
import java.util.Random;

public class ElGamal {

    // Function to perform modular exponentiation
    public static long modExp(long base, long exponent, long mod) {
        long result = 1;
        base = base % mod;

        while (exponent > 0) {
            if ((exponent & 1) == 1) { // If exponent is odd
                result = (result * base) % mod;
            }
            exponent = exponent >> 1; // Divide exponent by 2
            base = (base * base) % mod; // Square the base
        }
        return result;
    }

    // Function to compute modular inverse using extended Euclidean algorithm
    public static long modInverse(long a, long p) {
        long m0 = p, t, q;
        long x0 = 0, x1 = 1;

        if (p == 1) return 0;

        while (a > 1) {
            // q is quotient
            q = a / p;
            t = p;

            // m is remainder now, process same as Euclid's algorithm
            p = a % p;
            a = t;
            t = x0;

            x0 = x1 - q * x0;
            x1 = t;
        }

        // Make x1 positive
        if (x1 < 0) x1 += m0;

        return x1;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Input large prime number p
        System.out.print("Enter a large prime number p: ");
        long p = scanner.nextLong();

        // Input private key d (decryption key)
        System.out.print("Enter private key d (a number less than p): ");
        long d = scanner.nextLong();

        // Input e1 (any number less than p)
        System.out.print("Enter e1 (a number less than p): ");
        long e1 = scanner.nextLong();

        // Compute e2 = e1^d mod p
        long e2 = modExp(e1, d, p);

        // Public key is (e1, e2, p)
        System.out.println("Public Key (e1, e2, p): (" + e1 + ", " + e2 + ", " + p + ")");
        System.out.println("Private Key (d): " + d);

        // Input plaintext
        System.out.print("Enter the plaintext (a number less than p): ");
        long plaintext = scanner.nextLong();

        // Generate random integer r (1 < r < p)
        long r = random.nextInt((int) (p - 1)) + 1;
        System.out.println("Random r used for encryption: " + r);

        // Compute c1 = e1^r mod p
        long c1 = modExp(e1, r, p);

        // Compute c2 = (plaintext * e2^r) mod p
        long c2 = (plaintext * modExp(e2, r, p)) % p;

        // Ciphertext is (c1, c2)
        System.out.println("Ciphertext (c1, c2): (" + c1 + ", " + c2 + ")");

        // Decrypt the ciphertext
        // Compute PT = (c2 * (c1^d)^-1) mod p
        long c1d = modExp(c1, d, p); // c1^d mod p
        long c1d_inv = modInverse(c1d, p); // (c1^d)^-1 mod p
        long decryptedPlaintext = (c2 * c1d_inv) % p;

        // Output the decrypted plaintext
        System.out.println("Decrypted Plaintext: " + decryptedPlaintext);

        scanner.close();
    }
}
