import java.util.ArrayList;
import java.util.Scanner;

public class InterfaceConsole {

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

        printf = new Printf();
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
        printf.setMemory(memory);

        fileRoot = fileManager.getRoot();
        arrFile = new ArrayList<>();
        arrFile.add(fileRoot);


        System.out.println("\nВыберите операцию" +
                "\nCreate file - 1" +
                "\nCreate folder - 2" +
                "\nCopy - 3" +
                "\nPaste - 4" +
                "\nMove - 5" +
                "\nDelete - 6" +
                "\nSelect - 7" +
                "\nExit - 0\n");

        int chose = in.nextInt();
        System.out.println();
        while (chose != 0) {
            switch (chose) {
                case 1:
                    createFile();
                    break;
                case 2:
                    createFolder();
                    break;
                case 3:
                    copy();
                    break;
                case 4:
                    paste();
                    break;
                case 5:
                    move();
                    break;
                case 6:
                    delete();
                    break;
                case 7:
                    selectFile();
                    break;
                default:
                    break;
            }
            System.out.println("\nВыберите операцию" +
                    "\nCreate file - 1" +
                    "\nCreate folder - 2" +
                    "\nCopy - 3" +
                    "\nPaste - 4" +
                    "\nMove - 5" +
                    "\nDelete - 6" +
                    "\nSelect - 7" +
                    "\nExit - 0\n");
            chose = in.nextInt();
            System.out.println();
        }
    }

    private void printfFileList() {
        System.out.println("\nСписок для получения по индексу\n");
        for (int i = 0; i < arrFile.size(); i++) {
            System.out.println(i + " " + arrFile.get(i).getFileName());
        }
        System.out.println();
    }

    private void selectFile() {
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
    }

    private void createFile() {
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
                File f = folder.getNext();
                if (f != null) {
                    if (file != null) {
                        for (; f.getLeft() != null; f = f.getLeft()) {

                        }
                        f.setLeft(file);
                        file.setRight(f);
                        arrFile.add(file);
                    }
                } else {
                    folder.setNext(file);
                    file.setPrev(folder);
                }
            }
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
                File f = folder.getNext();
                if (f != null) {
                    if (file != null) {
                        for (; f.getLeft() != null; f = f.getLeft()) {
                        }
                        f.setLeft(file);
                        file.setRight(f);
                        arrFile.add(file);
                    }
                } else {
                    folder.setNext(file);
                    file.setPrev(folder);
                }
            }
        }
        printfFileList();
    }

    private void copy() {
        selectFile();
        if (fileSelected != null) {
            File f = fileSelected;
            File temp = f;
            fileBuffer = new File(f.getFileName(), f.getSize(), f.isFolder(), f.getCell());
            if (f.getNext() != null) {
                for (f = f.getNext(); f.getNext() != null; f = f.getNext()) {
                    temp = new File(f.getFileName(), f.getSize(), f.isFolder(), f.getCell());
                    temp.setPrev(fileBuffer);
                    fileBuffer.setNext(temp);
                    fileBuffer = temp;
                }
            }
        }
    }

    private void paste() {
        if (fileBuffer != null) {
            selectFile();
            if (fileSelected != null) {
                if (fileSelected.isFolder()) {
                    File f = fileSelected;
                    for (; f.getNext() != null; f = f.getNext()) {
                    }
                    fileBuffer.setPrev(f);
                    f.setNext(fileBuffer);
                }
            }
        }
        printfFileList();
    }

    private void move() {
        selectFile();
        if (fileSelected != null) {
            fileSelected.getPrev().setNext(null);
            File temp = fileSelected;
            selectFile();
            if (fileSelected != null) {
                File f = fileSelected;
                for (; f.getNext() != null; f = f.getNext()) {
                }
                temp.setPrev(f);
                f.setNext(temp);
            }
        }
    }

    private void delete() {
        selectFile();
        if (fileSelected != null) {
            fileBuffer = fileSelected;
            fileSelected.getPrev().setNext(null);
            fileManager.deleteFile(fileSelected);
            for (; fileSelected.getNext() != null; fileSelected = fileSelected.getNext()) {
                arrFile.remove(fileSelected);
            }
        }
    }

    private void select(File file) {
        MemoryCell memoryCell = file.getCell();
        if (memoryCell != null) {
            fileManager.selectFile(memoryCell);
        }
        if (file.getNext() != null) {
            if (file.isFolder()) {
                file = file.getNext();
                for (File f = file; f != null; f = f.getNext()) {
                    memoryCell = f.getCell();
                    if (memoryCell != null) {
                        fileManager.selectFile(memoryCell);
                    }
                }
            }
        }
    }
}
