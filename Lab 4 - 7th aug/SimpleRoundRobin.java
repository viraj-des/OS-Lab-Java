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

        int quantum = 3; 
        int currentTime = 0;

        while (!queue.isEmpty()) {
            Process p = queue.poll();

            if (p.remainingTime > quantum) {
                p.remainingTime = p.remainingTime - quantum;
                currentTime = currentTime + quantum;
                System.out.println("Executed " + p.name + ". Total time: " + currentTime);
                queue.add(p); 
            } 

            else {
                currentTime = currentTime + p.remainingTime;
                p.remainingTime = 0;
                System.out.println("Executed " + p.name + ". Total time: " + currentTime);
                System.out.println("-> " + p.name + " finished!");
            }
        }
    }
}
