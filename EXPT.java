//10. Implementation of Intermediate Code Generation.
import java.util.*;
class Instruction
{
    String op;    // operation
    String arg1;  // first argument
    String arg2;  // second argument
    String result;// result
    Instruction(String op, String arg1, String arg2, String result) 
    {
        this.op = op;
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.result = result;
    }
    @Override
    public String toString() 
    {
        return result + " = " + arg1 + " " + op + " " + arg2;
    }
}
class IntermediateCodeGenerator 
{
    private List<Instruction> instructions;
    private Stack<String> operands;
    private int tempCount;
    IntermediateCodeGenerator()
    {
        instructions = new ArrayList<>();
        operands = new Stack<>();
        tempCount = 0;
    }
    public List<Instruction> generate(String expression) 
    {
        Stack<Character> operators = new Stack<>();
        StringBuilder operand = new StringBuilder();

        for (int i = 0; i < expression.length(); i++)
        {
            char token = expression.charAt(i);
            if (Character.isWhitespace(token))
            {
                continue;
            }
            if (Character.isLetterOrDigit(token)) 
            {
                operand.append(token);
                if (i == expression.length() - 1 || !Character.isLetterOrDigit(expression.charAt(i + 1))) 
                {
                    operands.push(operand.toString());
                    operand.setLength(0);
                }
            } 
            else if (token == '(') 
            {
                operators.push(token);
            } 
            else if (token == ')') 
            {
                while (!operators.isEmpty() && operators.peek() != '(')
                {
                    processOperator(operators.pop());
                }
                operators.pop();
            }
            else if (isOperator(token)) 
            {
                while (!operators.isEmpty() && precedence(token) <= precedence(operators.peek()))
                {
                    processOperator(operators.pop());
                }
                operators.push(token);
            }
        }
        while (!operators.isEmpty())
        {
            processOperator(operators.pop());
        }

        return instructions;
    }
    private void processOperator(char operator)
    {
        String operand2 = operands.pop();
        String operand1 = operands.pop();
        String result = newTemp();
        instructions.add(new Instruction(String.valueOf(operator), operand1, operand2, result));
        operands.push(result);
    }
    private String newTemp()
    {
        return "t" + tempCount++;
    }
    private boolean isOperator(char token)
    {
        return token == '+' || token == '-' || token == '*' || token == '/';
    }

    private int precedence(char operator) 
    {
        switch (operator) 
        {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return -1;
        }
    }
}
public class EXPT
{
    public static void main(String[] args) 
     {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter an arithmetic expression:");
        String expression = scanner.nextLine();

        IntermediateCodeGenerator icg = new IntermediateCodeGenerator();
        List<Instruction> code = icg.generate(expression);
        System.out.println("Intermediate Code:");
        for (Instruction instr : code) {
            System.out.println(instr);
        }
        scanner.close();
    }
}