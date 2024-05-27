//9. Implementation of Stack Allocation Strategy.
import java.util.Scanner;
public class EXP9 {
    static int[] stack;
    static int top, n;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        top = -1;

        System.out.println("Enter the size of stack[MAX=100]: ");
        n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("Invalid stack size.");
            scanner.close();
            return;
        }

        stack = new int[n];

        System.out.println("\n\tStack Operations:");
        System.out.println("\t--------------------------");
        System.out.println("\t1. Push");
        System.out.println("\t2. Pop");
        System.out.println("\t3. Display");
        System.out.println("\t4. EXIT");

        int choice;
        do {
            System.out.println("\nEnter your choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    push(scanner);
                    break;
                case 2:
                    pop();
                    break;
                case 3:
                    display();
                    break;
                case 4:
                    System.out.println("\nEXIT");
                    break;
                default:
                    System.out.println("Please enter a valid choice.");
            }
        } while (choice != 4);

    }

    static void push(Scanner scanner) {
        if (top >= n - 1) {
            System.out.println("\nStack overflow");
        } else {
            System.out.println("Enter a value to be pushed: ");
            int x = scanner.nextInt();
            top++;
            stack[top] = x;
        }
    }

    static void pop() {
        if (top == -1)
            System.out.println("\nStack underflow");
        else {
            System.out.println("\nThe popped element is " + stack[top]);
            top--;
        }
    }

    static void display() {
        if (top >= 0) {
            System.out.println("\nThe elements in the stack are:");
            for (int i = top; i >= 0; i--)
                System.out.println(stack[i]);
            System.out.println("\nSelect next choice");
        } else
            System.out.println("\nThe stack is empty.");
    }
}