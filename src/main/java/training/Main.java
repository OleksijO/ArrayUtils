package training;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        int array1[] = {4, 7, 9, 2, 4, 8, 1, 5, 2, 3};
        int array2[] = {7, 6, 9, 9, 1, 5, 1, 2, 6, 3,12,13};
        List<Integer> differentValues = ArrayUtil.findDifferentValues(array1, array2);
        System.out.println(Arrays.toString(array1));
        System.out.println(Arrays.toString(array2));
        System.out.println("Different values: ");
        System.out.println(differentValues.toString());
    }

}
