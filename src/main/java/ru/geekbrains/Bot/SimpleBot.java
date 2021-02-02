package ru.geekbrains.Bot;

/**
 * Class SimpleBot
 *
 * @author Sergey Iryupin
 * @version 0.3.1 dated Apr 13, 2017
 */

import java.util.*;
import java.util.regex.*;

public class SimpleBot {
    final String[] COMMON_PHRASES = {
            "Нет ничего ценнее слов, сказанных к месту и ко времени.",
            "Порой молчание может сказать больше, нежели уйма слов.",
            "Перед тем как писать/говорить всегда лучше подумать.",
            "Вежливая и грамотная речь говорит о величии души.",
            "Приятно когда текст без орфографических ошибок.",
            "Многословие есть признак неупорядоченного ума.",
            "Слова могут ранить, но могут и исцелять.",
            "Записывая слова, мы удваиваем их силу.",
            "Кто ясно мыслит, тот ясно излагает.",
            "Боюсь Вы что-то не договариваете."};
    final String[] ELUSIVE_ANSWERS = {
            "Вопрос непростой, прошу тайм-аут на раздумья.",
            "Не уверен, что располагаю такой информацией.",
            "Может лучше поговорим о чём-то другом?",
            "Простите, но это очень личный вопрос.",
            "Не уверен, что Вам понравится ответ.",
            "Поверьте, я сам хотел бы это знать.",
            "Вы действительно хотите это знать?",
            "Уверен, Вы уже догадались сами.",
            "Зачем Вам такая информация?",
            "Давайте сохраним интригу?"};
    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
        // hello
        put("хай", "hello");
        put("привет", "hello");
        put("здорово", "hello");
        put("здравствуй", "hello");
        // who
        put("кто\\s.*ты", "who");
        put("ты\\s.*кто", "who");
        // name
        put("как\\s.*зовут", "name");
        put("как\\s.*имя", "name");
        put("есть\\s.*имя", "name");
        put("какое\\s.*имя", "name");
        // howareyou
        put("как\\s.*дела", "howareyou");
        put("как\\s.*жизнь", "howareyou");
        // whatdoyoudoing
        put("зачем\\s.*тут", "whatdoyoudoing");
        put("зачем\\s.*здесь", "whatdoyoudoing");
        put("что\\s.*делаешь", "whatdoyoudoing");
        put("чем\\s.*занимаешься", "whatdoyoudoing");
        // whatdoyoulike
        put("что\\s.*нравится", "whatdoyoulike");
        put("что\\s.*любишь", "whatdoyoulike");
        // iamfeelling
        put("кажется", "iamfeelling");
        put("чувствую", "iamfeelling");
        put("испытываю", "iamfeelling");
        // yes
        put("^да", "yes");
        put("согласен", "yes");
        // whattime
        put("который\\s.*час", "whattime");
        put("сколько\\s.*время", "whattime");
        // bye
        put("прощай", "bye");
        put("увидимся", "bye");
        put("до\\s.*свидания", "bye");
    }};
    final Map<String, String> ANSWERS_BY_PATTERNS = new HashMap<String, String>() {{
        put("hello", "Здравствуйте, рад Вас видеть.");
        put("who", "Я обычный чат-бот.");
        put("name", "Зовите меня Чаттер :)");
        put("howareyou", "Спасибо, что интересуетесь. У меня всё хорошо.");
        put("whatdoyoudoing", "Я пробую общаться с людьми.");
        put("whatdoyoulike", "Мне нравиться думать что я не просто программа.");
        put("iamfeelling", "Как давно это началось? Расскажите чуть подробнее.");
        put("yes", "Согласие есть продукт при полном непротивлении сторон.");
        put("bye", "До свидания. Надеюсь, ещё увидимся.");
    }};
    Pattern pattern; // for regexp
    Random random; // for random answers
    Date date; // for date and time

    public SimpleBot() {
        random = new Random();
        date = new Date();
    }

    public String sayInReturn(String msg, boolean ai) {
        String say = (msg.trim().endsWith("?"))?
                ELUSIVE_ANSWERS[random.nextInt(ELUSIVE_ANSWERS.length)]:
                COMMON_PHRASES[random.nextInt(COMMON_PHRASES.length)];
        if (ai) {
            String message =
                    String.join(" ", msg.toLowerCase().split("[ {,|.}?]+"));
            for (Map.Entry<String, String> o : PATTERNS_FOR_ANALYSIS.entrySet()) {
                pattern = Pattern.compile(o.getKey());
                if (pattern.matcher(message).find())
                    if (o.getValue().equals("whattime")) return date.toString();
                    else return ANSWERS_BY_PATTERNS.get(o.getValue());
            }
        }
        return say;
    }
}

/*
Common Phrases (общие фразы):

Нет ничего ценнее слов, сказанных к месту и ко времени.
Порой молчание может сказать больше, нежели уйма слов.
Перед тем как писать/говорить всегда лучше подумать.
Вежливая и грамотная речь говорит о величии души.
Приятно когда текст без орфографических ошибок.
Многословие есть признак неупорядоченного ума.
Слова могут ранить, но могут и исцелять.
Записывая слова, мы удваиваем их силу.
Кто ясно мыслит, тот ясно излагает.
Боюсь Вы что-то не договариваете.

Elusive Answers (уклончивые ответы):

Вопрос непростой, прошу тайм-аут на раздумья.
Не уверен, что располагаю этой информацией.
Может лучше поговорим о чём-то другом?
Простите, но это очень личный вопрос.
Не уверен, что Вам понравится ответ.
Поверьте, я сам хотел бы это знать.
Вы действительно хотите это знать?
Уверен, Вы уже догадались сами.
Зачем Вам такая информация?
Давайте сохраним интригу?

Patterns For Analysis (шаблоны для анализа):

хай -> hello
привет
здорово
здравствуй

ты\\s.*кто -> who
кто\\s.*ты

как\\s.*зовут -> name
как\\s.*имя
есть\\s.*имя
какое\\s.*имя

как\\s.*дела -> howareyou
как\\s.*жизнь

зачем\\s.*тут -> whatdoyoudoing
зачем\\s.*здесь
что\\s.*делаешь
чем\\s.*занимаешься

что\\s.*нравится -> whatdoyoulike
что\\s.*любишь

кажется -> iamfeelling
чувствую
испытываю

^да -> yes
согласен

который\\s.*час -> whattime
сколько\\s.*время

прощай -> bye
увидимся
до\\s.*свидания

Answers By Patterns (ответы по шаблонам):

hello -> Здравствуйте, рад Вас видеть.
who -> Я обычный чат-бот.
name -> Зовите меня Чаттер.
howareyou -> Спасибо, что интересуетесь. У меня всё хорошо.
whatdoyoudoing -> Я пробую общаться с людьми.
whatdoyoulike -> Мне нравиться думать что я не просто программа.
iamfeelling -> Как давно это началось? Расскажите чуть подробнее.
yes -> Согласие есть продукт при полном непротивлении сторон.
whattime -> show time
bye -> До свидания. Надеюсь, ещё увидимся.
 */