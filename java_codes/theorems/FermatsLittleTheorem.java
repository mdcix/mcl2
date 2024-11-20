import java.util.Scanner;

public class FermatsLittleTheorem {
    
    public static boolean checkPrime(int n){
        for(int i=2; i<=n/2; i++){
            if(n%i==0){
                return false;
            }
        }
        return true;
    }
    
    public static boolean checkNotDivisible(int a, int b){
        return !(a%b==0);
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter prime number (p): ");
        int p = scanner.nextInt();
        
        System.out.print("Enter number (a): ");
        int a = scanner.nextInt();

        if(checkPrime(p) && a>0 && checkNotDivisible(a,p)){
            System.out.println("\n"+p+" is a prime number and "+ a+" is a positive integer not divisible by "+p+"\nConditions Satisfied!\n");
            
            double powerVal = Math.pow(a,p-1);
            
            System.out.println("a^p-1 = "+ powerVal);
            System.out.println("a^p-1 mod p = "+ powerVal%p);
            System.out.println("1 mod p = "+ 1%p);
            
            if(powerVal%p == 1%p){
                System.out.println("\n'a' raised to the power p-1 is congruent to 1 modulo 'p'");
                System.out.println("\nHence Fermat's Little Theorem is Satisfied");
            }
            else{
                System.out.println("\nFermat's Little Theorem is Failed");
            }
            
        }
        else{
            if(!checkPrime(p)){
                System.out.println(p+" is not a prime number");
            }
            if(a<0){
                System.out.println(a+" is a negative number");
            }
            if(!checkNotDivisible(a,p)){
                System.out.println(a+" is divisible by p");
            }
            System.out.println("\nCondition Dissatisfied!");
        }

        scanner.close();
    }

}
