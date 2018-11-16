import java.util.Scanner;
import java.util.function.Supplier;

/**
 * @author Daniel Chuev
 */
public class Main {

    private static Supplier<Integer> scanText = () -> {
        System.out.print("Enter number of philosophers: ");
        return Integer.parseInt(new Scanner(System.in).nextLine());
    };

    public static void main(String args[]) throws InterruptedException {
        new Manager().execute(scanText.get());
    }
}