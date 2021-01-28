package ru.geekbrains.Lesson6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

class Lesson6MainTest {
    private Lesson6Main main;

    @BeforeEach
    public void init() {
        main = new Lesson6Main();
    }

    @ParameterizedTest
    @MethodSource("dataForOperation1")
    void getValuesAfterLastFour(int[] array, int[] expected) {
        Assertions.assertArrayEquals(expected, main.getValuesAfterLastFour(array));
    }

    public static Stream<Arguments> dataForOperation1() {
        List<Arguments> arguments = new ArrayList<>() {
            {
                add(Arguments.arguments(new int[]{1, 2, 3, 4, 5}, new int[]{5}));
                add(Arguments.arguments(new int[]{1, 2, 3, 4}, new int[]{}));
                add(Arguments.arguments(new int[]{1, 2, 3, 4, 1, 7}, new int[]{1, 7}));
                add(Arguments.arguments(new int[]{1, 4, 3, 4, 8, 6, 1, 7}, new int[]{8, 6, 1, 7}));
            }
        };
        return arguments.stream();
    }

    @Test
    void getValuesAfterLastFourWaitException() {
        int[] original = {1, 2, 3, 6, 8};
        Assertions.assertThrows(RuntimeException.class, () -> main.getValuesAfterLastFour(original));
        int[] empty = {};
        Assertions.assertThrows(RuntimeException.class, () -> main.getValuesAfterLastFour(empty));
    }


    @ParameterizedTest
    @MethodSource("dataForOperation2")
    void checkOneOrFourInArray(int[] array, boolean expected) {
        Assertions.assertEquals(expected, main.checkOneOrFourInArray(array));
    }

    public static Stream<Arguments> dataForOperation2() {
        List<Arguments> arguments = new ArrayList<>() {
            {
                add(Arguments.arguments(new int[]{9, 2, 3, 0, 5}, false));
                add(Arguments.arguments(new int[]{1, 2, 3, 4}, true));
                add(Arguments.arguments(new int[]{}, false));
                add(Arguments.arguments(new int[]{11, 49, 3, 64, 8, 6, 31, 7}, false));
            }
        };
        return arguments.stream();
    }
}