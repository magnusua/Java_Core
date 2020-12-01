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

    public static void main(String[] args) { // начало main
        print("Введите число участников, или нажмите ввод для 5 участников, введите число для заполнения или 0 для выхода.");
        int input_answer = Math.abs(NumberUtils.toInt((scanner.nextLine()), 5));
        if (!(input_answer == 0)) {
            //генерируем участников
            ArrayList<Subject> allSubject = new ArrayList<Subject>();
            for (int i = 0; i < input_answer; i++) {
                int Type = random.nextInt(3);
                if (Type == 0) {
                    allSubject.add(new Cat(RandomName("toxy"), random.nextInt(500), random.nextInt(200)));
                }
                if (Type == 1) {
                    allSubject.add(new Human(RandomName("yan'kus'"), random.nextInt(500), random.nextInt(200)));
                }
                if (Type == 2) {
                    allSubject.add(new Robot(RandomName("ited"), random.nextInt(500), random.nextInt(800)));
                }
            }
            //генерируем препятствия
            print("Введите число препятствий, или нажмите ввод для 2 препятствий.");
            input_answer = Math.abs(NumberUtils.toInt((scanner.nextLine()), 2));
            ArrayList<Object> allObject = new ArrayList<Object>();
            for (int i = 0; i < input_answer; i++) {
                int Type = random.nextInt(2);
                if (Type == 0) {
                    allObject.add(new Distance(random.nextInt(500)));
                }
                if (Type == 1) {
                    allObject.add(new Wall(random.nextInt(200)));
                }
            }

            for (Subject subject : allSubject)
                for (Object object : allObject) {
                    object.pass(subject);
                    if (!subject.isAlive()) {
                        break;
                    }
                }
        }
        print("Прощай - хорошего дня!");
    } //конец main

    public static String RandomName(String SurnamePrefix) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        int NameLength = (2 + random.nextInt(4));
        StringBuilder sb = new StringBuilder(NameLength); //создаем строку
        for (int i = 0; i < NameLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c); //генерируем строку из случайных букв
        }
        return sb.substring(0, 1).toUpperCase() + sb.substring(1) + SurnamePrefix; //выводим строку с Заглавной 1 буквой
    }
}

