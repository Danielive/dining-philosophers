import java.util.Scanner;

/**
 * @author Daniel Chuev
 */
public class Main {

    public static void main(String args[]) throws InterruptedException {
        System.out.print("Enter number of philosophers: ");
        new Manager().execute(Integer.parseInt(new Scanner(System.in).nextLine()));
    }
}