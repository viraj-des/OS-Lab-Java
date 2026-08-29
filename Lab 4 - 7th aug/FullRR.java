import java.util.*;

class Process {
    String name;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;

    public Process(String name, int arrivalTime, int burstTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class FullRR {
    public static void main(String[] args) {
        List<Process> processList = new ArrayList<>();
        processList.add(new Process("P1", 0, 10));
        processList.add(new Process("P2", 1, 5));
        processList.add(new Process("P3", 2, 8));

        // Sort processes by arrival time initially
        processList.sort(Comparator.comparingInt(p -> p.arrivalTime));

        Queue<Process> queue = new LinkedList<>();
        int currentTime = 0;
        int completed = 0;
        int n = processList.size();
        
        // Track whether a process is already added to the queue to avoid duplicates
        boolean[] inQueue = new boolean[n];

        // Add the first arriving process
        queue.add(processList.get(0));
        inQueue[0] = true;
        currentTime = processList.get(0).arrivalTime;

        int quantum = 3;

        while (!queue.isEmpty()) {
            Process p = queue.poll();

            // Execute for time quantum or remaining time
            if (p.remainingTime > quantum) {
                currentTime += quantum;
                p.remainingTime -= quantum;
            } else {
                currentTime += p.remainingTime;
                p.remainingTime = 0;
                p.completionTime = currentTime;
                p.turnaroundTime = p.completionTime - p.arrivalTime;
                p.waitingTime = p.turnaroundTime - p.burstTime;
                completed++;
                System.out.println("-> " + p.name + " finished at time " + currentTime);
            }

            // Check for newly arrived processes during this time slice and add to queue
            for (int i = 0; i < n; i++) {
                Process nextProc = processList.get(i);
                if (nextProc.arrivalTime <= currentTime && !inQueue[i] && nextProc.remainingTime > 0) {
                    queue.add(nextProc);
                    inQueue[i] = true;
                }
            }

            // If the current process is not finished, re-add it to the queue
            if (p.remainingTime > 0) {
                queue.add(p);
            } else if (queue.isEmpty()) {
                // If queue is empty but processes remain (CPU idle gap), find next available
                for (int i = 0; i < n; i++) {
                    if (!inQueue[i]) {
                        queue.add(processList.get(i));
                        inQueue[i] = true;
                        currentTime = processList.get(i).arrivalTime;
                        break;
                    }
                }
            }
        }

        // Print Results Table
        System.out.println("\nProcess\tArrival\tBurst\tCompletion\tTAT\tWAT");
        double totalTAT = 0, totalWAT = 0;
        for (Process p : processList) {
            totalTAT += p.turnaroundTime;
            totalWAT += p.waitingTime;
            System.out.println(p.name + "\t" + p.arrivalTime + "\t" + p.burstTime + "\t" + 
                               p.completionTime + "\t\t" + p.turnaroundTime + "\t" + p.waitingTime);
        }

        System.out.printf("\nAverage Turnaround Time: %.2f\n", (totalTAT / n));
        System.out.printf("Average Waiting Time: %.2f\n", (totalWAT / n));
    }
}
