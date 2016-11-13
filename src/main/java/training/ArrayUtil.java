package training;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
        List<Integer> sameValues = findSameValues(array1, array2);
        List<Integer> differentValues = addArrayElementsNotPresentInLists(array1, sameValues, new ArrayList<>());
        differentValues = addArrayElementsNotPresentInLists(array2, sameValues, differentValues);
        Collections.sort(differentValues);
        return differentValues;
    }

    /**
     * Adds elements of array to the result list if they are not present in both lists - excludedValues and
     * resultList.
     *
     * @param array          An array from which elements should be added to result list
     * @param excludedValues A list of values that should not be in result list
     * @param resultList     A result list of elements, that not present in this and excludedValues lists.
     * @return
     */
    private static List<Integer> addArrayElementsNotPresentInLists(
            int[] array, List<Integer> excludedValues, List<Integer> resultList) {
        for (int element : array) {
            if ((!excludedValues.contains(element)) && (!resultList.contains(element))) {
                resultList.add(element);
            }
        }
        return resultList;
    }

}
