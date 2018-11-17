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
    private Boolean state;
    private Boolean dined;

    Philosopher(final int numPhilosopher) {
        setName("Philosopher-" + (numPhilosopher+1));
        number = numPhilosopher;
        state = false;
        System.out.println(getName() + " has sit at the table : " + formatDate.format(new Date()));
        dined = false;
    }

//    void startDining() {
//        if (((number+1) < Manager.getForks().size()) && !Manager.getPhilosophers().get(number).getState() && !Manager.getPhilosophers().get(number+1).getState()) {
//            dining(number, (number + 1));
//        }
//        else if (!Manager.getPhilosophers().get(number).getState() && !Manager.getPhilosophers().get(0).getState()){
//            dining(number, 0);
//        }
//    }


    void dining() {
        if (!getDined() && getState()) {
            //Manager.getPhilosophers().get(number).setState(true);

            if ((number + 1) < Manager.getForks().size())
                System.out.println(getName() + " takes forks " + (number + 1) + " and " + (number + 2) + " : " + Philosopher.getFormatDate().format(new Date()));
            else
                System.out.println(getName() + " takes forks " + (number + 1) + " and " + (1) + " : " + Philosopher.getFormatDate().format(new Date()));

            System.out.println(getName() + " begin dining : " + Philosopher.getFormatDate().format(new Date()));

//        Manager.getForks().get(oneFork).setState(false);
//        Manager.getForks().get(twoFork).setState(false);

            // dining
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

            System.out.println(getName() + " end dining : " + Philosopher.getFormatDate().format(new Date()));

            //Manager.getForks().get(number).setState(true);
            //Manager.getForks().get(number+1).setState(true);

            //Manager.getPhilosophers().get(number).setState(false);
            //Manager.getPhilosophers().get(number+1).setState(false);

            setDined(true);
        }
    }
//
//    private StatePhilosopher state = (oneFork, twoFork) -> {
//        if (Manager.getForks().get(oneFork).isState() && Manager.getForks().get(twoFork).isState()) {
//            Manager.getForks().get(oneFork).setState(false);
//            Manager.getForks().get(twoFork).setState(false);
//            return true;
//        }
//        return false;
//    };


    @Contract(pure = true)
    Boolean getDined() {
        return dined;
    }
    public void setDined(Boolean dined) {
        this.dined = dined;
    }

    @Contract(pure = true)
    public Boolean getState() {
        return state;
    }
    public void setState(Boolean state) {
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

//    @Contract(pure = true)
//    Boolean isState(Integer oneFork, Integer twoFork) {return state.isReady(oneFork, twoFork);}

    @Contract(pure = true)
    private static SimpleDateFormat getFormatDate() {
        return formatDate;
    }
}