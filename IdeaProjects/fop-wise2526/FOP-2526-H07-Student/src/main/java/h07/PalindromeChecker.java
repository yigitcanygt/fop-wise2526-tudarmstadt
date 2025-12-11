package h07;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

public class PalindromeChecker {

    /**
     * Recursively checks if an array is a palindrome by calling a helper function.
     * @param arr the array to check
     * @return true if the array is a palindrome, false otherwise
     */
    @StudentImplementationRequired("H7.2.1")
    public static boolean isPalindromeRecursive(int[] arr) {
        return check(arr, 0, arr.length - 1);
    }
    private static boolean check(int[] row, int left, int right) {
        if (left > right) {
            return true;
        }
        if (row[left] == row[right]) {
            return check(row, left + 1, right - 1);
        } else {
            return false;
        }
    }

    /**
     * Iteratively checks if an array is a palindrome.
     * @param arr the array to check
     * @return true if the array is a palindrome, false otherwise
     */
    @StudentImplementationRequired("H7.2.2")
    public static boolean isPalindromeIterative(int[] arr) {
        int leftIndex = 0;
        int rightIndex = arr.length - 1;
        while (leftIndex < rightIndex) {
            if (arr[leftIndex] != arr[rightIndex]) {
                return false;
            }
            leftIndex = leftIndex + 1;
            rightIndex = rightIndex - 1;
        }
        return true;
    }

    @DoNotTouch
    public static int[] toDigits(int number) {
        int[] digits = new int[(int) Math.log10(number) + 1];
        int index = digits.length - 1;
        while (index >= 0) {
            digits[index] = Math.floorMod(number, 10);
            number /= 10;
            index -= 1;
        }
        return digits;
    }
}
