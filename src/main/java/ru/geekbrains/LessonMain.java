package ru.geekbrains;

import java.lang.reflect.Array;
import java.lang.runtime.ObjectMethods;
import java.text.DecimalFormat;
import java.util.*;

public class LessonMain {

    public static void main(String[] args) { // начало main
        System.out.println("1");
//        Задание 1
        String[] array = {"ache", "slow", "torn", "slum", "boom", "rival", "wrong",
                "cholera", "revenge", "arrogant", "papa", "book", "home", "ache", "cars",
                "jolly", "sugar", "friend", "book", "mother", "father", "bloomiest", "ache"};
        //уникальные и отсортированные элементы элементы
        Set<String> arrayUnique = new TreeSet<>(Arrays.asList(array));
        System.out.println(arrayUnique);
        //подсчитать сколько раз встречаются элементы
        Map<String, Integer> wordInArrayRepeat = new HashMap<>();
        for (String word : array) {
            if (wordInArrayRepeat.containsKey(word)) {
                wordInArrayRepeat.put(word, wordInArrayRepeat.get(word) + 1);
            } else {
                wordInArrayRepeat.put(word, 1);
            }
        }
        // сортировать по comparingByKey() or comparingByValue() и распечатать
        wordInArrayRepeat.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(System.out::println);

        System.out.println("2");
//        Задание 2
        PhoneDirectory PhoneDirectory = new PhoneDirectory();
        Random random = new Random();
        for (String txt : array) {
            String name = txt.substring(0, 1).toUpperCase() + txt.substring(1) + "tix";
            String randomNumber = "+780" + (100000000 + random.nextInt(899999999));

            PhoneDirectory.add(name, randomNumber);
        }

        PhoneDirectory.Print();

        try {
            System.out.println(PhoneDirectory.get("Lee"));
            System.out.println(PhoneDirectory.get("Booktix"));
            System.out.println(PhoneDirectory.get("Rivaltix"));
        } catch (Exception e) {
            System.out.println("Please move forward");
            System.exit(-1);
        }
    } //конец main
} //конец LessonMain

class PhoneDirectory {
    private final Map<String, ArrayList<String>> phoneBook = new TreeMap<>();

    public void add(String F, String phoneNumber) {
        ArrayList<String> NumberList = new ArrayList<>();
        if (phoneBook.containsKey(F)) {
            NumberList = phoneBook.get(F);
            NumberList.add(phoneNumber);
            System.out.println("Для " + F + " добавлен номер: " + phoneNumber);
        } else {
            NumberList.add(phoneNumber);
            phoneBook.put(F, NumberList);
            System.out.println("Добавлен " + F + ": " + phoneNumber);
        }
    }

    public String get(String F) {
        String value = String.valueOf(phoneBook.get(F));
        if ((phoneBook.containsKey(F)) && (value != null)) {
            return "Для " + F + " записаны номера: " + phoneBook.get(F);
        } else {
            return "Для " + F + " не записаны номера";
        }
    }

    public void Print() { //печать справочника
        phoneBook.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(System.out::println);
    }
}
