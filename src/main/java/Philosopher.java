import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel Chuev
 */
public class Philosopher extends Thread {

    private static SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss");

    private boolean dined;
    private int number;

    volatile private Manager manager = new Manager();

    Philosopher(int numPhilosopher) {
        setName("Philosopher-" + (numPhilosopher+1));
        number = numPhilosopher;
        dined = false;
        System.out.println(getName() + " has sit at the table : " + formatDate.format(new Date()));
    }

    public void run() {
        while (!isDined()) {
            if ((this.number+1) < manager.getForks().size()) {
                if (manager.getForks().get(this.number).isState() && manager.getForks().get(this.number+1).isState()) {
                    try {
                        dining(this.number, (this.number+1));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            else {
                if (manager.getForks().get(this.number).isState() && manager.getForks().get(1).isState()) {
                    try {
                        dining(this.number, 0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void dining(int oneFork, int twoFork) throws InterruptedException {
        System.out.println(getName() + " takes forks " + (oneFork+1)+ " and " + (twoFork+1) + " : " + formatDate.format(new Date()));

        System.out.println(getName() + " begin dining : " + formatDate.format(new Date()));

        manager.getForks().get(oneFork).setState(false);
        manager.getForks().get(twoFork).setState(false);

        Thread.sleep(2000);

        System.out.println(getName() + " end dining : "  + formatDate.format(new Date()));

        manager.getForks().get(oneFork).setState(true);
        manager.getForks().get(twoFork).setState(true);

        manager.getPhilosophers().get(this.number).setDined(true);
    }

    private boolean isDined() {
        return dined;
    }
    private void setDined(boolean dined) {
        this.dined = dined;
    }
}
