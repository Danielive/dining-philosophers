import org.jetbrains.annotations.Contract;

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

    @Contract(pure = true)
    Integer getNumber() {
        return number;
    }

    @Contract(pure = true)
    Boolean isState() {
        return state;
    }
    void setState(boolean state) {
        this.state = state;
    }
}