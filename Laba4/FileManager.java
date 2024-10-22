import java.util.ArrayList;
import java.util.Random;

public class FileManager {

    private final File root;
    private final Memory memory;
    private final ListOfUnoccupied listOfUnoccupied;
    private final Random random = new Random();
    private final ArrayList<File> listCopiedFile = new ArrayList<>();

    public FileManager(Memory memory) {
        this.memory = memory;
        listOfUnoccupied = new ListOfUnoccupied(memory);
        root = new File("Root", 1, true, selectMemoryForRoot());
    }

    public MemoryCell[] selectMemory(int size) {
        int quantity = Math.max((size / memory.getSizeCell()), 1);
        MemoryCell[] cells = new MemoryCell[quantity];
        if (quantity > listOfUnoccupied.getSize()) {
            return null;
        }
        for (int i = 0; i < quantity; i++) {
            int index = random.nextInt(memory.getAmountOfCells());
            while (!listOfUnoccupied.getBool(index)) {
                index = random.nextInt(memory.getAmountOfCells());
            }
            MemoryCell temp = memory.getCells()[index];
            temp.setStatus(MemoryCell.MemoryCellStatus.Selected);
            listOfUnoccupied.deleteUselessCluster(index);
            cells[i] = temp;
        }
        return cells;
    }

    public MemoryCell selectMemoryForRoot() {
        MemoryCell temp = memory.getCells()[0];
        temp.setStatus(MemoryCell.MemoryCellStatus.Selected);
        listOfUnoccupied.deleteUselessCluster(0);
        return temp;
    }

    public File addFile(String fileName, int size) {
        MemoryCell[] memoryCells = selectMemory(size);
        if (memoryCells == null) {
            return null;
        }
        return new File(fileName, size, false, memoryCells);
    }

    public void addFile(File replica, File parent) {
        MemoryCell[] memoryCell = selectMemory(replica.getSize());
        if (memoryCell != null) {
            File copy = new File(replica.getFileName() + "-copy", replica.getSize(), replica.isFolder(), memoryCell);
            if (replica.isFolder()) {
                for (File child : replica.getChildren()) {
                    addFile(child, copy);
                }
            }
            parent.addChild(copy);
            copy.setParent(parent);
            listCopiedFile.add(copy);
        }
    }

    public ArrayList getListCopiedFile() {
        return listCopiedFile;
    }

    public void clearListCopiedFile() {
        listCopiedFile.clear();
    }

    public File addFolder(String fileName) {
        MemoryCell[] memoryCell = selectMemory(1);
        if (memoryCell == null) {
            return null;
        }
        return new File(fileName, 1, true, memoryCell);
    }

    public void deleteFile(File file) {
        INode inode = file.getINode();
        INode prev = inode;
        for (; inode != null; inode = inode.getNext()) {
            prev.setNext(null);
            for (int i = 0; i < inode.getNodeCapacity(); i++) {
                MemoryCell cell = inode.getCluster(i);
                listOfUnoccupied.addCluster(cell.getIndex());
                cell.setStatus(MemoryCell.MemoryCellStatus.None);
            }
            prev = inode;
        }
    }

    public void selectFile(INode inode) {
        for (INode in = inode; in != null; in = in.getNext()) {
            for (int i = 0; i < in.getNodeCapacity(); i++) {
                MemoryCell cell = in.getCluster(i);
                cell.setStatus(MemoryCell.MemoryCellStatus.Selected);
            }
        }
    }

    public void removeSelecting() {
        for (int i = 0; i < memory.getCells().length; i++) {
            if (memory.getCells()[i].getStatus() == MemoryCell.MemoryCellStatus.Selected) {
                memory.getCells()[i].setStatus(MemoryCell.MemoryCellStatus.Removed);
            }
        }
    }

    public File getRoot() {
        return root;
    }
}
