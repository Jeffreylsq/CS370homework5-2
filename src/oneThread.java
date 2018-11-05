
import java.util.ArrayList;
/**
 * @author Joshua Chen
 * Date: Nov 03, 2018
 */
public class oneThread {
    public static void main(String[] args) {

        final int n = 1000000;
        ArrayList<Integer> numbers = generateNumbersTo(n);

        long timeIn = System.nanoTime();
        while (numbers.size() > 0) {
            // Smallest prime
            int p = numbers.get(0);
            System.out.println(p);

            // Find all multiples of p within numbers and remove them
            for (int i = 0; i < numbers.size(); i++) {
                if (numbers.get(i) % p == 0) {
                    numbers.remove(numbers.get(i));
                }
            }
        }
        long timeOut = System.nanoTime() - timeIn;

        System.out.printf("Time for 1 Thread: %.2f (ms)", timeOut * 1e-6);  // Time for 1 Thread: 694864.94 (ms)
    }

    private static ArrayList<Integer> generateNumbersTo(int lastInt) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 2; i <= lastInt; i++) {
            temp.add(i);
        }
        return temp;
    }
}