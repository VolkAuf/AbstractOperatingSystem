public class File {

    private final String fileName;
    private final int size;
    private final MemoryCell memoryCell;

    public File(String fileName, int size, MemoryCell memoryCell) {
        this.fileName = fileName;
        this.size = size;
        this.memoryCell = memoryCell;
    }

    public int getSize() {
        return size;
    }

    public MemoryCell getCell() {
        return memoryCell;
    }

    public String toString() {
        return fileName;
    }
}
