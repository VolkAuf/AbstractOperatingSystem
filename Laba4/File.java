public class File {

    private final String fileName;
    private final int size;
    private final MemoryCell memoryCell;
    private final boolean isFolder;
    private File next;
    private File prev;
    private File left;
    private File right;

    public File(String fileName, int size, boolean isFolder, MemoryCell memoryCell) {
        this.fileName = fileName;
        this.size = size;
        this.isFolder = isFolder;
        this.memoryCell = memoryCell;
    }

    public File getLeft() {
        return left;
    }

    public File getRight() {
        return right;
    }

    public void setRight(File right) {
        this.right = right;
    }

    public void setLeft(File left) {
        this.left = left;
    }

    public File getNext() {
        return next;
    }

    public void setNext(File next) {
        this.next = next;
    }

    public File getPrev() {
        return prev;
    }

    public void setPrev(File prev) {
        this.prev = prev;
    }

    public int getSize() {
        return size;
    }

    public MemoryCell getCell() {
        return memoryCell;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public String getFileName() {
        return fileName;
    }

    public String toString() {
        return fileName;
    }
}
