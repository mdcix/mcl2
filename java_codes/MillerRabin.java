//Miller Rabin
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class MillerRabin {

    // Function to perform the Miller-Rabin primality test
    public static boolean isProbablePrime(BigInteger n, int k) {
        // Handle trivial cases
        if (n.compareTo(BigInteger.TWO) < 0) {
            System.out.println(n + " is not prime (less than 2).");
            return false;
        }
        if (n.equals(BigInteger.TWO) || n.equals(BigInteger.valueOf(3))) {
            System.out.println(n + " is prime (2 or 3).");
            return true;
        }
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            System.out.println(n + " is not prime (even number).");
            return false;
        }

        // Write n - 1 as 2^s * d
        BigInteger d = n.subtract(BigInteger.ONE);
        int s = 0;
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.shiftRight(1);
            s++;
        }
        System.out.println("n - 1 = " + n.subtract(BigInteger.ONE) + " = 2^" + s + " * " + d);

        // Perform the test k times
        Random rand = new Random();
        for (int i = 0; i < k; i++) {
            BigInteger a = randomBigInteger(n.subtract(BigInteger.TWO)).add(BigInteger.ONE);
            System.out.println("Testing base a = " + a);
            if (!millerTest(a, d, n, s)) {
                System.out.println("Composite detected with base " + a);
                return false;
            }
            System.out.println("Base " + a + " passed.");
        }
        return true;
    }

    // Miller test for a single base
    private static boolean millerTest(BigInteger a, BigInteger d, BigInteger n, int s) {
        BigInteger x = a.modPow(d, n);
        System.out.println("Initial x = a^d % n = " + a + "^" + d + " % " + n + " = " + x);

        if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
            return true;
        }

        for (int r = 0; r < s - 1; r++) {
            x = x.modPow(BigInteger.TWO, n);
            System.out.println("x = x^2 % n = " + x);
            if (x.equals(BigInteger.ONE)) {
                return false;
            }
            if (x.equals(n.subtract(BigInteger.ONE))) {
                return true;
            }
        }

        return false;
    }

    // Generate a random BigInteger in the range [1, max)
    private static BigInteger randomBigInteger(BigInteger max) {
        Random rand = new Random();
        BigInteger randomNumber;
        do {
            randomNumber = new BigInteger(max.bitLength(), rand);
        } while (randomNumber.compareTo(max) >= 0);
        return randomNumber;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number to be tested
        System.out.print("Enter the number to test for primality: ");
        BigInteger number = new BigInteger(scanner.nextLine());

        // Read number of iterations
        System.out.print("Enter the number of iterations for the test: ");
        int k = scanner.nextInt();

        // Perform the Miller-Rabin primality test
        boolean result = isProbablePrime(number, k);
        System.out.println(number + " is " + (result ? "probably prime" : "composite"));

        scanner.close();
    }
}
