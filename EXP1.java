//1.Implementation of DFA to accept strings ending with abc.
import java.util.*;
public class EXP1 {
    public static void main(String[] arg) throws IOException {
        Scanner br = new Scanner(System.in);
        System.out.print("Enter the String : ");
        String s = br.readLine();
        int state = 0;
        System.out.print("Q0-->");
        for (char ch : s.toCharArray()) {
            if (state == 0) {
                if (ch == 'b' || ch == 'c')
                    System.out.print("Q0-->");
                else {
                    state = 1;
                    System.out.print("Q1-->");
                }
            } else if (state == 1) {
                if (ch == 'a')
                    System.out.print("Q1-->");
                else if (ch == 'b') {
                    state = 2;
                    System.out.print("Q2-->");
                } else {
                    state = 0;
                    System.out.print("Q0-->");
                }
            } else if (state == 2) {
                if (ch == 'a') {
                    state = 1;
                    System.out.print("Q1-->");
                } else if (ch == 'b') {
                    state = 0;
                    System.out.print("Q0-->");
                } else {
                    state = 3;
                    System.out.print("Q3-->");
                }
            } else {
                if (ch == 'b' || ch == 'c') {
                    state = 0;
                    System.out.print("Q0-->");
                } else {
                    state = 1;
                    System.out.print("Q1-->");
                }
            }
        }
        System.out.println("X");
        if (state == 3)
            System.out.println("Accepted");
        else
            System.out.println("Rejected");
    }
}
