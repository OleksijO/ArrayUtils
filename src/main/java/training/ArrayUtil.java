package training;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class contains  static method utils for arrays
 *
 * @author oleksij.onysymchuk@gmail.com
 * @version 1.0 13 NOV 2016
 */
public class ArrayUtil {

    /**
     * Searches same values in two different arrays.
     *
     * @param array1 the first array
     * @param array2 the second array
     * @return List of identical values in two arrays.
     */
    public static List<Integer> findSameValues(int[] array1, int[] array2) {
        Arrays.sort(array1);
        Arrays.sort(array2);
        int index1 = 0;
        int index2 = 0;
        List<Integer> sameValues = new ArrayList<>();
        while ((index1 < array1.length) && (index2 < array2.length)) {
            if (array1[index1] == array2[index2]) {
                sameValues.add(array1[index1]);
                int tmp = array1[index1];
                while ((index1 < array1.length) && (tmp == array1[index1])) {
                    index1++;
                }
            } else if (array1[index1] < array2[index2]) {
                index1++;
            } else {
                index2++;
            }
        }
        return sameValues;
    }

    /**
     * Searches throughout array for values, which are non repeatable in other array.
     *
     * @param array1 the first array
     * @param array2 the second array
     * @return List of identical values in two arrays.
     */
    public static List<Integer> findDifferentValues(int[] array1, int[] array2) {
        Arrays.sort(array1);
        Arrays.sort(array2);
        List<Integer> result = new ArrayList<>();
        int index1 = 0;
        int index2 = 0;
        while (!(index1 >= array1.length && index2 >= array2.length)) {
            while (index1 < array1.length - 1 && array1[index1] == array1[index1 + 1]) {
                index1++;
            }
            while (index2 < array2.length - 1 && array2[index2] == array2[index2 + 1]) {
                index2++;
            }
            if (index1 < array1.length && index2 < array2.length) {
                if (array1[index1] < array2[index2]) {
                    result.add(array1[index1]);
                    index1++;
                } else if (array1[index1] > array2[index2]) {
                    result.add(array2[index2]);
                    index2++;
                } else {
                    index1++;
                    index2++;
                }
            } else {
                if (index1 < array1.length) {
                    result.add(array1[index1]);
                    index1++;
                } else {
                    result.add(array2[index2]);
                    index2++;
                }
            }
        }
        return result;
    }

    /**
     * Searches throughout array for values, which are non repeatable in other array.
     *
     * @param array1 the first array
     * @param array2 the second array
     * @return List of identical values in two arrays.
     */
    public static List<Integer> findDifferentValuesUsingHashSet(int[] array1, int[] array2) {
        Set<Integer> set1 = new HashSet<Integer>(Arrays.stream(array1).boxed().collect(Collectors.toList()));
        Set<Integer> set2 = new HashSet<Integer>(Arrays.stream(array2).boxed().collect(Collectors.toList()));
        List<Integer> result = new ArrayList<>(set1);
        result.addAll(set2);
        set1.retainAll(set2);
        result.removeAll(set1);
        return result;
    }

}
