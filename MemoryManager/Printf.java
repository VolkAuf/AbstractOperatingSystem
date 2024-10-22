
public class Printf {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

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
                int index = memory.getCells()[i * ViewStringContentRowSize + j].getStatusInt();
                arrayMemory[i][j] = index;
                System.out.print("|" + getBgByStatusInt(index) + arrayMemory[i][j] + ANSI_RESET);
            }
            System.out.println("|\n");
        }
        for (int i = 0; i < ViewStringSeparatorSize; i++) {
            System.out.print("_");
        }
        System.out.println();
    }

    private String getBgByStatusInt(int index){
        return switch (index){
            case 0 -> ANSI_WHITE_BACKGROUND;
            case 1 -> ANSI_BLACK_BACKGROUND;
            case 2 -> ANSI_GREEN_BACKGROUND;
            default -> ANSI_RED_BACKGROUND;
        };
    }
}
