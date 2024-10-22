
public class Printf {
    private static final int ViewStringContentRowSize = 30;
    private static final int ViewStringSeparatorSize = 90;
    private Memory memory;
    int[][] arrayMemory;
    int n;
    int m;

    public Printf(Memory memory) {
        this.memory = memory;
        this.n = memory.getAmountOfCells() / ViewStringContentRowSize;
        this.m = memory.getAmountOfCells() / n;
        arrayMemory = new int[n][m];
        printf();
    }

    public void printf() {
        for (int i = 0; i < ViewStringSeparatorSize; i++) {
            System.out.print("_");
        }
        System.out.println("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int index = 0;
                switch (memory.getCells()[i * ViewStringContentRowSize + j].getStatus()) {
                    case None -> index = 0;
                    case Removed -> index = 1;
                    case Selected -> index = 2;
                }
                arrayMemory[i][j] = index;
                System.out.print("|" + arrayMemory[i][j]);
            }
            System.out.println("|\n");
        }
        for (int i = 0; i < ViewStringSeparatorSize; i++) {
            System.out.print("_");
        }
        System.out.println();
    }
}
