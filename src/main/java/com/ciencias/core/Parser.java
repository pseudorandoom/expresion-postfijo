package com.ciencias.core;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to parse and execute mathematical expressions.
 */
public final class Parser {
    private static final Map<Character, Integer> PRECEDENCE;
    private static final Pattern NUMBER_PATTERN = Pattern.compile("(\\d+)");

    static {
        Map<Character, Integer> tempMap = new HashMap<Character, Integer>(5);
        tempMap.put('^', 3);
        tempMap.put('/', 2);
        tempMap.put('*', 2);
        tempMap.put('+', 1);
        tempMap.put('-', 1);
        PRECEDENCE = Collections.unmodifiableMap(tempMap);
    }

    private Parser(){}

    /**
     * Given two mathematical math operands determines if the first one has PRECEDENCE over the second one.
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean hasPrecedence(char a, char b) {
        int anum = PRECEDENCE.get(a);
        int bnum = PRECEDENCE.get(b);
        return anum - bnum > 0;
    }

    /**
     * Given a postfix mathematical expression evaluates to a double or throws IllegalArgumentException if any
     * character is not a valid math operand.
     *
     * @param postfix
     * @return
     */
    public static Double executePostfix(String postfix) {
        String[] tokens = postfix.split("\\s+");
        Deque<Double> stack = new ArrayDeque<Double>();
        for (String token : tokens) {
            Matcher matcher = NUMBER_PATTERN.matcher(token);
            if (matcher.matches()) {
                stack.push(Double.parseDouble(token));
            } else if (!token.equals("")) {
                Double result = stack.pop();
                switch (token.charAt(0)) {
                    case '+':
                        result = stack.pop() + result;
                        break;
                    case '*':
                        result = stack.pop() * result;
                        break;
                    case '-':
                        result = stack.pop() - result;
                        break;
                    case '/':
                        result = stack.pop() / result;
                        break;
                    case '^':
                        result = Math.pow(stack.pop(), result);
                        break;
                    default:
                        throw new IllegalArgumentException(token + " not a valid math operand.");
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }

    /**
     * Converts an infix mathematical expression to postfix format.
     *
     * @param infix
     * @return
     */
    public static String infixToPostfix(String infix) {
        StringBuilder sb = new StringBuilder();
        Deque<Character> operators = new ArrayDeque<Character>();
        for (char c : infix.toCharArray()) {
            if (Character.isDigit(c)) {
                sb.append(c);
            } else if (!Character.isWhitespace(c)) {
                sb.append(' ');
                if (operators.isEmpty()) {
                    operators.push(c);
                } else {
                    if (c == ')') {
                        char i = operators.peek();
                        while (i != '(' && !operators.isEmpty()) {
                            sb.append(i).append(' ');
                            operators.pop();
                            i = operators.peek();
                        }
                        operators.pop();
                    } else {
                        while (!operators.isEmpty() && c != '(' && operators.peek() != '(' && !hasPrecedence(c, operators.peek())) {
                            sb.append(operators.pop()).append(' ');
                        }
                        operators.push(c);
                    }
                }
            }
        }
        while (!operators.isEmpty()) {
            sb.append(' ');
            sb.append(operators.pop());
        }
        return sb.toString();
    }

    /**
     * Replaces the numbers in the postfix expression with letters to be shown to the user.
     *
     * @param postfix
     * @return
     */
    public static String numbersToLetter(String postfix) {
        CharacterIterator ci = new StringCharacterIterator("abcdefghijklmnopqrstuvwxyz");
        Matcher matcher = NUMBER_PATTERN.matcher(postfix);
        while (matcher.find()) {
            String number = matcher.group();
            postfix = postfix.replaceFirst(number, Character.toString(ci.current()));
            ci.next();
        }
        return postfix;
    }
}