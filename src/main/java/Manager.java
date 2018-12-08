import org.jetbrains.annotations.Contract;

import java.util.*;

/**
 * @author Daniel Chuev
 */
final class Manager {

    private static List<Philosopher> philosophers = new ArrayList<>();
    private static List<Fork> forks = new ArrayList<>();
    private static boolean stateDined;
    private final static int max = 10;
    private final static int min = 1;
    private static List<Edge> edges = new ArrayList<>();
    private static Iterator <Edge> iterator = edges.iterator();

    void execute(final int count) {
        System.out.println("Number of philosophers: " + count);

        // set nodes
        for (int i = 0; i < count; i++) {
            philosophers.add(new Philosopher(i));
            forks.add(new Fork(i));
        }

        // execute
        while (true) {
            edges.clear();
            setStateDined(false);
            getPhilosophers().forEach(Philosopher::clear);
            getForks().forEach(Fork::clear);
            setEdges(count);
            Collections.sort(edges);

            while (!stateDined) {
//                edges.stream()
//                        .filter(Philosopher -> Philosopher::choicePhilosopher)
//                        .parallel()
//                        .forEach(Philosopher::dining);
//
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
        synchronized (this) {
            if ((Manager.getForks().get(one).isState() && Manager.getForks().get(two).isState()) &&
                    !(Manager.getPhilosophers().get(one).getState() && Manager.getPhilosophers().get(two).getState()) &&
                    !Manager.getPhilosophers().get(one).getDined() && !Manager.getPhilosophers().get(one).getTake()) {

                System.out.printf("* Philosopher-%s | edge: Node %s, Node %s%n", one+1, one+1, two+1);

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

        synchronized (this) {
            for (Edge edge : edges) {
                if ((Manager.getForks().get(edge.getPhilosopherOne().getNumber()).isState() && Manager.getForks().get(edge.getPhilosopherTwo().getNumber()).isState()) &&
                        !(Manager.getPhilosophers().get(edge.getPhilosopherOne().getNumber()).getState() && Manager.getPhilosophers().get(edge.getPhilosopherTwo().getNumber()).getState()) &&
                        !Manager.getPhilosophers().get(edge.getPhilosopherOne().getNumber()).getDined() && !Manager.getPhilosophers().get(edge.getPhilosopherOne().getNumber()).getTake()
                        && !edge.isStateEdge()) {

                    edge.setStateEdge(true);
                    System.out.printf("One 1: %s 2: %s%n", edge.getPhilosopherOne().getNumber() + 1, edge.getPhilosopherTwo().getNumber() + 1);
                    return isState(edge.getPhilosopherOne().getNumber(), edge.getPhilosopherTwo().getNumber());
                } else if ((Manager.getForks().get(edge.getPhilosopherTwo().getNumber()).isState() && Manager.getForks().get(edge.getPhilosopherOne().getNumber()).isState()) &&
                        !(Manager.getPhilosophers().get(edge.getPhilosopherTwo().getNumber()).getState() && Manager.getPhilosophers().get(edge.getPhilosopherOne().getNumber()).getState()) &&
                        !Manager.getPhilosophers().get(edge.getPhilosopherTwo().getNumber()).getDined() && !Manager.getPhilosophers().get(edge.getPhilosopherTwo().getNumber()).getTake()
                        && !edge.isStateEdge()) {

                    edge.setStateEdge(true);
                    System.out.printf("Two 1: %s 2: %s%n", edge.getPhilosopherTwo().getNumber() + 1, edge.getPhilosopherOne().getNumber() + 1);
                    return isState(edge.getPhilosopherTwo().getNumber(), edge.getPhilosopherOne().getNumber());
                }
            }
            return false;
        }
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

    private void setEdges(int count) {
        System.out.println("_________________________________\n");

        // set edges
        for (int i = 0; i < count; i++)
            if ((i+1) < count)
                edges.add(new Edge(philosophers.get(i), philosophers.get(i+1), new Random().nextInt((max - min + 1) + min)));

        // additionally
        for (int i = 0; i < count-2; i++)
            if ((i+2) != count-1)
                edges.add(new Edge(philosophers.get(i), philosophers.get(i+2), new Random().nextInt((max - min + 1) + min)));
            else
                edges.add(new Edge(philosophers.get(count-1), philosophers.get(0), new Random().nextInt((max - min + 1) + min)));

        System.out.println("_________________________________\n");
    }
}