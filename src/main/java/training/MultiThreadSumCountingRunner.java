package training;

public class MultiThreadSumCountingRunner {

    public static void main(String[] args) {
        int[] array = createIntFilledArray(40000000);
        countSumInCurrentThread(array);
        countSumInParallelStreams(array);
        for (int i = 1; i < 16; i++) {
            performMultiThreadingCount(array, i);
        }
        for (int i = 16 ; i <= 128; i+=16) {
            performMultiThreadingCount(array, i);
        }
        countSumInCurrentThread(array);
        countSumInParallelStreams(array);
    }
    // OUTPUT:
    /*
        Counting sum array[40000000] in current thread. 	Time = 417 ms. Sum =799999980000000
        Counting sum array[40000000] in parallel streams. 	Time = 167 ms. Sum =799999980000000
        Counting sum array[40000000] with 1 thread. 	Time = 103 ms. Sum =799999980000000
        Counting sum array[40000000] with 2 thread. 	Time = 55 ms. Sum =799999980000000
        Counting sum array[40000000] with 3 thread. 	Time = 37 ms. Sum =799999980000000
        Counting sum array[40000000] with 4 thread. 	Time = 40 ms. Sum =799999980000000
        Counting sum array[40000000] with 5 thread. 	Time = 35 ms. Sum =799999980000000
        Counting sum array[40000000] with 6 thread. 	Time = 36 ms. Sum =799999980000000
        Counting sum array[40000000] with 7 thread. 	Time = 37 ms. Sum =799999980000000
        Counting sum array[40000000] with 8 thread. 	Time = 37 ms. Sum =799999980000000
        Counting sum array[40000000] with 9 thread. 	Time = 58 ms. Sum =799999980000000
        Counting sum array[40000000] with 10 thread. 	Time = 58 ms. Sum =799999980000000
        Counting sum array[40000000] with 11 thread. 	Time = 57 ms. Sum =799999980000000
        Counting sum array[40000000] with 12 thread. 	Time = 69 ms. Sum =799999980000000
        Counting sum array[40000000] with 13 thread. 	Time = 51 ms. Sum =799999980000000
        Counting sum array[40000000] with 14 thread. 	Time = 86 ms. Sum =799999980000000
        Counting sum array[40000000] with 15 thread. 	Time = 105 ms. Sum =799999980000000
        Counting sum array[40000000] with 16 thread. 	Time = 74 ms. Sum =799999980000000
        Counting sum array[40000000] with 32 thread. 	Time = 201 ms. Sum =799999980000000
        Counting sum array[40000000] with 48 thread. 	Time = 308 ms. Sum =799999980000000
        Counting sum array[40000000] with 64 thread. 	Time = 372 ms. Sum =799999980000000
        Counting sum array[40000000] with 80 thread. 	Time = 282 ms. Sum =799999980000000
        Counting sum array[40000000] with 96 thread. 	Time = 154 ms. Sum =799999980000000
        Counting sum array[40000000] with 112 thread. 	Time = 114 ms. Sum =799999980000000
        Counting sum array[40000000] with 128 thread. 	Time = 166 ms. Sum =799999980000000
        Counting sum array[40000000] in current thread. 	Time = 548 ms. Sum =799999980000000
        Counting sum array[40000000] in parallel streams. 	Time = 131 ms. Sum =799999980000000
    */

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

    private static int[] createIntFilledArray(int totalNumber) {
        int[] arr = new int[totalNumber];
        for (int i = 0; i < totalNumber; i++) {
            arr[i] = i;
        }
        return arr;
    }


}
