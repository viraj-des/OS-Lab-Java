#Implementation of FCFS in python

#Collect Inputs

process = []
arrivalTime = []
burstTime = []

n = int(input("Enter the number of Processes ( N ) :"))

for i in range(1, n+1):
    process.append("P" + str(i))
    at = int(input(f"Arrival time of P{i}: "))
    bt = int(input(f"Burst Time of P{i}: "))
    arrivalTime.append(at)
    burstTime.append(bt)

print(f"Processes in the system: {process}")
print(f"Arrival Time of each process: {arrivalTime}")
print(f"Burst Time of each process: {burstTime}")


ct = [0] * n
ct[0] = arrivalTime[0] + burstTime[0]

for i in range(1, n):
    ct[i] = max(ct[i-1], arrivalTime[i]) + burstTime[i]

print("Completion times: " + str(ct))


for i in range(1, n):
    ct[i] = max(ct[i-1], arrivalTime[i]) + burstTime[i]


# Turnaround time TAT = CT - AT
turnaroundTime = [0] * n
turnaroundTime[0] = arrivalTime[0] + burstTime[0]

for i in range(1, n):
    turnaroundTime[i] = ct[i] - arrivalTime[i]

print("Turnatound times: " + str(turnaroundTime))

# WT = TAT - BT
wt = [0] * n
for i in range(1, n):
    wt[i] = turnaroundTime[i] - burstTime[i]

print("Waiting times: " + str(wt))