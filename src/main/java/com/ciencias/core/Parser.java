package com.ciencias.core;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private static final Map<Character, Integer> precedence;
    static{
        Map<Character, Integer> tempMap = new HashMap<Character, Integer>(5);
        tempMap.put('^', 3);
        tempMap.put('/', 2);
        tempMap.put('*', 2);
        tempMap.put('+', 1);
        tempMap.put('-', 1);
        precedence = Collections.unmodifiableMap(tempMap);
    }

    private static Pattern number = Pattern.compile("(\\d+)");

    public static boolean hasPrecedence(char a, char b){
        //System.out.println(a+","+b);
        int anum = precedence.get(a);
        int bnum = precedence.get(b);
        return anum-bnum > 0;
    }

    /*
    public static String execute(String argument){
        List<javax.script.ScriptEngineFactory> factories = new javax.script.ScriptEngineManager().getEngineFactories();
        try {
            javax.script.ScriptEngine engine = factories.get(0).getScriptEngine();
            Double respuesta = (Double) engine.eval(argument);
            //System.out.println("engine: " + engine.getFactory().getEngineName());
            System.out.println("respuesta: " + respuesta);
            return Double.toString(respuesta);
        } catch (javax.script.ScriptException e) {
            System.err.println("No se pudo evaluar la expresion.");
        }
        return null;
    }
    */

    public static Double executePostfix(String postfix){
        String[] tokens = postfix.split("\\s+");
        System.out.println("postfix: " + postfix);
        Stack<Double> stack = new Stack<Double>();
        for(String token: tokens){
                System.out.println("asd" + token + ".");
            Matcher matcher = number.matcher(token);
            if(matcher.matches()){
                stack.push(Double.parseDouble(token));
            } else if(!token.equals("")){
                Double result = stack.pop();
                switch (token.charAt(0)){
                    case '+':
                        result = stack.pop() + result; break;
                    case '*':
                        result = stack.pop() * result; break;
                    case '-':
                        result = stack.pop() - result; break;
                    case '/':
                        result = stack.pop() / result; break;
                    case '^':
                        result = Math.pow(stack.pop(), result); break;
                    default:
                        throw new IllegalArgumentException(token + " no es un operador matematico valido");
                }
                stack.push(result);
            }
        }
        return stack.pop();
    }

    public static String infixToPostfix(String infix){
        StringBuffer sb = new StringBuffer();
        Stack<Character> operators = new Stack<Character>();
        for(char c: infix.toCharArray()){
            if(Character.isDigit(c)){
                sb.append(c);
            } else if(!Character.isWhitespace(c)){
                sb.append(' ');
                if(operators.isEmpty()){
                    operators.push(c);
                }else{
                    if(c == ')'){
                        char i = operators.peek();
                        while (i != '(' && !operators.isEmpty()){
                            sb.append(i).append(' ');
                            operators.pop();
                            i = operators.peek();
                        }
                        operators.pop();
                    } else {
                        //char stackOperator = operators.peek();
                        while(!operators.isEmpty() && c != '(' && operators.peek() != '(' && !hasPrecedence(c, operators.peek())){
                            sb.append(operators.pop()).append(' ');
                            //operators.pop();
                            //stackOperator = operators.peek();
                        }
                        operators.push(c);
                    }
                }
            }
        }
        while(!operators.isEmpty()){
            sb.append(' ');
            sb.append(operators.pop());
        }
        return sb.toString();
    }

    public static String numbersToLetter(String postfix){
        CharacterIterator ci = new StringCharacterIterator("abcdefghijklmnopqrstuvwxyz");
        Matcher matcher = number.matcher(postfix);
        while(matcher.find()){
            String number = matcher.group();
            postfix = postfix.replaceFirst(number, Character.toString(ci.current()));
            ci.next();
        }
        return postfix;
    }
}