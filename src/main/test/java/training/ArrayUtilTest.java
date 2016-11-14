package training;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * This class contains test of static method utils for arrays
 *
 * @author oleksij.onysymchuk@gmail.com
 * @version 1.0 13 NOV 2016
 */
public class ArrayUtilTest {
    int array1[] = {4, 4, 7, 9, 2, 4, 8, 1, 5, 2, 3};
    int array2[] = {4, 4, 7, 6, 9, 9, 1, 5, 1, 2, 6, 3, 12, 13};
    int array3[] = {9, 2, 7, 4, 8, 1, 5, 2, 3};
    int array4[] = {7, 6, 9, 9, 1, 5, 1, 2, 6, 4, 3, 12, 13};

    List<Integer> sameSorted = Arrays.asList(1, 2, 3, 4, 5, 7, 9);
    List<Integer> diffSorted = Arrays.asList(6, 8, 12, 13);

    @Test
    public void testFindSameValues() {
        List<Integer> result = ArrayUtil.findSameValues(array1, array2);
        Collections.sort(result);
        assertEquals(sameSorted.toString(), result.toString());
        result = ArrayUtil.findSameValues(array2, array1);
        Collections.sort(result);
        assertEquals(sameSorted.toString(), result.toString());
        result = ArrayUtil.findSameValues(array3, array4);
        Collections.sort(result);
        assertEquals(sameSorted.toString(), result.toString());
        result = ArrayUtil.findSameValues(array4, array3);
        Collections.sort(result);
        assertEquals(sameSorted.toString(), result.toString());
    }

    @Test
    public void testFindDifferentValues() {
        List<Integer> result = ArrayUtil.findDifferentValues(array1, array2);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
        result = ArrayUtil.findDifferentValues(array2, array1);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
        result = ArrayUtil.findDifferentValues(array3, array4);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
        result = ArrayUtil.findDifferentValues(array4, array3);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
    }

    @Test
    public void testFindDifferentValuesUsingHashSet() {
        List<Integer> result = ArrayUtil.findDifferentValuesUsingHashSet(array1, array2);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
        result = ArrayUtil.findDifferentValuesUsingHashSet(array2, array1);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
        result = ArrayUtil.findDifferentValuesUsingHashSet(array3, array4);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
        result = ArrayUtil.findDifferentValuesUsingHashSet(array4, array3);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
    }

    @Test
    public void testFindDifferentValuesUsingHash() {
        List<Integer> result = ArrayUtil.findDifferentValuesUsingHash(array1, array2);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
        result = ArrayUtil.findDifferentValuesUsingHash(array2, array1);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
        result = ArrayUtil.findDifferentValuesUsingHash(array3, array4);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
        result = ArrayUtil.findDifferentValuesUsingHash(array4, array3);
        Collections.sort(result);
        assertEquals(diffSorted.toString(), result.toString());
    }

    /**
     * Compare of execution time of
     * 1) sorting with iteration method
     * 2) using hash set and collections methods
     * 3) using hashMap of one array without sorting
     */
    @Ignore
    @Test
    public void testMethodsSpeed() {
        int array1Length = 1000000;
        int array2Length = array1Length;
        int array1[] = initArray(array1Length);
        int array2[] = initArray(array2Length);
        List<Integer> result = ArrayUtil.findSameValues(array3, array4);
        long start = System.currentTimeMillis();
        result = ArrayUtil.findDifferentValues(array1, array2);
        System.out.println("Time for merge find different method: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        result = ArrayUtil.findDifferentValuesUsingHashSet(array1, array2);
        System.out.println("Time for hashset find different method: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        result = ArrayUtil.findDifferentValuesUsingHash(array1, array2);
        System.out.println("Time for hash map find different method: " + (System.currentTimeMillis() - start));
        start = System.currentTimeMillis();
        ArrayUtil.sortArrayByNumberOfOccurrences(array1);
        System.out.println("Time for sorting array by element occurrence number: " + (System.currentTimeMillis() - start));


    }

    private int[] initArray(int length) {
        int array[] = new int[length];
        for (int i = 0; i < length; i++) {
            array[i] = (int) (Math.random() * length / 10);
        }
        return array;
    }

    @Test
    public void testSortArrayByNumberOfOccurrences() {
        int array[] = {1};
        assertArrayEquals(array, ArrayUtil.sortArrayByNumberOfOccurrences(array));
        array = new int[]{1, 1, 1, 1};
        assertArrayEquals(array, ArrayUtil.sortArrayByNumberOfOccurrences(array));
        array = new int[]{1, 1, 2, 2, 3, 3};
        assertArrayEquals(array, ArrayUtil.sortArrayByNumberOfOccurrences(array));
        array = new int[]{3, 3, 2, 3, 2, 1};
        int arraySorted[] = new int[]{1, 2, 2, 3, 3, 3};
        assertArrayEquals(arraySorted, ArrayUtil.sortArrayByNumberOfOccurrences(array));
        array = new int[]{1, 100, 2, 100, 1, 1000};
        arraySorted = new int[]{2, 1000, 1, 1, 100, 100};
        assertArrayEquals(arraySorted, ArrayUtil.sortArrayByNumberOfOccurrences(array));
    }

}
