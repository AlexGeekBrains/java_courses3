package lesson6;

public class ArraysForTests {

 /* 1. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
 Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
 идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку, иначе в методе
 необходимо выбросить RuntimeException.*/

    public int[] returnArrWithoutFour(int[] arr) {
        int count = 0;
        int[] arrayWithoutQuadruples;
        for (int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
            if (arr[i] == 4) {
                count = j;
                break;
            }
            if (i == 0) {
                throw new RuntimeException("Incoming array not contain four");
            }
        }
        if (count == 0) {
            arrayWithoutQuadruples = new int[count];
        } else {
            arrayWithoutQuadruples = new int[count];
            System.arraycopy(arr, arr.length - count, arrayWithoutQuadruples, 0, count);
        }
        return arrayWithoutQuadruples;
    }

    /* Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет хоть одной четверки или единицы,
     то метод вернет false; Если массив содержит что то кроме 1 и 4, то метод вернет false; Написать набор тестов для
      этого метода (по 3-4 варианта входных данных).*/

    public boolean checkOneAndFour(int[] arr) {
        int countOne = 0;
        int countFour = 0;
        for (int j : arr) {
            if (j != 1 && j != 4) {
                return false;
            }
            if (j == 1) {
                countOne++;
            }
            if (j == 4) {
                countFour++;
            }
        }
        return countOne != 0 && countFour != 0;
    }
}