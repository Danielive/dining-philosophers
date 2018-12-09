import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Daniel Chuev
 */
final class Manager {

    final private static SimpleDateFormat formatDate = new SimpleDateFormat("ss:SSS");
    private static List<Philosopher> philosophers = new ArrayList<>();
    private static List<Fork> forks = new ArrayList<>();
    private static boolean stateDined;
    private final static int max = 10;
    private final static int min = 1;
    private static List<Edge> edges = new ArrayList<>();
    private static List<Edge> edgesExecute = new ArrayList<>();

    void execute(final int count) throws InterruptedException {
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

                edgesExecute.clear();

                for (Edge edge : edges)
                    if (choicePhilosopher(edge))
                        edgesExecute.add(edge);

                edgesExecute.stream()
                        .parallel()
                        .forEach(this::diningPhilosopher);

                edges.forEach(this::choiceRestorePhilosopher);

                checkDined(count);
            }
        }
    }

    @NotNull
    private Boolean choiceFun(Edge edge, int one, int two) {
        if ((Manager.getForks().get(one).isState() && Manager.getForks().get(two).isState()) &&
                !(Manager.getPhilosophers().get(one).getState() && Manager.getPhilosophers().get(two).getState()) &&
                !Manager.getPhilosophers().get(one).getDined() && !Manager.getPhilosophers().get(one).getTake()
                && !edge.isStateEdge()) {
            Manager.getForks().get(one).setState(false);
            Manager.getForks().get(two).setState(false);
            Manager.getPhilosophers().get(one).setState(true);
            Manager.getPhilosophers().get(two).setState(true);
            Manager.getPhilosophers().get(one).setTake(true);
            edge.setNumPhilosopher(one);
            edge.printEdge(one, two);
            edge.setStateEdge(true);
            return true;
        }
        return false;
    }

    @NotNull
    private Boolean choicePhilosopher(Edge edge) {
        if (choiceFun(edge, edge.getPhilosopherOne().getNumber(), edge.getPhilosopherTwo().getNumber())) {
            edge.setUseLeftOrRight(false);
            return true;
        }
        else if (choiceFun(edge, edge.getPhilosopherTwo().getNumber(), edge.getPhilosopherOne().getNumber())) {
            edge.setUseLeftOrRight(true);
            return true;
        }
        return false;
    }

    private void restoreFun(int one, int two) {
        if (!(Manager.getForks().get(one).isState() && Manager.getForks().get(two).isState()) &&
                (Manager.getPhilosophers().get(one).getState() && Manager.getPhilosophers().get(two).getState()) &&
                !Manager.getPhilosophers().get(one).getDined() && Manager.getPhilosophers().get(one).getTake()) {
            Manager.getForks().get(one).setState(true);
            Manager.getForks().get(two).setState(true);
            Manager.getPhilosophers().get(one).setState(false);
            Manager.getPhilosophers().get(two).setState(false);
            Manager.getPhilosophers().get(two).setTake(false);
            Manager.getPhilosophers().get(one).setDined(true);
        }
    }

    private void choiceRestorePhilosopher(Edge edge) {
        if (!edge.isUseLeftOrRight())
            restoreFun(edge.getPhilosopherOne().getNumber(), edge.getPhilosopherTwo().getNumber());
        else if (edge.isUseLeftOrRight())
            restoreFun(edge.getPhilosopherTwo().getNumber(), edge.getPhilosopherOne().getNumber());
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

    private static void setStateDined(boolean stateDined) {
        Manager.stateDined = stateDined;
    }

    private void diningPhilosopher(Edge edge) {
        getPhilosophers().get(edge.getNumPhilosopher()).dining();
    }

    @Contract(pure = true)
    private static List<Philosopher> getPhilosophers() {
        return philosophers;
    }

    @Contract(pure = true)
    private static List<Fork> getForks() {
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