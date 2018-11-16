import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel Chuev
 */
final class Philosopher {

    final private static SimpleDateFormat formatDate = new SimpleDateFormat("mm:ss:SSS");
    final private Integer number;
    private String name;

    Philosopher(final int numPhilosopher) {
        setName("Philosopher-" + (numPhilosopher+1));
        number = numPhilosopher;
        System.out.println(getName() + " has sit at the table : " + formatDate.format(new Date()));
    }

    void startDining() {
        if ((number+1) < Manager.getForks().size()) {
            dining(number, (number + 1));
        }
        else {
            dining(number, 0);
        }
    }

    private void dining(int oneFork, int twoFork) {
        System.out.println(Manager.getPhilosophers().get(oneFork).getName() + " takes forks " + (oneFork + 1) + " and " + (twoFork + 1) + " : " + Philosopher.getFormatDate().format(new Date()));
        System.out.println(Manager.getPhilosophers().get(oneFork).getName() + " begin dining : " + Philosopher.getFormatDate().format(new Date()));

        Manager.getForks().get(oneFork).setState(false);
        Manager.getForks().get(twoFork).setState(false);

        // dining
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println(Manager.getPhilosophers().get(oneFork).getName() + " end dining : " + Philosopher.getFormatDate().format(new Date()));

        Manager.getForks().get(oneFork).setState(true);
        Manager.getForks().get(twoFork).setState(true);
    }

    private StatePhilosopher state = (oneFork, twoFork) ->
            Manager.getForks().get(oneFork).isState() && Manager.getForks().get(twoFork).isState();

    @Contract(pure = true)
    Integer getNumber() {
        return number;
    }

    @Contract(pure = true)
    private String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }

    @Contract(pure = true)
    Boolean isState(Integer oneFork, Integer twoFork) {return state.isReady(oneFork, twoFork);}

    @Contract(pure = true)
    private static SimpleDateFormat getFormatDate() {
        return formatDate;
    }
}