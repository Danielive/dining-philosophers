import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel Chuev
 */
class Philosopher extends Thread {
    private static SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss:S");
    volatile private boolean stateFork;
    volatile private boolean dined;
    private int number;
    private final Mutex mutex = new Mutex();

    Philosopher(int numPhilosopher) {
        setName("Philosopher-" + (numPhilosopher+1));
        number = numPhilosopher;
        dined = false;
        stateFork = true;
        System.out.println(getName() + " has sit at the table : " + formatDate.format(new Date()));
    }

    public void run() {
        System.out.println("START" + formatDate.format(new Date()));

        try {
            Manager.getPhilosophers().get(number).startDining();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startDining() throws InterruptedException {
        while (!Manager.getPhilosophers().get(number).isDined()) {
            if ((number+1) < Manager.getForks().size()) {
                hungry(number, (number + 1));
            }
            else {
                hungry(number, 0);
            }
        }
    }

    private synchronized void hungry(int oneFork, int twoFork) throws InterruptedException {
        mutex.acquire();

        if (Manager.getForks().get(oneFork).isState() && Manager.getForks().get(twoFork).isState() && Manager.getPhilosophers().get(oneFork).isStateFork() && Manager.getPhilosophers().get(twoFork).isStateFork()) {
            dining(oneFork, twoFork);
        }

        mutex.release();
    }

    private void dining(int oneFork, int twoFork) throws InterruptedException {
        System.out.println(Manager.getPhilosophers().get(oneFork).getName() + " takes forks " + (oneFork+1)+ " and " + (twoFork+1) + " : " + Philosopher.getFormatDate().format(new Date()));
        System.out.println(Manager.getPhilosophers().get(oneFork).getName() + " begin dining : " + Philosopher.getFormatDate().format(new Date()));

        Manager.getForks().get(oneFork).setState(false);
        Manager.getForks().get(twoFork).setState(false);
        Manager.getPhilosophers().get(oneFork).setStateFork(false);
        Manager.getPhilosophers().get(twoFork).setStateFork(false);

        Thread.sleep(2000);

        System.out.println(Manager.getPhilosophers().get(oneFork).getName() + " end dining : "  + Philosopher.getFormatDate().format(new Date()));

        Manager.getForks().get(oneFork).setState(true);
        Manager.getForks().get(twoFork).setState(true);
        Manager.getPhilosophers().get(oneFork).setStateFork(true);
        Manager.getPhilosophers().get(twoFork).setStateFork(true);

        Manager.getPhilosophers().get(oneFork).setDined(true);
    }

    @Contract(pure = true)
    private synchronized boolean isDined() {
        return dined;
    }
    private synchronized void setDined(boolean dined) {
        this.dined = dined;
    }

    @Contract(pure = true)
    private synchronized boolean isStateFork() {
        return stateFork;
    }
    private synchronized void setStateFork(boolean stateFork) {
        this.stateFork = stateFork;
    }

    @Contract(pure = true)
    private static SimpleDateFormat getFormatDate() {
        return formatDate;
    }
}