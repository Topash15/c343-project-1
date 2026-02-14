import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.util.Stack;

public class Project1
{
    public static void main(String[] args)
    {
        addSpaces("input.txt", "temp.txt");
        toPostFix("temp.txt", "output.txt");
        evaluateExp("output.txt");
    }
    
    public static void addSpaces(String inputfile, String outputfile)
    {
        File f = new File(inputfile);
        try(Scanner input = new Scanner(f)) 
        {  
            try(FileWriter output = new FileWriter(outputfile, false)) 
            {
                while(input.hasNext()) 
                {       
                    String line = input.nextLine();
                    for(int i=0; i<line.length(); i++)
                    {
                        char c = line.charAt(i);
                        if(c=='+' || c=='-' || c=='*' || c=='/' || c=='(' || c==')')
                        {
                            output.write(" " + c + " ");  
                        }
                        else output.write(c);
                    }
                    output.write("\n");
                }
            }      
            catch(Exception e)    
            {         
                System.out.println(e); 
            }
                
        }     
        catch(Exception e)    
        {       
            System.out.println(e);  
        }
    }
    
    public static void toPostFix(String inputfile, String outputfile)
    {
        // your code goes here:
        // read infix expression from inputfile
        // convert it to to postix expression
        // save the postfix expression to outputfile
    }
    
    public static void evaluateExp(String inputfile)
    {
        // your code goes here:
        // read postfix expression from inputfile
        // evalue the the postix expression
        // display the result on screen
    }
}
