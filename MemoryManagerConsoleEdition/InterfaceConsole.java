import java.util.ArrayList;
import java.util.Scanner;

public class InterfaceConsole {

    private static final String INPUT_TEXT = """
            Выберите операцию:
            Create file - 1
            Create folder - 2
            Copy - 3
            Paste - 4
            Move - 5
            Delete - 6
            Select - 7
            Exit - 0
            """;

    int sizeMemory = 1024;
    int sizeSector = 8;
    Memory memory;
    Printf printf;
    private FileManager fileManager;
    private ArrayList<File> arrFile;
    private File fileRoot;
    private File fileSelected;
    private File fileBuffer;

    public InterfaceConsole() {
        Start();
    }

    public void Start() {

        Scanner in = new Scanner(System.in);

        System.out.println("введите размер диска, изначально 1024\n");
        int sizeMemoryIn = in.nextInt();
        System.out.println();
        if (sizeMemoryIn > 0) {
            sizeMemory = sizeMemoryIn;
        }

        System.out.println("введите размер сектора, изначально 8\n");
        int sizeSectorIn = in.nextInt();
        System.out.println();
        if (sizeSectorIn > 0) {
            sizeSector = sizeSectorIn;
        }

        memory = new Memory(sizeMemory, sizeSector);
        fileManager = new FileManager(memory);
        printf = new Printf(memory);

        fileRoot = fileManager.getRoot();
        arrFile = new ArrayList<>();
        arrFile.add(fileRoot);


        System.out.println(INPUT_TEXT);

        int choice = in.nextInt();
        System.out.println();
        while (choice != 0) {
            switch (choice) {
                case 1 -> createFile();
                case 2 -> createFolder();
                case 3 -> copy();
                case 4 -> paste();
                case 5 -> move();
                case 6 -> delete();
                case 7 -> selectFile();
                default -> {
                    return;
                }
            }
            System.out.println(INPUT_TEXT);
            choice = in.nextInt();
            System.out.println();
        }
    }

    private void printfFileList() {
        System.out.println("\nСписок для получения по индексу\n");
        for (int i = 0; i < arrFile.size(); i++) {
            System.out.println(i + " " + arrFile.get(i).getFileName());
        }
        System.out.println("");
    }

    private void selectFile() {
        try {
            fileManager.removeSelecting();

            Scanner in = new Scanner(System.in);
            System.out.println("Выберите файл\n");
            printfFileList();
            int i = in.nextInt();
            System.out.println();
            if (i >= 0) {
                fileSelected = arrFile.get(i);
                select(fileSelected);
                printf.printf();
            }
        } catch (Exception ex) {
            System.out.println("\nНет такого файла!!!\n");
        }
    }

    private void createFile() {
        try {
            selectFile();
            if (fileSelected != null) {
                if (fileSelected.isFolder()) {
                    Scanner in = new Scanner(System.in);
                    System.out.println("Введите имя файла\n");
                    String name = in.nextLine();
                    System.out.println();

                    System.out.println("Введите размер файла\n");
                    int size = in.nextInt();
                    System.out.println();

                    File file = fileManager.addFile(name, size);
                    File folder = fileSelected;

                    folder.addChild(file);
                    file.setParent(folder);
                    arrFile.add(file);
                }
            }
        } catch (Exception ex) {
            System.out.println("\nРазмер превысил допустимый\n");
        }
        printfFileList();
    }

    private void createFolder() {
        selectFile();
        if (fileSelected != null) {
            if (fileSelected.isFolder()) {
                Scanner in = new Scanner(System.in);
                System.out.println("\nВведите имя папки\n");
                String name = in.nextLine();
                System.out.println();

                File file = fileManager.addFolder(name);
                File folder = fileSelected;

                folder.addChild(file);
                file.setParent(folder);
                arrFile.add(file);
            }
        }
        printfFileList();
    }

    private void copy() {
        selectFile();
        fileManager.clearListCopiedFile();
        if (fileSelected != null) {
            fileBuffer = fileSelected;
        }
    }

    private void paste() {
        if (fileBuffer != null) {
            selectFile();
            if (fileSelected != null) {
                if (fileSelected.isFolder()) {
                    fileManager.clearListCopiedFile();
                    fileManager.addFile(fileBuffer, fileSelected);
                    arrFile.addAll(fileManager.getListCopiedFile());
                }
            }
        }
        printfFileList();
    }

    private void move() {
        copy();
        paste();
        deleteFile(fileBuffer);
        File folder = fileBuffer.getParent();
        folder.deleteChild(fileBuffer);
        printfFileList();
    }

    private void delete() {
        selectFile();
        if (fileSelected != null && fileSelected != fileRoot) {
            deleteFile(fileSelected);
            File folder = fileSelected.getParent();
            folder.deleteChild(fileSelected);
        }
    }

    private void deleteFile(File file) {
        if (file.isFolder()) {
            for (File child : file.getChildren()) {
                deleteFile(child);
            }
        }
        fileManager.deleteFile(file);
        arrFile.remove(file);
    }

    private void select(File file) {
        INode inode = file.getINode();
        if (inode != null) {
            fileManager.selectFile(inode);
        }
        if (file.isFolder()) {
            for (File child : file.getChildren()) {
                select(child);
            }
        }
    }
}
