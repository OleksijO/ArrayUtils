package training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

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

    private static void startCount(List<? extends Thread> counters) {
        counters.forEach(Thread::start);
    }

    private static void waitForAllDone(List<? extends Thread> counters) {
        for (Thread counter : counters) {
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

    /**
     * Counts ArraySum in several threads to One atomic var
     *
     * @param array array to count sum of its elements
     * @param threadsNumber Number of threads, which will be created to perform count
     * @return The sum of array's elements.
     */
    public static long countSumWithSpecifiedNumberOfThreadsToAtomicVar(int[] array, int threadsNumber) {
        AtomicLong sum=new AtomicLong(0);
        List<SumToAtomicCounter> counters = createSumToAtomicCounters(array, threadsNumber, sum);
        startCount(counters);
        waitForAllDone(counters);
        return sum.get();
    }

    private static List<SumToAtomicCounter> createSumToAtomicCounters(int[] array, int threadsNumber, AtomicLong sum) {
        List<SumToAtomicCounter> counters = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            counters.add(new SumToAtomicCounter(array, i, threadsNumber,sum));
        }
        return counters;
    }


    static class SumToAtomicCounter extends Thread {
        private int[] array;
        int offset;
        int interval;
        AtomicLong sum;

        public SumToAtomicCounter(int[] array, int offset, int interval, AtomicLong sum) {
            this.array = array;
            this.offset = offset;
            this.interval = interval;
            this.sum = sum;
        }

         @Override
        public void run() {
            for (int i = offset; i < array.length; i += interval) {
                sum.getAndAdd(array[i]);
            }
        }
    }

    public static long countSumWithSpecifiedNumberOfThreadsToAtomicVarCAS(int[] array, int threadNumber) {
        AtomicLong sum=new AtomicLong(0);
        List<SumToAtomicCounterCAS> counters = createSumToAtomicCountersCAS(array, threadNumber, sum);
        startCount(counters);
        waitForAllDone(counters);
        return sum.get();
    }

    private static List<SumToAtomicCounterCAS> createSumToAtomicCountersCAS(int[] array, int threadsNumber, AtomicLong sum) {
        List<SumToAtomicCounterCAS> counters = new ArrayList<>();
        for (int i = 0; i < threadsNumber; i++) {
            counters.add(new SumToAtomicCounterCAS(array, i, threadsNumber,sum));
        }
        return counters;
    }

    static class SumToAtomicCounterCAS extends Thread {
        private int[] array;
        int offset;
        int interval;
        AtomicLong sum;

        public SumToAtomicCounterCAS(int[] array, int offset, int interval, AtomicLong sum) {
            this.array = array;
            this.offset = offset;
            this.interval = interval;
            this.sum = sum;
        }

        @Override
        public void run() {
            long expected;
            long newValue;
            for (int i = offset; i < array.length; i += interval) {
                do{
                    expected = sum.get();
                    newValue = expected+array[i];
                } while (!sum.compareAndSet(expected,newValue));
            }
        }
    }

}
