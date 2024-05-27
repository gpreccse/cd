//4. Implementation of Finding a Left Factoring.
import java.util.*;
public class EXP4 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of productions:");
        int n = sc.nextInt();
        sc.nextLine();

        String[] productions = new String[n];
        System.out.println("Enter the production(s):");
        for (int i = 0; i < n; i++) {
            productions[i] = sc.nextLine();
        }

        boolean leftFactoringOccurred = eliminateLeftFactoring(productions);

        if (!leftFactoringOccurred) {
            System.out.println("There is no left factoring in the given production(s)");
        }
        sc.close();
    }

    private static boolean eliminateLeftFactoring(String[] productions) {
        boolean leftFactoringOccurred = false;
        List<String> newProductions = new ArrayList<>();
        for (String production : productions) {
            String[] parts = production.split("->");
            String lhs = parts[0].trim();
            String[] rhs = parts[1].split("\\|");
            String prefix = findCommonPrefix(rhs);

            if (!prefix.isEmpty()) {
                leftFactoringOccurred = true;
                newProductions.add(lhs + "->" + prefix + lhs + "'");
                List<String> newRhs = new ArrayList<>();
                for (String r : rhs) {
                    if (r.startsWith(prefix)) {
                        String suffix = r.substring(prefix.length()).trim();
                        if (suffix.isEmpty()) {
                            suffix = "ε";
                        }
                        newRhs.add(suffix);
                    } else {
                        newRhs.add(r);
                    }
                }
                newProductions.add(lhs + "'->" + String.join("|", newRhs));
            } else {
                newProductions.add(production);
            }
        }
        if (leftFactoringOccurred) {
            System.out.println("The new production(s) are:");
            for (String production : newProductions) {
                System.out.println(production);
            }
        }
        return leftFactoringOccurred;
    }

    private static String findCommonPrefix(String[] rhs) {
        String prefix = "";
        if (rhs.length > 1) {
            prefix = rhs[0];
            for (int i = 1; i < rhs.length; i++) {
                int j = 0;
                while (j < prefix.length() && j < rhs[i].length() && prefix.charAt(j) == rhs[i].charAt(j)) {
                    j++;
                }
                prefix = prefix.substring(0, j);
            }
        }
        return prefix;
    }
}