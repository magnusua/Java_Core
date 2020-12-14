package ru.geekbrains;

import java.util.Arrays;
import java.util.Random;

import static java.lang.System.*;

public class LessonMain {

    public static void main(String[] args) { // начало main
        Random random = new Random();
        int size = 100000000 + random.nextInt(10000000);
        System.out.println("Размер массива = "+ size);
        Thread firstThread = new Thread(() -> {
            out.println("Проход по массиву и вычисление новых значений: "+Method1(size));
        });
        firstThread.start();

        Thread secondThread = new Thread(() -> {
            out.println("Разбивка массива на два, в двух потоках высчитать новые значения и потом склеить обратно в массив: "+Method2(size));
        });
        secondThread.start();




    } //конец main

    private static long Method1(int size) {
        float[] array = new float[size];
        Arrays.fill(array, 1);
        long a = currentTimeMillis();
        fil(array);
        return currentTimeMillis() - a;
    }

    private static long Method2(int size) {
        float[] array = new float[size];
        Arrays.fill(array, 1);
        long a = currentTimeMillis();
        float[] leftArray, rightArray;
        if (array.length % 2 == 0) {
            leftArray = Arrays.copyOfRange(array, 0, array.length / 2);
            rightArray = Arrays.copyOfRange(array, array.length / 2, array.length);
        } else {
            leftArray = Arrays.copyOfRange(array, 0, (array.length + 1) / 2);
            rightArray = Arrays.copyOfRange(array, (array.length + 1) / 2, array.length);
        }
        Thread firstThread = new Thread(() -> fil(leftArray));
        firstThread.start();
        Thread secondThread = new Thread(() -> fil(rightArray));
        secondThread.start();

        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        arraycopy(leftArray, 0, array, 0, leftArray.length);
        arraycopy(rightArray, 0, array, leftArray.length, rightArray.length);
        return currentTimeMillis() - a;
    }

    private static void fil(float[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float) i / 5) * Math.cos(0.4f + (float) i / 2));
        }
    }
} //конец LessonMain