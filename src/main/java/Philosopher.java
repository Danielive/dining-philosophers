import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel Chuev
 */
final class Philosopher extends Thread {

    final private static SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss:SSS");
    private Boolean stateFork;
    final private Integer number;

    Philosopher(final int numPhilosopher) {
        setName("Philosopher-" + (numPhilosopher+1));
        number = numPhilosopher;
        stateFork = true;
        System.out.println(getName() + " has sit at the table : " + formatDate.format(new Date()));
    }

    public void run() {
        try {
            startDining();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startDining() throws InterruptedException {
        while (true) {
            if ((number+1) < Manager.getForks().size()) {
                dining(number, (number + 1));
            }
            else {
                dining(number, 0);
            }
        }
    }

    private void dining(int oneFork, int twoFork) throws InterruptedException {
        if (Manager.getForks().get(oneFork).isState() && Manager.getForks().get(twoFork).isState() && Manager.getPhilosophers().get(oneFork).isStateFork() && Manager.getPhilosophers().get(twoFork).isStateFork()) {
            System.out.println(Manager.getPhilosophers().get(oneFork).getName() + " takes forks " + (oneFork + 1) + " and " + (twoFork + 1) + " : " + Philosopher.getFormatDate().format(new Date()));
            System.out.println(Manager.getPhilosophers().get(oneFork).getName() + " begin dining : " + Philosopher.getFormatDate().format(new Date()));

            Manager.getForks().get(oneFork).setState(false);
            Manager.getForks().get(twoFork).setState(false);
            Manager.getPhilosophers().get(oneFork).setStateFork(false);
            Manager.getPhilosophers().get(twoFork).setStateFork(false);

            Thread.sleep(500);

            System.out.println(Manager.getPhilosophers().get(oneFork).getName() + " end dining : " + Philosopher.getFormatDate().format(new Date()));

            Manager.getPhilosophers().get(oneFork).setStateFork(true);
            Manager.getPhilosophers().get(twoFork).setStateFork(true);

            Thread.sleep(100);

            Manager.getForks().get(oneFork).setState(true);
            Manager.getForks().get(twoFork).setState(true);
        }
    }

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