//2. Implementation of Lexical Analyzer.
import java.util.*;
class EXP2
{
	private static final List<String> keywords = Arrays.asList("if","else","while","for","int","float","char","double"), operators=Arrays.asList("+","-","*","/","%",">","<",">=","<=","==","!=","&&","||","!","+=","-=","*=","/=","%=");
	enum TokenType{
		KEYWORD,NUMBER,IDENTIFIER,SYMBOL,OPERATOR
	}
	static class Token
	{
		TokenType type;
		String value;
		public Token(TokenType type,String value)
		{
			this.type=type;
			this.value=value;
		}
		public String toString()
		{
			return "< "+type+" , "+value+">";
		}
	}
	public static List<Token> analyze(String input)
	{
		List<Token> Tokens=new ArrayList<>();
		StringBuilder ct=new StringBuilder();
		for(char c:input.toCharArray())
		{
			if(Character.isWhitespace(c) && ct.length()>0)
			{
				Tokens.add(createToken(ct.toString()));
				ct=new StringBuilder();
			}
			else if(Character.isDigit(c)||Character.isLetter(c))
				ct.append(c);
			else
			{
				if(ct.length()>0)
				{
					Tokens.add(createToken(ct.toString()));
					ct=new StringBuilder();
				}
				if(operators.contains(c+""))
					Tokens.add(new Token(TokenType.OPERATOR,c+""));
				else
					Tokens.add(new Token(TokenType.SYMBOL,c+""));
			}
		}
		if(ct.length()>0)
			Tokens.add(createToken(ct.toString()));
		return Tokens;
	}
	private static Token createToken(String s)
	{
		if(keywords.contains(s))
			return new Token(TokenType.KEYWORD,s);
		else if(operators.contains(s))
			return new Token(TokenType.OPERATOR,s);
		else if(s.matches("[a-zA-z]+[a-zA-Z0-9]*"))
			return new Token(TokenType.IDENTIFIER,s);
		else if(s.matches("[0-9]+"))
			return new Token(TokenType.NUMBER,s);
		else
			throw new IllegalArgumentException("Invalid Token : "+s);
	}
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		System.out.println("Enter the Code to Analyze and type exit to finish");
		String input=sc.nextLine();
		while(!input.equals("exit"))
		{
			List<Token> tokens = analyze(input);
			for(Token token:tokens)
				System.out.print(token+" ");
			System.out.println();
			input=sc.nextLine();
		}
        sc.close();
	}
}