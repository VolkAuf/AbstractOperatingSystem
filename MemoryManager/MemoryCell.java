public class MemoryCell {

    public enum MemoryCellStatus {
        None,
        Removed,
        Selected,
    }

    private final int index;
    private MemoryCellStatus status;

    public MemoryCell(MemoryCellStatus status, int index) {
        this.status = status;
        this.index = index;
    }

    public void setStatus(MemoryCellStatus status) {
        this.status = status;
    }

    public MemoryCellStatus getStatus() {
        return status;
    }

    public int getStatusInt(){
        return switch (status) {
            case None -> 0;
            case Removed -> 1;
            case Selected -> 2;
        };
    }

    public int getIndex() {
        return index;
    }
}
