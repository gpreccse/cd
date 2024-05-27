//2. Implementation of Lexical Analyzer.
import java.util.*;
public class exp2 {
    private static final List<String> KEYWORDS = Arrays.asList("if", "else", "while", "for", "int", "float", "double");
    enum TokenType {
        IDENTIFIER, NUMBER, KEYWORD, SYMBOL
    }
    static class Token {
        TokenType type;
        String value;

        public Token(TokenType type, String value) {
            this.type = type;
            this.value = value;
        }
        public String toString() {
            return "<" + type + ", " + value + ">";
        }
    }
    public static List<Token> analyze(String input) {
        List<Token> tokens = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                if (currentToken.length() > 0) {
                    tokens.add(createToken(currentToken.toString()));
                    currentToken = new StringBuilder();
                }
            } else if (Character.isLetter(c)) {
                currentToken.append(c);
            } else if (Character.isDigit(c)) {
                currentToken.append(c);
            } else {
                if (currentToken.length() > 0) {
                    tokens.add(createToken(currentToken.toString()));
                    currentToken = new StringBuilder();
                }
                tokens.add(new Token(TokenType.SYMBOL, String.valueOf(c)));
            }
        }
        if (currentToken.length() > 0) {
            tokens.add(createToken(currentToken.toString()));
        
        return tokens;
    }
    private static Token createToken(String tokenValue) {
        if (KEYWORDS.contains(tokenValue)) {
            return new Token(TokenType.KEYWORD, tokenValue);
        } else if (tokenValue.matches("[a-zA-Z]+")) {
            return new Token(TokenType.IDENTIFIER, tokenValue);
        } else if (tokenValue.matches("[0-9]+")) {
            return new Token(TokenType.NUMBER, tokenValue);
        } else {
            throw new IllegalArgumentException("Invalid token: " + tokenValue);
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter input string: ");
        String input = scanner.nextLine();
        scanner.close();
        List<Token> tokens = analyze(input);
        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
