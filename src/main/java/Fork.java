/*
 * Developed by Daniel Chuev.
 * Last modified 23.12.18 13:41.
 * Copyright (c) 2018. All Right Reserved.
 */

/**
 * @author Daniel Chuev
 */
final class Fork {

    private Boolean state;
    final private Integer number;

    Fork(final int number) {
        state = true;
        this.number = number;
    }

    void clear(){
        setState(true);
    }

    Integer getNumber() {
        return number;
    }

    Boolean isState() {
        return state;
    }
    void setState(boolean state) {
        this.state = state;
    }
}