import java.util.ArrayList;

public class File {

    private final String fileName;
    private final int size;
    private final boolean isFolder;
    private File parent;
    private final ArrayList<File> children = new ArrayList();
    private final INode iNode;


    public File(String fileName, int size, boolean isFolder, MemoryCell[] memoryCell) {
        this.fileName = fileName;
        this.size = size;
        this.isFolder = isFolder;
        iNode = new INode();
        iNode.setClusters(memoryCell);
    }

    public File(String fileName, int size, boolean isFolder, MemoryCell memoryCell) {
        this.fileName = fileName;
        this.size = size;
        this.isFolder = isFolder;
        iNode = new INode();
        iNode.setClusters(memoryCell);
    }

    public INode getINode() {
        return iNode;
    }

    public void addChild(File child) {
        children.add(child);
    }

    public ArrayList<File> getChildren() {
        return children;
    }

    public File getParent() {
        return parent;
    }

    public void setParent(File parent) {
        this.parent = parent;
    }

    public int getSize() {
        return size;
    }

    public boolean isFolder() {
        return isFolder;
    }

    public void deleteChild(File file) {
        children.remove(file);
    }

    public String getFileName() {
        return fileName;
    }

    public String toString() {
        return fileName;
    }
}
