// Philosopher.java
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Philosopher extends Thread {
    private final int id;
    private Fork leftFork;
    private Fork rightFork;
    private Table table;
    public int tableNumber;
    public boolean isWaitingForRightFork = false;
    public boolean hasLeftFork = false;
    private final Lock lock = new ReentrantLock(); // To synchronize philosopher movement
    private static final Random random = new Random();

    public Philosopher(int id, int tableNumber, Fork leftFork, Fork rightFork, Table table) {
        this.id = id;
        this.tableNumber = tableNumber; 
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.table = table;
    }
    @Override
    public void run() {
        try {
            while (!Table.isTableDeadLocked[tableNumber]) {
                think();
                hungry();
                while(!pickUpForks()){
                    if(Table.isTableDeadLocked[tableNumber]) return;
                }
                eat();
                putDownForks();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Table: " + (tableNumber + 1) + " -->" + " Philosopher " + (char)('A' + id) + " is thinking.");
        Thread.sleep(random.nextInt(10000)); // Simulate thinking (0 to 10 seconds)
    }            
    private void hungry() {
        System.out.println("Table: " + (tableNumber + 1) + " -->" + " Philosopher " + (char)('A' + id) + " is hungry.");
    }

    private boolean pickUpForks() throws InterruptedException {
        // Try to pick up the left fork first
        if(hasLeftFork){
            System.out.println("Table: " + (tableNumber + 1) + " -->" + " Philosopher " + (char)('A' + id) + " is trying to pick the right fork");
        }
        else if(!leftFork.pickUp()) {
            System.out.println("Table: " + (tableNumber + 1) + " -->"  + " Philosopher "  + (char)('A' + id) + " failed to pick the left fork");
            return false;   
        }
        if(!hasLeftFork){ 
            System.out.println("Table: " + (tableNumber + 1) + " -->"  + " Philosopher " + (char)('A' + id) + " picked the left fork");
            hasLeftFork = true;
        }

        Thread.sleep(4000); // Wait for 4 seconds, then try to pick up the right fork 
        if (!rightFork.pickUp()) {

            isWaitingForRightFork = true;
            if (table.isDeadlocked()) {
                System.out.println("Deadlock detected at table " + (tableNumber + 1));
                if(tableNumber == 5){
                    lock.lock();
                    try{              
                        System.out.println("Table: " + (tableNumber + 1) + " -->" + " Philosopher " + (char)('A' + Table.lastPhilosopherAtSixthTable) + " was the last to move to the Sixth table");
                        DiningPhilosophers.deadLockAtLastTable = true;
                        Table.isTableDeadLocked[tableNumber] = true;

                        long endTime = System.currentTimeMillis();
                        long totalTime = endTime - DiningPhilosophers.startTime;
                        System.out.println("Total time taken by the program: " + totalTime / 1000 + " seconds");
                        System.exit(0);
                    }
                    finally{
                        lock.unlock();
                    }
                }
                table.moveToSixthTable(this); // Notify the table about the move
                return false;
            }

            System.out.println("Table: " + (tableNumber + 1) + " -->" + " Philosopher " + (char)('A' + id) + " failed to pick the right fork");
           
            return false;
        }

        System.out.println("Table: " + (tableNumber + 1) + " -->" + " Philosopher " + (char)('A' + id) + " picked up both forks.");
        return true;
    }

    private void eat() throws InterruptedException {
        System.out.println("Table: " + (tableNumber + 1) + " -->" + " Philosopher " + (char)('A' + id) + " is eating.");
        Thread.sleep(random.nextInt(5000)); // Simulate eating (0 to 5 seconds)
    }

    private void putDownForks() {
        leftFork.putDown();
        rightFork.putDown();
        hasLeftFork = false;
        isWaitingForRightFork = false;
        System.out.println("Table: " + (tableNumber + 1) + " -->" + " Philosopher " + (char)('A' + id) + " put down both forks.");
    }

    public int getPhilosopherId() {
        return id;
    }
    public boolean isWaitingForRightFork() {
        return isWaitingForRightFork;
    }
    public void updateInfo(int pos){
        leftFork.putDown();
        Table.isTableDeadLocked[tableNumber] = true;
        leftFork = FillTable.forks[5][pos];
        rightFork = FillTable.forks[5][(pos + 1) % FillTable.philosophersPerTable];
        table = FillTable.tables[5];
        tableNumber = 5;
        isWaitingForRightFork = false;
        hasLeftFork = false;
    }
}
