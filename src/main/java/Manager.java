import org.jetbrains.annotations.Contract;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * @author Daniel Chuev
 */
final class Manager {

    private static List<Philosopher> philosophers = new ArrayList<>();
    private static List<Fork> forks = new ArrayList<>();

    void execute(int count) throws InterruptedException {
        System.out.println("Number of philosophers: " + count);

        // set list philosophers
        for (int i = 0; i < count; i++) {
            philosophers.add(new Philosopher(i));
            forks.add(new Fork(i));
        }

        Thread.sleep(1000);

        // starting philosophers
        for (int i = 0; i < count; i++) {
            Executors.newFixedThreadPool(count).execute(philosophers.get(i));
            Thread.sleep(10);
        }

        Thread.sleep(1000);

        Executors.newFixedThreadPool(count).shutdown();
        // noinspection StatementWithEmptyBody
        while(!Executors.newFixedThreadPool(count).isTerminated()){
            // wait for all tasks to finish
        }
    }

    @Contract(pure = true)
    static List<Philosopher> getPhilosophers() {
        return philosophers;
    }

    @Contract(pure = true)
    static List<Fork> getForks() {
        return forks;
    }
}