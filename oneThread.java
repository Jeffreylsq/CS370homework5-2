
import java.util.ArrayList;

public class oneThread {
    public static void main(String[] args) {

         int num = 1000000;
        ArrayList<Integer> N = Convert(num);

        long start = System.nanoTime();
        
        while (N.size() > 0) {
            
            int p = N.get(0);
            System.out.println(p);
            for (int i = 0; i < N.size(); i++) {
                if (N.get(i) % p == 0) {
                    N.remove(N.get(i)); // if reminder is zero should not be prime
                }
            }
        }
        System.out.printf("Total Time : %.5f ms",  (System.nanoTime() - start)* 1e-6);  
    }

   public static ArrayList<Integer> Convert(int lastInt) {
        ArrayList<Integer> test = new ArrayList<>();
        for (int i = 2; i <= lastInt; i++) {
            test.add(i);
        }
        return test;
    }
}