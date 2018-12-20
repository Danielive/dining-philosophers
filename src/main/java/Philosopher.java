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

    Boolean getTake() {
        return take;
    }
    void setTake(Boolean take) {
        this.take = take;
    }

    Boolean getDined() {
        return dined;
    }
    void setDined(Boolean dined) {
        this.dined = dined;
    }

    Boolean getState() {
        return state;
    }
    void setState(Boolean state) {
        this.state = state;
    }

    Integer getNumber() {
        return number;
    }

    private String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }

    static SimpleDateFormat getFormatDate() {
        return formatDate;
    }
}