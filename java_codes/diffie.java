import java.util.Scanner;

class diffie {

    // Power function to return value of a ^ b mod P
    private static long power(long a, long b, long p) {
        long result = 1;
        a = a % p; // In case a is larger than p
        
        while (b > 0) {
            // If b is odd, multiply a with the result
            if (b % 2 == 1) {
                result = (result * a) % p;
            }
            // Square a and halve b
            a = (a * a) % p;
            b /= 2;
        }
        return result;
    }

    // Driver code
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        long P, G, x, a, y, b, ka, kb;

        // Get user inputs
        System.out.print("Enter a prime number P: ");
        P = scanner.nextLong();
        System.out.print("Enter a primitive root G for P: ");
        G = scanner.nextLong();

        // Alice will choose the private key a
        System.out.print("Enter the private key a for Alice: ");
        a = scanner.nextLong();

        // Bob will choose the private key b
        System.out.print("Enter the private key b for Bob: ");
        b = scanner.nextLong();

        // Alice and Bob compute their respective keys
        x = power(G, a, P); // Alice's generated key
        y = power(G, b, P); // Bob's generated key

        // Generating the secret keys after exchanging keys
        ka = power(y, a, P); // Secret key for Alice
        kb = power(x, b, P); // Secret key for Bob

        // Display results
        System.out.println("The value of P: " + P);
        System.out.println("The value of G: " + G);
        System.out.println("The private key a for Alice: " + a);
        System.out.println("The private key b for Bob: " + b);
        System.out.println("The generated key x for Alice: " + x);
        System.out.println("The generated key y for Bob: " + y);
        System.out.println("Secret key for Alice: " + ka);
        System.out.println("Secret key for Bob: " + kb);

        scanner.close();
    }
}
