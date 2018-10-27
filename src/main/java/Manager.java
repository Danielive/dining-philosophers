import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Daniel Chuev
 */
class Manager {

    private static List<Philosopher> philosophers = new ArrayList<>();
    private static List<Fork> forks = new ArrayList<>();
    private static ExecutorService ThreadPool;

    void execute(int count) throws InterruptedException {

        ThreadPool = Executors.newFixedThreadPool(count);

        // set list philosophers
        for (int i = 0; i < count; i++) {
            philosophers.add(new Philosopher(i));
            forks.add(new Fork(i));
        }

        Thread.sleep(1000);

        // starting philosophers
        for (int i = 0; i < count; i++) {
            ThreadPool.execute(philosophers.get(i));
        }

        Thread.sleep(1000);

        ThreadPool.shutdown();
        // noinspection StatementWithEmptyBody
        while(!ThreadPool.isTerminated()){
            // wait for all tasks to finish
        }

        System.out.println("All the philosophers of dined");
    }

    @Contract(pure = true)
    List<Philosopher> getPhilosophers() {
        return philosophers;
    }

    @Contract(pure = true)
    List<Fork> getForks() {
        return forks;
    }
}
