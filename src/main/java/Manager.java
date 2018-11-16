import org.jetbrains.annotations.Contract;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Chuev
 */
final class Manager {

    private static List<Philosopher> philosophers = new ArrayList<>();
    private static List<Fork> forks = new ArrayList<>();

    void execute(int count) {
        System.out.println("Number of philosophers: " + count);

        // set list philosophers
        for (int i = 0; i < count; i++) {
            philosophers.add(new Philosopher(i));
            forks.add(new Fork(i));
        }


        while (true) {
            getPhilosophers().stream().parallel().filter(this::choicePhilosopher).findAny().ifPresent(Philosopher::startDining);
        }
    }

    @Contract(pure = true)
    private Boolean choicePhilosopher(Philosopher philosopher) {
        if ((philosopher.getNumber()+1) < Manager.getPhilosophers().size())
            return philosopher.isState(philosopher.getNumber(), (philosopher.getNumber() + 1));
        else return philosopher.isState(philosopher.getNumber(), 0);
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