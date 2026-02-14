import java.util.Scanner;
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

        // your code goes here:
        File f = new File(inputfile);

        try (Scanner input = new Scanner(f)) {

            try (FileWriter output = new FileWriter(outputfile, false)) {

                while (input.hasNext()) {
                    String nextLine = input.nextLine();
                    for (int i = 0; i < nextLine.length(); i++) {
                        char nextChar = nextLine.charAt(i);
                        String token = "";

                        // combines characters back into strings separated by spaces
                        while (!Character.isWhitespace(nextChar)) {
                            token += nextChar;
                            nextChar = nextLine.charAt(++i);
                        }

                        // if token is empty space, skip to next iteration
                        if(token == ""){
                            continue;
                        }

                        boolean isNumber = false;
                        try {
                            Float tokenFloat = Float.parseFloat(token);
                            isNumber = true;
                        } catch (NumberFormatException e) {
                            isNumber = false;
                        }

                        // if token is a number, add to postfix string
                        if (isNumber == true) {
                            PostFix += token;
                            PostFix += " ";
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
                                PostFix += PostFixToken;
                                PostFix += " ";
                                PostFixToken = outputStack.pop();
                            }
                        } else {
                            // if stack is empty, add token to stack
                            if (outputStack.empty()) {
                                outputStack.push(token);
                                continue;
                            }

                            // if stack is not empty and top stack is not "("
                            String topStack = outputStack.peek();
                            if (!topStack.equals("(")) {
                                // if topStack has higher or equal precedence over scanned token, pop the stack
                                // and add to the postfix string
                                // repeat process until topstack is "(" or has lower precedence
                                if (token.equals("+") || token.equals("-")) {
                                    while (!outputStack.empty()) {
                                        if (topStack.equals("(") ||
                                            topStack.equals("+") ||
                                            topStack.equals("-")) {

                                            PostFix += topStack;
                                            PostFix += " ";
                                            System.out.println(outputStack.empty());
                                            topStack = outputStack.pop();
                                        }
                                        break;
                                    }
                                }
                                if (token.equals("*") || token.equals("/")) {
                                    while (!outputStack.empty()) {
                                        if (topStack.equals("(") ||
                                                topStack.equals("*") ||
                                                topStack.equals("/")) {
                                            topStack = outputStack.pop();

                                            PostFix += topStack;
                                            PostFix += " ";

                                            topStack = outputStack.pop();
                                        }

                                    }
                                }
                                outputStack.push(token);
                            } else {
                                outputStack.push(token);
                            }
                        }

                    }

                    // clear stack at the end of the line
                    while (!outputStack.empty()) {
                        PostFix += outputStack.pop();
                        PostFix += " ";
                    }

                    output.write(PostFix);
                    PostFix = "";
                    nextLine = input.nextLine();
                    // read infix expression from inputfile
                    // convert it to to postix expression
                    // save the postfix expression to outputfile

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
        // your code goes here:
        // read postfix expression from inputfile
        // evalue the the postix expression
        // display the result on screen
    }
}
