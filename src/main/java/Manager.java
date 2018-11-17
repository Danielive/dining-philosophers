import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Daniel Chuev
 */
final class Manager {

    private static List<Philosopher> philosophers = new ArrayList<>();
    private static List<Fork> forks = new ArrayList<>();
    private static boolean stateDined;

    void execute(final int count) {
        System.out.println("Number of philosophers: " + count);

        // set list philosophers
        for (int i = 0; i < count; i++) {
            philosophers.add(new Philosopher(i));
            forks.add(new Fork(i));
        }

        while (true) {
            setStateDined(false);
            getPhilosophers().forEach(Philosopher::clear);
            getForks().forEach(Fork::clear);

            while (!stateDined) {
                getPhilosophers()
                        .stream()
                        .filter(this::choicePhilosopher)
                        .parallel()
                        .forEach(Philosopher::dining);

                getPhilosophers().forEach(this::choiceRestorePhilosopher);

                checkDined(count);
            }
        }
    }

    private void checkDined(final int count) {
        int i = 0;

        for (Philosopher phil : Manager.getPhilosophers()) {
            if (phil.getDined()) {
                i++;
            }
        }

        if (i == count) setStateDined(true);
    }

    private StatePhilosopher state = (one, two) -> {
        if ((Manager.getForks().get(one).isState() && Manager.getForks().get(two).isState()) &&
                !(Manager.getPhilosophers().get(one).getState() && Manager.getPhilosophers().get(two).getState()) &&
                !Manager.getPhilosophers().get(one).getDined() && !Manager.getPhilosophers().get(one).getTake()) {
            Manager.getForks().get(one).setState(false);
            Manager.getForks().get(two).setState(false);
            Manager.getPhilosophers().get(one).setState(true);
            Manager.getPhilosophers().get(two).setState(true);
            Manager.getPhilosophers().get(one).setTake(true);
            return true;
        }
        return false;
    };

    private StatePhilosopher restore = (one, two) -> {
        if (!(Manager.getForks().get(one).isState() && Manager.getForks().get(two).isState()) &&
                (Manager.getPhilosophers().get(one).getState() && Manager.getPhilosophers().get(two).getState()) &&
                !Manager.getPhilosophers().get(one).getDined() && Manager.getPhilosophers().get(one).getTake()) {
            Manager.getForks().get(one).setState(true);
            Manager.getForks().get(two).setState(true);
            Manager.getPhilosophers().get(one).setState(false);
            Manager.getPhilosophers().get(two).setState(false);
            Manager.getPhilosophers().get(two).setTake(false);
            Manager.getPhilosophers().get(one).setDined(true);
            return true;
        }
        return false;
    };

    private static void setStateDined(boolean stateDined) {
        Manager.stateDined = stateDined;
    }

    @Contract(pure = true)
    private Boolean isState(Integer oneFork, Integer twoFork) {return state.isReady(oneFork, twoFork);}

    private void doRestore(Integer oneFork, Integer twoFork) {restore.isReady(oneFork, twoFork);}

    @Contract(pure = true)
    private Boolean choicePhilosopher(Philosopher philosopher) {
        if ((philosopher.getNumber()+1) < Manager.getPhilosophers().size())
            return isState(philosopher.getNumber(), (philosopher.getNumber() + 1));
        else return isState(philosopher.getNumber(), 0);
    }

    private void choiceRestorePhilosopher(Philosopher philosopher) {
        if (!philosopher.getDined() && philosopher.getState()) {
            if (philosopher.getNumber()+1 < Manager.getPhilosophers().size())
                doRestore(philosopher.getNumber(), philosopher.getNumber()+1);
            else
                doRestore(philosopher.getNumber(), 0);
        }
    }

    @Contract(pure = true)
    private static List<Philosopher> getPhilosophers() {
        return philosophers;
    }

    @Contract(pure = true)
    static List<Fork> getForks() {
        return forks;
    }
}