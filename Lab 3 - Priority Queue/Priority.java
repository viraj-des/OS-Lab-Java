public class Priority{

    public static void main(String[] args) {

        System.out.println("--- Pirority Queue Preemptive Implementation ---");

        // Assume Lower Number = Higher Priority
        // Shortforms:
        // CT = Completion Time
        // AT = Errival Time
        // BT = Burst Time
        // TAT = Turnaround Time
        // RT = Response Time ( FTS - AT)

        // INPUT

        int n = 5;

        int[] at = {0, 1, 2, 3, 4};
        int[] bt = {3, 4, 6, 4, 2};
        int[] priority = {3, 2, 4, 6, 10};

        int[] rem = new int[n];
        int[] ct = new int[n];
        int[] tat = new int[n];
        int[] wt = new int[n];
        int[] rt = new int[n];
        boolean[] started = new boolean[n];
        boolean[] finished = new boolean[n];

        for(int i = 0; i<n; i++){
            rem[i] = bt[i];
            rt[i] = -1;
        }

        int sysTime = 0;
        int completed = 0;

        while (completed < n){
            int idx = -1;
            
            for(int i = 0; i < n; i++){
                if(at[i] <= sysTime && !finished[i] && rem[i]>0){
                    if(idx == -1){
                        idx=i;
                    }
                    else{
                        if(priority[i] < priority[idx]){
                            idx=i;
                        }
                        else if(priority[i] == priority[idx]){
                        if (at[i] < at[idx]) {
                                idx = i;
                        } else if (at[i] == at[idx] && i < idx) {
                                idx = i;
                            }       
                    }
                    }
                    
                }
            }

            if (idx == -1) {
                sysTime++;
                continue;
            }

            if (!started[idx]) {
                rt[idx] = sysTime - at[idx];
                started[idx] = true;
            }

            rem[idx]--;
            sysTime++;

            if(rem[idx] == 0){
                finished[idx] = true;
                completed++;


                ct[idx] = sysTime;
                tat[idx] = ct[idx] - at[idx];
                wt[idx] = tat[idx] - bt[idx];
            }

            // Display results
            System.out.println("\nPID\tAT\tBT\tPR\tCT\tTAT\tWT\tRT");
            for (int i = 0; i < n; i++) {
                System.out.println(
                    "P" + (i + 1) + "\t" +
                    at[i] + "\t" +
                    bt[i] + "\t" +
                    priority[i] + "\t" +
                    ct[i] + "\t" +
                    tat[i] + "\t" +
                    wt[i] + "\t" +
                    rt[i]
                );
            }
        }
    }
}