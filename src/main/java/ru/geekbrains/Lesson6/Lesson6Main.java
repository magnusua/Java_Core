package ru.geekbrains.Lesson6;

import java.util.Arrays;

public class Lesson6Main {
    public static void main(String[] args) {


    }

    //1
    public int[] getValuesAfterLastFour(int[] array) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] == 4) {
                int[] slice = new int[array.length - (i + 1)];
                System.arraycopy(array, i + 1, slice, 0, slice.length);
                return slice;
            }
        }
        throw new RuntimeException();
    }


    //2
    public boolean checkOneOrFourInArray(int[] array) {
        return Arrays.stream(array).anyMatch(num -> num == 1 || 4 == num);
    }
}
