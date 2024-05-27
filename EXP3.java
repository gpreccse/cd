//3. Implement Elimination of Left Recursion.
import java.util.Scanner;
import java.util.*;

public class EXP3 {
    static void elrp1(Map<String, List<String>> grammar) {
        for (String nt : new ArrayList<>(grammar.keySet())) {
            List<String> P = grammar.get(nt);
            List<String> np = new ArrayList<>();
            List<String> rp = new ArrayList<>();
            for (String p : P) {
                if (p.startsWith(nt))
                    rp.add(p.substring(nt.length()).trim());
                else
                    np.add(p.trim());
            }
            if (!rp.isEmpty()) {
                String nnt = nt + "’";
                for (String b : np) {
                    np.add(b + nnt);
                }
                np.remove(0); // Remove the first element from np only once
                for (String a : rp) {
                    rp.add(a + nnt);
                }
                rp.remove(0);
                rp.add("$");
                grammar.put(nt, np);
                grammar.put(nnt, rp);
            }
        }
    }
    public static void main(String[] args) {
        Map<String, List<String>> grammar = new LinkedHashMap<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the grammar productions:");
        String si = sc.nextLine();
        while (!si.equals("done")) {
            String parts[] = si.split("->");
            grammar.put(parts[0].trim(), Arrays.asList(parts[1].split("\\|")));
            si = sc.nextLine();
        }
        elrp1(grammar);
        for (String nt : grammar.keySet()) {
            System.out.println(nt + "->" + String.join("|", grammar.get(nt)));
        }
    }
}


