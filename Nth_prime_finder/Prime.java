
import java.util.ArrayList;
import java.util.Scanner;


public class Prime {
    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        Scanner in = new Scanner(System.in);
        long primeNumber = in.nextLong();

        // printing out the nth prime as well as the value of the nth prime
        System.out.print(primeNumber + " " + nthPrime(primeNumber));

        long end = System.currentTimeMillis();

        float sec = end - start;

        System.out.print(" " + sec);
    }

    public static long nthPrime(long p) {

        // making a list for all the numbers and primes
        ArrayList<Long> all_Numbers = new ArrayList<Long>();
        ArrayList<Long> primes = new ArrayList<Long>();

        // adding the values to the prime that are not checked by the for loops below
        primes.add((long) 2);
        primes.add((long) 3);
        primes.add((long) 5);
        primes.add((long) 7);

        // inequality used for finding the upper bounds https://en.wikipedia.org/wiki/Prime-counting_function#Inequalities by Rosser(1941)

        int log_Num = (int) (p * Math.log((p * Math.log(p))));


        // for loop that finds approximation of the nth prime numbers value and sets that as the upper bound
        // used as the upper bound for all values that are (6 * k) + or - 1 since all prime numbers above 3
        // can be represented by the equation https://en.wikipedia.org/wiki/Primality_test#:~:text=9%20External%20links-,Simple%20methods,composite%2C%20otherwise%20it%20is%20prime.

        for (long i = 1; 6 * i + 1 < log_Num; i++) {
            all_Numbers.add(6 * i - 1);
            all_Numbers.add(6 * i + 1);
        }

        // for loop that checks all the values of the (6 * k) + or - 1 to see if they are prime
        for(int j = 0; j < all_Numbers.size(); j ++){

            // finding the square root of all the values from the equation (6 * k) + or - 1) to check for all non redundant multiples
            int square_Root = (int) Math.sqrt(all_Numbers.get(j));

            // for loop that starts at 3 and checks all odd values until the the sqrt of the number in the list of all the values
            for (int i = 3; i <= square_Root; i+=2) {

                // if the number is divisible by any of the multiples break for loop
                if (all_Numbers.get(j) % i == 0) {
                    break;
                }
                // if the number is not divisible but there is still possible multiplies. re start the loop
                else if(i + 1 < square_Root){
                    continue;
                }
                // if all the multiples checked + 1 is greater then the square root, all multiplies that are not redundant have been checked
                else if(i + 1 >= square_Root){
                    // add it to the list of primes
                    primes.add(all_Numbers.get(j));
                }
            }
        }
        // returning last value in the prime list
        return primes.get((int) (p-1));
    }
}




