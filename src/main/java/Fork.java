/**
 * @author Daniel Chuev
 */
class Fork {

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