public class MemoryCell {

    private int status;
    private final int index;

    public MemoryCell(int status, int index) {
        this.status = status;
        this.index = index;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public int getIndex() {
        return index;
    }
}
