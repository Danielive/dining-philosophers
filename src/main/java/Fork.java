/**
 * @author Daniel Chuev
 */
class Fork {

    private boolean state;
    private int number;

    Fork(int number) {
        state = true;
        this.number = number;
    }

    boolean isState() {
        return state;
    }
    void setState(boolean state) {
        this.state = state;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
