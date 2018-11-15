import org.jetbrains.annotations.Contract;

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

    @Contract(pure = true)
    Boolean isState() {
        return state;
    }
    void setState(boolean state) {
        this.state = state;
    }

    @Contract(pure = true)
    public Integer getNumber() {
        return number;
    }
}