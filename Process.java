public class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int priority;


    public Process(int pid, int arrivalTime, int burstTime, int priority) {
        this.pid = pid;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
    }
}
