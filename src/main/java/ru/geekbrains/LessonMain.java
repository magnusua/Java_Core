package ru.geekbrains;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class LessonMain {

    public static void main(String[] args) { // начало main
        new Main();


    } //конец main
} //конец LessonMain

class Main extends JFrame { //класс

    private static final int Size = 600;


    private final JTextArea log;
  //  private final JPanel panelTop;
    private final JPanel panelBottom;
    private final JTextField textFieldMessage;
    private final JButton buttonSend;

    public Main() { //конструктор
        log = new JTextArea();
        panelBottom = new JPanel(new BorderLayout());
        textFieldMessage = new JTextField();
        buttonSend = new JButton("Send");

        //настройки окна
        setTitle("Чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(Size, Size);
        setLocationRelativeTo(null); //выравнивание по центру
        setResizable(false);

        JScrollPane scrollLog = new JScrollPane(log);

        buttonSend.addActionListener(e -> {
            if (!textFieldMessage.getText().equals("")) {
                log.append(textFieldMessage.getText() + "\n");
                textFieldMessage.setText("");
            }
        });

        textFieldMessage.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!textFieldMessage.getText().equals("") && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    log.append(textFieldMessage.getText() + "\n");
                    textFieldMessage.setText("");
                }
            }
        });

        log.setEditable(false);
        panelBottom.add(textFieldMessage, BorderLayout.CENTER);
        panelBottom.add(buttonSend, BorderLayout.EAST);
        add(scrollLog, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
        setVisible(true);
    }}
