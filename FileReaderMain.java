import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderMain {
    public static void main(String[] args) {
        List<Process> processes = readProcessesFromFile("processes.txt");
        if (processes.isEmpty()) {
            System.out.println("No processes found in the file.");
            return;
        }

        processes.sort((p1, p2) -> Integer.compare(p1.arrivalTime, p2.arrivalTime));

        for (Process process : processes) {
            ProcessThread processThread = new ProcessThread(process);
            processThread.start();
        }
    }

    public static List<Process> readProcessesFromFile(String filename) {
        List<Process> processes = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line = br.readLine(); 
            while ((line = br.readLine()) != null) {
                String[] parts = line.trim().split("\\s+");
                if (parts.length == 4) {
                    processes.add(new Process(
                        Integer.parseInt(parts[0]),  // PID
                        Integer.parseInt(parts[1]),  // Arrival Time
                        Integer.parseInt(parts[2]),  // Burst Time
                        Integer.parseInt(parts[3])   // Priority
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return processes;
    }
}

class ProcessThread extends Thread {
    private final Process process;

    public ProcessThread(Process process) {
        this.process = process;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(process.arrivalTime * 1000); // Convert to milliseconds
            System.out.println("Process " + process.pid + " started at time " + process.arrivalTime);
            Thread.sleep(process.burstTime * 1000); // Simulate the CPU burst time
            System.out.println("Process " + process.pid + " finished after " + process.burstTime + " seconds.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
