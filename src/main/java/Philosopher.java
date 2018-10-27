import com.sun.corba.se.impl.orbutil.concurrent.Mutex;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel Chuev
 */
public class Philosopher extends Thread {

    private static SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss");

    volatile private boolean stateFork;
    volatile private boolean dined;
    private int number;

    volatile static private Manager manager = new Manager();
    volatile private Mutex mutex = new Mutex();

    Philosopher(int numPhilosopher) {
        setName("Philosopher-" + (numPhilosopher+1));
        number = numPhilosopher;
        dined = false;
        stateFork = true;
        System.out.println(getName() + " has sit at the table : " + formatDate.format(new Date()));
    }

    public void run() {
        while (!isDined()) {
            if ((this.number+1) < manager.getForks().size()) {
                synchronized (this) {
                    doIt(this.number, (this.number + 1));
                }
            }
            else {
                synchronized (this) {
                    doIt(this.number, 0);
                }
            }
        }
    }

    private void doIt(int oneFork, int twoFork) {
        if (manager.getForks().get(oneFork).isState() && manager.getForks().get(twoFork).isState() && manager.getPhilosophers().get(oneFork).isStateFork() && manager.getPhilosophers().get(twoFork).isStateFork()) {
            try {
                mutex.acquire();
                dining(oneFork, twoFork);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.release();
            }
        }
    }

    private void dining(int oneFork, int twoFork) throws InterruptedException {
        System.out.println(getName() + " takes forks " + (oneFork+1)+ " and " + (twoFork+1) + " : " + formatDate.format(new Date()));
        System.out.println(getName() + " begin dining : " + formatDate.format(new Date()));

        manager.getForks().get(oneFork).setState(false);
        manager.getForks().get(twoFork).setState(false);
        manager.getPhilosophers().get(oneFork).setStateFork(false);
        manager.getPhilosophers().get(twoFork).setStateFork(false);

        Thread.sleep(2000);

        System.out.println(getName() + " end dining : "  + formatDate.format(new Date()));

        manager.getForks().get(oneFork).setState(true);
        manager.getForks().get(twoFork).setState(true);
        manager.getPhilosophers().get(oneFork).setStateFork(true);
        manager.getPhilosophers().get(twoFork).setStateFork(true);

        manager.getPhilosophers().get(this.number).setDined(true);
    }

    private synchronized boolean isDined() {
        return dined;
    }
    private synchronized void setDined(boolean dined) {
        this.dined = dined;
    }

    private synchronized boolean isStateFork() {
        return stateFork;
    }
    private synchronized void setStateFork(boolean stateFork) {
        this.stateFork = stateFork;
    }
}