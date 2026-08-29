import java.util.*;

public class Main {

    static void printResult(String[] process, int[] arrivalTime, int[] burstTime,
                            int[] completionTime, int[] turnaroundTime, int[] waitingTime) {

        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");

        for (int i = 0; i < process.length; i++) {
            System.out.println(process[i] + "\t" + arrivalTime[i] + "\t"
                    + burstTime[i] + "\t" + completionTime[i] + "\t"
                    + turnaroundTime[i] + "\t" + waitingTime[i]);
        }

        double totalWT = 0;
        double totalTAT = 0;

        for (int i = 0; i < process.length; i++) {
            totalWT = totalWT + waitingTime[i];
            totalTAT = totalTAT + turnaroundTime[i];
        }

        System.out.println("\nAverage Waiting Time: " + totalWT / process.length);
        System.out.println("Average Turnaround Time: " + totalTAT / process.length);
    }

    static void fcfs(String[] process, int[] arrivalTime, int[] burstTime) {

        int n = process.length;

        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];

        completionTime[0] = arrivalTime[0] + burstTime[0];

        for (int i = 1; i < n; i++) {
            completionTime[i] =
                    Math.max(completionTime[i - 1], arrivalTime[i]) + burstTime[i];
        }

        for (int i = 0; i < n; i++) {
            turnaroundTime[i] = completionTime[i] - arrivalTime[i];
            waitingTime[i] = turnaroundTime[i] - burstTime[i];
        }

        System.out.println("\nFCFS Scheduling");
        printResult(process, arrivalTime, burstTime,
                completionTime, turnaroundTime, waitingTime);
    }

    static void sjf(String[] process, int[] arrivalTime, int[] burstTime) {

        int n = process.length;

        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];

        boolean[] completed = new boolean[n];

        int completedProcesses = 0;
        int time = 0;

        while (completedProcesses < n) {

            int index = -1;
            int shortestBurst = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {

                if (!completed[i] && arrivalTime[i] <= time) {

                    if (burstTime[i] < shortestBurst) {
                        shortestBurst = burstTime[i];
                        index = i;
                    }
                }
            }

            if (index == -1) {
                time++;
            } else {

                time = time + burstTime[index];

                completionTime[index] = time;
                turnaroundTime[index] =
                        completionTime[index] - arrivalTime[index];
                waitingTime[index] =
                        turnaroundTime[index] - burstTime[index];

                completed[index] = true;
                completedProcesses++;
            }
        }

        System.out.println("\nSJF Scheduling");
        printResult(process, arrivalTime, burstTime,
                completionTime, turnaroundTime, waitingTime);
    }

    static void priorityScheduling(String[] process, int[] arrivalTime,
                                    int[] burstTime, int[] priority) {

        int n = process.length;

        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];

        boolean[] completed = new boolean[n];

        int completedProcesses = 0;
        int time = 0;

        while (completedProcesses < n) {

            int index = -1;
            int highestPriority = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {

                if (!completed[i] && arrivalTime[i] <= time) {

                    if (priority[i] < highestPriority) {
                        highestPriority = priority[i];
                        index = i;
                    }
                }
            }

            if (index == -1) {
                time++;
            } else {

                time = time + burstTime[index];

                completionTime[index] = time;
                turnaroundTime[index] =
                        completionTime[index] - arrivalTime[index];
                waitingTime[index] =
                        turnaroundTime[index] - burstTime[index];

                completed[index] = true;
                completedProcesses++;
            }
        }

        System.out.println("\nPriority Scheduling");
        printResult(process, arrivalTime, burstTime,
                completionTime, turnaroundTime, waitingTime);
    }

    static void roundRobin(String[] process, int[] arrivalTime,
                           int[] burstTime, int quantum) {

        int n = process.length;

        int[] remainingTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];

        boolean[] added = new boolean[n];

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            remainingTime[i] = burstTime[i];
        }

        int completedProcesses = 0;
        int time = 0;

        while (completedProcesses < n) {

            for (int i = 0; i < n; i++) {

                if (!added[i] && arrivalTime[i] <= time) {
                    queue.add(i);
                    added[i] = true;
                }
            }

            if (queue.isEmpty()) {
                time++;
                continue;
            }

            int index = queue.remove();

            if (remainingTime[index] > quantum) {

                time = time + quantum;
                remainingTime[index] =
                        remainingTime[index] - quantum;

            } else {

                time = time + remainingTime[index];
                remainingTime[index] = 0;

                completionTime[index] = time;

                turnaroundTime[index] =
                        completionTime[index] - arrivalTime[index];

                waitingTime[index] =
                        turnaroundTime[index] - burstTime[index];

                completedProcesses++;
            }

            for (int i = 0; i < n; i++) {

                if (!added[i] && arrivalTime[i] <= time) {
                    queue.add(i);
                    added[i] = true;
                }
            }

            if (remainingTime[index] > 0) {
                queue.add(index);
            }
        }

        System.out.println("\nRound Robin Scheduling");
        printResult(process, arrivalTime, burstTime,
                completionTime, turnaroundTime, waitingTime);
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("CPU Scheduling Java Implementation");

        System.out.print("Enter the Number of Processes: ");
        int n = sc.nextInt();

        String[] process = new String[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] priority = new int[n];

        for (int i = 0; i < n; i++) {

            process[i] = "P" + (i + 1);

            System.out.print("Enter Arrival Time of " + process[i] + ": ");
            arrivalTime[i] = sc.nextInt();

            System.out.print("Enter Burst Time of " + process[i] + ": ");
            burstTime[i] = sc.nextInt();

            System.out.print("Enter Priority of " + process[i] + ": ");
            priority[i] = sc.nextInt();
        }

        System.out.println("\nChoose Scheduling Algorithm");
        System.out.println("1. FCFS");
        System.out.println("2. SJF");
        System.out.println("3. Round Robin");
        System.out.println("4. Priority Scheduling");

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        switch (choice) {

            case 1:
                fcfs(process, arrivalTime, burstTime);
                break;

            case 2:
                sjf(process, arrivalTime, burstTime);
                break;

            case 3:
                System.out.print("Enter Time Quantum: ");
                int quantum = sc.nextInt();

                roundRobin(process, arrivalTime, burstTime, quantum);
                break;

            case 4:
                priorityScheduling(process, arrivalTime, burstTime, priority);
                break;

            default:
                System.out.println("Invalid Choice");
        }

        sc.close();
    }
}
