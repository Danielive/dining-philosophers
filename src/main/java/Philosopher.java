/*
 * Developed by Daniel Chuev.
 * Last modified 23.12.18 13:41.
 * Copyright (c) 2018. All Right Reserved.
 */

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Daniel Chuev
 */
final class Philosopher {

    final private static SimpleDateFormat formatDate = new SimpleDateFormat("ss:SSS");
    final private Integer number;
    private String name;
    private Boolean state;
    private Boolean dined;
    private Boolean take;

    Philosopher(final int numPhilosopher) {
        setName("Philosopher " + (numPhilosopher+1));
        number = numPhilosopher;
        state = false;
        dined = false;
        take = false;
        System.out.println(getName() + " has sit at the table : " + formatDate.format(new Date()));
    }

    void dining() {
        if (getState() && getTake()) {
            // dining & creation block

        }
    }

    void clear() {
        setState(false);
        setTake(false);
    }

    Boolean getTake() {
        return take;
    }
    void setTake(Boolean take) {
        this.take = take;
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

    String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }

    static SimpleDateFormat getFormatDate() {
        return formatDate;
    }

    Boolean getDined() {
        return dined;
    }
    public void setDined(Boolean dined) {
        this.dined = dined;
    }
}