package queues.tasks;


import java.util.ArrayList;

public class PrimeNumbersWritter implements MyTask {
    @Override
    public boolean execute() {
        ArrayList<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i < 10; i++) {
            if (isDividedByNumber(i))
                primeNumbers.add(i);
        }
        System.out.println(primeNumbers.toString());
        return true;
    }

    @Override
    public boolean doWhenFail() {
        return false;
    }

    private boolean isDividedByNumber(long n) {
        for (int i = 2; i < n; i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

}
