package training;

public class MultiThreadSumCountingRunner {

    public static void main(String[] args) {
        int[] array = createIntFilledArray(40000000);
        countSumInCurrentThread(array);
        countSumInParallelStreams(array);
        for (int i = 1; i < 16; i++) {
            performMultiThreadingCount(array, i);
        }
        for (int i = 1; i < 4; i++) {
            performMultiThreadingCountToAtomic(array, i);
        }
        for (int i = 4; i <= 16; i+=4) {
            performMultiThreadingCountToAtomic(array, i);
        }
        for (int i = 1; i < 4; i++) {
            performMultiThreadingCountToAtomicCAS(array, i);
        }
        for (int i = 4; i <= 16; i+=4) {
            performMultiThreadingCountToAtomicCAS(array, i);
        }

        countSumInCurrentThread(array);
        countSumInParallelStreams(array);
    }


    // OUTPUT:
    /*
        Counting sum array[40000000] in current thread. 	Time = 617 ms. Sum =799999980000000
        Counting sum array[40000000] in parallel streams. 	Time = 162 ms. Sum =799999980000000
        Counting sum array[40000000] with 1 thread. 	Time = 96 ms. Sum =799999980000000
        Counting sum array[40000000] with 2 thread. 	Time = 45 ms. Sum =799999980000000
        Counting sum array[40000000] with 3 thread. 	Time = 31 ms. Sum =799999980000000
        Counting sum array[40000000] with 4 thread. 	Time = 24 ms. Sum =799999980000000
        Counting sum array[40000000] with 5 thread. 	Time = 34 ms. Sum =799999980000000
        Counting sum array[40000000] with 6 thread. 	Time = 29 ms. Sum =799999980000000
        Counting sum array[40000000] with 7 thread. 	Time = 28 ms. Sum =799999980000000
        Counting sum array[40000000] with 8 thread. 	Time = 21 ms. Sum =799999980000000
        Counting sum array[40000000] with 9 thread. 	Time = 42 ms. Sum =799999980000000
        Counting sum array[40000000] with 10 thread. 	Time = 37 ms. Sum =799999980000000
        Counting sum array[40000000] with 11 thread. 	Time = 30 ms. Sum =799999980000000
        Counting sum array[40000000] with 12 thread. 	Time = 35 ms. Sum =799999980000000
        Counting sum array[40000000] with 13 thread. 	Time = 19 ms. Sum =799999980000000
        Counting sum array[40000000] with 14 thread. 	Time = 63 ms. Sum =799999980000000
        Counting sum array[40000000] with 15 thread. 	Time = 100 ms. Sum =799999980000000
        Counting sum array[40000000] with 1 thread. to atomic 	Time = 570 ms. Sum =799999980000000
        Counting sum array[40000000] with 2 thread. to atomic 	Time = 1671 ms. Sum =799999980000000
        Counting sum array[40000000] with 3 thread. to atomic 	Time = 2571 ms. Sum =799999980000000
        Counting sum array[40000000] with 4 thread. to atomic 	Time = 3119 ms. Sum =799999980000000
        Counting sum array[40000000] with 8 thread. to atomic 	Time = 4870 ms. Sum =799999980000000
        Counting sum array[40000000] with 12 thread. to atomic 	Time = 4719 ms. Sum =799999980000000
        Counting sum array[40000000] with 16 thread. to atomic 	Time = 5133 ms. Sum =799999980000000
        Counting sum array[40000000] with 1 thread. to atomic CAS 	Time = 476 ms. Sum =799999980000000
        Counting sum array[40000000] with 2 thread. to atomic CAS 	Time = 1470 ms. Sum =799999980000000
        Counting sum array[40000000] with 3 thread. to atomic CAS 	Time = 2600 ms. Sum =799999980000000
        Counting sum array[40000000] with 4 thread. to atomic CAS 	Time = 2840 ms. Sum =799999980000000
        Counting sum array[40000000] with 8 thread. to atomic CAS 	Time = 4852 ms. Sum =799999980000000
        Counting sum array[40000000] with 12 thread. to atomic CAS 	Time = 4892 ms. Sum =799999980000000
        Counting sum array[40000000] with 16 thread. to atomic CAS 	Time = 5018 ms. Sum =799999980000000
        Counting sum array[40000000] in current thread. 	Time = 550 ms. Sum =799999980000000
        Counting sum array[40000000] in parallel streams. 	Time = 148 ms. Sum =799999980000000
    */

    private static int[] createIntFilledArray(int totalNumber) {
        int[] arr = new int[totalNumber];
        for (int i = 0; i < totalNumber; i++) {
            arr[i] = i;
        }
        return arr;
    }

    private static void countSumInCurrentThread(int[] array) {
        long startTime = System.currentTimeMillis();
        long sum = MultiThreadArrayUtil.countSumInCurrentThread(array);
        System.out.println("Counting sum array[" + array.length + "] in current thread. \tTime = " + (System.currentTimeMillis() - startTime) + " ms. Sum =" + sum);
    }

    private static void countSumInParallelStreams(int[] array) {
        long startTime = System.currentTimeMillis();
        long sum = MultiThreadArrayUtil.countSumInParallelStreams(array);
        System.out.println("Counting sum array[" + array.length + "] in parallel streams. \tTime = " + (System.currentTimeMillis() - startTime) + " ms. Sum =" + sum);
    }

    private static void performMultiThreadingCount(int[] array, int threadNumber) {
        long startTime = System.currentTimeMillis();
        long sum = MultiThreadArrayUtil.countSumWithSpecifiedNumberOfThreads(array, threadNumber);
        System.out.println("Counting sum array[" + array.length + "] with " + threadNumber + " thread. " +
                "\tTime = " + (System.currentTimeMillis() - startTime) + " ms. Sum =" + sum);
    }

    private static void performMultiThreadingCountToAtomic(int[] array, int threadNumber) {
        long startTime = System.currentTimeMillis();
        long sum = MultiThreadArrayUtil.countSumWithSpecifiedNumberOfThreadsToAtomicVar(array, threadNumber);
        System.out.println("Counting sum array[" + array.length + "] with " + threadNumber + " thread. to atomic " +
                "\tTime = " + (System.currentTimeMillis() - startTime) + " ms. Sum =" + sum);
    }

    private static void performMultiThreadingCountToAtomicCAS(int[] array, int threadNumber) {
        long startTime = System.currentTimeMillis();
        long sum = MultiThreadArrayUtil.countSumWithSpecifiedNumberOfThreadsToAtomicVarCAS(array, threadNumber);
        System.out.println("Counting sum array[" + array.length + "] with " + threadNumber + " thread. to atomic CAS " +
                "\tTime = " + (System.currentTimeMillis() - startTime) + " ms. Sum =" + sum);
    }




}
