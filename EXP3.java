//3. Implement Elimination of Left Recursion.
import java.util.Scanner;
public class EXP3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        char nt;
        char b, a;
        int num;
        int index = 3;
        System.out.print("Enter Number of productions:");
        num = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        String[] p = new String[num];
        System.out.println("Enter the grammar as E->E-A:");
        for (int i = 0; i < num; i++) {
            p[i] = scanner.nextLine();
        }
        for (int i = 0; i < num; i++) {
            System.out.println("\nGRAMMAR:" + p[i]);
            nt = p[i].charAt(0);

            if (nt == p[i].charAt(index)) {
                a = p[i].charAt(index);
                System.out.println("is left recursive:");
                while (index < p[i].length() && p[i].charAt(index) != '|' && p[i].charAt(index) != 0) {
                    index++;
                }
                if (index < p[i].length()) {
                    b = p[i].charAt(index + 1);
                    System.out.println("Grammar without left recursion;");
                    System.out.println(nt + "->" + b + nt + "'");
                    System.out.println("\n" + nt + "'->" + a + nt + "'|E");
                } else {
                    System.out.println("can’t be reduced");
                }
            } else {
                System.out.println("is not left recursive");
            }
            index = 3;
        }
    }
}


