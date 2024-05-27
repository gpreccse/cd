//1.Implementation of DFA to accept strings ending with abc.
import java.util.Scanner;
public class EXP1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the input string : ");
        String s = sc.nextLine();
        if(s.endsWith("abc"))
            System.out.println("String "+s+" is accepted.");
        else
            System.out.println("String "+s+" is not accepted.");
        sc.close();
    }
}
