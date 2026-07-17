import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("FCFS Java Implementation");

        // Collect N

        System.out.println("Enter the Number of Processes: ");
        int n = sc.nextInt();

        System.out.println("Number of Processes: " + n);

        // create arrays
        String[] process = new String[n];
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] completionTime = new int[n];
        int[] turnaroundTime = new int[n];
        int[] waitingTime = new int[n];

        // Collect Inputs 
        
        for(int i=0;i<n;i++){
            process[i] = "P" + (i+1);

            System.out.print("Enter Arrival Time of " + process[i] + ": ");
            arrivalTime[i] = sc.nextInt(); 
            System.out.print("Enter Burst Time of " + process[i] + ": ");
            burstTime[i] = sc.nextInt(); 
        }

        // Calculate CT
        completionTime[0] = arrivalTime[0] + burstTime[0];

        for(int i=1;i<n;i++){
            completionTime[i] = Math.max(completionTime[i-1], arrivalTime[i]) + burstTime[i];
        }

        // Calculate TAT
        for(int i=0;i<n;i++){
            turnaroundTime[i] = completionTime[i] - arrivalTime[i];
        }

        // Calculate WT
        for(int i=0;i<n;i++){
            waitingTime[i] = turnaroundTime[i] - burstTime[i];
        }

        // prints
        for(int i=0;i<n;i++){

            System.out.println(" Arrival Time of " + process[i] + " is : " + arrivalTime[i]);
            System.out.println(" Burst Time of " + process[i] + " is : " + burstTime[i]);
            System.out.println(" Completion Time of " + process[i] + " is : " + completionTime[i]);
            System.out.println(" Turnaround Time of " + process[i] + " is : " + turnaroundTime[i]);
            System.out.println(" Waiting Time of " + process[i] + " is : " + waitingTime[i]);


        }

        sc.close();

    }
}
