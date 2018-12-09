import org.jetbrains.annotations.Contract;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel Chuev
 */
class Philosopher {

    final private static SimpleDateFormat formatDate = new SimpleDateFormat("ss:SSS");
    final private Integer number;
    private String name;
    private Boolean state;
    private Boolean dined;
    private Boolean take;

    Philosopher(final int numPhilosopher) {
        setName("Philosopher-" + (numPhilosopher+1));
        number = numPhilosopher;
        state = false;
        dined = false;
        take = false;
        System.out.println(getName() + " has sit at the table : " + formatDate.format(new Date()));
    }

    void dining() {
        if (!getDined() && getState() && getTake()) {
            System.out.println(getName() + " begin dining : " + Philosopher.getFormatDate().format(new Date()));

            // dining
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println(getName() + " end dining : " + Philosopher.getFormatDate().format(new Date()));
        }
    }

    void clear() {
        setState(false);
        setDined(false);
        setTake(false);
    }

    @Contract(pure = true)
    Boolean getTake() {
        return take;
    }
    void setTake(Boolean take) {
        this.take = take;
    }

    @Contract(pure = true)
    Boolean getDined() {
        return dined;
    }
    void setDined(Boolean dined) {
        this.dined = dined;
    }

    @Contract(pure = true)
    Boolean getState() {
        return state;
    }
    void setState(Boolean state) {
        this.state = state;
    }

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
    static SimpleDateFormat getFormatDate() {
        return formatDate;
    }
}