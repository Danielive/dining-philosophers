import org.jetbrains.annotations.Contract;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Daniel Chuev
 */
class Manager {

    volatile private static List<Philosopher> philosophers = new ArrayList<>();
    volatile private static List<Fork> forks = new ArrayList<>();

    void execute(int count) throws InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(count);

        // set list philosophers
        for (int i = 0; i < count; i++) {
            philosophers.add(new Philosopher(i));
            forks.add(new Fork(i));
        }

        Thread.sleep(1000);

        // starting philosophers
        for (int i = 0; i < count; i++) {
            threadPool.execute(philosophers.get(i));
            Thread.sleep(10);
        }

        Thread.sleep(1000);

        threadPool.shutdown();
        // noinspection StatementWithEmptyBody
        while(!threadPool.isTerminated()){
            // wait for all tasks to finish
        }

        System.out.println("All the philosophers of dined");
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