import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReversalProgram {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Enter numbers separated by spaces:");
        String[] input = scanner.nextLine().split("\\s+");
        int[] array = new int[input.length];

        for (int i = 0; i < input.length; i++) {
            array[i] = Integer.parseInt(input[i]);
        }

        while (!isSorted(array)) {
            System.out.println("Current array:");
            printArray(array);

            List<int[]> strips = findStrips(array);
            int[] stripToReverse = selectStripToReverse(strips, array);

            reverse(array, stripToReverse[0], stripToReverse[1]);
            System.out.printf("Reversed from index %d to %d\n", stripToReverse[0], stripToReverse[1]);

            printArray(array);
            printBreakpoints(array);
        }

        System.out.println("Final sorted array:");
        printArray(array);
    }

    private static boolean isSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) {
                return false;
            }
        }
        return true;
    }

    private static List<int[]> findStrips(int[] array) {
        List<int[]> strips = new ArrayList<>();
        int start = 0;
        boolean isIncreasing = array[1] > array[0];

        for (int i = 1; i < array.length; i++) {
            if ((isIncreasing && array[i] < array[i - 1]) || (!isIncreasing && array[i] > array[i - 1])) {
                if (i - start > 1) {
                    strips.add(new int[]{start, i - 1, isIncreasing ? 1 : 0});
                }
                start = i - 1;
                isIncreasing = array[i] > array[i - 1];
            }
        }

        if (start < array.length - 1) {
            strips.add(new int[]{start, array.length - 1, isIncreasing ? 1 : 0});
        }
        return strips;
    }

    private static int[] selectStripToReverse(List<int[]> strips, int[] array) {
        int[] selectedStrip = null;
        for (int[] strip : strips) {
            if (strip[2] == 0) { // decreasing strip
                if (selectedStrip == null || strip[1] - strip[0] < selectedStrip[1] - selectedStrip[0]) {
                    selectedStrip = strip;
                }
            }
        }

        if (selectedStrip == null) {
            for (int[] strip : strips) {
                if (strip[2] == 1) { // increasing strip
                    if (selectedStrip == null || strip[1] - strip[0] > selectedStrip[1] - selectedStrip[0]) {
                        selectedStrip = strip;
                    }
                }
            }
        }

        return selectedStrip;
    }

    private static void reverse(int[] array, int start, int end) {
        while (start < end) {
            int temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            start++;
            end--;
        }
    }

    private static void printArray(int[] array) {
        for (int value : array) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    private static void printBreakpoints(int[] array) {
        int breakpoints = 0;
        System.out.println("Breakpoints:");
        for (int i = 0; i < array.length - 1; i++) {
            if (Math.abs(array[i] - array[i + 1]) >= 2) {
                System.out.printf("Between index %d and %d\n", i, i + 1);
                breakpoints++;
            }
        }
        System.out.println("Total breakpoints: " + breakpoints);
    }
}
