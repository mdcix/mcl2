import java.math.BigInteger;
import java.util.Scanner;

public class EulersTheorem {

    // Function to compute Euler's Totient Function φ(m) with detailed steps
    public static BigInteger eulerTotient(BigInteger m) {
        BigInteger result = m;
        BigInteger originalM = m; // Save the original value of m for printing later
        BigInteger i = BigInteger.valueOf(2);

        System.out.println("Computing φ(" + originalM + "):");

        // Check for all prime factors
        while (i.multiply(i).compareTo(m) <= 0) {
            if (m.mod(i).equals(BigInteger.ZERO)) {
                // i is a prime factor of m
                System.out.println("  Prime factor: " + i);
                while (m.mod(i).equals(BigInteger.ZERO)) {
                    m = m.divide(i);
                }
                result = result.multiply(BigInteger.ONE.subtract(BigInteger.ONE.divide(i)));
                System.out.println("  Intermediate result: φ = " + result);
            }
            i = i.add(BigInteger.ONE);
        }

        // If m is a prime number greater than 1
        if (m.compareTo(BigInteger.ONE) > 0) {
            System.out.println("  Prime factor: " + m);
            result = result.multiply(BigInteger.ONE.subtract(BigInteger.ONE.divide(m)));
            System.out.println("  Intermediate result: φ = " + result);
        }

        return result;
    }

    // Function for modular exponentiation
    public static BigInteger modularExponentiation(BigInteger base, BigInteger exponent, BigInteger mod) {
        System.out.println("Computing " + base + "^" + exponent + " % " + mod + ":");
        BigInteger result = base.modPow(exponent, mod);
        System.out.println("  Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read base, exponent, and modulus from user
        System.out.print("Enter the base (a): ");
        BigInteger a = new BigInteger(scanner.nextLine());
        System.out.print("Enter the exponent (b): ");
        BigInteger b = new BigInteger(scanner.nextLine());
        System.out.print("Enter the modulus (m): ");
        BigInteger m = new BigInteger(scanner.nextLine());

        // Compute φ(m)
        BigInteger phi = eulerTotient(m);
        System.out.println("φ(" + m + ") = " + phi);

        // Reduce the exponent modulo φ(m)
        BigInteger reducedExponent = b.mod(phi);
        System.out.println("Reduced exponent b % φ(m) = " + reducedExponent);

        // Compute a^b % m
        BigInteger result = modularExponentiation(a, reducedExponent, m);
        System.out.println(a + "^" + b + " % " + m + " = " + result);

        scanner.close();
    }
}
