import java.util.Random;

public class FileManager {

    private final File root;
    private final Memory memory;
    private final ListOfUnoccupied listOfUnoccupied;
    private final Random random = new Random();

    public FileManager(Memory memory) {
        this.memory = memory;
        listOfUnoccupied = new ListOfUnoccupied(memory);
        root = new File("Root", 1, selectMemoryForRoot());
    }

    public MemoryCell selectMemory(int size) {
        int quantity = (size / memory.getSizeCell()) + 1;
        if (quantity > listOfUnoccupied.getSize()) {
            return null;
        }
        MemoryCell firstMemoryCell = null;
        MemoryCell prevMemoryCell = null;
        for (int i = 0; i < quantity; i++) {
            int index = random.nextInt(memory.getAmountOfCells());
            while (!listOfUnoccupied.getBool(index)) {
                index = random.nextInt(memory.getAmountOfCells());
            }
            MemoryCell temp = memory.getCells()[index];
            temp.setStatus(1);
            /*
            System.out.println();
            temp.getIndex();
            System.out.println(index);
            System.out.println();
            System.out.println(memory.getAmountOfCells());
            System.out.println();
            */
            listOfUnoccupied.deleteUselessCluster(index);
            if (prevMemoryCell != null) {
                prevMemoryCell.setNextCell(temp);
            } else {
                firstMemoryCell = temp;
            }
            prevMemoryCell = temp;
        }
        return firstMemoryCell;
    }

    public MemoryCell selectMemoryForRoot() {
        MemoryCell temp = memory.getCells()[0];
        temp.setStatus(1);
        listOfUnoccupied.deleteUselessCluster(0);
        return temp;
    }

    public File addFile(String fileName, int size) {
        MemoryCell memoryCell = selectMemory(size);
        if (memoryCell == null) {
            return null;
        }
        return new File(fileName, size, memoryCell);
    }

    public File addFolder(String fileName) {
        MemoryCell memoryCell = selectMemory(1);
        if (memoryCell == null) {
            return null;
        }
        return new File(fileName, 1, memoryCell);
    }

    public void deleteFile(File file) {
        MemoryCell prevMemoryCell = null;
        MemoryCell memoryCell = file.getCell();
        while (memoryCell != null) {
            memoryCell.setStatus(0);
            listOfUnoccupied.addCluster(memoryCell.getIndex());
            if (prevMemoryCell != null) {
                prevMemoryCell.setNextCell(null);
            }
            prevMemoryCell = memoryCell;
            memoryCell = memoryCell.getNextCell();
        }
    }

    public void selectFile(MemoryCell memoryCell) {
        while (memoryCell != null) {
            memoryCell.setStatus(2);
            memoryCell = memoryCell.getNextCell();
        }
    }

    public void removeSelecting() {
        for (int i = 0; i < memory.getCells().length; i++) {
            if (memory.getCells()[i].getStatus() == 2) {
                memory.getCells()[i].setStatus(1);
            }
        }
    }

    public File getRoot() {
        return root;
    }
}
