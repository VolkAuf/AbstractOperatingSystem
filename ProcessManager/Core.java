
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Core {

    private final Queue<Process> queueProcess;
    private final Queue<Process> queueProcessBlocking;
    private int timeProcess;
    private int timeProcessBlocking;
    private final int timeDevice = 4;
    private final Random random;

    Core() {
        random = new Random();
        queueProcess = new LinkedList<>();
        queueProcessBlocking = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            startProcess(i, random.nextInt(10) + 5, random.nextBoolean());
        }
    }

    public void startProcess(int pID, int TimeProcess, boolean deviceConnect) {
        queueProcess.add(new Process(pID, TimeProcess, deviceConnect));
        queueProcessBlocking.add(new Process(pID, TimeProcess, deviceConnect));
    }

    public void layoutProcess() {
        System.out.println(">>>>>");
        Process temp = queueProcess.poll();
        while (temp != null) {
            System.out.println("*-----*");
            System.out.println("Process №" + temp.getPID() + " started");
            System.out.println("Process time: " + temp.getTimeProcess());
            if (temp.isDeviceConnection()) {
                System.out.println("---");
                System.out.println("Process " + temp.getPID() + " works with the device");
                System.out.println("Planner stopped");
                timeProcess += timeDevice;
                System.out.println("Planner continued to run");
                System.out.println("---");
            }
            System.out.println("Process №" + temp.getPID() + " finished");
            timeProcess += temp.getTimeProcess();
            temp = queueProcess.poll();
            System.out.println("*-----*\n");
        }
        System.out.println("Time without blocking: " + timeProcess);
        System.out.println(">>>>>\n");
    }

    public void layoutProcessBlocking() {
        System.out.println("<<<<<");
        Process temp = queueProcessBlocking.poll();
        while (temp != null) {
            System.out.println("*-----*");
            if (temp.isLock()) {
                timeProcessBlocking += 1;
                if (timeProcessBlocking - temp.getTimeBlocking() > timeDevice) {
                    System.out.println("---");
                    System.out.println("Process: " + temp.getPID() + " unlocked");
                    System.out.println("Process: " + temp.getPID() + " finished");
                    System.out.println("---");
                    timeProcessBlocking += temp.getTimeProcess();
                } else {
                    queueProcessBlocking.add(temp);
                }
                temp = queueProcessBlocking.poll();
                continue;
            }
            System.out.println("Process " + temp.getPID() + " started");
            if (temp.isDeviceConnection()) {
                System.out.println("Process " + temp.getPID() + " works with the device");
                System.out.println("Process is blocked");
                temp.setLock(true);
                temp.setTimeBlocking(timeProcessBlocking);
                queueProcessBlocking.add(temp);
            } else {
                timeProcessBlocking += temp.getTimeProcess();
                for (Process process : queueProcessBlocking) {
                    if (process.isLock()) {
                        if (timeProcessBlocking - process.getTimeBlocking() > timeDevice) {
                            System.out.println("Process " + process.getPID() + " has finished works with the device, continue work");
                            System.out.println("Process " + process.getPID() + " finished");
                            timeProcessBlocking += process.getTimeProcess();
                            queueProcessBlocking.remove(process);
                            break;
                        }
                    }
                }
                System.out.println("Process " + temp.getPID() + " finished");
            }
            temp = queueProcessBlocking.poll();
            System.out.println("*-----*");
        }
        System.out.println("Time with blocking: " + timeProcessBlocking);
        System.out.println("<<<<<");
    }
}
