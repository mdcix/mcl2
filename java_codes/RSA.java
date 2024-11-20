import java.math.BigInteger;

public class RSA {

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
        int p = 7;
        int q = 11;
        int n = p * q; // Modulus
        int phi = (p - 1) * (q - 1); // Euler's totient function
        int e = 13; // Public exponent

        // Check if e is coprime with phi
        while (e < phi) {
            if (gcd(e, phi) == 1) break;
            e++;
        }

        int d = modInverse(e, phi); // Private exponent

        System.out.println("Public key (n, e): (" + n + ", " + e + ")");
        System.out.println("Private key (d): " + d);

        int msg = 5; 
        System.out.println("Original Message: " + msg);

        // Encryption
        int c = modPow(msg, e, n);
        System.out.println("Encrypted data: " + c);

    
        int pt = modPow(c, d, n);
        System.out.println("Decrypted (Original Message): " + pt);
    }
}
