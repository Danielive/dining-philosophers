import org.jetbrains.annotations.NotNull;
import java.util.Date;

/**
 * @author Daniel Chuev
 */
class Edge implements Comparable<Edge> {

    private Philosopher philosopherOne;
    private Philosopher philosopherTwo;
    private Integer weight;
    private Integer numPhilosopher;
    private boolean one = false;
    private boolean two = false;
    private boolean stateEdge = false;
    private boolean useLeftOrRight = false;

    Edge (Philosopher philosopherOne, Philosopher philosopherTwo, Integer weight) {
        this.philosopherOne = philosopherOne;
        this.philosopherTwo = philosopherTwo;
        this.weight = weight;
        System.out.printf("edge: Node %s, Node %s | Weight: %s %n", philosopherOne.getNumber()+1, philosopherTwo.getNumber()+1, weight);
    }

    Philosopher getPhilosopherOne() {
        return philosopherOne;
    }
    Philosopher getPhilosopherTwo() {
        return philosopherTwo;
    }

    public Integer getWeight() {
        return weight;
    }
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(@NotNull Edge edge) {
        if(this.weight > edge.weight)
            return 1;
        else if(this.weight < edge.weight)
            return -1;
        else
            return 0;
    }

    void printEdge(int one, int two) {
        System.out.printf("edge: Node %s, Node %s%n" +
                        "Philosopher-%s takes forks %s and %s : %s%n", philosopherOne.getNumber() + 1, philosopherTwo.getNumber() + 1,
                one + 1, one + 1, two + 1,
                Philosopher.getFormatDate().format(new Date()));
    }

    boolean isUseLeftOrRight() {
        return useLeftOrRight;
    }
    void setUseLeftOrRight(boolean useLeftOrRight) {
        this.useLeftOrRight = useLeftOrRight;
    }

    public boolean isOne() {
        return one;
    }
    public void setOne(boolean one) {
        this.one = one;
    }

    public boolean isTwo() {
        return two;
    }
    public void setTwo(boolean two) {
        this.two = two;
    }

    boolean isStateEdge() {
        return stateEdge;
    }
    void setStateEdge(boolean stateEdge) {
        this.stateEdge = stateEdge;
    }

    Integer getNumPhilosopher() {
        return numPhilosopher;
    }
    void setNumPhilosopher(Integer numPhilosopher) {
        this.numPhilosopher = numPhilosopher;
    }
}
