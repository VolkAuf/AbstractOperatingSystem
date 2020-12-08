
public class Printf {
    private Memory memory;
    int[][] arrayMemory;
    int n;
    int m;

    public Printf() {

    }

    public void setMemory(Memory memory) {
        this.memory = memory;
        fillMemory();
    }

    public void fillMemory() {
        if (memory != null) {
            this.n = memory.getAmountOfCells() / 30;
            this.m = memory.getAmountOfCells() / n;
            arrayMemory = new int[n][m];
            for (int i = 0; i < 30; i++) {
                System.out.print("_");
            }
            System.out.println("\n");
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int index = 0;
                    switch (memory.getCells()[i * 30 + j].getStatus()) {
                        case 0 -> index = 0;
                        case 1 -> index = 1;
                        case 2 -> index = 2;
                    }
                    System.out.print("|" + index);
                    arrayMemory[i][j] = index;
                }
                System.out.println("|\n");
            }
            for (int i = 0; i < 80; i++) {
                System.out.print("_");
            }
            System.out.println();
        }
    }

    public void printf() {
        for (int i = 0; i < 80; i++) {
            System.out.print("_");
        }
        System.out.println("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int index = 0;
                switch (memory.getCells()[i * 30 + j].getStatus()) {
                    case 0 -> index = 0;
                    case 1 -> index = 1;
                    case 2 -> index = 2;
                }
                arrayMemory[i][j] = index;
                System.out.print("|" + arrayMemory[i][j]);
            }
            System.out.println("|\n");
        }
        for (int i = 0; i < 80; i++) {
            System.out.print("_");
        }
        System.out.println();
    }
}
