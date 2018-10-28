import java.util.Scanner;

/**
 * @author Daniel Chuev
 */
public class Main {

    public static void main(String args[]) throws InterruptedException {
        Scanner in = new Scanner(System.in);

        System.out.print("Enter number of philosophers: ");
        String input = in.nextLine();
        int count = Integer.parseInt(input);
        System.out.println("Number of philosophers: " + count);

        Manager manager = new Manager();
        manager.execute(count);
    }
}