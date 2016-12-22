package training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class contains static method utils for arrays using multi-threading
 *
 * @author oleksij.onysymchuk@gmail.com
 * @version 1.0 13 NOV 2016
 */
public class MultiThreadArrayUtil {

    /**
     * Counts ArraySum in one thread
     *
     * @param array array to count sum of its elements
     * @return The sum of array's elements.
     */
    public static long countSumInCurrentThread(int[] array) {
        return Arrays.stream(array).mapToLong(el -> el).sum();
    }

    /**
     * Counts ArraySum in one thread but in parallel streams
     *
     * @param array array to count sum of its elements
     * @return The sum of array's elements.
     */
    public static long countSumInParallelStreams(int[] array) {
        return Arrays.stream(array).parallel().mapToLong(el -> el).sum();
    }

    /**
     * Counts ArraySum in several threads
     *
     * @param array array to count sum of its elements
     * @param threadsNumber Number of threads, which will be created to perform count
     * @return The sum of array's elements.
     */
    public static long countSumWithSpecifiedNumberOfThreads(int[] array, int threadsNumber) {
        long sum = 0;
        List<SumCounter> counters = createCounters(array, threadsNumber);
        startCount(counters);
        waitForAllDone(counters);
        return sumThreadResults(counters);
    }

    private static List<SumCounter> createCounters(int[] array, int threadsNumber) {
        List<SumCounter> counters = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            counters.add(new SumCounter(array, i, threadsNumber));
        }
        return counters;
    }

    private static void startCount(List<SumCounter> counters) {
        counters.forEach(Thread::start);
    }

    private static void waitForAllDone(List<SumCounter> counters) {
        for (SumCounter counter : counters) {
            try {
                counter.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static long sumThreadResults(List<SumCounter> counters) {
        return counters.stream().mapToLong(SumCounter::getLocalSum).sum();
    }


    static class SumCounter extends Thread {
        private int[] array;
        int offset;
        int interval;
        long localSum = 0;

        public SumCounter(int[] array, int offset, int interval) {
            this.array = array;
            this.offset = offset;
            this.interval = interval;
        }

        public long getLocalSum() {
            return localSum;
        }

        @Override
        public void run() {
            for (int i = offset; i < array.length; i += interval) {
                localSum += array[i];
            }
        }
    }

}
