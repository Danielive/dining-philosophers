import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel Chuev
 */
final class Philosopher {

    final private static SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss:SSS");
    final private Integer number;
    private Boolean stateFork;
    private String name;

    Philosopher(final int numPhilosopher) {
        setName("Philosopher-" + (numPhilosopher+1));
        number = numPhilosopher;
        stateFork = true;
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

        Manager.getPhilosophers().get(oneFork).setStateFork(false);
        Manager.getPhilosophers().get(twoFork).setStateFork(false);
        Manager.getForks().get(oneFork).setState(false);
        Manager.getForks().get(twoFork).setState(false);

        try {
            Thread.sleep(50);
        }
        catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        System.out.println(Manager.getPhilosophers().get(oneFork).getName() + " end dining : " + Philosopher.getFormatDate().format(new Date()));

        Manager.getPhilosophers().get(oneFork).setStateFork(true);
        Manager.getPhilosophers().get(twoFork).setStateFork(true);
        Manager.getForks().get(oneFork).setState(true);
        Manager.getForks().get(twoFork).setState(true);
    }

    private StatePhilosopher state = (oneFork, twoFork) ->
            Manager.getForks().get(oneFork).isState() &&
                    Manager.getForks().get(twoFork).isState() &&
                    Manager.getPhilosophers().get(oneFork).isStateFork() &&
                    Manager.getPhilosophers().get(twoFork).isStateFork();

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
    private Boolean isStateFork() {
        return stateFork;
    }
    private void setStateFork(final boolean stateFork) {
        this.stateFork = stateFork;
    }

    @Contract(pure = true)
    private static SimpleDateFormat getFormatDate() {
        return formatDate;
    }
}