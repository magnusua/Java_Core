package ru.geekbrains.Lesson4;

public class Lesson4Main {
    private static final Object key = new Object();
    private static final int COUNT_TIMESTAMP = 5;
    private static volatile char currentLetter = 'A';

    public static void main(String[] args) {

        new Thread(() -> {
            try {
                xyPrint('C', 'A');
            } catch (InterruptedException ignored) {
                System.exit(-1);
            }
        }).start();

        new Thread(() -> {
            try {
                xyPrint('A', 'B');
            } catch (InterruptedException ignored) {
                System.exit(-2);
            }
        }).start();

        new Thread(() -> {
            try {
                xyPrint('B', 'C');
            } catch (InterruptedException ignored) {
                System.exit(-3);
            }
        }).start();

    }

    public static void xyPrint(char x, char y) throws InterruptedException {
        int i = 0;
        while (i < COUNT_TIMESTAMP) {
            synchronized (key) {
                while (x != currentLetter) {
                    key.wait();
                }
                System.out.print(currentLetter);
                currentLetter = y;
                key.notifyAll();
            }
            i++;
        }
    }
}