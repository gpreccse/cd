//7. Implementation of Shift Reduce parsing using stack.
import java.util.*;

class Production {
    String lhs;
    List<String> rhs;

    public Production(String lhs, List<String> rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }
}

class Grammar {
    private List<Production> productions;

    public Grammar() {
        this.productions = new ArrayList<>();
    }

    public void addProduction(String lhs, String... rhs) {
        productions.add(new Production(lhs, Arrays.asList(rhs)));
    }

    public List<Production> getProductions() {
        return productions;
    }

    public List<Production> getProductionsForLHS(String lhs) {
        List<Production> result = new ArrayList<>();
        for (Production p : productions) {
            if (p.lhs.equals(lhs)) {
                result.add(p);
            }
        }
        return result;
    }

    public static Grammar parseGrammar(Scanner scanner) {
        Grammar grammar = new Grammar();
        System.out.println("Enter productions (empty line to end):");
        while (true) {
            String line = scanner.nextLine();
            if (line.trim().isEmpty()) break;
            String[] parts = line.split("->");
            String lhs = parts[0].trim();
            String[] rhs = parts[1].trim().split("\\s+");
            grammar.addProduction(lhs, rhs);
        }
        return grammar;
    }
}

public class EXP7 {
    private List<Token> tokens;
    private Stack<String> stack;
    private int currentTokenIndex;
    private Grammar grammar;

    public EXP7(List<Token> tokens, Grammar grammar) {
        this.tokens = tokens;
        this.stack = new Stack<>();
        this.currentTokenIndex = 0;
        this.grammar = grammar;
    }

    private Token getNextToken() {
        return tokens.get(currentTokenIndex);
    }

    private void shift() {
        Token token = getNextToken();
        stack.push(token.getValue());
        currentTokenIndex++;
        printState("shift " + token.getValue());
    }

    private boolean reduce() {
        for (Production production : grammar.getProductions()) {
            if (match(production.rhs)) {
                for (int i = 0; i < production.rhs.size(); i++) {
                    stack.pop();
                }
                stack.push(production.lhs);
                printState("reduce by " + production.lhs + " -> " + String.join(" ", production.rhs));
                return true;
            }
        }
        return false;
    }

    private boolean match(List<String> rhs) {
        if (stack.size() < rhs.size()) return false;
        for (int i = 0; i < rhs.size(); i++) {
            if (!stack.get(stack.size() - rhs.size() + i).equals(rhs.get(i))) {
                return false;
            }
        }
        return true;
    }

    private void printState(String action) {
        System.out.println(stack + "\t" + remainingInput() + "\t" + action);
    }

    private String remainingInput() {
        StringBuilder sb = new StringBuilder();
        for (int i = currentTokenIndex; i < tokens.size(); i++) {
            sb.append(tokens.get(i).getValue());
        }
        return sb.toString();
    }

    public boolean parse() {
        stack.push("$");
        printState("");
        while (true) {
            Token token = getNextToken();
            if (token.getType() == Token.Type.END && stack.size() == 2 && stack.get(1).equals("S")) {
                printState("Accept");
                return true; // Successfully parsed
            }
            if (token.getType() == Token.Type.END) {
                if (!reduce()) {
                    return false; // Parsing failed
                }
            } else {
                shift();
                while (reduce()) {
                    // Continue reducing while possible
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read grammar from user
        Grammar grammar = Grammar.parseGrammar(scanner);

        // Read input string
        System.out.println("Enter the input string:");
        String input = scanner.nextLine();
        Lexer lexer = new Lexer(input);
        List<Token> tokens = lexer.tokenize();

        EXP7 parser = new EXP7(tokens, grammar);
        System.out.println("Stack contents\tInput string\tActions");
        System.out.println("------------------------------------------");
        boolean result = parser.parse();
        System.out.println("Parsing result: " + result);
    }
}

class Token {
    public enum Type {
        ID, PLUS, MINUS, LPAREN, RPAREN, END
    }

    private Type type;
    private String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}

class Lexer {
    private String input;
    private int pos;
    private int length;

    public Lexer(String input) {
        this.input = input;
        this.pos = 0;
        this.length = input.length();
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (pos < length) {
            char current = input.charAt(pos);
            switch (current) {
                case '+':
                    tokens.add(new Token(Token.Type.PLUS, "+"));
                    pos++;
                    break;
                case '-':
                    tokens.add(new Token(Token.Type.MINUS, "-"));
                    pos++;
                    break;
                case '(':
                    tokens.add(new Token(Token.Type.LPAREN, "("));
                    pos++;
                    break;
                case ')':
                    tokens.add(new Token(Token.Type.RPAREN, ")"));
                    pos++;
                    break;
                default:
                    if (Character.isLetter(current)) {
                        StringBuilder sb = new StringBuilder();
                        while (pos < length && Character.isLetterOrDigit(input.charAt(pos))) {
                            sb.append(input.charAt(pos++));
                        }
                        tokens.add(new Token(Token.Type.ID, sb.toString()));
                    } else {
                        pos++;
                    }
                    break;
            }
        }
        tokens.add(new Token(Token.Type.END, "$"));
        return tokens;
    }
}
