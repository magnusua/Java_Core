package ru.geekbrains;

import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;

public class LessonMain {
    public static final Scanner scanner = new Scanner(System.in);
    public static Random random = new Random();

    public static void print(String text) {
        System.out.println(text);
        System.out.print(System.lineSeparator());
    }

    public static void main(String[] args) throws MyArraySizeException, MyArrayDataException { // начало main
        print("Help, it alive! Press Enter for start default." + "\r\n" + "Введите размер массива отличный от 4 или 0 для выхода");
        int input_answer = Math.abs(NumberUtils.toInt((scanner.nextLine()), 4));

        if (!(input_answer == 0)) {
            String[][] array = new String[input_answer][input_answer];
            if (input_answer == 4) {
                for (int i = 0; i < array.length; i++) {
                    for (int j = 0; j < array[i].length; j++) {
                        array[i][j] = String.valueOf(i + j);
                        System.out.print(array[i][j] + " ");
                        // Вывод значений массива в одну строку с пробелом
                    }
                    System.out.print("\r\n"); // Переход на новую строку для мультиплатформенных приложений WIn/bsd/HTML
                }
                System.out.print("\r\n");

                System.out.println("Правильный массив");
                changeToIntAndSum(array);

                System.out.println("\n Не правильный массив (not a numbers)");
                array[random.nextInt(input_answer)][random.nextInt(input_answer)] = "Seven";
                try {
                    changeToIntAndSum(array);
                } catch (MyArrayDataException e) {
                   // e.printStackTrace();
                    print("please move forward");
                    System.exit(-1);
                }
            } else
                System.out.println("Не правильный массив (error of correct size)");
            try {
                changeToIntAndSum(array);
            } catch (MyArraySizeException e) {
                //e.printStackTrace();
                print("please move forward");
            }
        }
        print("Прощай - хорошего дня!");
    } //конец main

    private static void changeToIntAndSum(String[][] array) throws MyArraySizeException, MyArrayDataException {
        int Sum = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array.length != 4 && array[i].length != 4) {
                    print(("Размер массива != [4][4]. Выбранный массив: [" + array.length + "][" + array[i].length + "]."));
                    //System.exit(4);
                    throw new MyArraySizeException("Размер массива != [4][4]. Выбранный массив: [" + array.length + "][" + array[i].length + "].");
                }
                try {
                    Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    print("На вертикале " + i + ", и горизонтали " + j + " не число, а - " + array[i][j] + "\n");
                   // System.exit(5);
                    throw new MyArrayDataException("На вертикале " + i + ", и горизонтали " + j + " не число " + array[i][j] + "\n");
                }
                Sum += Integer.parseInt(array[j][j]);
            }
        }
        System.out.println("Сумма = " + Sum);
    }
} //конец LessonMain

class MyArraySizeException extends Exception {
    public MyArraySizeException(String message) {
        super(message);
    }
}

class MyArrayDataException extends NumberFormatException {
    public MyArrayDataException(String s) {
        super(s);
    }
}