package ru.gb;

import lesson6.ArraysForTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestMainApp {
    ArraysForTests arraysForTests;

    @BeforeEach
    public void init() {
        arraysForTests = new ArraysForTests();
    }

//Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
//Вх: [ 1 2 4 1 7 5 3 2 ] -> вых: [ 1 7 5 3 2 ].
//Вх: [ 1 2 4 ] -> вых: [ ].
//Вх: [ 1 2 5 6 ] -> вых: RuntimeException

    public static Stream<Arguments> valuesForTestArrWithoutFour() {
        List<Arguments> out = new ArrayList<>();
        out.add(Arguments.arguments(new int[]{2, 2, 3, 1, 7}, new int[]{4, 2, 2, 3, 1, 7}));
        out.add(Arguments.arguments(new int[]{1, 7}, new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}));
        out.add(Arguments.arguments(new int[]{1, 7, 5, 3, 2}, new int[]{1, 2, 4, 1, 7, 5, 3, 2}));
        out.add(Arguments.arguments(new int[]{}, new int[]{1, 2, 4}));
        return out.stream();
    }

    @ParameterizedTest
    @MethodSource("valuesForTestArrWithoutFour")
    public void testArrWithoutFour(int[] arr1, int[] arr2) {
        Assertions.assertArrayEquals(arr1, arraysForTests.returnArrWithoutFour(arr2));
    }

    @Test
    public void testArrWithoutFourException() {
        Assertions.assertThrows(RuntimeException.class, () -> arraysForTests.returnArrWithoutFour(new int[]{2, 3, 5, 6, 7, 8, 9, 10}));
    }


//[ 1 1 1 4 4 1 4 4 ] -> true
//[ 1 1 1 1 1 1 ] -> false
//[ 4 4 4 4 ] -> false
//[ 1 4 4 1 1 4 3 ] -> false
//[ 14 41 11 44 ] -> false

    public static List<int[]> valuesForTestCheckOneAndFourFail() {
        List<int[]> out = new ArrayList<>();
        out.add(new int[]{1, 1, 1, 1, 1, 1});
        out.add(new int[]{4, 4, 4,});
        out.add(new int[]{14, 41, 11, 44});
        return out;
    }

    @ParameterizedTest
    @MethodSource("valuesForTestCheckOneAndFourFail")
    public void testCheckOneAndFour(int[] arr) {
        Assertions.assertFalse(arraysForTests.checkOneAndFour(arr));
    }

    public static List<int[]> valuesForTestCheckOneAndFourOk() {
        List<int[]> out = new ArrayList<>();
        out.add(new int[]{1, 4, 1, 1, 1, 1});
        out.add(new int[]{4, 4, 4, 1});
        out.add(new int[]{1, 4, 1, 4});
        return out;
    }

    @ParameterizedTest
    @MethodSource("valuesForTestCheckOneAndFourOk")
    public void testCheckOneAndFourOk(int[] arr) {
        Assertions.assertTrue(arraysForTests.checkOneAndFour(arr));
    }
}
