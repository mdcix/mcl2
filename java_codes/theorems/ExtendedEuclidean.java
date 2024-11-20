import java.util.Scanner;

public class ExtendedEuclidean {

    // Function to perform the extended Euclidean algorithm
    public static void extendedEuclidean(long a, long b) {
        // Initialize variables
        long s1 = 1, s2 = 0, t1 = 0, t2 = 1;
        long r1 = a, r2 = b;

        while (r2 != 0) {
            // Calculate quotient
            long q = r1 / r2;

            // Calculate remainder
            long remainder = r1 % r2;

            // Update r1 and r2
            r1 = r2;
            r2 = remainder;

            // Update s1 and s2
            long tempS = s1 - q * s2;
            s1 = s2;
            s2 = tempS;

            // Update t1 and t2
            long tempT = t1 - q * t2;
            t1 = t2;
            t2 = tempT;
        }

        // r1 now holds the GCD of a and b
        System.out.println("GCD: " + r1);
        System.out.println("Coefficients: s = " + s1 + ", t = " + t1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read inputs
        System.out.print("Enter first integer (a): ");
        long a = scanner.nextLong();
        
        System.out.print("Enter second integer (b): ");
        long b = scanner.nextLong();

        // Perform Extended Euclidean Algorithm
        extendedEuclidean(a, b);

        scanner.close();
    }
}
