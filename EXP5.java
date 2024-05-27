//5. Implementation of First and Follow functions.
import java.util.*;
import java.io.*;
public class EXP5 {
    static char nonTerminals[], terminals[];
    static int nonTermLen, termLen;
    static String grammar[][], first[], follow[];
    static String calculateFirst(int index) {
        String temp = "";
        for (String production : grammar[index]) {
            char firstChar = production.charAt(0);
            if (Character.isLowerCase(firstChar) || firstChar == '9')
                temp += firstChar;
            else
                temp += calculateFirst(getIndex(firstChar));
        }
        return temp;
    }
    static String calculateFollow(int index) {
        String temp = "";
        if (index == 0)
            temp = "$";
        for (int j = 0; j < nonTermLen; j++) {
            for (String production : grammar[j]) {
                for (int k = 0; k < production.length(); k++) {
                    if (production.charAt(k) == nonTerminals[index]) {
                        if (k == production.length() - 1) {
                            if (j < index)
                                temp += follow[j];
                        } else {
                            int nextIndex = getIndex(production.charAt(k + 1));
                            if (nextIndex != -1) {
                                temp += first[nextIndex];
                                if (first[nextIndex].contains("9") && k + 1 == production.length() - 1)
                                    temp += follow[j];
                            } else
                                temp += production.charAt(k + 1);
                        }
                    }
                }
            }
        }
        return temp;
    }
    static void printSets(String[] set) {
        for (String s : set)
            System.out.println(removeDuplicates(s));
    }
    static int getIndex(char c) {
        for (int i = 0; i < nonTermLen; i++) {
            if (nonTerminals[i] == c)
                return i;
        }
        return -1;
    }
    static String removeDuplicates(String str) {
        char[] chars = str.toCharArray();
        Set<Character> charSet = new LinkedHashSet<>();
        for (char c : chars)
            charSet.add(c);
        StringBuilder sb = new StringBuilder();
        for (Character character : charSet)
            sb.append(character);
        return sb.toString();
    }
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the non-terminals:");
        String nt = br.readLine();
        nonTermLen = nt.length();
        nonTerminals = nt.toCharArray();
        System.out.println("Enter the terminals:");
        String t = br.readLine();
        termLen = t.length();
        terminals = t.toCharArray();
        System.out.println("Specify the grammar (Enter 9 for epsilon production):");
        grammar = new String[nonTermLen][];
        for (int i = 0; i < nonTermLen; i++) {
            System.out.println("Enter the number of productions for " + nonTerminals[i]);
            int n = Integer.parseInt(br.readLine());
            grammar[i] = new String[n];
            System.out.println("Enter the productions:");
            for (int j = 0; j < n; j++)
                grammar[i][j] = br.readLine();
        }
        first = new String[nonTermLen];
        for (int i = 0; i < nonTermLen; i++)
            first[i] = calculateFirst(i);
        System.out.println("First Set:");
        printSets(first);
        follow = new String[nonTermLen];
        for (int i = 0; i < nonTermLen; i++)
            follow[i] = calculateFollow(i);
        System.out.println("Follow Set:");
        printSets(follow);
    }
}