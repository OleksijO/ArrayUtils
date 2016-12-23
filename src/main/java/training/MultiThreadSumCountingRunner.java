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
        for (int i = 1; i < 4; i++) {
            performMultiThreadingCountToAtomicCAS2(array, i);
        }
        for (int i = 4; i <= 16; i+=4) {
            performMultiThreadingCountToAtomicCAS2(array, i);
        }

        countSumInCurrentThread(array);
        countSumInParallelStreams(array);
    }


    // OUTPUT:
    /*
        Counting sum array[40000000] in current thread.     Time = 580 ms. Sum =799999980000000
        Counting sum array[40000000] in parallel streams.     Time = 162 ms. Sum =799999980000000
        Counting sum array[40000000] with 1 thread.     Time = 88 ms. Sum =799999980000000
        Counting sum array[40000000] with 2 thread.     Time = 54 ms. Sum =799999980000000
        Counting sum array[40000000] with 3 thread.     Time = 31 ms. Sum =799999980000000
        Counting sum array[40000000] with 4 thread.     Time = 36 ms. Sum =799999980000000
        Counting sum array[40000000] with 5 thread.     Time = 17 ms. Sum =799999980000000
        Counting sum array[40000000] with 6 thread.     Time = 32 ms. Sum =799999980000000
        Counting sum array[40000000] with 7 thread.     Time = 31 ms. Sum =799999980000000
        Counting sum array[40000000] with 8 thread.     Time = 22 ms. Sum =799999980000000
        Counting sum array[40000000] with 9 thread.     Time = 31 ms. Sum =799999980000000
        Counting sum array[40000000] with 10 thread.     Time = 32 ms. Sum =799999980000000
        Counting sum array[40000000] with 11 thread.     Time = 37 ms. Sum =799999980000000
        Counting sum array[40000000] with 12 thread.     Time = 32 ms. Sum =799999980000000
        Counting sum array[40000000] with 13 thread.     Time = 31 ms. Sum =799999980000000
        Counting sum array[40000000] with 14 thread.     Time = 53 ms. Sum =799999980000000
        Counting sum array[40000000] with 15 thread.     Time = 84 ms. Sum =799999980000000
        Counting sum array[40000000] with 1 thread. to atomic     Time = 533 ms. Sum =799999980000000
        Counting sum array[40000000] with 2 thread. to atomic     Time = 1809 ms. Sum =799999980000000
        Counting sum array[40000000] with 3 thread. to atomic     Time = 2526 ms. Sum =799999980000000
        Counting sum array[40000000] with 4 thread. to atomic     Time = 3086 ms. Sum =799999980000000
        Counting sum array[40000000] with 8 thread. to atomic     Time = 4728 ms. Sum =799999980000000
        Counting sum array[40000000] with 12 thread. to atomic     Time = 4960 ms. Sum =799999980000000
        Counting sum array[40000000] with 16 thread. to atomic     Time = 5053 ms. Sum =799999980000000
        Counting sum array[40000000] with 1 thread. to atomic CAS     Time = 506 ms. Sum =799999980000000
        Counting sum array[40000000] with 2 thread. to atomic CAS     Time = 1439 ms. Sum =799999980000000
        Counting sum array[40000000] with 3 thread. to atomic CAS     Time = 2468 ms. Sum =799999980000000
        Counting sum array[40000000] with 4 thread. to atomic CAS     Time = 3509 ms. Sum =799999980000000
        Counting sum array[40000000] with 8 thread. to atomic CAS     Time = 5076 ms. Sum =799999980000000
        Counting sum array[40000000] with 12 thread. to atomic CAS     Time = 4940 ms. Sum =799999980000000
        Counting sum array[40000000] with 16 thread. to atomic CAS     Time = 4779 ms. Sum =799999980000000
        Counting sum array[40000000] with 1 thread. to atomic CAS2 with local nonadded sum     Time = 586 ms. Sum =799999980000000
        Counting sum array[40000000] with 2 thread. to atomic CAS2 with local nonadded sum     Time = 1066 ms. Sum =799999980000000
        Counting sum array[40000000] with 3 thread. to atomic CAS2 with local nonadded sum     Time = 1047 ms. Sum =799999980000000
        Counting sum array[40000000] with 4 thread. to atomic CAS2 with local nonadded sum     Time = 1162 ms. Sum =799999980000000
        Counting sum array[40000000] with 8 thread. to atomic CAS2 with local nonadded sum     Time = 995 ms. Sum =799999980000000
        Counting sum array[40000000] with 12 thread. to atomic CAS2 with local nonadded sum     Time = 999 ms. Sum =799999980000000
        Counting sum array[40000000] with 16 thread. to atomic CAS2 with local nonadded sum     Time = 991 ms. Sum =799999980000000
        Counting sum array[40000000] in current thread.     Time = 486 ms. Sum =799999980000000
        Counting sum array[40000000] in parallel streams.     Time = 132 ms. Sum =799999980000000
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

    private static void performMultiThreadingCountToAtomicCAS2(int[] array, int threadNumber) {
        long startTime = System.currentTimeMillis();
        long sum = MultiThreadArrayUtil.countSumWithSpecifiedNumberOfThreadsToAtomicVarCAS2(array, threadNumber);
        System.out.println("Counting sum array[" + array.length + "] with " + threadNumber + " thread. to atomic CAS2 with local nonadded sum " +
                "\tTime = " + (System.currentTimeMillis() - startTime) + " ms. Sum =" + sum);
    }





}
