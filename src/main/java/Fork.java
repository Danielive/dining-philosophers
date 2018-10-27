/**
 * @author Daniel Chuev
 */
class Fork {

    volatile private boolean state;
    private int number;

    Fork(int number) {
        state = true;
        this.number = number;
    }

    synchronized boolean isState() {
        return state;
    }
    synchronized void setState(boolean state) {
        this.state = state;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
}
