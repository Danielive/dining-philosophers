import java.util.*;

/**
 * @author Daniel Chuev
 */
class Manager {

    private static List<Philosopher> philosophers = new ArrayList<>();
    private static List<Fork> forks = new ArrayList<>();
    private static boolean stateDined;
    private final static int max = 9;
    private final static int min = 1;
    private static List<Edge> edges = new ArrayList<>();
    private static List<Edge> edgesExecute = new ArrayList<>();

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
            printGraphFor5();
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

    private static List<Philosopher> getPhilosophers() {
        return philosophers;
    }

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

    private void printGraphFor5() {
        System.out.println(
                "                     #" + edges.get(0).getWeight() + "     \n" +
                        "       ---------" + 1 + "---------" + 2 + "\n" +
                        "       |#" + edges.get(6).getWeight() + "       \\#" + edges.get(4).getWeight() + "     /|\n" +
                        "       |           \\    / |\n" +
                        "       " + 5 + "             \\/   |#" + edges.get(1).getWeight() + "\n" +
                        "       |            /  \\  |\n" +
                        "       |#" + edges.get(3).getWeight() + "        /#" + edges.get(5).getWeight() + "    \\|\n" +
                        "       ---------" + 4 + "---------" + 3 + "\n" +
                        "                     #" + edges.get(2).getWeight() + "     \n"
        );
        System.out.println("_________________________________\n");
    }
}