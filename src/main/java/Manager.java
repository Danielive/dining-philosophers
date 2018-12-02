import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;
import org.jetbrains.annotations.Contract;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Daniel Chuev
 */
final class Manager {

    private static List<Philosopher> philosophers = new ArrayList<>();
    private static List<Fork> forks = new ArrayList<>();
    private static boolean stateDined;
    private final static int max = 10;
    private final static int min = 1;
    private static MutableValueGraph<Philosopher, Integer> weightedGraph = ValueGraphBuilder.undirected().build();

    void execute(final int count) {
        System.out.println("Number of philosophers: " + count);

        // set nodes
        for (int i = 0; i < count; i++) {
            philosophers.add(new Philosopher(i));
            forks.add(new Fork(i));
            weightedGraph.addNode(philosophers.get(i));
        }

        // set edges
        for (int i = 0; i < count; i++) {
            if ((i+1) < count)
                weightedGraph.putEdgeValue(philosophers.get(i), philosophers.get(i + 1), new Random().nextInt(max - min + 1) + min);
            else
                weightedGraph.putEdgeValue(philosophers.get(i), philosophers.get(0), new Random().nextInt(max - min + 1) + min);
        }

        // additionally
        for (int i = 0; i < count; i++) {
            if ((i+3) < count)
                weightedGraph.putEdgeValue(philosophers.get(i+1), philosophers.get(i+3), new Random().nextInt(max - min + 1) + min);
            if ((i+4) < count)
                weightedGraph.putEdgeValue(philosophers.get(i+2), philosophers.get(i+4), new Random().nextInt(max - min + 1) + min);
            if ((i+5) < count)
                weightedGraph.putEdgeValue(philosophers.get(i+3), philosophers.get(i+5), new Random().nextInt(max - min + 1) + min);
        }

        System.out.println(weightedGraph.toString());

        // execute
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
        synchronized (this) {
            // TODO: 03.12.2018 Определяем первого по меньшему весу
            // TODO: 03.12.2018 Запускаем первого и затем смотрим вес от него идущего, у кого меньше тот следующий
            // todo другой поток видит свободный узел и он не занят и исполняет его
            // todo и так пока все не насытятся

            // Смотрит вес между вершинами
            //optionalToString(weightedGraph.edgeValue(philosophers.get(one), philosophers.get(two)).toString())

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

    private String optionalToString(String str) {
        str = str.substring(9);
        str = str.replaceFirst("]", "");
        return str;
    }
}