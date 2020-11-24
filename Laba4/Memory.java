public class Memory {

    private final int sizeCell;
    private final int amountOfCells;
    private final MemoryCell[] MemoryCells;

    public Memory(int sizeMemory, int sizeCell) {
        this.sizeCell = sizeCell;
        amountOfCells = sizeMemory / sizeCell;
        MemoryCells = new MemoryCell[amountOfCells];
        fillMemory();
    }

    public void fillMemory() {
        for (int i = 0; i < amountOfCells; i++) {
            MemoryCells[i] = new MemoryCell(0, i);
        }
    }

    public int getSizeCell() {
        return sizeCell;
    }

    public int getAmountOfCells() {
        return amountOfCells;
    }

    public MemoryCell[] getCells() {
        return MemoryCells;
    }
}
