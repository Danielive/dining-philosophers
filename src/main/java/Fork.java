/*
 * Developed by Daniel Chuev on 22.12.18 22:08.
 * Last modified 22.12.18 12:02.
 * Copyright (c) 2018. All right reserved.
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