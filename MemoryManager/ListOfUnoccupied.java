import java.util.ArrayList;

public class ListOfUnoccupied {

    private final ArrayList<Boolean> clustersBool;
    Memory memory;
    private int size;

    public ListOfUnoccupied(Memory memory) {
        this.memory = memory;
        this.clustersBool = new ArrayList<>();
        this.size = memory.getCells().length;
        for (int i = 0; i < size; i++) {
            if (memory.getCells()[i].getStatus() == MemoryCell.MemoryCellStatus.None) {
                clustersBool.add(i, true);
            }
        }
    }

    public void deleteUselessCluster(int index) {
        clustersBool.set(index, false);
        size--;
    }

    public void addCluster(int index) {
        clustersBool.set(index, true);
        size++;
    }

    public boolean getBool(int index) {
        return clustersBool.get(index);
    }

    public int getSize() {
        return size;
    }

    public int getIndex(int index) {
        int i = index;
        while (!clustersBool.get(i)) {
            i++;
            if (i == memory.getCells().length) {
                i = 1;
            }
            if (i == index) {
                return -1;
            }
        }
        return i;
    }
}
