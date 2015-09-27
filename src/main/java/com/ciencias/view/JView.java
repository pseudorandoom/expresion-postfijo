package com.ciencias.view;

import com.ciencias.core.Parser;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JView {
    private JFrame frame;
    private JPanel panel;
    private JTextField input;
    private JButton button;
    private JLabel warning;
    private JLabel answer;
    private JLabel posfix;

    public JView() {
        frame = new JFrame("Calculador de expresiones aritmeticas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LayoutManager layout = new FlowLayout(FlowLayout.CENTER, 10, 10);
        panel = new JPanel(layout);
        input = new JTextField(15);
        button = new JButton("Calcular");
        button.setEnabled(false);

        warning = new JLabel("Solo se aceptan +,-,*,/,^,(,), y numeros");
        warning.setForeground(Color.red);
        warning.setVisible(false);
        answer = new JLabel();
        answer.setForeground(Color.BLUE);
        posfix = new JLabel();
        posfix.setForeground(Color.BLUE);

        events();
        panel.add(new JLabel("Ingrese la expresion a evaluar"));
        panel.add(input);
        panel.add(button);
        panel.add(warning);
        panel.add(answer);
        panel.add(posfix);
        frame.setContentPane(panel);
        frame.setSize(300, 200);
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dimension.width / 2 - frame.getWidth() / 2, dimension.height / 2 - frame.getHeight() / 2);
        frame.setVisible(true);
    }

    private void events() {
        final Pattern pattern = Pattern.compile("^[\\^\\*/\\+()0-9\\-]+$");
        final Border initialBorder = input.getBorder();
        final Border successBorder = new LineBorder(Color.green, 1);
        final Border errorBorder = new LineBorder(Color.red, 1);
        input.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = input.getText().trim();
                Matcher matcher = pattern.matcher(text);
                if (text.isEmpty()) {
                    input.setBorder(initialBorder);
                    warning.setVisible(false);
                } else if (matcher.matches()) {
                    input.setBorder(successBorder);
                    warning.setVisible(false);
                    button.setEnabled(true);
                } else {
                    input.setBorder(errorBorder);
                    warning.setVisible(true);
                    button.setEnabled(false);
                }
            }
        });
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = input.getText().trim();
                String posfixStr = Parser.infixToPostfix(text);
                posfix.setText("Expresion posfix: " + Parser.numbersToLetter(posfixStr));
                Double respuesta = Parser.executePostfix(posfixStr);
                String answerStr = respuesta.toString();
                answer.setText("Resultado: " + answerStr);
                System.out.println(posfixStr);
            }
        });
    }
}
