import java.util.*;

class Process {
    String name;
    int burstTime;
    int remainingTime;

    public Process(String name, int burstTime) {
        this.name = name;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}

public class SimpleRoundRobin {
    public static void main(String[] args) {

        Queue<Process> queue = new LinkedList<>();
        queue.add(new Process("P1", 10));
        queue.add(new Process("P2", 5));
        queue.add(new Process("P3", 8));

        int timeQuantum = 3;
        int currentTime = 0;

        while (!queue.isEmpty()) {
            Process p = queue.poll();

            int execTime = Math.min(p.remainingTime, timeQuantum);
            p.remainingTime -= execTime;
            currentTime += execTime;

            System.out.println("Executed " + p.name + " for " + execTime 
                + " units of time. Total time elapsed: " + currentTime);

            if (p.remainingTime > 0) {
                queue.add(p);
            } else {
                System.out.println("-> " + p.name + " finished!");
            }
        }
    }
}
