import java.util.Scanner;

public class ChineseRemainderTheorem {

    // Function to find the GCD of two numbers
    public static int gcd(int a, int b) {
        return (b == 0) ? a : gcd(b, a % b);
    }

    // Function to find the modular inverse of a under modulo m using the Extended Euclidean Algorithm
    public static int modInverse(int a, int m) {
        int m0 = m, t, q;
        int x0 = 0, x1 = 1;

        if (m == 1)
            return 0;

        // Apply the extended Euclid algorithm
        while (a > 1) {
            // q is quotient
            q = a / m;
            t = m;

            // m is remainder now, process same as Euclid's algorithm
            m = a % m;
            a = t;
            t = x0;

            x0 = x1 - q * x0;
            x1 = t;
        }

        // Make x1 positive
        if (x1 < 0)
            x1 += m0;

        return x1;
    }

    // Function to implement the Chinese Remainder Theorem
    public static int chineseRemainderTheorem(int[] num, int[] rem, int k) {
        int prod = 1; // Product of all numbers
        for (int i = 0; i < k; i++)
            prod *= num[i];

        int result = 0;

        // Apply the CRT
        for (int i = 0; i < k; i++) {
            int pp = prod / num[i]; // Product of all numbers divided by num[i]
            result += rem[i] * modInverse(pp, num[i]) * pp;
        }

        return result % prod; // Return the result modulo prod
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input the number of equations
        System.out.print("Enter the number of equations: ");
        int k = scanner.nextInt();

        int[] num = new int[k];
        int[] rem = new int[k];

        // Input the moduli and the remainders
        System.out.println("Enter the moduli:");
        for (int i = 0; i < k; i++) {
            System.out.print("num[" + i + "]: ");
            num[i] = scanner.nextInt();
        }

        System.out.println("Enter the remainders:");
        for (int i = 0; i < k; i++) {
            System.out.print("rem[" + i + "]: ");
            rem[i] = scanner.nextInt();
        }

        // Check for pairwise coprime
        for (int i = 0; i < k; i++) {
            for (int j = i + 1; j < k; j++) {
                if (gcd(num[i], num[j]) != 1) {
                    System.out.println("The moduli must be pairwise coprime.");
                    scanner.close();
                    return;
                }
            }
        }

        // Calculate the result using CRT
        int result = chineseRemainderTheorem(num, rem, k);
        System.out.println("The solution x is: " + result);

        scanner.close();
    }
}
