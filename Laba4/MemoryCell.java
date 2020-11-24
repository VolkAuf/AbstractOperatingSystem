public class MemoryCell {

    private MemoryCell next;
    private int status;
    private int index;

    public MemoryCell(int status, int index) {
        this.status = status;
        this.index = index;
    }

    public MemoryCell getNextCell() {
        return next;
    }

    public void setNextCell(MemoryCell next) {
        this.next = next;
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
