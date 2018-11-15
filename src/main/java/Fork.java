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
    boolean isState() {
        return state;
    }
    void setState(boolean state) {
        this.state = state;
    }

    @Contract(pure = true)
    public int getNumber() {
        return number;
    }
}