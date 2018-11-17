import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Daniel Chuev
 */
final class Manager {

    private static List<Philosopher> philosophers = Collections.synchronizedList(new ArrayList<>());
    private static List<Fork> forks = Collections.synchronizedList(new ArrayList<>());

    void execute(int count) {
        System.out.println("Number of philosophers: " + count);

        // set list philosophers
        for (int i = 0; i < count; i++) {
            philosophers.add(new Philosopher(i));
            forks.add(new Fork(i));
        }

        while (true) {
            boolean stateDined = false;

            for (Philosopher phil : Manager.getPhilosophers()) {
                phil.setDined(false);
                phil.setState(false);
                phil.setTake(false);
            }
            for (Fork fork : Manager.getForks()) {
                fork.setState(true);
            }

            while (!stateDined) {
                getPhilosophers()
                        .stream()
                        .filter(this::choicePhilosopher)
                        .parallel()
                        .forEach(Philosopher::dining);

                int i = 0;

                for (Philosopher phil : Manager.getPhilosophers()) {
                    if (!phil.getDined() && phil.getState()) {
                        if (phil.getNumber()+1 < Manager.getPhilosophers().size())
                            restore(phil.getNumber(), phil.getNumber()+1);
                        else
                            restore(phil.getNumber(), 0);
                    }
                }

                for (Philosopher phil : Manager.getPhilosophers()) {
                    if (phil.getDined()) {
                        i++;
                    }
                }

                if (i == count) stateDined = true;
            }
        }
    }

    private StatePhilosopher state = (one, two) -> {
        synchronized (this) {
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
        }
    };

    @NotNull
    private Boolean restore(Integer one, Integer two) {
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
    }

    @Contract(pure = true)
    private Boolean isState(Integer oneFork, Integer twoFork) {return state.isReady(oneFork, twoFork);}

    @Contract(pure = true)
    private Boolean choicePhilosopher(Philosopher philosopher) {
        if ((philosopher.getNumber()+1) < Manager.getPhilosophers().size())
            return isState(philosopher.getNumber(), (philosopher.getNumber() + 1));
        else return isState(philosopher.getNumber(), 0);
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