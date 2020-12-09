
class Process {
    private final int PID;
    private final int timeProcess;
    private final boolean deviceConnection;
    private int timeBlocking;
    private boolean lock;

    Process(int PID, int timeProcess, boolean deviceConnection) {
        this.PID = PID;
        this.timeProcess = timeProcess;
        this.deviceConnection = deviceConnection;
    }

    public int getPID() {
        return PID;
    }

    public int getTimeProcess() {
        return timeProcess;
    }

    public boolean isDeviceConnection() {
        return deviceConnection;
    }

    public int getTimeBlocking() {
        return timeBlocking;
    }

    public void setTimeBlocking(int timeBlocking) {
        this.timeBlocking = timeBlocking;
    }

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }
}
