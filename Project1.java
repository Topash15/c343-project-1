//Name: Alexander Topash

import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Stack;

public class Project1 {
    public static void main(String[] args) {
        addSpaces("input.txt", "temp.txt");
        toPostFix("temp.txt", "output.txt");
        evaluateExp("output.txt");
    }

    public static void addSpaces(String inputfile, String outputfile) {
        File f = new File(inputfile);
        try (Scanner input = new Scanner(f)) {
            try (FileWriter output = new FileWriter(outputfile, false)) {
                while (input.hasNext()) {
                    String line = input.nextLine();
                    for (int i = 0; i < line.length(); i++) {
                        char c = line.charAt(i);
                        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')') {
                            output.write(" " + c + " ");
                        } else
                            output.write(c);
                    }
                    output.write("\n");
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void toPostFix(String inputfile, String outputfile) {

        Stack<String> outputStack = new Stack<String>();
        String PostFix = "";

        File f = new File(inputfile);

        try (Scanner input = new Scanner(f)) {

            try (BufferedWriter output = new BufferedWriter(new FileWriter(outputfile, false))) {

                while (input.hasNextLine()) {
                    String nextLine = input.nextLine();

                    for (int i = 0; i < nextLine.length(); i++) {
                        char nextChar = nextLine.charAt(i);
                        String token = "";

                        // combines characters back into strings separated by spaces
                        while (!Character.isWhitespace(nextChar) && i < nextLine.length() - 1) {
                            token += nextChar;
                            nextChar = nextLine.charAt(++i);
                        }

                        if (nextChar != ' ' && token.equals("")) {
                            token += nextChar;
                        }

                        // if token is empty space, skip to next iteration
                        if (token == "") {
                            continue;
                        }

                        // if token is a number, add to postfix string
                        boolean isNumber = false;
                        try {
                            Float tokenFloat = Float.parseFloat(token);
                            isNumber = true;
                        } catch (NumberFormatException e) {
                            isNumber = false;
                        }
                        if (isNumber == true) {
                            PostFix += token + " ";
                        }

                        // if token is "(", push to stack
                        else if (token.equals("(")) {
                            outputStack.push(token);
                        }

                        // if token is ")", pop stack and add to string until topstack is "(".
                        // pop "(" out of stack
                        else if (token.equals(")")) {
                            String PostFixToken = outputStack.pop();
                            while (!PostFixToken.equals("(")) {
                                PostFix += PostFixToken + " ";
                                PostFixToken = outputStack.pop();
                            }
                        }
                        // handles operators
                        else {
                            // if stack is empty, add token to stack
                            if (outputStack.empty()) {
                                outputStack.push(token);
                                continue;
                            }

                            // if stack is not empty and top stack is not "("
                            String topStack = outputStack.peek();
                            if (!topStack.equals("(")) {
                                // if topStack has higher or equal precedence over scanned token, 
                                // pop the stack and add to the postfix string
                                // repeat process until topstack is "(" or has lower precedence
                                if (token.equals("+") || token.equals("-")) {

                                    // everything has higher or equal precedence for + and - so empty stack
                                    while (!outputStack.empty()) {
                                        topStack = outputStack.peek();

                                        if (topStack.equals("(")) {
                                            break;
                                        }

                                        PostFix += outputStack.pop() + " ";
                                    }

                                } else if (token.equals("*") || token.equals("/")) {

                                    // * and / take precedence over + and -
                                    while (!outputStack.empty() &&
                                            (!topStack.equals("+") &&
                                             !topStack.equals("-"))) {
                                        topStack = outputStack.peek();

                                        if (topStack.equals("(")) {
                                            break;
                                        }

                                        PostFix += outputStack.pop() + " ";
                                    }
                                }
                            }
                            
                            // once operators within parenthesis are handled, push current operator to stack
                            outputStack.push(token);
                        }

                    }

                    // clear stack at the end of the line
                    while (!outputStack.empty()) {
                        String operator = outputStack.pop();

                        PostFix += operator + " ";
                    }

                    output.write(PostFix);
                    if (input.hasNextLine()) {
                        output.write("\n");
                    }
                    PostFix = "";
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        } catch (

        Exception e) {
            System.out.println(e);
        }

    }

    public static void evaluateExp(String inputfile) {

        File f = new File(inputfile);
        Stack<Float> evaluateStack = new Stack<Float>();

        try (Scanner input = new Scanner(f)) {

            while (input.hasNextLine()) {
                String nextLine = input.nextLine();

                for (int i = 0; i < nextLine.length(); i++) {
                    char nextChar = nextLine.charAt(i);
                    String token = "";

                    // combines characters back into strings separated by spaces
                    while (!Character.isWhitespace(nextChar) && i < nextLine.length() - 1) {
                        token += nextChar;
                        nextChar = nextLine.charAt(++i);
                    }

                    if (nextChar != ' ' && token.equals("")) {
                        token += nextChar;
                    }

                    // if token is empty space, skip to next iteration
                    if (token == "") {
                        continue;
                    }

                    boolean isNumber = false;
                    try {
                        Float tokenFloat = Float.parseFloat(token);
                        isNumber = true;
                    } catch (NumberFormatException e) {
                        isNumber = false;
                    }

                    // if number, push to stack
                    if (isNumber == true) {
                        evaluateStack.push(Float.parseFloat(token));
                    }
                    // if operator,
                    else {
                        // error if less than two numbers in stack
                        if (evaluateStack.size() < 2) {
                            throw new Exception("Not enough numbers in stack to apply operator.");
                        }
                        // else apply operator to first and second numbers in stack
                        float num1 = evaluateStack.pop();
                        float num2 = evaluateStack.pop();

                        switch (token) {
                            case "+":
                                evaluateStack.push(num2 + num1);
                                break;
                            case "-":
                                evaluateStack.push(num2 - num1);
                                break;
                            case "*":
                                evaluateStack.push(num2 * num1);
                                break;
                            case "/":
                                evaluateStack.push(num2 / num1);
                                break;
                        }
                    }
                }
                // if 0 or more than 1 numbers in stack, error
                if (evaluateStack.size() != 1) {
                    throw new Exception("Operator and Number mismatch. Calculation incomplete.");
                }
                // else result = stack.pop
                else {
                    System.out.println(evaluateStack.pop());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
